package org.jetbrains.kotlincsharpdemo

import org.junit.Assert
import org.junit.Test

class D01_LoadJson {

  @Test
  fun load() {
    val sourceData = TestData.beerWwithNulls
    val repository = BeersRepository.fromFile(sourceData);

    Assert.assertTrue(repository.GetBeers().any())
  }

}
