repositories {
    // vault
    maven("https://jitpack.io")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // vault
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    // eplugin
    implementation(project(":eplugin-core"))
}