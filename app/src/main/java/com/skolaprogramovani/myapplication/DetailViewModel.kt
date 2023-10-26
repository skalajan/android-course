package com.skolaprogramovani.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class DetailViewModel : ViewModel() {
  val kocka = MutableStateFlow<KockyRepository.Kocka?>(null)
  val sheetZobrazen = MutableStateFlow(false)

  fun nactiKocku(idKocky: Long?) {
    kocka.value = KockyRepository.nactiKocku(idKocky)
  }

  fun smazKocku() {
    kocka.value?.id?.let {
      KockyRepository.smazKocku(it)
    }
  }
}