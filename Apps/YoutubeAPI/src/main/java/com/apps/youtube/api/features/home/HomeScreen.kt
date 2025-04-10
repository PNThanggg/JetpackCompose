package com.apps.youtube.api.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@PreviewLightDark()
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomNavScreen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(BottomNavScreen.Home.route) {
                HomeTabContent()
            }

            composable(BottomNavScreen.Search.route) {
                SearchTabContent()
            }

            composable(BottomNavScreen.Notifications.route) {
                NotificationsTabContent()
            }

            composable(BottomNavScreen.Profile.route) {
                ProfileTabContent()
            }
        }
    }
}

sealed class BottomNavScreen(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavScreen("home_tab", Icons.Default.Home, "Home")
    object Search : BottomNavScreen("search_tab", Icons.Default.Search, "Search")
    object Notifications :
        BottomNavScreen("notifications_tab", Icons.Default.Notifications, "Notifications")

    object Profile : BottomNavScreen("profile_tab", Icons.Default.Person, "Profile")
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Search,
        BottomNavScreen.Notifications,
        BottomNavScreen.Profile
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = index == selectedItemIndex,
                onClick = {
                    selectedItemIndex = index

                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

// Các composable cho từng tab
@Composable
fun HomeTabContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Tab")
    }
}

@Composable
fun SearchTabContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Search Tab")
    }
}

@Composable
fun NotificationsTabContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Notifications Tab")
    }
}

@Composable
fun ProfileTabContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Profile Tab")
    }
}