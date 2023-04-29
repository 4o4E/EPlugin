package top.e404.eplugin.hook

import org.bukkit.Bukkit
import top.e404.eplugin.EPlugin

@Suppress("UNUSED")
abstract class EHook(
    open val plugin: EPlugin,
    open val name: String,
) {
    open var enable = false

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
        afterHook()
    }

    open fun afterHook() {}
}
