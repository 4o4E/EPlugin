import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    // placeholderAPI
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    spigot()
    eplugin()
    // placeholderAPI
    compileOnly("me.clip:placeholderapi:2.11.6")
}

java {
    withSourcesJar()
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}
