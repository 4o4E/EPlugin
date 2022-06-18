package top.e404.eplugin.listener

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

abstract class EListener(open val plugin: JavaPlugin) : Listener {
    fun register() = Bukkit.getPluginManager().registerEvents(this, plugin)
}