package top.e404.eplugin.hook.nova

import org.bukkit.Location
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import xyz.xenondevs.nova.api.Nova

@Suppress("UNUSED")
open class NovaHook(
    override val plugin: EPlugin,
) : EHook(plugin, "Nova") {
    val nova: Nova
        get() = Nova.getNova()

    fun getNovaBlock(location: Location) = nova.blockManager.getBlock(location)
    fun hasNovaBlock(location: Location) = nova.blockManager.hasBlock(location)
}