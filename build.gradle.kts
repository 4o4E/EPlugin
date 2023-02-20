import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.serialization") version Versions.kotlin
    `maven-publish`
    `java-library`
}

allprojects {
    group = Versions.group
    version = Versions.version

    repositories {
        // spigot
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/groups/public/")
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.gradle.maven-publish")
    apply(plugin = "org.gradle.java-library")

    repositories {
        // spigot
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/groups/public/")
        mavenCentral()
        mavenLocal()
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

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.create("eplugin-publish") {
    subprojects.forEach {
        dependsOn(it.tasks.clean)
        dependsOn(it.tasks.publishToMavenLocal)
    }
}