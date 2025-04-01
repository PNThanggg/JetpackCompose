package jc.app.bank_pick.activity.intro

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import jc.app.bank_pick.activity.authentication.AuthenticationActivity
import jc.app.bank_pick.datastore.repository.PreferencesRepository
import kotlinx.coroutines.launch
import modules.core.theme.JetpackComposeTheme
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {
    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            JetpackComposeTheme {
                IntroScreen(
                    nextScreen = {
                        lifecycleScope.launch {
                            preferencesRepository.updateApplicationPreferences {
                                it.copy(
                                    firstLaunch = false
                                )
                            }
                        }

                        Intent(this@IntroActivity, AuthenticationActivity::class.java).also {
                            startActivity(it)
                            finish()
                        }
                    },
                )
            }
        }
    }
}