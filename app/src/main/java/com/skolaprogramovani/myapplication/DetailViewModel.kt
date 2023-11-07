package com.skolaprogramovani.myapplication

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
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

    val textZeServeru = MutableStateFlow<StavVolani>(StavVolani.NacitaSe)

    init {
        nactiKocku(idKocky)
        stahniText()
    }

    fun nactiKocku(idKocky: Long?) {
        viewModelScope.launch {
            kocka.value = KockyRepository.nactiJednuKockuZeServeru(idKocky)
        }
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

    fun stahniText(){
        textZeServeru.value = StavVolani.NacitaSe
        viewModelScope.launch {
            delay(2000)
            val respose = AplikaceKocky.instance.httpClient.get("http://topdort.cz/android-lekce-8")
            val text = respose.bodyAsText()

            try {
                textZeServeru.value = StavVolani.Uspech(text)
            }catch (t: Throwable){
                textZeServeru.value = StavVolani.Chyba(t)
            }
        }
    }

    sealed class StavVolani{
        object NacitaSe: StavVolani()
        data class Uspech(val text: String): StavVolani()
        data class Chyba(val chyba: Throwable): StavVolani()
    }
}