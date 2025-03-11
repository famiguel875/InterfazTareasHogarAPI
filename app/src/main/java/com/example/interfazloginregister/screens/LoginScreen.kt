package com.example.interfazloginregister.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interfazloginregister.viewmodel.AuthViewModelLogin

/**
 * Pantalla de Login de la aplicación.
 *
 * Esta función Composable muestra el formulario de autenticación, permitiendo que el usuario
 * ingrese su nombre de usuario y contraseña. Se ofrece la posibilidad de alternar la visibilidad
 * de la contraseña. Durante el proceso de autenticación se muestra un indicador de carga y,
 * en caso de error, se muestra un mensaje. Cuando se obtiene un token válido (JWT), la pantalla
 * navega a la pantalla "menu", eliminando la pantalla de login de la pila de navegación.
 *
 * @param navController Controlador de navegación utilizado para redirigir a otras pantallas.
 * @param viewModel Instancia de AuthViewModelLogin que gestiona la lógica de autenticación.
 */
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModelLogin = viewModel()
) {
    // Variables de estado para guardar las credenciales y el estado de la contraseña.
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    // Se recogen los valores del token, indicador de carga y mensajes de error del ViewModel.
    val token by viewModel.token.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Cuando se actualiza el token (por ejemplo, después de un login exitoso),
    // se navega a la pantalla "menu", eliminando la pantalla de login.
    LaunchedEffect(token) {
        if (token != null) {
            navController.navigate("menu") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    // Contenedor principal que centra el contenido en la pantalla.
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Contenedor vertical que agrupa los elementos del formulario de login.
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la pantalla.
            Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))

            // Campo de entrada para el nombre de usuario.
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Campo de entrada para la contraseña.
            // Permite alternar la visibilidad mediante el ícono de trailing.
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = if (passwordVisible) "Ocultar" else "Mostrar"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Muestra un indicador de carga si el proceso de autenticación está en curso.
            if (loading) CircularProgressIndicator()

            // Muestra un mensaje de error si ocurre alguno durante el login.
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            // Botón para iniciar el proceso de login.
            // Llama a la función login del ViewModel con las credenciales ingresadas.
            Button(
                onClick = { viewModel.login(username, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Texto clicable para navegar a la pantalla de registro.
            ClickableText(
                text = AnnotatedString("¿No tienes cuenta? Regístrate"),
                onClick = { navController.navigate("register") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


