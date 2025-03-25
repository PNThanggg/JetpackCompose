package com.apps.youtube.api.navigate

import android.app.Activity.RESULT_OK
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.apps.youtube.api.GoogleAuthClient
import com.apps.youtube.api.features.home.HomeScreen
import com.apps.youtube.api.features.home.HomeViewModel
import com.apps.youtube.api.features.splash.SplashScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    googleAuthClient: GoogleAuthClient,
    lifecycleScope: LifecycleCoroutineScope,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(
            Screen.Splash.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700)
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

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
            ) { result ->
                if (result.resultCode == RESULT_OK) {
                    lifecycleScope.launch {
                        val signInResult = googleAuthClient.signInWithIntent(
                            intent = result.data ?: return@launch
                        )
                        viewModel.onSignInResult(signInResult)
                    }
                }
            }

            val context = LocalContext.current

            LaunchedEffect(key1 = state.value.isSignInSuccessful) {
                if (state.value.isSignInSuccessful) {
                    Toast.makeText(
                        context, "Sign in successful", Toast.LENGTH_LONG
                    ).show()
                }
            }

            HomeScreen(
                state = state.value, onSignInClick = {
                    lifecycleScope.launch {
                        val signInIntentSender = googleAuthClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                })
        }
    }
}