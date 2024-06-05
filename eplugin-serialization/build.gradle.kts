plugins {
    kotlin("plugin.serialization")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    eplugin()
    serializationCore("api")
    serializationJson("api")
    kaml("api")
}
