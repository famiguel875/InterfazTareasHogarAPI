package com.example.interfazloginregister.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.interfazloginregister.viewmodel.AuthViewModelLogin

@Composable
fun MenuScreen(
    navController: NavHostController,
    viewModel: AuthViewModelLogin = viewModel() // O el ViewModel que maneje la sesión
) {
    val token by viewModel.token.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Menú Principal", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("usuarioMenu") }) {
            Text("Usuario Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("tareaMenu") }) {
            Text("Tarea Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("direccionMenu") }) {
            Text("Dirección Screen")
        }
        Spacer(modifier = Modifier.height(16.dp))
        token?.let { Text("Token almacenado (no se muestra en producción)") }
    }
}
