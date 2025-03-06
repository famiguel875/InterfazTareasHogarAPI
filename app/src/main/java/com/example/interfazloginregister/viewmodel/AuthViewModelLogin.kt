package com.example.interfazloginregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.LoginUsuarioDTO
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.network.RetrofitInstance
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel para Login
class AuthViewModelLogin(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> get() = _token

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = api.loginUser(LoginUsuarioDTO(username, password))
                if (response.token.isNotEmpty()) {
                    userPreferences.saveAuthToken(response.token)
                    _token.value = response.token
                } else {
                    _error.value = "Token inválido"
                }
            } catch (e: Exception) {
                _error.value = "Error de autenticación"
            }
            _loading.value = false
        }
    }
}

