repositories {
    // playerpoints
    maven("https://repo.rosewooddev.io/repository/public/")
}

dependencies {
    spigot()
    eplugin()
    // playerpoints
    compileOnly("org.black_ixx:playerpoints:${Hooks.playerPoints}")
}
