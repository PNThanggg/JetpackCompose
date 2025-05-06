package com.apps.youtube.api.features.login

import androidx.lifecycle.ViewModel
import com.apps.youtube.api.datastore.repository.LocalPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localPreferencesRepository: LocalPreferencesRepository
) : ViewModel() {
    sealed class LoginState {
        object Idle : LoginState()
        object Success : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun onLoginSuccess() {
        _loginState.value = LoginState.Success
    }
}