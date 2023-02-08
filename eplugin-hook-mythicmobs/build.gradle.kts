repositories {
    // lumine
    maven("https://mvn.lumine.io/repository/maven-public/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // mm
    compileOnly("io.lumine:Mythic-Dist:${Hooks.mythicmobs}")
    // eplugin
    implementation(project(":eplugin-core"))
}