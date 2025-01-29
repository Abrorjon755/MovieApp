package dev.abrorjon755.movieapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.abrorjon755.movieapp.ui.screen.home.MovieApp
import dev.abrorjon755.movieapp.ui.theme.MovieAppTheme
import dev.abrorjon755.movieapp.util.ThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode = remember { mutableStateOf(true) }

            fun toggleTheme(context: Context, isDarkMode: Boolean) {
                CoroutineScope(Dispatchers.IO).launch {
                    ThemeManager.saveDarkModePreference(context, isDarkMode)
                }
            }
            LaunchedEffect(Unit) {
                ThemeManager.isDarkMode(this@MainActivity).collect {
                    isDarkMode.value = it
                }
            }
            MovieAppTheme(darkTheme = isDarkMode.value) {
                MovieApp(
                    changeTheme = { toggleTheme(this@MainActivity, !isDarkMode.value) }
                )
            }
        }
    }
}
