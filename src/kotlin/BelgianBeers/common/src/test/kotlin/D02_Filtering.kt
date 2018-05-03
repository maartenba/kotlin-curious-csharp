import org.jetbrains.kotlincsharpdemo.Beer
import org.jetbrains.kotlincsharpdemo.TestData
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







  fun Sequence<Beer>.filterBeer(f: Beer.() -> Boolean) = filter { it.f() }

  @Test
  fun linqDSL_ex() {

    val beersWithOkayRating =

            TestData.beerFlow
                    .filterBeer { Rating > .50 && Votes >= 10 }
                    .toList()

    assertTrue(beersWithOkayRating.any())
  }





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
