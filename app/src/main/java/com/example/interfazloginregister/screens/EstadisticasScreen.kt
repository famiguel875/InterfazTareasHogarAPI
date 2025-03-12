package com.example.interfazloginregister.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.interfazloginregister.R

/**
 * EstadisticasScreen
 *
 * Esta pantalla está diseñada para mostrar estadísticas y datos analíticos que complementan la información
 * de la aplicación. Es útil para visualizar métricas, informes y tendencias de uso, lo que permite al usuario
 * evaluar el rendimiento y la eficiencia de la aplicación.
 *
 * Recursos utilizados:
 * - Imagen Representativa: R.drawable.estadisticas (archivo .png que ilustra la sección de estadísticas).
 *
 * Elementos incluidos:
 * - TopAppBar: Con título "Estadísticas" y un botón de navegación para regresar al menú principal.
 * - Imagen Banner: Muestra una imagen representativa de estadísticas.
 * - Sección de Resumen: Texto que describe las métricas generales (por ejemplo, cantidad de tareas completadas,
 *   usuarios activos, etc.).
 * - Sección de Detalle: Listado de indicadores clave y tendencias que se actualizarán en futuras versiones.
 * - Texto adicional: Comentarios que indican la inclusión de gráficos y análisis detallados próximamente.
 *
 * @param navController Controlador de navegación para regresar al menú principal.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstadisticasScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Estadísticas", style = MaterialTheme.typography.headlineMedium)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("menu") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver al Menú")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Contenedor principal con scroll para manejar contenido extenso.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen representativa (banner) de estadísticas.
            Image(
                painter = painterResource(id = R.drawable.estadisticas),
                contentDescription = "Banner de Estadísticas",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Sección de resumen de estadísticas.
            Text(
                text = "Resumen de Estadísticas",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "• Tareas Completadas: 75\n" +
                        "• Tareas Pendientes: 20\n" +
                        "• Usuarios Activos: 150",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Sección de detalle.
            Text(
                text = "Indicadores Clave",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "La aplicación ofrece análisis en tiempo real y tendencias de uso que te permiten " +
                        "monitorear el progreso y la eficiencia de las tareas gestionadas. Próximamente se " +
                        "incluirán gráficos interactivos y reportes detallados.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Texto adicional para comentarios futuros.
            Text(
                text = "Gráficos, tendencias y análisis detallados se mostrarán próximamente. Mantente atento a las actualizaciones para obtener más información visual y analítica.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}