package com.example.interfazloginregister

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.interfazloginregister.navigation.AppNavigation
import com.example.interfazloginregister.network.RetrofitInstance
import com.example.interfazloginregister.repository.UserPreferences
import com.example.interfazloginregister.ui.theme.InferazLoginRegisterTheme
import com.example.interfazloginregister.viewmodel.AuthViewModelLogin
import com.example.interfazloginregister.viewmodel.AuthViewModelRegister
import com.example.interfazloginregister.viewmodel.DireccionesViewModel
import com.example.interfazloginregister.viewmodel.TareasViewModel
import com.example.interfazloginregister.viewmodel.UsuariosViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InferazLoginRegisterTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val context = LocalContext.current
    // Instancia de UserPreferences basada en el contexto
    val userPreferences = remember { UserPreferences(context) }
    // Instancia de ApiService usando UserPreferences (para el interceptor)
    val apiService = RetrofitInstance.getApiService()

    // Instanciar los ViewModels usando los constructores que reciben ApiService y UserPreferences
    val authViewModelLogin = AuthViewModelLogin(api = apiService, userPreferences = userPreferences)
    val authViewModelRegister = AuthViewModelRegister(api = apiService, userPreferences = userPreferences)
    val tareasViewModel = TareasViewModel(api = apiService, userPreferences = userPreferences)
    val direccionesViewModel = DireccionesViewModel(api = apiService, userPreferences = userPreferences)
    val usuariosViewModel = UsuariosViewModel(api = apiService, userPreferences = userPreferences)

    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AppNavigation(
                navController = navController,
                authViewModelLogin = authViewModelLogin,
                authViewModelRegister = authViewModelRegister,
                tareasViewModel = tareasViewModel,
                direccionesViewModel = direccionesViewModel,
                usuariosViewModel = usuariosViewModel
            )
        }
    }
}


