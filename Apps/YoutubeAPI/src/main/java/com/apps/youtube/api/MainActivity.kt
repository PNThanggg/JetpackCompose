package com.apps.youtube.api

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.apps.youtube.api.features.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import modules.core.theme.JetpackComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    val gso: GoogleSignInOptions by lazy {
//        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(
//            getString(R.string.google_client_id)
//        ).requestEmail().build()
//    }
//
//    private val googleSignInClient: GoogleSignInClient by lazy {
//        GoogleSignIn.getClient(this, gso)
//    }
//
//    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

//        signInLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//                if (result.resultCode == RESULT_OK) {
//                    try {
//                        val data = result.data
//                        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//                        val account = task.getResult(ApiException::class.java)
//                        val token = account?.idToken
//                        Timber.tag(TAG).d("Đăng nhập thành công: $token")
//                    } catch (e: ApiException) {
//                        Timber.tag(TAG).e(e, "Đăng nhập thất bại: ${e.statusCode}")
//                    }
//                } else {
//                    Timber.tag(TAG).e("Kết quả không thành công: ${result.resultCode}")
//                }
//            }

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
                        SplashScreen(
                            navController = navController,
                        )
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
}

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")

    data object Home : Screen("home")
}