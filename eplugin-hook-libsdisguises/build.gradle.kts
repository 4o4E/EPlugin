repositories {
    maven("https://repo.md-5.net/content/groups/public/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    spigot()
    eplugin()
    // LibsDisguises
    compileOnly("LibsDisguises:LibsDisguises:10.0.44") {
        exclude("org.spigotmc", "spigot")
    }
    // ProtocolLib
    compileOnly("com.comphenix.protocol:ProtocolLib:5.1.0")
}
