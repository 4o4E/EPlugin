import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `java-library`
}

repositories {
    maven("https://repo.rosewooddev.io/repository/public/")
}

dependencies {
    spigot()
    eplugin()
    eplugin("serialization")
    // player particles
    compileOnly("dev.esophose:playerparticles:8.4")
}

java {
    withSourcesJar()
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
