package com.apps.youtube.api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.apps.youtube.api.navigate.AppNavigation
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint
import modules.core.theme.JetpackComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val googleAuthClient by lazy {
        GoogleAuthClient(
            context = this@MainActivity, oneTapClient = Identity.getSignInClient(this@MainActivity)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()

        lifecycleScope

        setContent {
            JetpackComposeTheme {
                AppNavigation(
                    googleAuthClient = googleAuthClient,
                    lifecycleScope = lifecycleScope,
                )
            }
        }
    }
}