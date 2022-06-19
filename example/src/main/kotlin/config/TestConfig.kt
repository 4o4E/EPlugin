package top.e404.testplugin.config

import org.bukkit.configuration.file.YamlConfiguration
import top.e404.eplugin.config.EConfig
import top.e404.testplugin.INSTANCE

object TestConfig : EConfig(
    INSTANCE,
    "config.yml",
    Companion.JarConfig(INSTANCE, "config.yml"),
    false
) {
    var debug = false
        private set

    override fun YamlConfiguration.onLoad() {
        debug = getBoolean("debug")
    }
}