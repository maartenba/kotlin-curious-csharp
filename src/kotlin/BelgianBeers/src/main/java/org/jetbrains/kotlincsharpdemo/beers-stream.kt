package org.jetbrains.kotlincsharpdemo

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.File
import kotlin.coroutines.experimental.buildIterator

data class BeerItem(
        val name: String?,
        val brewery: String?,
        val rating: Double?,
        val votes: Double?
)

object BeersStream {

  fun fromString(text: String) = ObjectMapper().factory.createParser(text).streamData()

  fun fromFile(file: File): Sequence<BeerItem> = ObjectMapper().factory.createParser(file).streamData()


  fun JsonParser.streamData() = buildIterator {
    use { parser ->
      if (parser.nextToken() != JsonToken.START_ARRAY) throw error("Array was expected in JSON data")

      while (parser.nextToken() == JsonToken.START_OBJECT) {
        val beerData = parser.readValueAsTree<ObjectNode>()

        val breweryName = beerData["brewery"]?.asText()
        val beerName = beerData["name"]?.asText()
        val rating = beerData["rating"]?.asDouble()
        val votes = beerData["votes"]?.asDouble()

        yield(BeerItem(
                name = beerName,
                brewery = breweryName,
                rating = rating,
                votes = votes))
      }
    }
  }.asSequence()

}
