plugins {
    id("org.jetbrains.intellij") version "1.17.1"
    id("org.jetbrains.kotlin.jvm") version "1.9.22"
}

group = "team.idivision.plugin.font_importer"
version = "1.0-alpha05"

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
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