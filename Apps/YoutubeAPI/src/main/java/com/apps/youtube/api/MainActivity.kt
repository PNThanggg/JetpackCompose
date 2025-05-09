package com.apps.youtube.api

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apps.youtube.api.datastore.repository.LocalPreferencesRepository
import com.apps.youtube.api.features.home.HomeScreen
import com.apps.youtube.api.features.login.LoginScreen
import com.apps.youtube.api.features.login.LoginViewModel
import com.apps.youtube.api.features.splash.SplashScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modules.core.theme.JetpackComposeTheme
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val scope: Scope = Scope("https://www.googleapis.com/auth/youtube.readonly")

    val gso: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestServerAuthCode(getString(R.string.google_client_id)).requestEmail()
            .requestScopes(scope).build()
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignIn.getClient(this, gso)
    }

    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var localPreferencesRepository: LocalPreferencesRepository

    val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        signInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    try {
                        val data = result.data
                        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                        val account = task.getResult(ApiException::class.java)
                        val token = account?.idToken
                        Timber.tag(TAG).d("Đăng nhập thành công: $token")

                        exchangeAuthCodeForToken(account)
                    } catch (e: ApiException) {
                        Timber.tag(TAG).e(e, "Đăng nhập thất bại: ${e.statusCode}")
                    }
                } else {
                    Timber.tag(TAG).e("Kết quả không thành công: ${result.resultCode}")
                }
            }

        setContent {
            JetpackComposeTheme {
                val navController = rememberNavController()
                val loginState = loginViewModel.loginState.collectAsState()
                LaunchedEffect(loginState) {
                    when (loginState) {
                        is LoginViewModel.LoginState.Success -> {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }

                        else -> {}
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = Screen.Splash.route,
                ) {
                    composable(
                        Screen.Splash.route,
                        enterTransition = {
                            slideIntoContainer(
                                AnimatedContentTransitionScope.SlideDirection.Left,
                                animationSpec = tween(700)
                            )
                        },
                        exitTransition = {
                            slideOutOfContainer(
                                AnimatedContentTransitionScope.SlideDirection.Right,
                                animationSpec = tween(700)
                            )
                        },
                    ) {
                        val lastSignedInAccount =
                            GoogleSignIn.getLastSignedInAccount(this@MainActivity)

                        SplashScreen(
                            navController = navController, lastLogin = lastSignedInAccount != null
                        )
                    }

                    composable(Screen.Login.route) {
                        LoginScreen(
                            onSignInClick = {
                                val signInIntent = googleSignInClient.signInIntent
                                signInLauncher.launch(signInIntent)
                            })
                    }

                    composable(Screen.Home.route) {
                        HomeScreen()
                    }
                }
            }
        }
    }

    private fun exchangeAuthCodeForToken(account: GoogleSignInAccount) {
        if (account.serverAuthCode == null) {
            Toast.makeText(this@MainActivity, "Auth code is null", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()

                val formBody: RequestBody =
                    FormBody.Builder().add("code", account.serverAuthCode!!).add(
                        "client_id", BuildConfig.YOUTUBE_APP_CLIENT_ID
                    ).add(
                        "client_secret", BuildConfig.YOUTUBE_APP_CLIENT_SECRET
                    ).add(
                        "redirect_uri", "urn:ietf:wg:oauth:2.0:oob"
                    ).add("grant_type", "authorization_code").build()

                val request: Request =
                    Request.Builder().url("https://oauth2.googleapis.com/token").post(formBody)
                        .build()

                val response: Response = client.newCall(request).execute()
                val responseData: String? = response.body!!.string()

                Timber.tag(TAG).d("$responseData")
                responseData?.let {
                    val jsonObject = JSONObject(it)
                    val accessToken = jsonObject.getString("access_token")

                    localPreferencesRepository.updateApplicationPreferences {
                        it.copy(
                            accessToken = accessToken
                        )
                    }

                    loginViewModel.onLoginSuccess()
                } ?: run {
                    Timber.tag(TAG).e("Response data is null")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")

    data object Login : Screen("login")

    data object Home : Screen("home")
}