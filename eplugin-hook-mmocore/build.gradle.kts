repositories {
    // lumine
    maven("https://mvn.lumine.io/repository/maven-public/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // mmocore
    compileOnly("net.Indyuce:MMOCore:${Hooks.mmocore}")
    // mythic lib
    compileOnly("io.lumine:MythicLib-dist:${Hooks.mythicLib}")
    // eplugin
    implementation(project(":eplugin-core"))
}