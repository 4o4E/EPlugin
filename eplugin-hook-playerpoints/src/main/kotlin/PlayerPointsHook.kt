package top.e404.eplugin.hook.bentobox

import org.black_ixx.playerpoints.PlayerPoints
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

open class PlayerPointsHook(
    override val plugin: EPlugin,
) : EHook<PlayerPoints>(plugin, "PlayerPoints") {
    val pp: PlayerPoints
        get() = PlayerPoints.getInstance()!!

    fun getBal(player: Player) = pp.api.look(player.uniqueId)
    fun give(player: Player, amount: Int) = pp.api.give(player.uniqueId, amount)
    fun take(player: Player, amount: Int) = pp.api.take(player.uniqueId, amount)
    fun getTop() = pp.api.topSortedPoints
}