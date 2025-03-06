package com.example.interfazloginregister.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.interfazloginregister.screens.DireccionListScreen
import com.example.interfazloginregister.screens.LoginScreen
import com.example.interfazloginregister.screens.MenuScreen
import com.example.interfazloginregister.screens.RegisterScreen
import com.example.interfazloginregister.screens.TaskListScreen
import com.example.interfazloginregister.screens.UserListScreen
import com.example.interfazloginregister.viewmodel.AuthViewModelLogin
import com.example.interfazloginregister.viewmodel.AuthViewModelRegister
import com.example.interfazloginregister.viewmodel.DireccionesViewModel
import com.example.interfazloginregister.viewmodel.TareasViewModel
import com.example.interfazloginregister.viewmodel.UsuariosViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    authViewModelLogin: AuthViewModelLogin,
    authViewModelRegister: AuthViewModelRegister,
    tareasViewModel: TareasViewModel,
    direccionesViewModel: DireccionesViewModel,
    usuariosViewModel: UsuariosViewModel
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = authViewModelLogin
            )
        }
        composable("register") {
            RegisterScreen(
                navController = navController,
                viewModel = authViewModelRegister,
                onRegisterSuccess = { navController.navigate("menu") }
            )
        }
        composable("menu") {
            MenuScreen(
                navController = navController,
                viewModel = authViewModelLogin  // O el ViewModel que maneje la sesión
            )
        }
        // Rutas para Usuario
        composable("usuarioMenu") {
            UserListScreen(
                viewModel = usuariosViewModel,
                navController = navController
            )
        }
        // Rutas para Tareas
        composable("tareaMenu") {
            TaskListScreen(
                viewModel = tareasViewModel,
                navController = navController
            )
        }
        // Rutas para Direcciones
        composable("direccionMenu") {
            DireccionListScreen(
                viewModel = direccionesViewModel,
                navController = navController
            )
        }
    }
}




