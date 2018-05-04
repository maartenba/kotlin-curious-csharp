package org.jetbrains.kotlincsharpdemo

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.kotlincsharpdemo.TestData.beerFlow
import org.junit.Test
import java.security.SecureRandom

class D05_Async {
  val random = SecureRandom.getInstance("SHA1PRNG")

  suspend fun queryBeerAcl(beer: Beer): Double {
    delay(random.nextDouble().run { 300 + this * 300 }.toInt())
    return random.nextDouble().run { this * this * 0.2 }
  }

  @Test
  fun coroutine_per_beer() = runBlocking(CommonPool) {

    val allTasks = mutableListOf<Deferred<Pair<Beer, Double>>>()

    for (beer in beerFlow.toList()) {

      allTasks += async {

        //query a remote service to get beer strongness
        val strong = queryBeerAcl(beer)

        return@async beer to strong
      }

    }

    println()
    println("Running ${allTasks.size} coroutines")
    println()


    logTime {  allTasks.map { it.await() } }
            .sortedBy { -it.second }
            .take(10)
            .forEachIndexed { idx, it ->

              println("${idx.inc().toString().padStart(5)}. " +
                      "${it.second.toString().padStart(10)} " +
                      it.first.Name)
            }


    println()
    println()
  }
}

inline fun <R> logTime(block: () -> R): R {
  val start = System.currentTimeMillis()
  val r = block()
  val time = System.currentTimeMillis() - start

  println()
  println("Execution time was: $time ms")
  println()

  return r
}
