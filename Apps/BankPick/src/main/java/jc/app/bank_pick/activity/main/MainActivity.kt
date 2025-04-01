package jc.app.bank_pick.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import jc.app.bank_pick.activity.authentication.AuthenticationActivity
import jc.app.bank_pick.activity.intro.IntroActivity
import jc.app.bank_pick.activity.intro.IntroScreen
import jc.app.bank_pick.activity.main.main.MainScreen
import kotlinx.coroutines.launch
import modules.core.theme.JetpackComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JetpackComposeTheme {
                MainScreen()
            }
        }
    }
}
