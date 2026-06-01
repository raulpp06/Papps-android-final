package es.upsa.papps.trabajofinal.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class AppTheme { LIGHT, DARK, SYSTEM }

class ThemePreferenceRepository(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "settings")

    private val THEME_KEY = stringPreferencesKey("app_theme")

    val theme: Flow<AppTheme> = context.dataStore.data.map { prefs ->
        when (prefs[THEME_KEY]) {
            "LIGHT" -> AppTheme.LIGHT
            "DARK" -> AppTheme.DARK
            else -> AppTheme.SYSTEM
        }
    }

    suspend fun setTheme(theme: AppTheme) {
        context.dataStore.edit { prefs ->
            prefs[THEME_KEY] = theme.name
        }
    }
}