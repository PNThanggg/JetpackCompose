package com.apps.youtube.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.domain.usecase.GetCommentsParams
import com.apps.youtube.api.domain.usecase.GetCommentsUseCase
import com.apps.youtube.api.domain.usecase.GetVideosWithDetailsParams
import com.apps.youtube.api.domain.usecase.GetVideosWithDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVideosWithDetailsUseCase: GetVideosWithDetailsUseCase,
    private val getCommentsUseCase: GetCommentsUseCase,
) : ViewModel() {
    private val _videos = MutableLiveData<VideoListResponse>()
    val videos: LiveData<VideoListResponse> get() = _videos

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
}