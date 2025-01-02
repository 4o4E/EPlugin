import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    jitpack()
}

dependencies {
    spigot()
    eplugin()
    // slimefun
    compileOnly("com.github.Slimefun:Slimefun4:${Hooks.slimefun}")
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
