package com.skolaprogramovani.myapplication

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson

class AplikaceKocky : Application() {
    val dataStore : DataStore<Preferences> by preferencesDataStore(name = "kocky")
    lateinit var httpClient: HttpClient

    init {
        Log.v("Kocky", "Spustila se aplikace")
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        httpClient = HttpClient {
            install(ContentNegotiation){
                gson()
            }
        }
    }

    companion object{
        lateinit var instance: AplikaceKocky
    }
}