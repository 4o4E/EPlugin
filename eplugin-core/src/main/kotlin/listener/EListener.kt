package top.e404.eplugin.listener

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import top.e404.eplugin.EPlugin
import java.lang.reflect.Field

abstract class EListener(open val plugin: EPlugin) : Listener {
    open fun register() = Bukkit.getPluginManager().registerEvents(this, plugin)

    @Suppress("UNUSED")
    open fun unregister() {
        this::class.java.declaredMethods.filter {
            it.getDeclaredAnnotationsByType(EventHandler::class.java).isNotEmpty()
                    && it.parameterTypes.size == 1
        }.forEach { method ->
            var cls = method.parameters[0].type
            if (!Event::class.java.isAssignableFrom(cls)) {
                plugin.debug { "注销eventHandler时跳过非event方法: ${method.returnType.name} ${method.name}(${cls.name})" }
                return@forEach
            }
            plugin.debug { "开始注销eventHandler: ${method.returnType.name} ${method.name}(${cls.name})" }
            var handlersField: Field
            while (true) {
                try {
                    handlersField = cls.getDeclaredField("handlers")
                    break
                } catch (e: Exception) {
                    cls = cls.superclass ?: throw Exception("${method.returnType.name} ${method.name}(${cls.name})不是Event")
                }
            }
            handlersField.isAccessible = true
            val handlerList = handlersField.get(cls) as HandlerList
            handlerList.unregister(this)
            plugin.debug { "完成注销eventHandler: ${method.returnType.name} ${method.name}(${cls.name})" }
        }
    }
}
