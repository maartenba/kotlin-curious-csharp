import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
  base
  id("kotlin-platform-js")
}


dependencies {
  add("expectedBy", project(":src:kotlin:BelgianBeers:common"))


  compile(kotlin("stdlib-js"))

}


kotlin {
  experimental.coroutines = Coroutines.ENABLE
}

