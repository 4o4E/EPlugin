repositories {
    // placeholderAPI
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // placeholderAPI
    compileOnly("me.clip:placeholderapi:2.11.1")
    // eplugin
    implementation(project(":eplugin-core"))
}