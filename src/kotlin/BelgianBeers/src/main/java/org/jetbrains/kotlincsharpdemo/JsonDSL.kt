package org.jetbrains.kotlincsharpdemo

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.StringWriter

object JsonDSL {
  object TNULL

  class JsonObjectBuilder(private val mapper: ObjectMapper,
                          private val node: ObjectNode) {
    val NULL get() = TNULL

    operator fun String.rem(value: TNULL) { node.set(this, mapper.nodeFactory.nullNode()) }
    operator fun String.rem(value: JsonNode) { node.set(this, value) }
    operator fun String.rem(value: String) { node.put(this, value) }
    operator fun String.rem(value: Int) { node.put(this, value) }
    operator fun String.rem(value: Boolean) { node.put(this, value) }

    fun obj(builder: JsonObjectBuilder.() -> Unit) = buildJsonObjectImpl(builder, mapper)
    fun array(builder: JSONArrayBuilder.() -> Unit) = buildJsonArrayImpl(builder, mapper)
  }

  class JSONArrayBuilder(private val mapper: ObjectMapper,
                         private val node: ArrayNode) {
    val NULL get() = TNULL

    fun add(value: Int) { node.add(value) }
    fun add(value: String) { node.add(value) }
    fun add(value: Boolean) { node.add(value) }
    fun add(value: JsonNode) { node.add(value) }
    fun add(value: TNULL) { node.add(mapper.nodeFactory.nullNode()) }

    fun obj(builder: JsonObjectBuilder.() -> Unit) { node.add(buildJsonObjectImpl(builder, mapper)) }
    fun array(builder: JSONArrayBuilder.() -> Unit) { node.add(buildJsonArrayImpl(builder, mapper)) }
  }

  class JsonRootBuilder {
    private val mapper = ObjectMapper()
    internal lateinit var text : String

    fun obj(builder: JsonObjectBuilder.() -> Unit) {
      text = buildJsonObjectImpl(builder, mapper).toPrettyString
    }

    fun array(builder: JSONArrayBuilder.() -> Unit) {
      text = buildJsonArrayImpl(builder, mapper).toPrettyString
    }

    private val JsonNode.toPrettyString
      get() = StringWriter().use { sw ->
        mapper.factory.createGenerator(sw).useDefaultPrettyPrinter().use { it.writeTree(this) }
        sw.toString()
      }
  }

  fun json(builder : JsonRootBuilder.() -> Unit) : String {
    val host = JsonRootBuilder()
    host.builder()
    return host.text
  }

  private fun buildJsonObjectImpl(builder : JsonObjectBuilder.() -> Unit, mapper : ObjectMapper) : JsonNode {
    val node = mapper.nodeFactory.objectNode()
    JsonObjectBuilder(mapper, node).builder()
    return node
  }

  private fun buildJsonArrayImpl(builder: JSONArrayBuilder.() -> Unit, mapper: ObjectMapper) : JsonNode {
    val node = mapper.nodeFactory.arrayNode()
    JSONArrayBuilder(mapper, node).builder()
    return node
  }
}
