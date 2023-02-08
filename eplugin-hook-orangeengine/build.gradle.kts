dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // oe
    compileOnly(fileTree("libs"))
    // eplugin
    implementation(project(":eplugin-core"))
}