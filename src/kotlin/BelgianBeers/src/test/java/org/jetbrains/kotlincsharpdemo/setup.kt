package org.jetbrains.kotlincsharpdemo

import java.io.File

object TestData {

  fun determineDataPath(name: String): File {
    return org.jetbrains.kotlincsharpdemo.determineDataPath(fileName = name)
  }

  val beerWwithNulls by lazy { determineDataPath("beerswithnulls.json") }
}
