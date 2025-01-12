package top.e404.eplugin.listener

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import top.e404.eplugin.EPlugin
import java.lang.reflect.Field

abstract class EListener(open val plugin: EPlugin) : Listener {
    open fun register() {
        plugin.debug { "注册Listener: ${this.javaClass.simpleName}" }
        Bukkit.getPluginManager().registerEvents(this, plugin)
    }

    @Suppress("UNUSED")
    open fun unregister() {
        this::class.java.methods.filter {
            it.getAnnotationsByType(EventHandler::class.java).isNotEmpty() && it.parameterTypes.size == 1
        }.forEach { method ->
            var param0 = method.parameters[0].type
            if (!Event::class.java.isAssignableFrom(param0)) {
                plugin.debug { "注销eventHandler时跳过非event方法: $method" }
                return@forEach
            }
            plugin.debug { "开始注销eventHandler: $method" }
            var handlersField: Field
            while (true) {
                try {
                    handlersField = param0.getDeclaredField("handlers")
                    break
                } catch (e: Exception) {
                    // paper HANDLER_LIST
                    try {
                        handlersField = param0.getDeclaredField("HANDLER_LIST")
                        break
                    } catch (e: Exception) {
                        param0 = param0.superclass ?: throw Exception("$param0 不是Event")
                    }
                }
            }
            handlersField.isAccessible = true
            val handlerList = handlersField.get(param0) as HandlerList
            handlerList.unregister(this)
            plugin.debug { "完成注销eventHandler: ${method.returnType.name} ${method.name}(${param0.name})" }
        }
    }
}
