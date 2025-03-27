package jc.apps.lol.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import jc.apps.lol.presentation.detail.ChampionDetailsScreen
import jc.apps.lol.presentation.detail.ChampionDetailsViewModel
import jc.apps.lol.presentation.home.HomeScreen
import jc.apps.lol.presentation.home.HomeViewModel
import jc.apps.lol.presentation.splash.SplashScreen
import modules.core.theme.JetpackComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = AppRouter.Splash.route) {
                    composable(AppRouter.Splash.route) {
                        SplashScreen(
                            navController = navController,
                        )
                    }

                    composable(AppRouter.Home.route) {
                        val viewModel by viewModels<HomeViewModel>()

                        val state = viewModel.state.collectAsStateWithLifecycle()

                        HomeScreen(
                            state = state.value,
                            onValueChange = viewModel::onSearchTextChange,
                            navigate = { name ->
                                navController.navigate(AppRouter.Detail.createRoute(name))
                            },
                        )
                    }

                    composable(
                        route = AppRouter.Detail.route,
                        arguments = listOf(navArgument("name") { type = NavType.StringType })
                    ) { backStackEntry ->
                        Log.d("TAG", "onCreate: ${backStackEntry.arguments?.getString("name")}")
                        val name = backStackEntry.arguments?.getString("name")
                        val viewModel by viewModels<ChampionDetailsViewModel>()

                        name?.let {
                            LaunchedEffect(Unit) {
                                viewModel.loadChampion(it)
                            }
                        }

                        viewModel.champion.value?.let {
                            ChampionDetailsScreen(champion = it)
                        }
                    }
                }
            }
        }
    }
}