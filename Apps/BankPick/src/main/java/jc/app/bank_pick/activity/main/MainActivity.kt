package jc.app.bank_pick.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jc.app.bank_pick.activity.main.cards.MyCardsScreen
import jc.app.bank_pick.activity.main.home.HomeScreen
import jc.app.bank_pick.activity.main.setting.SettingsScreen
import jc.app.bank_pick.activity.main.statistics.StatisticsScreen
import modules.core.theme.JetpackComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JetpackComposeTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(navController)
                    },
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = BottomNavItem.Home.route
                    ) {
                        composable(BottomNavItem.Home.route) { HomeScreen() }

                        composable(BottomNavItem.MyCards.route) { MyCardsScreen() }

                        composable(BottomNavItem.Statistics.route) { StatisticsScreen() }

                        composable(BottomNavItem.Settings.route) { SettingsScreen() }
                    }
                }
            }
        }
    }
}
