package com.apps.youtube.api.features.home

import androidx.lifecycle.ViewModel
import com.apps.youtube.api.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: SignInResult) {
        _state.value = state.value.copy(
            isSignInSuccessful = result.data != null, signInError = result.errorMessage
        )
    }

    fun resetState() {
        _state.update { HomeScreenState() }
    }
}