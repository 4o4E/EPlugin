import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    `maven-publish`
    `java-library`
}

group = Versions.group
version = Versions.version

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    // enginehub
    maven("https://maven.enginehub.org/repo/")
    mavenCentral()
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // eplugin
    implementation(project(":eplugin-core"))
    implementation(project(":eplugin-hook-worldguard"))
    implementation(project(":eplugin-serialization"))
    // worldguard
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:${Hooks.worldguard}")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    withJavadocJar()
    withSourcesJar()
    targetCompatibility = JavaVersion.VERSION_17
}

afterEvaluate {
    publishing.publications.create<MavenPublication>("java") {
        from(components["kotlin"])
        artifact(tasks.getByName("sourcesJar"))
        artifact(tasks.getByName("javadocJar"))
        artifactId = "eplugin-serialization-worldguard"
        groupId = Versions.group
        version = Versions.version
    }
}