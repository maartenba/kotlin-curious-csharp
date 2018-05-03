package org.jetbrains.kotlincsharpdemo

import kotlin.test.Test
import kotlin.test.assertTrue

class D02_Filtering {
  @Test
  fun linqDSL() {
    /*
    *   // Filtering data with a DSL - Get beers with a rating > .50, and at least 10 votes for relevance
            var beersWithOkayRating = from beer in repository.GetBeers()
                where beer.Rating > .50 && beer.Votes >= 10
                select beer;

            Assert.True(beersWithOkayRating.Any());
    */

    val beersWithOkayRating =

            TestData.beerFlow
                    .filter { it.Rating > .50 && it.Votes >= 10 }
                    .toList()

    assertTrue(beersWithOkayRating.any());
  }








  @Test
  fun linqDSL_ex() {

    val beersWithOkayRating =

            TestData.beerFlow
                    .filterBeer { Rating > .50 && Votes >= 10 }
                    .toList()

    assertTrue(beersWithOkayRating.any())
  }

}

fun Sequence<Beer>.filterBeer(f: Beer.() -> Boolean) = filter { it.f() }
