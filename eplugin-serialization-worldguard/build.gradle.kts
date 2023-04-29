plugins {
    kotlin("plugin.serialization")
}

repositories {
    engineHub()
}

dependencies {
    spigot()
    eplugin()
    eplugin("hook-worldguard")
    eplugin("serialization")
    worldguard()
}
