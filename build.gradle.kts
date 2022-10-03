import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin
}

group = Versions.group
version = Versions.version

repositories {
    // spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    mavenCentral()
    mavenLocal()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}