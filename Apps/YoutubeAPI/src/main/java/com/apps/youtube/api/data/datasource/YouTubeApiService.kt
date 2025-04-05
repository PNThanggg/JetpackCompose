package com.apps.youtube.api.data.datasource

import com.apps.youtube.api.BuildConfig
import com.apps.youtube.api.data.models.VideoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("search")
    suspend fun searchVideos(
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet",
        @Query("type") type: String = "video",
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: String = "10",
        @Query("pageToken") pageToken: String? = null,
        @Query("order") order: String = "viewCount"
    ): Response<VideoListResponse>
}