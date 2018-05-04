
buildscript {

  repositories {
    jcenter()
    mavenCentral()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
  }

  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.41")
    classpath("org.jetbrains.kotlin:kotlin-frontend-plugin:0.0.30")
  }
}

subprojects {
  repositories {
    mavenCentral()
    jcenter()
  }


  group = rootProject.group
  version = rootProject.version
}
