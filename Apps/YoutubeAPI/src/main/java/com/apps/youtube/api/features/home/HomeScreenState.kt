package com.apps.youtube.api.features.home

data class HomeScreenState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null,
    val isLoading: Boolean = false
)