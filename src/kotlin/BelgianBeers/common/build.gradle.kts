import org.jetbrains.kotlin.gradle.dsl.Coroutines

plugins {
  base
  id("kotlin-platform-common")
}

dependencies {
  compile("org.jetbrains.kotlin:kotlin-stdlib-common")

  testCompile("org.jetbrains.kotlin:kotlin-test-common")
  testCompile("org.jetbrains.kotlin:kotlin-test-annotations-common")
}


kotlin {
  experimental.coroutines = Coroutines.ENABLE
}
