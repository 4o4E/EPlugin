repositories {
    // jitpack
    maven("https://jitpack.io")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // slimefun
    compileOnly("com.github.Slimefun:Slimefun4:${Hooks.slimefun}")
    // eplugin
    implementation(project(":eplugin-core"))
}