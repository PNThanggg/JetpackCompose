package com.apps.youtube.api.features.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.apps.youtube.api.R

@PreviewLightDark()
@Composable
fun LoginScreen(
    onSignInClick: () -> Unit = {},
) {
    val isDarkTheme = isSystemInDarkTheme()

    Box(
        modifier = Modifier
            .background(
                if (isDarkTheme) Color.Black else Color.White
            )
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = {
                onSignInClick.invoke()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isDarkTheme) Color.White else Color.Black,
                contentColor = if (isDarkTheme) Color.Black else Color.White,
            )
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp),
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = stringResource(id = R.string.app_name)
            )
            Text(text = "Sign in with Google", modifier = Modifier.padding(6.dp))
        }
    }
}
