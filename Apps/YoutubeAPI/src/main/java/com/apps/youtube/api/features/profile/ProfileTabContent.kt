package com.apps.youtube.api.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.apps.youtube.api.MainViewModel

@Preview()
@Composable
fun ProfileTabContent(
    mainViewModel: MainViewModel = hiltViewModel<MainViewModel>(),
) {
    val channelState = mainViewModel.channel.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        mainViewModel.fetchProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        when {
            channelState.value == null -> {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Loading profile...")
            }

            else -> {
                channelState.value?.items?.firstOrNull()?.let { channelItem ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {

                    }
                    AsyncImage(
                        model = channelItem.snippet.thumbnails.high.url,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = channelItem.snippet.title, textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = channelItem.snippet.description, textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Subscribers: ${channelItem.statistics.subscriberCount}",
                    )
                } ?: Text("No channel data available")
            }
        }
    }
}