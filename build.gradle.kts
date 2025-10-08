import java.util.*

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
        maven("https://nexus.e404.top:3443/repository/maven-snapshots/")
        mavenCentral()
        mavenLocal()
    }
}

val local = Properties().apply {
    val file = projectDir.resolve("local.properties")
    if (file.exists()) file.bufferedReader().use { load(it) }
}

val nexusUsername get() = local.getProperty("nexus.username") ?: ""
val nexusPassword get() = local.getProperty("nexus.password") ?: ""

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.gradle.maven-publish")
    apply(plugin = "org.gradle.java-library")

    repositories {
        maven("https://maven.aliyun.com/repository/public/")
        mavenCentral()
        // spigot
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://oss.sonatype.org/content/groups/public/")
        // paper
        maven("https://repo.papermc.io/repository/maven-public/")
        mavenLocal()
    }

    afterEvaluate {
        publishing.publications.create<MavenPublication>("java") {
            from(components["kotlin"])
            artifact(tasks.getByName("sourcesJar"))
            artifactId = project.name
            groupId = Versions.group
            version = Versions.version
        }
    }

    publishing {
        repositories {
            maven {
                name = "snapshot"
                url = uri("https://nexus.e404.top:3443/repository/maven-snapshots/")
                credentials {
                    username = nexusUsername
                    password = nexusPassword
                }
            }
        }
    }
}

tasks.clean {
    subprojects.forEach { dependsOn(it.tasks.clean) }
}
