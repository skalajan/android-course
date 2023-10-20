package com.skolaprogramovani.myapplication

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

private val TAG = "KatalogKocek"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KatalogKocek(kliknutiNaKocku: (Long)->Unit) {
  Log.v(TAG, "KatalogKocek")
  val viewModel: KatalogViewModel = viewModel()
  val kocky = viewModel.kocky.collectAsState()

  Column {
    Text(text = stringResource(id = R.string.titulek_katalogu))

    Column {
      val radek = false
      if(radek) {
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
          kocky.value.forEach { kocka ->
            Zaznam(kocka = kocka, kliknutiNaKocku)
          }
        }
      }else{
        Column {
          kocky.value.forEach { kocka ->
            Zaznam(kocka = kocka, kliknutiNaKocku)
          }
        }
      }
      BasicTextField(
        value = viewModel.jmenoNoveKocky.collectAsState().value,
        onValueChange = { novaHodnota: String ->
          viewModel.jmenoNoveKocky.value = novaHodnota
        })
      OutlinedTextField(
        value = viewModel.vekNoveKocky.collectAsState().value?.toString() ?: "",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange = {
          try {
            viewModel.vekNoveKocky.value = it.toInt()
          } catch (e: Exception) {
            Log.e(TAG, "Chybný věk", e)
          }
        })
      Button(onClick = {
        Log.v(TAG, "Přidávam kočku")
        viewModel.pridejKocku()
      }) {
        Text(text = "Přidej kočku")
      }
    }

  }
}

@Composable
fun Zaznam(kocka: KockyRepository.Kocka, kliknutiNaKocku: (Long)->Unit) {
  Column(modifier = Modifier.padding(all = 4.dp).clickable{
    kliknutiNaKocku(kocka.id)
  }) {
    Image(painter = painterResource(id = kocka.obrazekRes), contentDescription = "")
    Text(text = kocka.jmeno)
    Text(text = kocka.vek?.toString() ?: "")
    Text(text = kocka.vaha.toString())
  }
}