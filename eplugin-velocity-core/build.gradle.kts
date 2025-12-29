import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `java-library`
}

repositories {
    papermc()
}

dependencies {
    velocity()
    bstats(platform = "velocity")
    serializationCore("api")
    serializationJson("api")
    kaml("api")
}

java {
    withSourcesJar()
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}
