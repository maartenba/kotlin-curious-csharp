package org.jetbrains.kotlincsharpdemo

import org.jetbrains.kotlincsharpdemo.BeersStream.fromFile
import org.jetbrains.kotlincsharpdemo.TestData.beerWithNulls
import org.junit.Assert

import org.junit.Test

class D01_LoadJson_Approach1 {

  @Test
  fun load() {
    val breweries = mutableMapOf<String, Brewery>()
    val beerNames = mutableSetOf<String>()
    val beers = mutableListOf<Beer>()

    for ((beerName, breweryName, rating, votes) in fromFile(beerWithNulls)) {
      if (beerName == null) continue

      if (!beerNames.add(beerName)) continue

      val brewery = if (breweryName == null) {
        null
      } else {
        breweries.getOrPut(breweryName) { Brewery(breweryName) }
      }

      beers += Beer(beerName, brewery, rating ?: 0.0, votes ?: 0.0)
    }

    Assert.assertTrue(beers.any())
  }
}
