package es.upsa.papps.trabajofinal.ui.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.upsa.papps.trabajofinal.R
import es.upsa.papps.trabajofinal.model.repository.AppTheme

@Composable
fun ThemeSelectorRow(
    currentTheme: AppTheme,
    onThemeChange: (AppTheme) -> Unit,
) {
    Row {
        FilterChip(
            selected = currentTheme == AppTheme.SYSTEM,
            onClick = { onThemeChange(AppTheme.SYSTEM) },
            label = { Text(stringResource(R.string.ui_theme_system)) }
        )
        Spacer(Modifier.width(8.dp))
        FilterChip(
            selected = currentTheme == AppTheme.LIGHT,
            onClick = { onThemeChange(AppTheme.LIGHT) },
            label = { Text(stringResource(R.string.ui_theme_light)) }
        )
        Spacer(Modifier.width(8.dp))
        FilterChip(
            selected = currentTheme == AppTheme.DARK,
            onClick = { onThemeChange(AppTheme.DARK) },
            label = { Text(stringResource(R.string.ui_theme_dark)) }
        )
    }
}