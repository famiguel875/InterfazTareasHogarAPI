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
    // Variables de estado para los campos de entrada.
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var rol by remember { mutableStateOf("") }

    // Recoge los estados de carga y error del ViewModel para mostrarlos en la UI.
    val loading by viewModel.loading.collectAsState()
    val registerError by viewModel.error.collectAsState()

    // Se utiliza un scroll state para permitir el desplazamiento en pantallas con muchos campos.
    val scrollState = rememberScrollState()

    // Box es el contenedor principal, que centra su contenido y aplica un padding general.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Column organiza verticalmente los elementos del formulario.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la pantalla.
            Text("Registro", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            // Campo para ingresar el nombre de usuario.
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // Campo para ingresar el email.
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // Campo para ingresar la contraseña.
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // Campo para repetir la contraseña.
            OutlinedTextField(
                value = passwordRepeat,
                onValueChange = { passwordRepeat = it },
                label = { Text("Repetir Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            // Campo para ingresar el rol. Si se deja vacío, se asignará "USER".
            OutlinedTextField(
                value = rol,
                onValueChange = { rol = it },
                label = { Text("Rol") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Muestra un indicador de carga si se está procesando el registro.
            if (loading) CircularProgressIndicator()
            // Muestra el error de registro si existe.
            registerError?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            // Botón para registrar al usuario.
            // Llama a la función register del ViewModel con los datos ingresados.
            Button(
                onClick = {
                    viewModel.register(
                        UsuarioRegisterDTO(
                            username, email, password, passwordRepeat,
                            rol.ifBlank { "USER" }  // Si el campo rol está en blanco, se asigna "USER".
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Texto clicable que permite navegar a la pantalla de login si el usuario ya tiene cuenta.
            ClickableText(
                text = AnnotatedString("¿Ya tienes cuenta? Inicia sesión"),
                onClick = { navController.navigate("login") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
