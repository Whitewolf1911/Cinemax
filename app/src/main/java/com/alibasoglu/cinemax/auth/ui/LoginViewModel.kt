package com.alibasoglu.cinemax.auth.ui

import androidx.lifecycle.viewModelScope
import com.alibasoglu.cinemax.auth.domain.AuthRepository
import com.alibasoglu.cinemax.core.BaseViewModel
import com.alibasoglu.cinemax.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private var _loginState = MutableStateFlow<Resource<Unit>>(Resource.Loading(isLoading = false))
    val loginState: StateFlow<Resource<Unit>>
        get() = _loginState

    fun loginWithEmailPassword(email: String, password: String) {
        viewModelScope.launch {
            authRepository.loginWithEmailPassword(email, password).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _loginState.value = Resource.Success(null)
                    }
                    is Resource.Error -> {
                        _loginState.value = Resource.Error(message = result.message ?: "Unknown Error")
                    }
                    is Resource.Loading -> {
                        _loginState.value = Resource.Loading(isLoading = result.isLoading)
                    }
                }
            }
        }
    }
}
