import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.frontend.KotlinFrontendExtension
import org.jetbrains.kotlin.gradle.frontend.npm.NpmExtension
import org.jetbrains.kotlin.gradle.frontend.util.frontendExtension
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackExtension
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
  base
  id("kotlin-platform-js")
  id("org.jetbrains.kotlin.frontend")
}


dependencies {
  expectedBy(project(":src:kotlin:BelgianBeers:common"))

  compile(kotlin("stdlib-js"))

  compile(kotlin("test-js"))
  testCompile(kotlin("test-js"))
}


kotlin {
  experimental.coroutines = Coroutines.ENABLE
}

tasks.withType<Kotlin2JsCompile> {
  kotlinOptions.metaInfo = true
  kotlinOptions.sourceMap = true
  kotlinOptions.moduleKind = "commonjs"
  kotlinOptions.main = "call"

  if (!name.contains("test", ignoreCase = true)) {
    kotlinOptions.outputFile = "${project.buildDir.path}/js/${project.name}.js"
  } else {
    kotlinOptions.outputFile = "${project.buildDir.path}/js-tests/${project.name}-tests.js"
  }
}

extensions.getByType(NpmExtension::class.java).apply {
  dependency("qunit")
  devDependency("karma")
}

extensions.getByType(KotlinFrontendExtension::class.java).apply {
//  downloadNodeJsVersion = "latest"
  define("PRODUCTION", false)
}
