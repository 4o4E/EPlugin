repositories {
    // engine hub
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // eplugin
    implementation(project(":eplugin-core"))
    // world guard
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:${Hooks.worldguard}")
}