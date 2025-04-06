package com.apps.youtube.api.data.models

import com.google.gson.annotations.SerializedName

data class PageInfo(
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("resultsPerPage") val resultsPerPage: Int
)