// Extracts config data from `gradle.properties` file.
fun properties(key: String) = providers.gradleProperty(key)

plugins {
    id("java")
    id("org.jetbrains.intellij") version ("1.17.1")
    id("org.jetbrains.kotlin.jvm") version ("1.9.22")
}

group = properties("pluginGroup").get()
version = properties("pluginVersion").get()

repositories {
    mavenCentral()
    gradlePluginPortal()
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version.set("231.9392.1")
    type.set("IC")
    plugins.set(listOf("android"))
}

tasks {
    runIde {
        ideDir.set(file("/Applications/Android Studio.app/Contents"))
    }
}