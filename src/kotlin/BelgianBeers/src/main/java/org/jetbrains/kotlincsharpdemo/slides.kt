package org.jetbrains.kotlincsharpdemo


fun hi(ƒ: (Int) -> Int)            {

  //call
  hi({ name -> name + 1 })

  //last lambda, no ()!
  hi { it + 1 }


  //extension function (property)
  fun Int.five(): Int = 5 + this



  fun wow(ƒ: Int.() -> String) {
    //call extension λ func ƒ
    5.ƒ()
  }
  //call λ with receiver
  wow { this.toString() }
  //same (this === 5)
  wow { toString() }







}

