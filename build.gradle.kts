
plugins {
  kotlin("jvm") version "1.2.41" apply false
}

subprojects {
  repositories {
    mavenCentral()
    jcenter()
  }


  group = rootProject.group
  version = rootProject.version
}
