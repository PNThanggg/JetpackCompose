package com.apps.youtube.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.youtube.api.data.models.ChannelListResponse
import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.datastore.repository.LocalPreferencesRepository
import com.apps.youtube.api.domain.usecase.GetCommentsParams
import com.apps.youtube.api.domain.usecase.GetCommentsUseCase
import com.apps.youtube.api.domain.usecase.GetMyChannelDetailParam
import com.apps.youtube.api.domain.usecase.GetMyChannelDetailUseCase
import com.apps.youtube.api.domain.usecase.GetVideosWithDetailsParams
import com.apps.youtube.api.domain.usecase.GetVideosWithDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localPreferencesRepository: LocalPreferencesRepository,
    private val getVideosWithDetailsUseCase: GetVideosWithDetailsUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
    private val getMyChannelDetailUseCase: GetMyChannelDetailUseCase,
) : ViewModel() {
    private val _videos = MutableLiveData<VideoListResponse>()
    val videos: LiveData<VideoListResponse> get() = _videos

    private val _channel = MutableStateFlow<ChannelListResponse?>(null)
    val channel: StateFlow<ChannelListResponse?> = _channel.asStateFlow()

    val appPrefs = localPreferencesRepository.applicationPreferences.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = runBlocking { localPreferencesRepository.applicationPreferences.first() },
    )

    fun fetchVideos(channelId: String, pageToken: String? = null) {
        val params = GetVideosWithDetailsParams(channelId, pageToken)
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getVideosWithDetailsUseCase.run(params) }
            result.fold(
                fnL = { failure ->

                },
                fnR = { response ->
                    _videos.value = response
                },
            )
        }
    }

    fun fetchComments(videoId: String, pageToken: String? = null) {
        val params = GetCommentsParams(videoId, pageToken)
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getCommentsUseCase.run(params) }
            result.fold(
                fnL = { failure ->
                    Timber.tag(TAG).e(failure.message)
                },
                fnR = { response ->
                    Timber.tag(TAG).d("$response")
                },
            )
        }
    }

    fun fetchProfile() {
        viewModelScope.launch {
            val params = GetMyChannelDetailParam(
                accessToken = appPrefs.value.accessToken,
            )
            val result = withContext(Dispatchers.IO) { getMyChannelDetailUseCase.run(params) }
            result.fold(
                fnL = { failure ->

                },
                fnR = { response ->
                    _channel.value = response
                },
            )
        }
    }
}