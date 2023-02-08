dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // eplugin
    implementation(project(":eplugin-core"))
    // serialization
    api(kotlinx("serialization-core-jvm", Versions.serialization))
    api(kotlinx("serialization-json", "1.3.3"))
    // kaml
    api("com.charleskorn.kaml:kaml:${Versions.kaml}")
}