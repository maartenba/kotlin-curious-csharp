import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
  java
  kotlin("jvm")
}

dependencies {

}


kotlin {
  experimental.coroutines = Coroutines.ENABLE
}
