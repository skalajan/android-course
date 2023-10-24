package com.skolaprogramovani.myapplication.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skolaprogramovani.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
  loginBylUspesny: () -> Unit
) {
  val viewModel: LoginViewModel = viewModel()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      modifier = Modifier.size(300.dp),
      painter = painterResource(id = R.mipmap.ic_launcher_foreground),
      contentDescription = null,
      contentScale = ContentScale.FillBounds
    )
    OutlinedTextField(value = viewModel.uzivatelskeJmeno.collectAsState().value, onValueChange = {
      viewModel.uzivatelskeJmeno.value = it
    }, label = {
      Text(text = stringResource(id = R.string.login_uzivatelske_jmeno))
    })

    OutlinedTextField(
      value = viewModel.heslo.collectAsState().value,
      visualTransformation = PasswordVisualTransformation(),
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
      onValueChange = {
        viewModel.heslo.value = it
      },
      label = {
        Text(text = stringResource(id = R.string.login_uzivatelske_heslo))
      })

    Spacer(modifier = Modifier.weight(1f))

    Row(verticalAlignment = Alignment.CenterVertically) {
      Checkbox(checked = viewModel.souhlas.collectAsState().value, onCheckedChange = {
        viewModel.souhlas.value = it
      })
      Text(text = stringResource(id = R.string.login_podminky))
    }

    Text(text = viewModel.error.collectAsState().value, color = Color.Red, textAlign = TextAlign.Center)

    Button(modifier = Modifier
      .padding(16.dp)
      .fillMaxWidth(), onClick = {
      if (viewModel.jePlatny()) {
        loginBylUspesny()
      }
    }) {
      Text(text = stringResource(id = R.string.login_prihlasit))
    }
  }
}