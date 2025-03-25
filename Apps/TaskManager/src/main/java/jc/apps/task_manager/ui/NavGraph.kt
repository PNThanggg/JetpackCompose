package jc.apps.task_manager.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import jc.apps.task_manager.R
import jc.apps.task_manager.ui.calender.CalenderScreen
import jc.apps.task_manager.ui.home.HomeScreen
import jc.apps.task_manager.ui.notifiaction.NotificationScreen
import jc.apps.task_manager.ui.setting.SettingScreen
import modules.bottombar.smooth.BlueTint
import modules.bottombar.smooth.BottomBarProperties
import modules.bottombar.smooth.SmoothAnimationBottomBar
import modules.bottombar.smooth.SmoothBottomBarItem

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val currentIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    val bottomNavigationItems = listOf<SmoothBottomBarItem>(
        SmoothBottomBarItem(
            AppDestinations.HOME_ROUTE, "Home", R.drawable.ic_home
        ), SmoothBottomBarItem(
            AppDestinations.CALENDER_ROUTER, "Calender", R.drawable.ic_home
        ), SmoothBottomBarItem(
            AppDestinations.NOTIFICATION_ROUTER, "Notification", R.drawable.ic_home
        ), SmoothBottomBarItem(
            AppDestinations.SETTING_ROUTE, "Setting", R.drawable.ic_home
        )
    )

    Scaffold(
        bottomBar = {
            SmoothAnimationBottomBar(
                navController,
                bottomNavigationItems,
                initialIndex = currentIndex,
                bottomBarProperties = BottomBarProperties(
                    backgroundColor = Color.White,
                    indicatorColor = Color.Black.copy(alpha = 0.2F),
                    iconTintColor = BlueTint,
                    iconTintActiveColor = Color.White,
                    textActiveColor = Color.Black,
                    cornerRadius = 18.dp,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                ),
                onSelectItem = {
                    Log.i("SELECTED_ITEM", "Selected Item ${it.name}")
                },
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.HOME_ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = AppDestinations.HOME_ROUTE,
            ) { navBackStackEntry ->
                HomeScreen()
            }

            composable(
                route = AppDestinations.CALENDER_ROUTER,
            ) { navBackStackEntry ->
                CalenderScreen()
            }

            composable(
                route = AppDestinations.SETTING_ROUTE,
            ) { navBackStackEntry ->
                SettingScreen()
            }

            composable(
                route = AppDestinations.NOTIFICATION_ROUTER,
            ) { navBackStackEntry ->
                NotificationScreen()
            }
        }
    }
}