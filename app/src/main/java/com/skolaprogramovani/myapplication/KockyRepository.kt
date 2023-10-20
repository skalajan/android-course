package com.skolaprogramovani.myapplication

object KockyRepository {
  class Kocka(val id: Long, val jmeno: String, val vaha: Double, val vek: Int?, val obrazekRes: Int)

  private val kocky = mutableListOf(
    Kocka(0, "Monty", 6.0, 3, R.drawable.ic_kocka1),
    Kocka(1, "Cip", 2.0, 1, R.drawable.ic_kocka2)
  )

  fun nactiKocky() = kocky
  fun pridejKocku(novaKocka: Kocka) {
    kocky.add(novaKocka)
  }

  fun nactiKocku(idKocky: Long?): Kocka? =
    kocky.firstOrNull { kocka -> kocka.id == idKocky }
}