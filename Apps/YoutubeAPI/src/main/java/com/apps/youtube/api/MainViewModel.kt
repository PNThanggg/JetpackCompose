package com.apps.youtube.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.youtube.api.data.models.VideoListResponse
import com.apps.youtube.api.domain.usecase.GetVideosWithDetailsParams
import com.apps.youtube.api.domain.usecase.GetVideosWithDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVideosWithDetailsUseCase: GetVideosWithDetailsUseCase
) : ViewModel() {
    private val _videos = MutableLiveData<VideoListResponse>()
    val videos: LiveData<VideoListResponse> get() = _videos

    fun fetchVideos(channelId: String, pageToken: String? = null) {
        val params = GetVideosWithDetailsParams(channelId, pageToken)
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) { getVideosWithDetailsUseCase.run(params) }
            result.fold(fnL = { failure ->

            }, fnR = { response ->
                _videos.value = response
            })
        }
    }
}