package top.e404.eplugin.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import top.e404.eplugin.EPlugin

abstract class EListener(open val plugin: EPlugin) : Listener {
    open fun register() = Bukkit.getPluginManager().registerEvents(this, plugin)

    open fun unregister() {
        this::class.java.declaredMethods.filter {
            it.getDeclaredAnnotationsByType(EventHandler::class.java).isNotEmpty()
        }.forEach { method ->
            val cls = method.parameters[0].type
            val handlerList = cls.getDeclaredField("handlers").also {
                it.isAccessible = true
            }.get(cls) as HandlerList
            handlerList.unregister(this)
        }
    }
}
