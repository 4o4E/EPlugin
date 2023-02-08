repositories {
    // nova
    maven("https://repo.xenondevs.xyz/releases/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // nova
    compileOnly("xyz.xenondevs.nova:nova-api:${Hooks.nova}")
    compileOnly("xyz.xenondevs.nova:nova:${Hooks.nova}")
    // eplugin
    implementation(project(":eplugin-core"))
}