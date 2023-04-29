@file:Suppress("UNUSED")

package top.e404.eplugin.config

import org.bukkit.configuration.file.YamlConfiguration
import top.e404.eplugin.EPlugin
import top.e404.eplugin.EPlugin.Companion.color
import top.e404.eplugin.EPlugin.Companion.placeholder

abstract class ELangManager(
    override val plugin: EPlugin,
) : YamlConfig(
    plugin = plugin,
    path = "lang.yml",
    default = JarConfigDefault(plugin, "lang.yml"),
    clearBeforeSave = false
) {
    private var cache = mutableMapOf<String, String>()
    override operator fun get(path: String) = cache.getOrElse(path) { path }.color()
    operator fun get(path: String, vararg placeholder: Pair<String, Any?>) =
        cache[path]?.placeholder(*placeholder) ?: path

    override fun YamlConfiguration.onLoad() {
        val cache = mutableMapOf<String, String>()
        getKeys(true).forEach {
            val value = get(it)
            if (value !is String) return@forEach
            cache[it] = value
        }
        this@ELangManager.cache = cache
    }
}
