package dev.abrorjon755.movieapp

import android.app.Application
import dev.abrorjon755.movieapp.data.AppContainer
import dev.abrorjon755.movieapp.data.AppDatabase
import dev.abrorjon755.movieapp.data.DefaultAppContainer

class MovieApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}