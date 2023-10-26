@file:OptIn(ExperimentalMaterial3Api::class)

package com.skolaprogramovani.myapplication.katalog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skolaprogramovani.myapplication.KockyRepository
import com.skolaprogramovani.myapplication.R

private val TAG = "KatalogKocek"

@Composable
fun KatalogKocek(kliknutiNaKocku: (Long) -> Unit) {
  Log.v(TAG, "KatalogKocek")
  val viewModel: KatalogViewModel = viewModel()
  val kocky = viewModel.kocky.collectAsState()
  
  if(viewModel.pridavaciDialog.collectAsState().value){
    Dialog(onDismissRequest = {
      viewModel.pridavaciDialog.value = false
    }) {
      Card {
        FormularPridani(viewModel)
      }
    }
  }

  Box(modifier = Modifier.fillMaxSize()) {
    LazyColumn {
      item {
        Text(
          modifier = Modifier.padding(16.dp),
          text = stringResource(id = R.string.titulek_katalogu),
          fontSize = 30.sp
        )
      }

      for (i in 0 until kocky.value.size step 4) {
        val kockyVRadku =
          listOf(
            kocky.value[i],
            kocky.value.getOrNull(i + 1),
            kocky.value.getOrNull(i + 2),
            kocky.value.getOrNull(i + 3)
          )
            .filterNotNull()
        item {
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            kockyVRadku.forEach { kocka ->
              Zaznam(kocka = kocka, kliknutiNaKocku)
            }
          }
        }
      }
    }

    FloatingActionButton(modifier = Modifier
      .padding(32.dp)
      .align(Alignment.BottomEnd),
      onClick = {
        viewModel.pridavaciDialog.value = true
      }, containerColor = Color.DarkGray, contentColor = Color.White
    ) {
      Text(text = "+")
    }
  }
}

@Composable
private fun Zaznam(kocka: KockyRepository.Kocka, kliknutiNaKocku: (Long) -> Unit) {
  Column(modifier = Modifier
    .padding(all = 4.dp)
    .clickable {
      kliknutiNaKocku(kocka.id)
    }) {
    Image(painter = painterResource(id = kocka.obrazekRes), contentDescription = "")
    Text(text = kocka.jmeno)
    Text(text = kocka.vek?.toString() ?: "")
    Text(text = kocka.vaha.toString())
  }
}

@Composable
private fun FormularPridani(viewModel: KatalogViewModel) {
  Column(modifier = Modifier.padding(16.dp)) {
    OutlinedTextField(
      label = {
        Text(text = "Jmeno kočky")
      },
      value = viewModel.jmenoNoveKocky.collectAsState().value,
      onValueChange = { novaHodnota: String ->
        viewModel.jmenoNoveKocky.value = novaHodnota
      })
    OutlinedTextField(
      label = {
        Text(text = "Věk kočky")
      },
      value = viewModel.vekNoveKocky.collectAsState().value?.toString() ?: "",
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
      onValueChange = {
        try {
          viewModel.vekNoveKocky.value = it.toInt()
        } catch (e: Exception) {
          Log.e(TAG, "Chybný věk", e)
        }
      })
    Button(modifier = Modifier.padding(top = 16.dp).align(Alignment.End), onClick = {
      Log.v(TAG, "Přidávam kočku")
      viewModel.pridejKocku()
    }) {
      Text(text = "Přidej kočku")
    }
  }
}