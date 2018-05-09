package org.jetbrains.kotlincsharpdemo

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.kotlincsharpdemo.TestData.beerFlow
import org.junit.Test
import java.security.SecureRandom
import kotlin.coroutines.experimental.suspendCoroutine

class D06_Callbacks {
  val random = SecureRandom.getInstance("SHA1PRNG")

  val callbackPool = newFixedThreadPoolContext(5, "network")
  val runPool = newFixedThreadPoolContext(16, "network")

  fun queryBeerAcl(beer: Beer, callback: (Double) -> Unit) {
    async(callbackPool) {
      delay(random.nextDouble().run { 300 + this * 300 }.toInt())
      callback(random.nextDouble().run { this * this * 0.2 })
    }
  }

  @Test
  fun callback_per_beer() = runBlocking(runPool) {

    val allTasks = mutableListOf<Deferred<Pair<Beer, Double>>>()

    for (beer in beerFlow.toList()) {

      allTasks += async(runPool) {

        //query a remote service to get beer strongness
        val strong = suspendCoroutine<Double> { task ->

          //suspend execution to wait for the callback
          queryBeerAcl(beer) { strong ->
            task.resume(strong)
          }


        }

        return@async beer to strong
      }
    }

    println()
    println("Running ${allTasks.size} coroutines")
    println()


    logTime { allTasks.map { it.await() } }
            .sortedBy { -it.second }
            .take(10)
            .forEachIndexed { idx, it ->

              println("${idx.inc().toString().padStart(5)}. " +
                      "${it.second.toString().take(5).padStart(7)} " +
                      it.first.Name)
            }


    println()
    println()
  }
}