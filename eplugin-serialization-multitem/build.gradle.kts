plugins {
    kotlin("plugin.serialization")
}

repositories {
    // jitpack
    maven("https://jitpack.io")
    // mm
    maven("https://mvn.lumine.io/repository/maven-public/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // eplugin
    implementation(project(":eplugin-core"))
    implementation(project(":eplugin-serialization"))
    implementation(project(":eplugin-hook-itemsadder"))
    implementation(project(":eplugin-hook-mmoitems"))
    // mythic lib
    compileOnly("io.lumine:MythicLib-dist:1.4")
    // mi
    compileOnly("net.Indyuce:MMOItems:6.7.3")
    // itemsadder
    compileOnly("com.github.LoneDev6:api-itemsadder:3.0.0")
    // serialization
    api(kotlinx("serialization-core-jvm", Versions.serialization))
}