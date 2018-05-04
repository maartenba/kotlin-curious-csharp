package org.jetbrains.kotlincsharpdemo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.StringWriter

object JsonDSL {
  object TNULL

  @DslMarker annotation class ArrayBuilder
  @DslMarker annotation class ObjectBuilder
  @DslMarker annotation class RootBuilder

  @RootBuilder
  interface JsonRootBuilder {
    fun obj(builder: JsonObjectBuilder.() -> Unit)
    fun array(builder: JSONArrayBuilder.() -> Unit)
  }

  fun json(builder : JsonRootBuilder.() -> Unit) : String {
    val om = ObjectMapper()

    lateinit var text : String

    object: JsonRootBuilder {
      override fun obj(builder: JsonObjectBuilder.() -> Unit) {
        text = buildJsonObjectImpl(builder, om).toPrettyString
      }

      override fun array(builder: JSONArrayBuilder.() -> Unit) {
        text = buildJsonArrayImpl(builder, om).toPrettyString
      }
    }.builder()

    return text
  }

  @ObjectBuilder
  interface JsonObjectBuilder {
    val NULL get() = TNULL

    operator fun String.rem(value : TNULL)
    operator fun String.rem(value : String)
    operator fun String.rem(value : Int)
    operator fun String.rem(value : Boolean)
    operator fun String.rem(value : JsonNode)

    fun obj(builder : JsonObjectBuilder.() -> Unit) : JsonNode
    fun array(builder : JSONArrayBuilder.() -> Unit) : JsonNode
  }

  @ArrayBuilder
  interface JSONArrayBuilder {
    val NULL get() = TNULL

    fun obj(builder : JsonObjectBuilder.() -> Unit)
    fun array(builder : JSONArrayBuilder.() -> Unit)

    fun add(value : TNULL)
    fun add(value : Int)
    fun add(value : String)
    fun add(value : Boolean)
    fun add(value : JsonNode)
  }

  private fun buildJsonObjectImpl(builder : JsonObjectBuilder.() -> Unit, mapper : ObjectMapper) : JsonNode
          = mapper.nodeFactory.objectNode().also { node ->

    object: JsonObjectBuilder {
      override fun String.rem(value: TNULL) { node.set(this, mapper.nodeFactory.nullNode()) }
      override fun String.rem(value: JsonNode) { node.set(this, value) }
      override fun String.rem(value: String) { node.put(this, value) }
      override fun String.rem(value: Int) { node.put(this, value) }
      override fun String.rem(value: Boolean) { node.put(this, value) }

      override fun obj(builder: JsonObjectBuilder.() -> Unit) = buildJsonObjectImpl(builder, mapper)
      override fun array(builder: JSONArrayBuilder.() -> Unit) = buildJsonArrayImpl(builder, mapper)
      }.builder()
  }

  private fun buildJsonArrayImpl(builder: JSONArrayBuilder.() -> Unit, mapper: ObjectMapper) : JsonNode
          = mapper.nodeFactory.arrayNode().also { node ->
    object: JSONArrayBuilder {
      override fun add(value: Int) { node.add(value) }
      override fun add(value: String) { node.add(value) }
      override fun add(value: Boolean) { node.add(value) }
      override fun add(value: JsonNode) { node.add(value) }
      override fun add(value: TNULL) { node.add(mapper.nodeFactory.nullNode()) }

      override fun obj(builder: JsonObjectBuilder.() -> Unit) { node.add(buildJsonObjectImpl(builder, mapper)) }
      override fun array(builder: JSONArrayBuilder.() -> Unit) { node.add(buildJsonArrayImpl(builder, mapper)) }
    }.builder()
  }

  val JsonNode.toPrettyString
          get() = StringWriter().use { sw ->
    ObjectMapper().factory.createGenerator(sw).useDefaultPrettyPrinter().use { it.writeTree(this) }
    sw.toString()
  }
}