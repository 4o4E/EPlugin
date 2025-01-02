import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    // playerpoints
    maven("https://repo.rosewooddev.io/repository/public/")
}

dependencies {
    spigot()
    eplugin()
    // playerpoints
    compileOnly("org.black_ixx:playerpoints:${Hooks.playerPoints}")
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
