import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    spigot()
    eplugin()
    // ProtocolLib
    compileOnly("com.comphenix.protocol:ProtocolLib:5.3.0")
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
