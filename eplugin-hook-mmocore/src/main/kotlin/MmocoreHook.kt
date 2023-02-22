package top.e404.eplugin.hook.mmocore

import net.Indyuce.mmocore.MMOCore
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class MmocoreHook(
    override val plugin: EPlugin,
) : EHook(plugin, "MMOCore") {
    val mmoCore inline get() = MMOCore.plugin!!

    fun getPlayerData(p: OfflinePlayer) = mmoCore.dataProvider.dataManager.get(p.uniqueId)!!

    fun getLevel(p: Player) = getPlayerData(p).level
    fun getMana(p: Player) = getPlayerData(p).mana
    fun setMana(p: Player, value: Double) {
        getPlayerData(p).mana = value
    }

    fun getMaxMana(p: Player) = getPlayerData(p).stats.getStat("MAX_MANA")
}