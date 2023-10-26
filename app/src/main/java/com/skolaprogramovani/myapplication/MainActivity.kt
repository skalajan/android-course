package com.skolaprogramovani.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skolaprogramovani.myapplication.katalog.KatalogKocek
import com.skolaprogramovani.myapplication.login.LoginScreen

private val TAG = "MainActivity"


class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    Log.v(TAG, "OnCreate")

    setContent {
      HlavniNavigace()
    }
  }
}

@Composable
fun HlavniNavigace() {
  val navController = rememberNavController()

  NavHost(navController = navController, startDestination = "detailKocky/1") {
    composable("login"){
      LoginScreen(loginBylUspesny = {
        navController.navigate("katalogKocek")
      })
    }
    composable("katalogKocek") {
      KatalogKocek { idKocky ->
        Log.v(TAG, "Klik na kocku $idKocky")
        navController.navigate("detailKocky/$idKocky")
      }
    }
    composable("detailKocky/{idKocky}", arguments = listOf(navArgument("idKocky") {
      type = NavType.LongType
    })) {
      val idKocky = it.arguments?.getLong("idKocky")
      Log.v(TAG, "Klik na kocku $idKocky")
      DetailKocky(idKocky){
        navController.popBackStack()
      }
    }
  }
}