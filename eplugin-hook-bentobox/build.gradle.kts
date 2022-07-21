import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "top.e404"
version = "1.0.0"

repositories {
    // spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    // BentBox
    maven("https://repo.codemc.org/repository/maven-public/")
    mavenCentral()
    mavenLocal()
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    // BentBox
    compileOnly("world.bentobox:bentobox:1.20.1-SNAPSHOT")
    // eplugin
    implementation(rootProject)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.shadowJar {
    doLast {
        println("==== copy ====")
        for (file in File("build/libs").listFiles() ?: emptyArray()) {
            println("正在复制`${file.path}`")
            file.copyTo(File("jar/${file.name}"), true)
        }
    }
}