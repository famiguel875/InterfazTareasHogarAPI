package com.example.interfazloginregister.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.interfazloginregister.screens.DireccionListScreen
import com.example.interfazloginregister.screens.EstadisticasScreen
import com.example.interfazloginregister.screens.InfoScreen
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

/**
 * AppNavigation
 *
 * Esta función define la navegación principal de la aplicación utilizando un NavHost. Se configuran rutas
 * para las siguientes pantallas:
 * - Login: Pantalla para iniciar sesión.
 * - Register: Pantalla para el registro de nuevos usuarios.
 * - Menu: Pantalla principal del menú.
 * - UsuarioScreen: Pantalla de usuarios (UserListScreen).
 * - TareaScreen: Pantalla de tareas (TaskListScreen).
 * - DireccionScreen: Pantalla de direcciones (DireccionListScreen).
 * - InfoScreen: Pantalla de información adicional.
 * - EstadisticasScreen: Pantalla de estadísticas y análisis.
 *
 * @param navController Controlador de navegación para cambiar entre pantallas.
 * @param authViewModelLogin ViewModel que gestiona el inicio de sesión y la sesión de usuario.
 * @param authViewModelRegister ViewModel que gestiona el registro de usuarios.
 * @param tareasViewModel ViewModel que gestiona las tareas.
 * @param direccionesViewModel ViewModel que gestiona las direcciones.
 * @param usuariosViewModel ViewModel que gestiona los usuarios.
 */
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
        // Ruta para la pantalla de Login
        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = authViewModelLogin
            )
        }
        // Ruta para la pantalla de Registro
        composable("register") {
            RegisterScreen(
                navController = navController,
                viewModel = authViewModelRegister,
                onRegisterSuccess = { navController.navigate("menu") }
            )
        }
        // Ruta para el menú principal
        composable("menu") {
            MenuScreen(
                navController = navController,
                viewModel = authViewModelLogin // O el ViewModel que maneje la sesión
            )
        }
        // Ruta para la pantalla de usuarios
        composable("usuarioMenu") {
            UserListScreen(
                viewModel = usuariosViewModel,
                navController = navController
            )
        }
        // Ruta para la pantalla de tareas
        composable("tareaMenu") {
            TaskListScreen(
                viewModel = tareasViewModel,
                navController = navController
            )
        }
        // Ruta para la pantalla de direcciones
        composable("direccionMenu") {
            DireccionListScreen(
                viewModel = direccionesViewModel,
                navController = navController
            )
        }
        // Ruta para la pantalla de información adicional
        composable("infoScreen") {
            InfoScreen(navController = navController)
        }
        // Ruta para la pantalla de estadísticas
        composable("estadisticasScreen") {
            EstadisticasScreen(navController = navController)
        }
    }
}





