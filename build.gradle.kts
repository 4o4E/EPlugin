import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    `maven-publish`
    `java-library`
}

group = "top.e404"
version = "1.0.0"

repositories {
    // spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    mavenCentral()
    mavenLocal()
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // eplugin
    testImplementation("top.e404:EPlugin:1.0.0")
    // spigot
    testImplementation("org.spigotmc:spigot-api:1.13.2-R0.1-SNAPSHOT")
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
        artifactId = "eplugin"
        groupId = project.group.toString()
        version = project.version.toString()
    }
}

tasks.jar {
    doLast {
        println("==== copy ====")
        for (file in File("build/libs").listFiles() ?: emptyArray()) {
            println("正在复制`${file.path}`")
            file.copyTo(File("jar/${file.name}"), true)
        }
    }
}