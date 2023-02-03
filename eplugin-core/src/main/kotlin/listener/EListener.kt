package top.e404.eplugin.listener

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import top.e404.eplugin.EPlugin

abstract class EListener(open val plugin: EPlugin) : Listener {
    open fun register() = Bukkit.getPluginManager().registerEvents(this, plugin)
}