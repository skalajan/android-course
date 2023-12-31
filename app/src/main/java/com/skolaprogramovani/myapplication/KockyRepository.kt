package com.skolaprogramovani.myapplication

import com.skolaprogramovani.myapplication.odpovedizeserveru.KockyOdpoved
import io.ktor.client.call.body
import io.ktor.client.request.get

object KockyRepository {
    private val jmena = listOf(
        "Amber",
        "Arthur",
        "Arya",
        "Atticus",
        "Aurora",
        "Ava",
        "Baby Girl",
        "Barney",
        "Basil",
        "Benji",
        "Billy",
        "Biscuit",
        "Blaze",
        "Blu",
        "Bobby",
        "Boomer",
        "Bootsie",
        "Boris",
        "Bowie",
        "Bubbles",
        "Bug",
        "Bunny",
        "Buttercup",
        "Butters",
        "Calvin",
        "Casey",
        "Cash",
        "Cassie",
        "Chance",
        "Charlie",
        "Chase",
        "Chewy",
        "Chip",
        "Cinnamon",
        "Clover",
        "Cocoa",
        "Cody",
        "Cuddles",
        "Cupcake",
        "Dash",
        "Diamond",
        "Diego",
        "Diesel",
        "Dixie",
        "Domino",
        "Dora",
        "Duchess",
        "Duke",
        "Echo",
        "Eddie",
        "Elvis",
        "Ember",
        "Emily",
        "Emmy",
        "Ernie",
        "Evie",
        "Finnegan",
        "Freddie",
        "Gary",
        "Georgie",
        "Ghost",
        "Guy",
        "Hank",
        "Harvey",
        "Hobbes",
        "Hope",
        "Indy",
        "Isabella",
        "Jerry",
        "Juno",
        "Kali",
        "Karma",
        "Kat",
        "Kevin",
        "Kit",
        "Kiwi",
        "Kona",
        "Lady",
        "Larry",
        "Lilo",
        "Link",
        "Linus",
        "Logan",
        "Lucifer",
        "Luna",
        "Mabel",
        "Mac",
        "Macy",
        "Maddie",
        "Magic",
        "Maui",
        "Maverick",
        "Maxwell",
        "Mikey",
        "Mila",
        "Miles",
        "Misha",
        "Mo",
        "Moe",
        "Mojo",
        "Monster",
        "Monty",
        "Moo",
        "Moon",
        "Mowgli",
        "Munchkin",
        "Nacho",
        "Neko",
        "Nemo",
        "Niko",
        "Nina",
        "Noah",
        "Noodle",
        "Norman",
        "Nyx",
        "Odin",
        "Oliver",
        "Opie",
        "Orion",
        "Panda",
        "Panther",
        "Parker",
        "Pebbles",
        "Pete",
        "Phoenix",
        "Pip",
        "Pippin",
        "Poe",
        "Polly",
        "Quinn",
        "Remi",
        "Remy",
        "Rex",
        "Ringo",
        "Ripley",
        "Rocket",
        "Rory",
        "Roscoe",
        "Rose",
        "Roxie",
        "Rudy",
        "Rufus",
        "Sabrina",
        "Sammie",
        "Sampson",
        "Sandy",
        "Sheba",
        "Shelby",
        "Sheldon",
        "Skittles",
        "Sky",
        "Sonny",
        "Sox",
        "Spencer",
        "Spike",
        "Stanley",
        "Stitch",
        "Suki",
        "Sully",
        "Sunshine",
        "Sweet Pea",
        "Sweetie",
        "Tabby",
        "Tabitha",
        "Thunder",
        "Tilly",
        "Tink",
        "Tinkerbell",
        "Tiny",
        "Tony",
        "Toothless",
        "Trouble",
        "Vader",
        "Waffles",
        "Walter",
        "Whiskey",
        "Willie",
        "Wilson",
        "Xena",
        "Yoshi",
        "Yuki",
        "Zeke"
    )

    class Kocka(
        val id: Long,
        val jmeno: String,
        val vaha: Double,
        val vek: Int?,
        val obrazekRes: Int?,
        val obrazek: String?
    )

    private val kocky: MutableList<Kocka> = jmena.mapIndexed { index, jmeno ->
        Kocka(index.toLong(), jmeno, 1.1, 2, R.drawable.ic_kocka1, null)
    }.toMutableList()

    fun nactiKocky() = kocky
    fun pridejKocku(novaKocka: Kocka) {
        kocky.add(novaKocka)
    }

    fun nactiKocku(idKocky: Long?): Kocka? =
        kocky.firstOrNull { kocka -> kocka.id == idKocky }

    fun smazKocku(id: Long) {
        kocky.removeIf { it.id == id }
    }

    suspend fun nactiKockyZeServeru() : KockyOdpoved {
        val odpoved =  AplikaceKocky.instance.httpClient.get("http://topdort.cz/android-kocky.json")
        val kocky: KockyOdpoved = odpoved.body()
        return kocky
    }

    suspend fun nactiJednuKockuZeServeru(idKocky: Long?) : Kocka? {
        return nactiKockyZeServeru().kocky.first { kocka -> kocka.id == idKocky }
    }
}