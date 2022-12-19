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
    mavenLocal()
    // spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    // nova
    maven("https://repo.xenondevs.xyz/releases/")
    mavenCentral()
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // nova
    compileOnly("xyz.xenondevs.nova:nova-api:${Hooks.nova}")
    compileOnly("xyz.xenondevs.nova:nova:${Hooks.nova}")
    // eplugin
    implementation(project(":eplugin-core"))
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
        artifactId = project.name
        groupId = Versions.group
        version = Versions.version
    }
}