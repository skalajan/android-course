package com.skolaprogramovani.myapplication

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailKocky(zpet: () -> Unit) {
    val viewModel: DetailViewModel = viewModel()

    val kocka = viewModel.kocka.collectAsState().value
    val sheetState = rememberModalBottomSheetState()

    if (kocka != null) {
        if (viewModel.sheetZobrazen.collectAsState().value) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { viewModel.sheetZobrazen.value = false }) {
                ObsahBottomSheetu(zpet, viewModel)
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            Column {
                Nadpis(viewModel, zpet)

                TextZeServeru(viewModel.textZeServeru.collectAsState().value)

                ZobrazeniKocky(kocka = kocka)

                Text(
                    text = if (viewModel.oblibena.collectAsState(false).value) {
                        "Oblibena"
                    } else {
                        "Neoblíbená"
                    }
                )
                Button(onClick = { viewModel.pridejDoOblibenych() }) {
                    Text(text = "Přidat do oblíbených")
                }

                Button(onClick = { viewModel.odeberZOblibenych() }) {
                    Text(text = "Odeber z oblíbených")
                }
            }
        }
    }
}

@Composable
fun TextZeServeru(value: DetailViewModel.StavVolani) {
    when(value){
        is DetailViewModel.StavVolani.Chyba -> {
            Text(text = "Chyba")
        }
        DetailViewModel.StavVolani.NacitaSe -> Text(text = "...")
        is DetailViewModel.StavVolani.Uspech -> Text(text = value.text)
    }
}

@Composable
fun ZobrazeniKocky(kocka: KockyRepository.Kocka) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 4.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (kocka.obrazek != null) {
            AsyncImage(model = kocka.obrazek, contentDescription = "")
        } else {
            Image(
                painter = painterResource(id = kocka.obrazekRes ?: R.drawable.ic_kocka1),
                contentDescription = ""
            )
        }
        Text(text = kocka.jmeno)
        Text(text = kocka.vek?.toString() ?: "")
        Text(text = kocka.vaha.toString())

        val context = LocalContext.current

        Row {
            Button(onClick = {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://almondmendoza.com/android-applications/")
                    )
                )
            }) {
                Text(text = "Jdi na web")
            }
            Button(onClick = {
                val id = "ahoj"
                val appIntent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        "vnd.youtube:$id"
                    )
                )
                val webIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=$id")
                )
                try {
                    context.startActivity(appIntent)
                } catch (ex: ActivityNotFoundException) {
                    context.startActivity(webIntent)
                }
            }) {
                Text(text = "Jdi YT")
            }
            Button(onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345")
                )
                context.startActivity(intent)
            }) {
                Text(text = "Najdi kočku")
            }
        }
    }
}

@Composable
private fun ObsahBottomSheetu(zpet: () -> Unit, viewModel: DetailViewModel) {
    Button(modifier = Modifier
        .padding(vertical = 16.dp)
        .fillMaxWidth(), onClick = {
        viewModel.smazKocku()
        zpet()
    }) {
        Text(text = "Smazat kočku")
    }
}

@Composable
private fun Nadpis(viewModel: DetailViewModel, zpet: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = zpet) {
            Icon(painter = painterResource(id = R.drawable.ic_back_arrow), "Zpět")
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Detail kočky",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { viewModel.sheetZobrazen.value = true }) {
            Icon(painter = painterResource(id = R.drawable.ic_akce_vice), "Více")
        }
    }
}