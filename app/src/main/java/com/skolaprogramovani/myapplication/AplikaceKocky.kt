package com.skolaprogramovani.myapplication

import android.app.Application
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class AplikaceKocky : Application() {
    val dataStore : DataStore<Preferences> by preferencesDataStore(name = "kocky")

    init {
        Log.v("Kocky", "Spustila se aplikace")
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

    companion object{
        lateinit var instance: AplikaceKocky
    }
}