package org.jetbrains.kotlincsharpdemo

import org.junit.Assert
import org.junit.Test

class D01_LoadJson_Approach1 {

  @Test
  fun load() {
    val breweries = mutableMapOf<Brewery, Brewery>()
    val beers = LinkedHashSet<Beer>()

    for ((beerName, breweryName, rating, votes) in TestData.beers) {
      if (beerName == null) continue

      val brewery = if (breweryName == null) {
        null
      } else {
        val b = Brewery(breweryName)
        breweries.getOrPut(b) { b }
      }

      beers += Beer(beerName, brewery, rating ?: 0.0, votes ?: 0.0)
    }

    Assert.assertTrue(beers.any())
  }
}
