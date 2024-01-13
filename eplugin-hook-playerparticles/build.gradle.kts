plugins {
    kotlin("plugin.serialization")
}

repositories {
    maven("https://repo.rosewooddev.io/repository/public/")
}

dependencies {
    spigot()
    eplugin()
    eplugin("serialization")
    // player particles
    compileOnly("dev.esophose:playerparticles:8.4")
}
