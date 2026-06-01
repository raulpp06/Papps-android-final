package es.upsa.papps.trabajofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upsa.papps.trabajofinal.model.repository.AppTheme
import es.upsa.papps.trabajofinal.ui.MainScreen
import es.upsa.papps.trabajofinal.ui.theme.TrabajoFinalTheme
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: MainViewModel = viewModel()
            val appTheme by vm.appTheme.collectAsState()

            val darkTheme = when (appTheme) {
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
                AppTheme.SYSTEM -> isSystemInDarkTheme()
            }

            TrabajoFinalTheme(darkTheme = darkTheme) {
                MainScreen(vm)
            }
        }
    }
}
