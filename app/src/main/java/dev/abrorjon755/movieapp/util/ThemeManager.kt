package dev.abrorjon755.movieapp.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

object ThemeManager {
    private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

    suspend fun saveDarkModePreference(context: Context, isDarkMode: Boolean) {
        context.dataStore.edit {
            it[DARK_MODE_KEY] = isDarkMode
        }
    }

    fun isDarkMode(context: Context): Flow<Boolean> {
        return context.dataStore.data.map {
            it[DARK_MODE_KEY] ?: true
        }
    }
}
