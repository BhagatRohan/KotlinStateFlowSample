package com.rohan.kotlinstateflowsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.Empty)
    val loginUiState: StateFlow<LoginUIState> = _loginUIState

    fun login(userName: String, password: String) = viewModelScope.launch {
        _loginUIState.value = LoginUIState.Loading
        delay(2000L)
        if (userName == "android" && password == "topsecret"){
            _loginUIState.value = LoginUIState.Success
        }else{
            _loginUIState.value = LoginUIState.Error("Wrong Credentials")
        }
    }

    sealed class LoginUIState{
        object Success: LoginUIState()
        data class Error(val message: String) : LoginUIState()
        object Loading: LoginUIState()
        object Empty: LoginUIState()
    }
}