package com.skolaprogramovani.myapplication.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class LoginViewModel : ViewModel() {
  companion object{
    val UZIVATEL = "a"
    val HESLO = "b"
  }

  val error = MutableStateFlow("")
  val uzivatelskeJmeno = MutableStateFlow("")
  val heslo = MutableStateFlow("")
  val souhlas = MutableStateFlow(false)

  fun jePlatny(): Boolean {
    if(uzivatelskeJmeno.value != UZIVATEL){
      error.value = "Neznámý uživatel"
      return false
    }
    if(heslo.value != HESLO){
      error.value = "Špatný heslo"
      return false
    }
    if(souhlas.value != true){
      error.value = "Souhlas vole"
      return false
    }

    return true
  }
}