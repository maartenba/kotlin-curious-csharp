package org.jetbrains.kotlincsharpdemo

import org.junit.Test
import kotlin.test.assertTrue


class D02_FilteringEx {
  val Beer.`Rating is OK` get() = Rating > .50
  val Beer.Popular get() = Votes > .50

  @Test
  fun linqDSL_ex2() {
    val beersWithOkayRating =

            TestData.beerFlow
                    .filterBeer { `Rating is OK` and Popular }
                    .toList()

    assertTrue(beersWithOkayRating.any())
  }
}
