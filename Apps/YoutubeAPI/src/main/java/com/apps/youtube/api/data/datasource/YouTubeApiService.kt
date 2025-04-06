package com.apps.youtube.api.data.datasource

import com.apps.youtube.api.BuildConfig
import com.apps.youtube.api.data.models.CommentThreadListResponse
import com.apps.youtube.api.data.models.SearchResponseModel
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
    ): Response<SearchResponseModel>

    @GET("search")
    suspend fun searchVideosWithQuery(
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet",
        @Query("type") type: String = "video",
        @Query("q") query: String,
        @Query("maxResults") maxResults: String = "10",
        @Query("pageToken") pageToken: String? = null,
    ): Response<SearchResponseModel>

    @GET("search")
    suspend fun searchVideoShortWithQuery(
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet",
        @Query("type") type: String = "video",
        @Query("q") query: String,
        @Query("videoDuration") videoDuration: String = "short",
        @Query("order") order: String = "viewCount",
        @Query("maxResults") maxResults: String = "10",
        @Query("pageToken") pageToken: String? = null,
    ): Response<SearchResponseModel>

    @GET("videos")
    suspend fun getVideoDetails(
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("id") id: String,
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("fields") fields: String = "items(id,snippet(title,channelTitle,channelId,thumbnails,publishedAt,description),statistics(viewCount,likeCount,commentCount),contentDetails(duration))",
    ): Response<VideoListResponse>

    @GET("commentThreads")
    suspend fun getComments(
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet,replies",
        @Query("videoId") videoId: String,
        @Query("maxResults") maxResults: String = "10",
        @Query("pageToken") pageToken: String? = null,
        @Query("order") order: String = "relevance"
    ): Response<CommentThreadListResponse>
}