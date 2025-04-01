package jc.app.bank_pick.activity.splash

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import jc.app.bank_pick.activity.main.MainActivity
import jc.app.bank_pick.activity.authentication.AuthenticationActivity
import jc.app.bank_pick.activity.intro.IntroActivity
import jc.app.bank_pick.datastore.repository.PreferencesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import modules.core.theme.JetpackComposeTheme
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        lifecycleScope.launch {
            delay(500)

            preferencesRepository.applicationPreferences.collect { prefs ->
                if (prefs.firstLaunch) {
                    startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                } else {
                    if (prefs.isLogin) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    } else {
                        startActivity(
                            Intent(
                                this@SplashActivity, AuthenticationActivity::class.java
                            )
                        )
                    }
                }
                finish()
            }
        }

        setContent {
            JetpackComposeTheme {
                SplashScreen()
            }
        }
    }
}