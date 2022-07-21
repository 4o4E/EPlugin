package top.e404.eplugin.hook

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import top.e404.eplugin.EPlugin

abstract class EHook<T : Plugin>(
    open val plugin: EPlugin,
    open val name: String,
) {
    open var enable = false

    lateinit var hookedPlugin: T

    fun checkHook() {
        val plugin = Bukkit.getPluginManager().getPlugin(name)
        if (plugin == null
            || !plugin.isEnabled
        ) {
            enable = false
            return
        }
        @Suppress("UNCHECKED_CAST")
        hookedPlugin = plugin as T
        afterHook()
    }

    open fun afterHook() {}
}