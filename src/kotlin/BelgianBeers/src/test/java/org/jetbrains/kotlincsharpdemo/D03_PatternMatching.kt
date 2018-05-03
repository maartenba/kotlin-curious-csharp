package org.jetbrains.kotlincsharpdemo

import org.junit.Test

sealed class BeerWithTaste(val beer: Beer) {
  override fun toString(): String {
    return "${javaClass.simpleName}($beer)"
  }
}

class JustBeer(beer: Beer) : BeerWithTaste(beer)

class DubbelBeer(beer: Beer) : BeerWithTaste(beer) {
  val dubbelName get() = beer.Name
}

class TrippelBeer(beer: Beer) : BeerWithTaste(beer)
val TrippelBeer.tripperName get() = beer.Name

fun tasteBeer(beer: Beer): BeerWithTaste {
  return when {
    beer.Name.contains("dubbel", ignoreCase = true) -> DubbelBeer(beer)
    beer.Name.contains("tripel", ignoreCase = true) -> TrippelBeer(beer)
    else -> JustBeer(beer)
  }
}

infix fun String.looksLike(s: String) = this.contains(s, ignoreCase = true)


class D03_PatternMatching {
  @Test
  fun patternMatching() {

    val beersWithTaste = TestData.beerFlow.map(::tasteBeer).toList()

    beersWithTaste.filterIsInstance<TrippelBeer>().forEach {
      println("Tripper: $it")
    }

    beersWithTaste.filterIsInstance<DubbelBeer>().forEach {
      println("Dubbel: $it")
    }

    val westmalleBeers = beersWithTaste.filter { it.beer.Name looksLike "Brouwerij der Trappisten van Westmalle" }

    // Pattern matching (on a property, not on type):
    for (westmalleBeer in westmalleBeers) {
      when {
        westmalleBeer is DubbelBeer ->
          println(westmalleBeer.dubbelName)

        westmalleBeer is TrippelBeer && westmalleBeer.beer.Name.contains("tripel", ignoreCase = true) ->
          println(westmalleBeer.tripperName)

        else -> {
          //No beer today!
        }
      }
    }
  }
}
