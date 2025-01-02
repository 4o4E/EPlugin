import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    taboo()
}

dependencies {
    spigot()
    eplugin()
    // ady
    compileOnly("ink.ptms.adyeshach:all:2.0.0-snapshot-10")
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