package com.skolaprogramovani.myapplication

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DetailViewModel(
    savedState: SavedStateHandle
) : ViewModel() {
    val kocka = MutableStateFlow<KockyRepository.Kocka?>(null)
    val sheetZobrazen = MutableStateFlow(false)

    val idKocky: Long? = savedState["idKocky"]
    val klicOblibenosti = booleanPreferencesKey("oblibenostKocky" + idKocky)

    val oblibena : Flow<Boolean> = AplikaceKocky.instance.dataStore.data.map { prefences ->
        prefences[klicOblibenosti] ?: false
    }

    init {
        nactiKocku(idKocky)
    }

    fun nactiKocku(idKocky: Long?) {
        kocka.value = KockyRepository.nactiKocku(idKocky)
    }

    fun smazKocku() {
        kocka.value?.id?.let {
            KockyRepository.smazKocku(it)
        }
    }

    fun pridejDoOblibenych() {
        viewModelScope.launch {
            AplikaceKocky.instance.dataStore.edit { preferences ->
                preferences.set(klicOblibenosti, true)
            }
        }
    }

    fun odeberZOblibenych() {
        viewModelScope.launch {
            AplikaceKocky.instance.dataStore.edit { preferences ->
                preferences.set(klicOblibenosti, false)
            }
        }
    }
}