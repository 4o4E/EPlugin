import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `java-library`
}

dependencies {
    spigot()
    eplugin()
    // mysql 8.0.30 5.1.49
    api("mysql:mysql-connector-java:8.0.30")
    // hikari
    api("com.zaxxer:HikariCP:4.0.3")
    // commons-lang3
    api("org.apache.commons:commons-lang3:3.12.0")
    // serialization
    serializationCore("compileOnly")
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
