repositories {
    // BentoBox
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // BentoBox
    compileOnly("world.bentobox:bentobox:${Hooks.bentobox}")
    // eplugin
    implementation(project(":eplugin-core"))
}