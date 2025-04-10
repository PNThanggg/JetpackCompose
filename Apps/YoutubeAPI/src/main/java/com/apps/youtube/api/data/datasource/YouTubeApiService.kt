package com.apps.youtube.api.data.datasource

import com.apps.youtube.api.BuildConfig
import com.apps.youtube.api.data.models.ChannelListResponse
import com.apps.youtube.api.data.models.CommentThreadListResponse
import com.apps.youtube.api.data.models.SearchResponseModel
import com.apps.youtube.api.data.models.SubscriptionListResponse
import com.apps.youtube.api.data.models.VideoListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("subscriptions")
    suspend fun getSubscriptions(
        @Header("Authorization") accessToken: String,
        @Query("part") part: String = "snippet",
        @Query("channelId") channelId: String? = null,
        @Query("id") id: String? = null,
        @Query("mine") mine: Boolean = true,
        @Query("myRecentSubscribers") myRecentSubscribers: Boolean? = null,
        @Query("mySubscribers") mySubscribers: Boolean? = null,
        @Query("forChannelId") forChannelId: String? = null,
        @Query("maxResults") maxResults: Int = 5,
        @Query("order") order: String = "alphabetical",
        @Query("pageToken") pageToken: String? = null,
    ): Response<SubscriptionListResponse>

    @GET("channels")
    suspend fun getMyChannelDetail(
        @Header("Authorization") accessToken: String,
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet,statistics",
        @Query("mine") mine: Boolean = true,
        @Query("hl") hl: String? = null,
        @Query("maxResults") maxResults: Int? = null,
        @Query("onBehalfOfContentOwner") onBehalfOfContentOwner: String? = null,
        @Query("pageToken") pageToken: String? = null,
    ): Response<ChannelListResponse>

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

    @GET("channels")
    suspend fun getChannelDetails(
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet,statistics",
        @Query("id") id: String
    ): Response<ChannelListResponse>

    @GET("youtube/v3/videos")
    suspend fun getVideosByCategory(
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("chart") chart: String = "mostPopular",
        @Query("videoCategoryId") videoCategoryId: String,
        @Query("regionCode") regionCode: String = "PK",
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: String = "25",
        @Query("pageToken") pageToken: String? = null
    ): Response<VideoListResponse>
}