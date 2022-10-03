package top.e404.eplugin.bungeecord.hook

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin
import top.e404.eplugin.bungeecord.EPlugin

abstract class EHook<T : Plugin>(
    open val plugin: EPlugin,
    open val name: String,
) {
    open var enable = false

    lateinit var hookedPlugin: T

    fun checkHook() {
        val hookedPlugin = ProxyServer.getInstance().pluginManager.getPlugin(name)
        if (hookedPlugin == null) {
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