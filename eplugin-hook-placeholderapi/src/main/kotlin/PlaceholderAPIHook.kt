package top.e404.eplugin.hook.placeholderapi

import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.PlaceholderAPIPlugin
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class PlaceholderAPIHook(
    override val plugin: EPlugin,
) : EHook(plugin, "PlaceholderAPI") {
    val papi inline get() = PlaceholderAPIPlugin.getInstance()

    fun placeholder(src: String, p: Player) = PlaceholderAPI.setPlaceholders(p, src)
    fun placeholder(src: String, p: OfflinePlayer) = PlaceholderAPI.setPlaceholders(p, src)
}
