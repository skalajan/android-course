package com.skolaprogramovani.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailKocky(idKocky: Long?) {
  val viewModel : DetailViewModel = viewModel()
  viewModel.nactiKocku(idKocky)

  val kocka = viewModel.kocka.collectAsState().value

  if(kocka != null) {
    Column(modifier = Modifier.padding(all = 4.dp)) {
      Image(painter = painterResource(id = kocka.obrazekRes), contentDescription = "")
      Text(text = kocka.jmeno)
      Text(text = kocka.vek?.toString() ?: "")
      Text(text = kocka.vaha.toString())
    }
  }
}