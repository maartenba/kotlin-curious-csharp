package org.jetbrains.kotlincsharpdemo

import org.intellij.lang.annotations.Language
import org.jetbrains.kotlincsharpdemo.JsonDSL.json
import org.junit.Test

class D01_LoadJson_Invalid {


  @Test
  fun testObject() {
    BeersStream.fromString("{}")
  }

  @Test
  fun testEmptyArray() {
    BeersStream.fromString("[]")
  }

  @Test
  fun testNulls() {
    @Language("JSON")
    val json = "[\n  {}\n]"

    BeersStream.fromString(json)
  }

  @Test
  fun testNullsFields() {
    val json = "[{\"name\": null, " +
            "\"brewery\": null, " +
            "\"votes\": null, " +
            "\"rating\": null}]"

    BeersStream.fromString(json)
  }

  @Test
  fun testDSLFields() {
    val text = json {
      array {
        obj {
          "name" % "Kotlin"
          "brewery" % "DSL"
          "votes" % 42
          "rating" % 777
        }
        obj {
          "name" % "Kotlin"
          "brewery" % "DSL"
          "votes" % 412
          "rating" % NULL
        }

        add(NULL)
      }
    }

    println(text)

    BeersStream.fromString(text)
  }

}



