repositories {
    // taboo
    maven("https://repo.tabooproject.org/repository/releases/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // ady
    compileOnly("ink.ptms.adyeshach:all:2.0.0-snapshot-10")
    // eplugin
    implementation(project(":eplugin-core"))
}