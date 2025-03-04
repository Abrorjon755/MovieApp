package dev.abrorjon755.movieapp.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings1")

object LanguageManager {
    private val LANGUAGE_KEY = stringPreferencesKey("app_language")

    suspend fun saveLanguage(context: Context, language: String) {
        context.dataStore.edit {
            it[LANGUAGE_KEY] = language
        }
    }

    fun getSavedLanguage(context: Context): Flow<String> {
        return context.dataStore.data.map {
            it[LANGUAGE_KEY] ?: "en"
        }
    }
}
