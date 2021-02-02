package com.rohan.kotlinstateflowsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    private val _loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.Empty)
    val loginUiState: StateFlow<LoginUIState> = _loginUIState

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow: StateFlow<Int> = _stateFlow

    fun login(userName: String, password: String) = viewModelScope.launch {
        _loginUIState.value = LoginUIState.Loading
        delay(2000L)
        if (userName == "android" && password == "topsecret") {
            _loginUIState.value = LoginUIState.Success
        } else {
            _loginUIState.value = LoginUIState.Error("Wrong Credentials")
        }
    }

    fun printNumbers() = viewModelScope.launch {
        (1..5).forEach {
            delay(1000)
          //  _stateFlow.value = it
        }
    }

    sealed class LoginUIState {
        object Success : LoginUIState()
        data class Error(val message: String) : LoginUIState()
        object Loading : LoginUIState()
        object Empty : LoginUIState()
    }
}