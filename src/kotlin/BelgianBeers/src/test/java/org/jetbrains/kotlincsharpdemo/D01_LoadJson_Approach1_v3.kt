package org.jetbrains.kotlincsharpdemo

import org.jetbrains.kotlincsharpdemo.BeersStream.fromFile
import org.jetbrains.kotlincsharpdemo.TestData.beerWithNulls
import org.junit.Assert

import org.junit.Test

class D01_LoadJson_Approach1_v3 {
  @Test
  fun load() {
    val breweries = mutableMapOf<Brewery, Brewery>()

    val beers = fromFile(beerWithNulls).mapNotNull { (beerName, breweryName, rating, votes) ->
      beerName?.let {
        Beer(
                Name = beerName,
                Brewery = breweryName?.let { Brewery(it) }?.let { breweries.getOrPut(it) { it } },
                Rating = rating ?: 0.0,
                Votes = votes ?: 0.0)
      }
    }.toSet()

    Assert.assertTrue(beers.any())
  }
}
