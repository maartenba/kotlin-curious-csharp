package org.jetbrains.kotlincsharpdemo

import java.io.File

object TestData {

  tailrec fun determineDataPath(fileName: String,
                                base: File = File("").canonicalFile): File {

    val data = File(base, "data/$fileName")
    if (data.isFile) return data

    return determineDataPath(
            fileName = fileName,
            base = base.parentFile ?: error("Failed to find data $fileName"))
  }

  val beers : Sequence<BeerItem>
    get() = BeersStream.fromFile(determineDataPath("beerswithnulls.json"))

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


inline fun <R> logTime(block: () -> R): R {
  val start = System.currentTimeMillis()
  val r = block()
  val time = System.currentTimeMillis() - start

  println()
  println("Execution time was: $time ms")
  println()

  return r
}
