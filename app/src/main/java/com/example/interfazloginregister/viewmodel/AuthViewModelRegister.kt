package com.example.interfazloginregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interfazloginregister.model.UsuarioRegisterDTO
import com.example.interfazloginregister.network.ApiService
import com.example.interfazloginregister.network.RetrofitInstance
import com.example.interfazloginregister.repository.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModelRegister(
    private val api: ApiService,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun register(user: UsuarioRegisterDTO) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                api.registerUser(user)
            } catch (e: Exception) {
                _error.value = "Error en el registro: ${e.message}"
            }
            _loading.value = false
        }
    }
}


