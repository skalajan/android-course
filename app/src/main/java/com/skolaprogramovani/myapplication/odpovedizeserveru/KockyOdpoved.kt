package com.skolaprogramovani.myapplication.odpovedizeserveru

import com.skolaprogramovani.myapplication.KockyRepository

data class KockyOdpoved(
    val kocky: List<KockyRepository.Kocka>
)
