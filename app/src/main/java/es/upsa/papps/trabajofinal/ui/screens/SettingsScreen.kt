package es.upsa.papps.trabajofinal.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.repository.AppTheme
import es.upsa.papps.trabajofinal.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    vm: MainViewModel,
) {
    val appTheme by vm.appTheme.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.ui_settings)) }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.ui_theme),
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(Modifier.padding(8.dp))

            Row(Modifier.fillMaxWidth()) {
                FilterChip(
                    selected = appTheme == AppTheme.SYSTEM,
                    onClick = { vm.setTheme(AppTheme.SYSTEM) },
                    label = { Text(stringResource(R.string.ui_theme_system)) }
                )
                Spacer(Modifier.width(8.dp))
                FilterChip(
                    selected = appTheme == AppTheme.LIGHT,
                    onClick = { vm.setTheme(AppTheme.LIGHT) },
                    label = { Text(stringResource(R.string.ui_theme_light)) }
                )
                Spacer(Modifier.width(8.dp))
                FilterChip(
                    selected = appTheme == AppTheme.DARK,
                    onClick = { vm.setTheme(AppTheme.DARK) },
                    label = { Text(stringResource(R.string.ui_theme_dark)) }
                )
            }
        }
    }
}