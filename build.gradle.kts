
buildscript {

  repositories {
    jcenter()
  }

  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.2.41")
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
