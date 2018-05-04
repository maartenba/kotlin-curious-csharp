import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
  base
  id("kotlin-platform-jvm")
}


dependencies {
  expectedBy(project(":src:kotlin:BelgianBeers:common"))

  compile(kotlin("stdlib-jdk8"))
  compile(kotlin("reflect"))

  compile(kotlin("test-junit"))


  compile("com.fasterxml.jackson.core:jackson-core:2.9.4")
  compile("com.fasterxml.jackson.core:jackson-databind:2.9.4")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.4")


  compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:0.22.5")


  testCompile("junit:junit:4.12")
}


kotlin {
  experimental.coroutines = Coroutines.ENABLE
}
