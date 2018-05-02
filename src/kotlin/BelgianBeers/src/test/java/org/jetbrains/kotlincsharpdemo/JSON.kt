package org.jetbrains.kotlincsharpdemo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.IOException
import java.io.StringWriter

object JSON {
  object TNULL

  interface JSONBuilder {
    val NULL get() = TNULL

    operator fun String.rem(value : TNULL)
    operator fun String.rem(value : String)
    operator fun String.rem(value : Int)
    operator fun String.rem(value : Boolean)
    operator fun String.rem(value : JsonNode)
    operator fun String.rem(value : JSONBuilder.() -> Unit) = rem(obj(value))

    fun obj(builder : JSONBuilder.() -> Unit) : JsonNode
    fun array(builder : JSONArrayBuilder.() -> Unit) : JsonNode
  }

  interface JSONArrayBuilder {
    operator fun Int.unaryPlus() = add(this)
    operator fun String.unaryPlus() = add(this)
    operator fun Boolean.unaryPlus() = add(this)
    operator fun JsonNode.unaryPlus() = add(this)
    operator fun (JSONBuilder.() -> Unit).unaryPlus() = add(this)

    fun obj(builder : JSONBuilder.() -> Unit) : JsonNode

    fun add(value : Int)
    fun add(value : String)
    fun add(value : Boolean)
    fun add(value : JsonNode)
    fun add(builder : JSONBuilder.() -> Unit) = add(obj(builder))
  }

  fun build(builder : JSONBuilder.() -> Unit) : String = to_s(buildJSON(builder))

  fun buildJSON(builder : JSONBuilder.() -> Unit) : JsonNode = buildJSONImpl(builder, ObjectMapper())

  private fun buildJSONImpl(builder : JSONBuilder.() -> Unit, mapper : ObjectMapper) : JsonNode = mapper.nodeFactory.objectNode().apply {
    object: JSONBuilder {
      override fun String.rem(value: TNULL) { set(this, mapper.nodeFactory.nullNode()) }
      override fun String.rem(value: JsonNode) { set(this, value) }
      override fun String.rem(value: String) { put(this, value) }
      override fun String.rem(value: Int) { put(this, value) }
      override fun String.rem(value: Boolean) { put(this, value) }
      override fun obj(builder: JSONBuilder.() -> Unit) = buildJSONImpl(builder, mapper)
      override fun array(builder: JSONArrayBuilder.() -> Unit) = mapper.nodeFactory.arrayNode().apply {
        val that = this
        object:JSONArrayBuilder {
          override fun add(value: Int) { that.add(value) }
          override fun add(value: String) { that.add(value) }
          override fun add(value: Boolean) { that.add(value) }
          override fun add(value: JsonNode) { that.add(value) }

          override fun obj(builder: JSONBuilder.() -> Unit): JsonNode = buildJSONImpl(builder, mapper)
        }.builder()
      }
    }.builder()
  }

  fun to_s(s: JsonNode) = StringWriter().use { sw ->
    ObjectMapper().factory.createGenerator(sw).useDefaultPrettyPrinter().use { it.writeTree(s) }
    sw.toString()
  }
}