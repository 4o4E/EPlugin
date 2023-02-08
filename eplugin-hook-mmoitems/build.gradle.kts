repositories {
    // lumine
    maven("https://mvn.lumine.io/repository/maven-public/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // mi
    compileOnly("net.Indyuce:MMOItems:${Hooks.mmoitems}")
    // mythic lib
    compileOnly("io.lumine:MythicLib-dist:${Hooks.mythicLib}")
    // eplugin
    implementation(project(":eplugin-core"))
}