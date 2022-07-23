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
        val hookedPlugin = Bukkit.getPluginManager().getPlugin(name)
        if (hookedPlugin == null
            || !hookedPlugin.isEnabled
        ) {
            plugin.info(plugin.langManager["hook.disable", "plugin" to name])
            enable = false
            return
        }
        enable = true
        plugin.info(plugin.langManager["hook.enable", "plugin" to name])
        @Suppress("UNCHECKED_CAST")
        this.hookedPlugin = hookedPlugin as T
        afterHook()
    }

    open fun afterHook() {}
}