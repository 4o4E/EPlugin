plugins {
    kotlin("plugin.serialization")
}

dependencies {
    spigot()
    eplugin()
    serializationCore("api")
    serializationJson("api")
    kaml("api")
}
