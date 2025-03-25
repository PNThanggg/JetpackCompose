package com.apps.spotify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import modules.spotify.network.ApiService
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    fun getApiToken() {
        viewModelScope.launch {
            try {
                val res = apiService.getApiToken(
                    clientId = BuildConfig.CLIENT_ID,
                    clientSecret = BuildConfig.CLIENT_SECRET,
                )

                Timber.tag(TAG).d("Token: ${res.accessToken}")
            } catch (e: Exception) {
                Timber.tag(TAG).e(e)
            }
        }
    }
}