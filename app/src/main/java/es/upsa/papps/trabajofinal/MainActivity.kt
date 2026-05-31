package es.upsa.papps.trabajofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upsa.papps.trabajofinal.ui.MainScreen
import es.upsa.papps.trabajofinal.ui.theme.TrabajoFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrabajoFinalTheme {
                MainScreen(viewModel())
            }
        }
    }
}