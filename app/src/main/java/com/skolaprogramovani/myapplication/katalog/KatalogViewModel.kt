package com.skolaprogramovani.myapplication.katalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skolaprogramovani.myapplication.KockyRepository
import com.skolaprogramovani.myapplication.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class KatalogViewModel : ViewModel() {
  val jmenoNoveKocky = MutableStateFlow("")
  val vekNoveKocky: MutableStateFlow<Int?> = MutableStateFlow(null)
  val kocky = MutableStateFlow<List<KockyRepository.Kocka>>(emptyList())

  val pridavaciDialog = MutableStateFlow(false)

  init {
      prenacti()
  }

  fun prenacti(){
    viewModelScope.launch {
      kocky.value = KockyRepository.nactiKockyZeServeru().kocky
    }
  }

  fun pridejKocku() {
    KockyRepository.pridejKocku(
      KockyRepository.Kocka(
        22,
        jmenoNoveKocky.value,
        1.1,
        vekNoveKocky.value,
        R.drawable.ic_kocka3,
        null
      )
    )
    prenacti()
    jmenoNoveKocky.value = ""
    vekNoveKocky.value = null

    pridavaciDialog.value = false
  }
}