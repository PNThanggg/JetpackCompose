package jc.app.bank_pick.activity.authentication

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import modules.core.theme.JetpackComposeTheme

class AuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            JetpackComposeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = AuthenticationScreen.SignIn.route,
                ) {
                    composable(AuthenticationScreen.SignIn.route) {
                        SignInScreen()
                    }

                    composable(AuthenticationScreen.SignUp.route) {
                        SignUpScreen()
                    }
                }
            }
        }
    }
}

sealed class AuthenticationScreen(
    val route: String,
) {
    object SignIn : AuthenticationScreen("sign_in_screen")

    object SignUp : AuthenticationScreen("sign_up_screen")
}