import org.jetbrains.kotlincsharpdemo.TestData.beerFlow
import kotlin.test.Test

class D04_Statistics {

  inline fun <T> Iterable<T>.avg(f : T.() -> Double) : Double {
    var d = 0.0
    var c = 0
    forEach {
      c++
      d += it.f()
    }
    return if (c == 0) 0.0 else d / c
  }


  @Test
  fun statistics() {

    /// from beer in repository.GetBeers()
    /// where beer.Brewery != null
    /// group beer by beer.Brewery into beersPerBrewery
    ///         orderby beersPerBrewery.Average(beer => beer.Rating) descending
    /// select beersPerBrewery.Key;
    /// take 10

    beerFlow
            .filter { it.Brewery != null }
            .groupBy { it.Brewery }
            .entries
            .sortedByDescending { it.value.avg { Rating } }
            .mapNotNull { it.key }
            .take(10)
            .forEachIndexed { idx, it ->
              println("${idx.toString().padStart(5)}. ${it.Name}")
            }

  }
}

