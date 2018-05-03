import org.jetbrains.kotlincsharpdemo.Beer
import org.jetbrains.kotlincsharpdemo.TestData
import kotlin.test.Test

sealed class BeerWithTaste(val beer: Beer)

class JustBeer(beer: Beer) : BeerWithTaste(beer) {
  override fun toString() = "JustBeer($beer)"
}

class DubbelBeer(beer: Beer) : BeerWithTaste(beer) {
  val dubbelName get() = beer.Name
  override fun toString() = "BubbelBeer($beer)"
}

class TrippelBeer(beer: Beer) : BeerWithTaste(beer) {
  override fun toString() = "TrippelBeer($beer)"
}

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
