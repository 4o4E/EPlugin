plugins {
    kotlin("plugin.serialization")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")
    eplugin()
    eplugin("serialization")
    serializationCore()
}
