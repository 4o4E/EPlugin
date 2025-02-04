import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `java-library`
}

dependencies {
    spigot()
    implementation(project(":eplugin-core")) {
        exclude("org.spigotmc", "spigot-api")
    }
    serializationCore("api")
    serializationJson("api")
    kaml("api")
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