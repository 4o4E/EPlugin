import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    lumine()
}

dependencies {
    spigot()
    eplugin()
    mythicMobs()
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
