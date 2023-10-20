package com.skolaprogramovani.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class KatalogViewModel : ViewModel() {
  val jmenoNoveKocky = MutableStateFlow("")
  val vekNoveKocky: MutableStateFlow<Int?> = MutableStateFlow(null)
  val kocky = MutableStateFlow(KockyRepository.nactiKocky())

  fun prenacti(){
    kocky.value = KockyRepository.nactiKocky()
  }

  fun pridejKocku() {
    KockyRepository.pridejKocku(
      KockyRepository.Kocka(
        22,
        jmenoNoveKocky.value,
        1.1,
        vekNoveKocky.value,
        R.drawable.ic_kocka3
      )
    )
    prenacti()
    jmenoNoveKocky.value = ""
  }
}