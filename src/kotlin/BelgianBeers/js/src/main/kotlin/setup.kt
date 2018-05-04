package org.jetbrains.kotlincsharpdemo

import kotlin.coroutines.experimental.buildIterator

external fun require(module:String):dynamic

actual object TestDataPlatform {

  actual val beers: Sequence<BeerItem>
    get() {
      val data = require("./beerswithnulls.json")

      return Sequence {
        buildIterator {

          for (beerData in data) {
            val breweryName = beerData["brewery"]
            val beerName = beerData["name"]
            val rating = beerData["rating"]
            val votes = beerData["votes"]

            yield(BeerItem(
                    name = beerName,
                    brewery = breweryName,
                    rating = rating,
                    votes = votes))
          }
        }
      }
    }
}
