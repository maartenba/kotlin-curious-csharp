package org.jetbrains.kotlincsharpdemo

import java.io.File


fun main(args: Array<String>) {
  println("Hello!")

  println(File(".").canonicalFile)

  val sourceData = determineDataPath("beerswithnulls.json")

  val repository = loadBeersFromFile(sourceData)

  println("Loaded Brewery: beers=${repository.GetBeers().count()}, breweries=${repository.GetBreweries().count()}")

  // Filtering data:
  // Get beers with a rating > .50, and at least 10 votes for relevance
  val beersWithOkayRating = repository.GetBeers()
          .Where{ beer -> beer.Rating > .50 && beer.Votes >= 10}
          .ToList()

  // Get beers that are from brewery "Westmalle"
  // TODO DEMO: Needs null check - Brewery property can be null (use annotation so IDE warns us)
  val westmalleBeers = repository.GetBeers()
          .Where{beer -> "Brouwerij der Trappisten van Westmalle".equals(beer.Brewery.Name, ignoreCase = true)}
          .ToList()

  // TODO: Find an API that returns something around beer, so we can e.g. fetch images. Maybe Google Image API? Other?
}


fun <T> Sequence<T>.Where(predicate: (T) -> Boolean) = filter(predicate)
fun <T> Sequence<T>.ToList() = toList()


tailrec fun determineDataPath(fileName : String, base : File = File(".").canonicalFile) : File {
  val data = File(base, "data/$fileName")
  if (data.isFile) return data

  return determineDataPath(
          fileName = fileName,
          base = base.parentFile ?: error("Failed to find data $fileName"))
}
