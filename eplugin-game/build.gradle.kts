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
    compileOnly("top.e404:DynamicMap:1.0.0")
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