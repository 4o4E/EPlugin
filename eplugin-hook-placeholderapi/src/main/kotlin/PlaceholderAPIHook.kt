package top.e404.eplugin.hook.placeholderapi

import me.clip.placeholderapi.PlaceholderAPIPlugin
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

open class PlaceholderAPIHook(
    override val plugin: EPlugin,
) : EHook<PlaceholderAPIPlugin>(plugin, "PlaceholderAPI") {
    val papi: PlaceholderAPIPlugin
        get() = PlaceholderAPIPlugin.getInstance()
}