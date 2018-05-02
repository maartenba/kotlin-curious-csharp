package org.jetbrains.kotlincsharpdemo

import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.File
import kotlin.coroutines.experimental.buildIterator

fun streamBeersFromFile(file: File) = buildIterator {
  if (!file.isFile) {
    throw Error("Data file not found. $file");
  }

  val om = ObjectMapper()

  using(om.factory.createParser(file)) { parser ->
    if (parser.nextToken() != JsonToken.START_ARRAY) throw error("Array was expected in JSON data")

    while (parser.nextToken() == JsonToken.START_OBJECT) {
      val beerData = parser.readValueAsTree<ObjectNode>()

      val breweryName = beerData["brewery"]?.asText()
      val beerName = beerData["name"].asText()
      val rating = beerData["rating"].asDouble()
      val votes = beerData["votes"].asDouble()

      yield(Beer(
              Name = beerName,
              Brewery = breweryName?.let { Brewery(it) },
              Rating = rating,
              Votes = votes))
    }
  }
}
