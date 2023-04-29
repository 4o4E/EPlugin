package top.e404.eplugin.hook.playerpoints

import org.black_ixx.playerpoints.PlayerPoints
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class PlayerPointsHook(
    override val plugin: EPlugin,
) : EHook(plugin, "PlayerPoints") {
    val pp inline get() = PlayerPoints.getInstance()!!

    fun getBal(player: Player) = pp.api.look(player.uniqueId)
    fun give(player: Player, amount: Int) = pp.api.give(player.uniqueId, amount)
    fun take(player: Player, amount: Int) = pp.api.take(player.uniqueId, amount)
    fun getTop() = pp.api.topSortedPoints
}
