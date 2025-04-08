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
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apps.youtube.api.features.home.HomeScreen
import com.apps.youtube.api.features.home.HomeViewModel
import com.apps.youtube.api.features.login.LoginScreen
import com.apps.youtube.api.features.splash.SplashScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import dagger.hilt.android.AndroidEntryPoint
import modules.core.theme.JetpackComposeTheme
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import timber.log.Timber

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

                        handleSignInResult(account)
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
                        val viewModel = viewModel<HomeViewModel>()
                        val state = viewModel.state.collectAsStateWithLifecycle()

                        val context = LocalContext.current

                        LaunchedEffect(key1 = state.value.isSignInSuccessful) {
                            if (state.value.isSignInSuccessful) {
                                Toast.makeText(
                                    context, "Sign in successful", Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        HomeScreen()
                    }
                }
            }
        }
    }

    private fun handleSignInResult(account: GoogleSignInAccount) {
        val accessToken = account.serverAuthCode // Code để lấy Access Token
        val email = account.email

        Timber.tag(TAG).d("Email: $email")
        Timber.tag(TAG).d("Server Auth Code: $accessToken")

        // Gửi auth code này đến server hoặc tự đổi lấy access token
        exchangeAuthCodeForToken(accessToken)
    }

    private fun exchangeAuthCodeForToken(authCode: String?) {
        if (authCode == null) {
            Toast.makeText(this@MainActivity, "Auth code is null", Toast.LENGTH_SHORT).show()
            return
        }

        Thread(Runnable {
            try {
                val client = OkHttpClient()

                val formBody: RequestBody = FormBody.Builder().add("code", authCode).add(
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
                    getSubscriptions(accessToken)
                } ?: run {
                    Timber.tag(TAG).e("Response data is null")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
    }

    private fun getSubscriptions(accessToken: String?) {
        Thread(Runnable {
            try {
                val client = OkHttpClient()
                val request: Request = Request.Builder()
                    .url("https://www.googleapis.com/youtube/v3/subscriptions?part=snippet&mine=true")
                    .addHeader("Authorization", "Bearer $accessToken").build()

                val response = client.newCall(request).execute()
                val responseData: String? = response.body!!.string()
                Timber.tag(TAG).d("$responseData")
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }).start()
    }
}

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")

    data object Login : Screen("login")

    data object Home : Screen("home")
}