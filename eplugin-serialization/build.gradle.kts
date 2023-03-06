plugins {
    kotlin("plugin.serialization")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // eplugin
    implementation(project(":eplugin-core"))
    // serialization
    api(kotlinx("serialization-core-jvm", Versions.serialization))
    api(kotlinx("serialization-json", Versions.serialization))
    // kaml
    api("com.charleskorn.kaml:kaml:${Versions.kaml}")
}