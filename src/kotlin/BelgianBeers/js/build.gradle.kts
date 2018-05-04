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
  testCompile(kotlin("test-js"))
}


kotlin {
  experimental.coroutines = Coroutines.ENABLE
}

tasks.withType<Kotlin2JsCompile> {
  kotlinOptions.metaInfo = true
  kotlinOptions.outputFile = "${project.buildDir.path}/js/${project.name}-${name}.js"
  kotlinOptions.sourceMap = true
  kotlinOptions.moduleKind = "commonjs"
  kotlinOptions.main = "call"
}

extensions.getByType(NpmExtension::class.java).apply {
  dependency("qunit")
  devDependency("karma")
}

