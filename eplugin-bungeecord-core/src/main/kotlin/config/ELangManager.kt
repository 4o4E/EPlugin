@file:Suppress("UNUSED")

package top.e404.eplugin.bungeecord.config

import top.e404.eplugin.bungeecord.EPlugin
import top.e404.eplugin.bungeecord.EPlugin.Companion.color
import top.e404.eplugin.bungeecord.EPlugin.Companion.placeholder

abstract class ELangManager(
    override val plugin: EPlugin,
) : EConfig(
    plugin = plugin,
    path = "lang.yml",
    default = JarConfig(plugin, "lang.yml"),
    clearBeforeSave = false
) {
    override operator fun get(path: String) = getOrSelf(path).color()
    operator fun get(path: String, vararg placeholder: Pair<String, Any?>) =
        config.getString(path)?.placeholder(*placeholder) ?: path

    fun getList(path: String) = config.getStringList(path).map { it.color() }
}