package org.jetbrains.kotlincsharpdemo

import org.jetbrains.kotlincsharpdemo.BeersStream.fromFile
import org.jetbrains.kotlincsharpdemo.TestData.beerWithNulls
import org.junit.Assert

import org.junit.Test

class D01_LoadJson_Approach1x {
  @Test
  fun load() {
    val breweries = mutableMapOf<String, Brewery>()
    val beerNames = mutableSetOf<String>()

    val beers = fromFile(beerWithNulls).mapNotNull { (beerName, breweryName, rating, votes)  ->
      if (beerName == null) return@mapNotNull null

      if (!beerNames.add(beerName)) return@mapNotNull null

      val brewery = if (breweryName == null) {
        null
      } else {
        breweries.getOrPut(breweryName) { Brewery(breweryName) }
      }

      Beer(beerName, brewery, rating ?: 0.0, votes ?: 0.0)
    }.toList()

    Assert.assertTrue(beers.any())
  }
}
