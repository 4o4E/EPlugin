import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `java-library`
}

repositories {
    lumine()
    phoenix()
    // minecraft
    maven("https://libraries.minecraft.net")
}

dependencies {
    spigot()
    eplugin()
    eplugin("serialization")
    eplugin("reflect")
    mythicLib()
    mmoitems()
    // authlib
    compileOnly("com.mojang:authlib:3.11.49")
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
