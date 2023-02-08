dependencies {
    // spigot
    compileOnly("org.spigotmc:spigot-api:${Versions.spigot}")
    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.0")
    implementation(kotlinx("serialization-core-jvm", Versions.serialization))
}