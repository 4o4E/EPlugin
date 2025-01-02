import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    engineHub()
}

dependencies {
    spigot()
    eplugin()
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
