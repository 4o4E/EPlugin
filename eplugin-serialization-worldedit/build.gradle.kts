import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `java-library`
}

repositories {
    engineHub()
}

dependencies {
    spigot()
    eplugin()
    eplugin("hook-worldguard")
    eplugin("serialization")
    worldeditCore()
    worldeditBukkit()
}

java {
    withSourcesJar()
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}
