package com.apps.youtube.api.features.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.apps.youtube.api.R

@Composable
fun HomeScreen(
    state: HomeScreenState,
    onSignInClick: () -> Unit = {}
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold { contentPadding ->
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .background(
                    color = Color.White
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
                    containerColor = Color.Black,
                    contentColor = Color.White,
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
}