import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    `java-library`
}

repositories {
    // nova
    maven("https://repo.xenondevs.xyz/releases/")
}

dependencies {
    spigot()
    eplugin()
    // nova
    compileOnly("xyz.xenondevs.nova:nova-api:${Hooks.nova}")
    compileOnly("xyz.xenondevs.nova:nova:${Hooks.nova}")
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
