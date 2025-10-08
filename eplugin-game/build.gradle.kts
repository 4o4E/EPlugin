import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `java-library`
}

repositories {
    // papermc
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // paper
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    // eplugin
    eplugin()
    eplugin("serialization")
    compileOnly("top.e404:DynamicMap:1.0.0-SNAPSHOT")
    // coroutine
    compileOnly("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.20.0")
    compileOnly("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.20.0")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
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