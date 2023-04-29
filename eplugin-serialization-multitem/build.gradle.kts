plugins {
    kotlin("plugin.serialization")
}

repositories {
    jitpack()
    lumine()
    phoenix()
}

dependencies {
    spigot()
    eplugin()
    eplugin("serialization")
    eplugin("hook-itemsadder")
    eplugin("hook-mmoitems")
    mythicLib()
    mmoitems()
    itemsadder()
    serializationCore()
}
