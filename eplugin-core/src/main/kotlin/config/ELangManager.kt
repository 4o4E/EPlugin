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
    private val defaultLang by lazy {
        val cfg = YamlConfiguration().also { it.loadFromString(default.string) }
        val keys = cfg.getKeys(true)
        val map = HashMap<String, String>(keys.size)
        keys.forEach {
            val value = cfg.get(it)
            if (value !is String) return@forEach
            map[it] = value
        }
        map
    }
    private var cacheLang = mutableMapOf<String, String>()
    override operator fun get(path: String) =
        cacheLang[path]?.color()
            ?: defaultLang[path]?.color()
            ?: path

    operator fun get(path: String, vararg placeholder: Pair<String, Any?>) =
        cacheLang[path]?.placeholder(*placeholder)
            ?: defaultLang[path]?.placeholder(*placeholder)
            ?: path

    override fun YamlConfiguration.onLoad() {
        val keys = getKeys(true)
        val cache = HashMap<String, String>(keys.size)
        keys.forEach {
            val value = get(it)
            if (value !is String) return@forEach
            cache[it] = value
        }
        cacheLang = cache
    }
}
