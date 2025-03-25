package jc.apps.lol.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
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

                NavHost(navController = navController, startDestination = Splash) {
                    composable<Splash> {
                        SplashScreen(
                            navController = navController,
                        )
                    }

                    composable<Home> {
                        val viewModel by viewModels<HomeViewModel>()

                        val state = viewModel.state.collectAsStateWithLifecycle()

                        HomeScreen(
                            state = state.value,
                            onValueChange = viewModel::onSearchTextChange,
                            navigate = { name ->
//                                navController.navigate(ChampionDetails(name))
                            },
                        )
                    }
                }
            }
        }
    }
}