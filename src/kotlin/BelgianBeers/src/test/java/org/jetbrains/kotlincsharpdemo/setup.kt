package org.jetbrains.kotlincsharpdemo

import java.io.File

object TestData {

  fun determineDataPath(name: String): File {
    return org.jetbrains.kotlincsharpdemo.determineDataPath(fileName = name)
  }

  val beerWithNulls by lazy { determineDataPath("beerswithnulls.json") }

  val beerFlow: Sequence<Beer>
    get() {
      val breweries = mutableMapOf<Brewery, Brewery>()

      return BeersStream.fromFile(beerWithNulls).mapNotNull { (beerName, breweryName, rating, votes) ->
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
