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
}

tasks.create("eplugin-publish") {
    dependsOn(tasks.clean)
    subprojects.forEach { dependsOn(it.tasks.publishToMavenLocal) }
}

tasks.clean {
    subprojects.forEach { dependsOn(it.tasks.clean) }
}
