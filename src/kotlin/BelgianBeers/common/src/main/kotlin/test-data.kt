package org.jetbrains.kotlincsharpdemo


object TestData {
  val beers: Sequence<BeerItem>
    get() = TestDataPlatform.beers

  val beerFlow: Sequence<Beer>
    get() {
      val breweries = mutableMapOf<Brewery, Brewery>()

      return beers.mapNotNull { (beerName, breweryName, rating, votes) ->
        beerName?.let {
          Beer(
                  Name = beerName,
                  Brewery = breweryName?.let { Brewery(it) }?.let { breweries.getOrPut(it) { it } },
                  Rating = rating ?: 0.0,
                  Votes = votes ?: 0.0)
        }
      }
    }
}


expect object TestDataPlatform {
  val beers: Sequence<BeerItem>
}
