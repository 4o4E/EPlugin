package top.e404.eplugin.bungeecord.listener

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Listener
import top.e404.eplugin.bungeecord.EPlugin

abstract class EListener(open val plugin: EPlugin) : Listener {
    fun register() = ProxyServer.getInstance().pluginManager.registerListener(plugin, this)
}