package top.e404.eplugin.listener

import top.e404.eplugin.EPluginVelocity

@Suppress("UNUSED")
abstract class EListener(open val plugin: EPluginVelocity) {
    open fun register() {
        plugin.debug { "注册Listener: ${this.javaClass.simpleName}" }
        plugin.server.eventManager.register(plugin, this)
    }

    open fun unregister() {
        plugin.server.eventManager.unregisterListener(plugin, this)
    }
}
