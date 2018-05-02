package org.jetbrains.kotlincsharpdemo

import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.Closeable
import java.io.File


class BeersRepository {
  private val _breweries = HashSet<Brewery>()
  private val _beers = HashSet<Beer>()

  companion object {
    fun fromFile(file: File) = BeersRepository()
  }

  fun GetBrewery(breweryName: String): Brewery? {
    return _breweries.firstOrNull { brewery ->
      breweryName.equals(brewery.Name, ignoreCase = true)
    }
  }

  fun GetBreweries() = _breweries.asSequence()

  fun AddBrewery(brewery: Brewery) {
    _breweries += brewery
  }

  fun GetBeer(beerName: String): Beer? {
    return _beers.firstOrNull { beer ->
      beerName.equals(beer.Name, ignoreCase = true)
    }
  }

  fun GetBeers() = _beers.asSequence()

  fun AddBeer(beer: Beer) {
    _beers += beer
  }
}

inline fun <T, Y : Closeable> using(obj : Y, action:(Y) -> T) : T {
  try {
    return action(obj)
  } finally {
    obj.close()
  }
}

fun loadBeersFromFile(file : File) : BeersRepository {
  if (!file.isFile) {
    throw Error("Data file not found. $file");
  }

  val repository = BeersRepository()


  val om = ObjectMapper()

  using (om.factory.createParser(file)) { parser ->
    if (parser.nextToken() != JsonToken.START_ARRAY) throw error("Array was expected")

    while(parser.nextToken() == JsonToken.START_OBJECT) {
      val beerData = parser.readValueAsTree<ObjectNode>()

      val breweryName = beerData["brewery"].asText()
      val beerName = beerData["name"].asText()
      val rating = beerData["rating"].asDouble()
      val votes = beerData["votes"].asDouble()

      // Store the brewery
      // TODO DEMO: This get/add pattern is stupid!
      // TODO DEMO: Add GetHashCode to Brewery() and just call .Add on the underlying hashset
//      var brewery = repository.GetBrewery(breweryName)
//      if (brewery == null) {
        val brewery = Brewery(breweryName);
        repository.AddBrewery(brewery);
//      }

      // Store the beer
      // TODO DEMO: This get/add is needed to ensure no duplicates, however we could again do a GetHashCode() instead
//      var beer = repository.GetBeer(beerName);
//      if (beer == null) {
        val beer = Beer(beerName, brewery, rating, votes);
        repository.AddBeer(beer);
//      }

//                        // Store the brewery
//                        // TODO DEMO: This get/add pattern is stupid! Add GetHashCode to Brewery() and just call .Add on the underlying hashset
//                        // TODO DEMO: Null checks here are ugly, quick. Does Kotlin have anything better?
//                        Brewery brewery =  null;
//                        if (!string.IsNullOrEmpty(breweryName))
//                        {
//                            brewery = repository.GetBrewery(breweryName);
//                            if (brewery == null)
//                            {
//                                brewery = new Brewery(breweryName);
//                                repository.AddBrewery(brewery);
//                            }
//                        }
//
//                        // Store the beer
//                        // TODO DEMO: This get/add is needed to ensure no duplicates, however we could again do a GetHashCode() instead
//                        var beer = repository.GetBeer(beerName);
//                        if (beer == null)
//                        {
//                            beer = new Beer(beerName, brewery, rating, votes);
//                            repository.AddBeer(beer);
//                        }

//                        // Store the brewery
//                        // TODO DEMO: Null checks here are ugly, quick. Does Kotlin have anything better?
//                        var brewery = !string.IsNullOrEmpty(breweryName)
//                            ? new Brewery(breweryName)
//                            : null;
//                        repository.AddBrewery(brewery);
//
//                        // Store the beer
//                        var beer = new Beer(beerName, brewery, rating, votes);
//                        repository.AddBeer(beer);
    }
  }

  return repository;
}
