import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
    `maven-publish`
    `java-library`
}

group = Versions.group
version = Versions.version

repositories {
    // spigot
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    mavenCentral()
    mavenLocal()
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.0")

    // eplugin
    testImplementation(rootProject)
    // spigot
    testImplementation("org.spigotmc:spigot-api:${Versions.spigot}")
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