repositories {
    // jitpack
    maven("https://jitpack.io")
}

dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // itemsadder
    compileOnly("com.github.LoneDev6:api-itemsadder:${Hooks.itemsadder}")
    // eplugin
    implementation(project(":eplugin-core"))
}