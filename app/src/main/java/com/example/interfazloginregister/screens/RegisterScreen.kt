package com.example.interfazloginregister.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.interfazloginregister.model.UsuarioRegisterDTO
import com.example.interfazloginregister.viewmodel.AuthViewModelRegister

/**
 * Pantalla de Registro.
 *
 * Esta función Composable permite que un usuario nuevo se registre en la aplicación.
 * El usuario debe ingresar su nombre de usuario, email, contraseña, repetir la contraseña y el rol.
 * Al presionar el botón de "Registrarse", se invoca la función register del ViewModel,
 * que se encarga de enviar los datos a la API para crear el nuevo usuario.
 *
 * Si el registro es exitoso, se puede navegar a otra pantalla mediante el callback onRegisterSuccess.
 *
 * @param viewModel Instancia del AuthViewModelRegister que maneja la lógica de registro y se comunica con la API.
 * @param navController Controlador de navegación para cambiar de pantalla (por ejemplo, a la pantalla de login).
 * @param onRegisterSuccess Callback que se invoca cuando el registro es exitoso, permitiendo redirigir al usuario.
 */
@Composable
fun RegisterScreen(
    viewModel: AuthViewModelRegister,
    navController: NavController,
    onRegisterSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }

    val loading by viewModel.loading.collectAsState()
    val registerError by viewModel.error.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registro", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Usuario") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = passwordRepeat, onValueChange = { passwordRepeat = it }, label = { Text("Repetir Contraseña") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(value = rol, onValueChange = { rol = it }, label = { Text("Rol") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(16.dp))
            if (loading) CircularProgressIndicator()
            registerError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
            Button(
                onClick = {
                    viewModel.register(
                        UsuarioRegisterDTO(
                            username, email, password, passwordRepeat,
                            rol.ifBlank { "USER" }  // Se envía el rol en mayúsculas o se asigna "USER"
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                text = AnnotatedString("¿Ya tienes cuenta? Inicia sesión"),
                onClick = { navController.navigate("login") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
