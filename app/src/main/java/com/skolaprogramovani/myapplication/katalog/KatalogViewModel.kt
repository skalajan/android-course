package com.skolaprogramovani.myapplication.katalog

import androidx.lifecycle.ViewModel
import com.skolaprogramovani.myapplication.KockyRepository
import com.skolaprogramovani.myapplication.R
import kotlinx.coroutines.flow.MutableStateFlow

class KatalogViewModel : ViewModel() {
  val jmenoNoveKocky = MutableStateFlow("")
  val vekNoveKocky: MutableStateFlow<Int?> = MutableStateFlow(null)
  val kocky = MutableStateFlow(KockyRepository.nactiKocky())

  val pridavaciDialog = MutableStateFlow(false)

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
    vekNoveKocky.value = null

    pridavaciDialog.value = false
  }
}