repositories {
    // nova
    maven("https://repo.xenondevs.xyz/releases/")
}

dependencies {
    spigot()
    eplugin()
    // nova
    compileOnly("xyz.xenondevs.nova:nova-api:${Hooks.nova}")
    compileOnly("xyz.xenondevs.nova:nova:${Hooks.nova}")
}
