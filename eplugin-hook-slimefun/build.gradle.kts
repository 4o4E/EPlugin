import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    `maven-publish`
    `java-library`
}

group = Versions.group
version = Versions.version

repositories {
    // spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    // sf
    maven("https://jitpack.io")
    mavenCentral()
    mavenLocal()
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // sf
    compileOnly("com.github.Slimefun:Slimefun4:${Hooks.slimefun}")
    // eplugin
    implementation(rootProject)
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    withJavadocJar()
    withSourcesJar()
}

afterEvaluate {
    publishing.publications.create<MavenPublication>("java") {
        from(components["kotlin"])
        artifact(tasks.getByName("sourcesJar"))
        artifact(tasks.getByName("javadocJar"))
        artifactId = "eplugin-hook-slimefun"
        groupId = Versions.group
        version = Versions.version
    }
}