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
    maven("https://oss.sonatype.org/content/groups/public/")
    mavenCentral()
    mavenLocal()
}

dependencies {
    // bungeecord
    implementation("net.md-5:bungeecord-api:1.16-R0.5-SNAPSHOT")
    // bstats
    implementation("org.bstats:bstats-bungeecord:3.0.0")
    // serialization
    implementation(kotlinx("serialization-core-jvm", "1.3.3"))
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