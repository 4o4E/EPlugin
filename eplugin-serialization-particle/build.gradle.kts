plugins {
    kotlin("plugin.serialization")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    // eplugin
    implementation(project(":eplugin-core"))
    implementation(project(":eplugin-serialization"))
    // serialization
    api(kotlinx("serialization-core-jvm", Versions.serialization))
}