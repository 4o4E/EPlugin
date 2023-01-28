package top.e404.eplugin.hook.mmocore

import net.Indyuce.mmocore.MMOCore
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class MmocoreHook(
    override val plugin: EPlugin,
) : EHook(plugin, "MMOCore") {
    val mmoCore: MMOCore
        get() = MMOCore.plugin!!

    fun getLevel(p: Player) = mmoCore.dataProvider.dataManager.get(p.uniqueId).level
}