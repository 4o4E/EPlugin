repositories {
    // engine hub
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // eplugin
    implementation(project(":eplugin-core"))
    implementation(project(":eplugin-hook-worldguard"))
    implementation(project(":eplugin-serialization"))
    // world edit
    compileOnly("com.sk89q.worldedit:worldedit-core:${Hooks.worldedit}")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:${Hooks.worldedit}")
}