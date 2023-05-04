plugins {
    kotlin("plugin.serialization")
}

repositories {
    lumine()
    phoenix()
    // minecraft
    maven("https://libraries.minecraft.net")
}

dependencies {
    spigot()
    eplugin()
    eplugin("serialization")
    eplugin("reflect")
    mythicLib()
    mmoitems()
    // authlib
    compileOnly("com.mojang:authlib:3.11.49")
}
