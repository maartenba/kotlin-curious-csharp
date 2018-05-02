package org.jetbrains.kotlincsharpdemo

import org.jetbrains.kotlincsharpdemo.BeersStream.fromFile
import org.jetbrains.kotlincsharpdemo.TestData.beerWithNulls
import org.junit.Assert

import org.junit.Test

class D01_LoadJson_Approach1_v1 {
  @Test
  fun load() {
    val breweries = mutableMapOf<Brewery, Brewery>()

    val beers = fromFile(beerWithNulls).mapNotNull { (beerName, breweryName, rating, votes)  ->
      if (beerName == null) return@mapNotNull null

      val brewery = if (breweryName == null) {
        null
      } else {
        val b = Brewery(breweryName)
        breweries.getOrPut(b) { b }
      }

      Beer(beerName, brewery, rating ?: 0.0, votes ?: 0.0)
    }.toList()

    Assert.assertTrue(beers.any())
  }
}
