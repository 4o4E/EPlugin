package top.e404.eplugin.hook

import top.e404.eplugin.EPluginVelocity
import kotlin.jvm.optionals.getOrNull

@Suppress("UNUSED")
abstract class EHook(
    open val plugin: EPluginVelocity,
    open val name: String,
) {
    open var enable = false

    fun checkHook() {
        plugin.server.pluginManager.getPlugin(name).getOrNull() ?: run {
            plugin.info(plugin.langManager["hook.disable", "plugin" to name])
            enable = false
            return
        }
        enable = true
        plugin.info(plugin.langManager["hook.enable", "plugin" to name])
        afterHook()
    }

    open fun afterHook() {}
}
