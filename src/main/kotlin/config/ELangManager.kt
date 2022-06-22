@file:Suppress("UNUSED")

package top.e404.eplugin.config

import top.e404.eplugin.EPlugin
import top.e404.eplugin.EPlugin.Companion.placeholder

abstract class ELangManager(
    override val plugin: EPlugin,
) : EConfig(
    plugin = plugin,
    path = "lang.yml",
    default = Companion.JarConfig(plugin, "lang.yml"),
    clearBeforeSave = false
) {
    override operator fun get(path: String) = getOrSelf(path)
    operator fun get(path: String, vararg placeholder: Pair<String, Any>) =
        config.getString(path)?.placeholder(*placeholder) ?: path
}