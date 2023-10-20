package com.skolaprogramovani.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class DetailViewModel : ViewModel() {
  val kocka = MutableStateFlow<KockyRepository.Kocka?>(null)

  fun nactiKocku(idKocky: Long?) {
    kocka.value = KockyRepository.nactiKocku(idKocky)
  }
}