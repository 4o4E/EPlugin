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
    mavenCentral()
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // eplugin
    implementation(rootProject)
    // serialization
    api(kotlinx("serialization-core-jvm", "1.3.3"))
    api(kotlinx("serialization-json", "1.3.3"))
    // kaml
    api("com.charleskorn.kaml:kaml:0.45.0")
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
        artifactId = "eplugin-serialization"
        groupId = Versions.group
        version = Versions.version
    }
}