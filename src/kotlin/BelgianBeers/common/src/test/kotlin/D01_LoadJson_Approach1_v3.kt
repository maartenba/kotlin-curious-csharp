package org.jetbrains.kotlincsharpdemo

import kotlin.test.Test
import kotlin.test.assertTrue

class D01_LoadJson_Approach1_v3 {

  @Test
  fun load() {
    val breweries = mutableMapOf<Brewery, Brewery>()

    val beers = TestData.beers.mapNotNull { (beerName, breweryName, rating, votes) ->
      beerName?.let {
        Beer(
                Name = beerName,
                Brewery = breweryName?.let { Brewery(it) }?.let { breweries.getOrPut(it) { it } },
                Rating = rating ?: 0.0,
                Votes = votes ?: 0.0)
      }
    }.toSet()

    assertTrue(beers.any())
  }
}
