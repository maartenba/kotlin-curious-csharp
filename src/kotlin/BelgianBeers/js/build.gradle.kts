import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.frontend.KotlinFrontendExtension
import org.jetbrains.kotlin.gradle.frontend.config.BundleConfig
import org.jetbrains.kotlin.gradle.frontend.npm.NpmExtension
import org.jetbrains.kotlin.gradle.frontend.util.frontendExtension
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackBundler
import org.jetbrains.kotlin.gradle.frontend.webpack.WebPackExtension
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile

plugins {
  base
  id("kotlin-platform-js")
  id("org.jetbrains.kotlin.frontend")
}

operator fun File.div(s:String) = File(this, s)

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
    kotlinOptions.outputFile = "${project.buildDir}/js/js.js"
  } else {
    kotlinOptions.outputFile = "${project.buildDir}/js/js-tests.js"
  }
}

extensions.getByType(NpmExtension::class.java).apply {
  dependency("qunit")
  devDependency("karma")
}

extensions.getByType(KotlinFrontendExtension::class.java).apply {
//  downloadNodeJsVersion = "latest"
  define("PRODUCTION", false)

  bundle<BundleConfig>("webpack") {
    this as WebPackExtension

    bundleName = "main"
    contentPath = rootProject.file("data")
  }
}


task<Copy>(name = "copyJSONDataToBuild") {
  from(fileTree(rootProject.file("data")))
  into("${project.buildDir.path}/js")
}

tasks.getByName("classes").dependsOn("copyJSONDataToBuild")



task<Exec>(name = "run_JS_Program") {
  dependsOn("classes", "copyJSONDataToBuild")

  //TODO: reuse path from the plugin
  commandLine(listOf(
          "node",
          "js/js.js"
  ))

  workingDir(buildDir)
}


task<Exec>("run_Qunit") {
  dependsOn(listOf("compileTestKotlin2Js", "copyJSONDataToBuild"))
  commandLine(listOf(
          "node",
          "node_modules/qunit/bin/qunit",
          "js/js-tests.js"
  ))

  environment("NODE_PATH", "$buildDir:$buildDir/js:$buildDir/node_modules")

  workingDir(buildDir)
}
