repositories {
    // playerpoints
    maven("https://repo.rosewooddev.io/repository/public/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // playerpoints
    compileOnly("org.black_ixx:playerpoints:${Hooks.playerPoints}")
    // eplugin
    implementation(project(":eplugin-core"))
}