package org.jetbrains.kotlincsharpdemo

import org.junit.Test

class D02_loadInvalidJSON {


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
    BeersStream.fromString("[{}]")
  }

  @Test
  fun testNullsFields() {
    BeersStream.fromString("[{\"name\": null, \"brewery\": null, \"votes\": null, \"rating\": null}]")
  }

  @Test
  fun testDSLFields() {
    val text = JsonDSL.build {
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



