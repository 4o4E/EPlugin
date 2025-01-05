@file:Suppress("UNUSED")

package top.e404.eplugin.game

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team
import top.e404.eplugin.config.serialization.ScoreboardConfig
import top.e404.eplugin.scoreboard.Scoreboard

abstract class ScoreboardManager(val config: ScoreboardConfig) {
    private val data = mutableMapOf<Player, Scoreboard>()

    /**
     * 初始化此计分板管理器
     *
     * @param players 注册到此管理器的玩家
     */
    fun init(players: Collection<Player>) {
        data.clear()
        players.forEach { player ->
            data[player] = Scoreboard("").also {
                config.update(it, *placeholders(player))
                it.show(player)
            }
        }
    }

    /**
     * 在此计分板管理器结束使用时调用
     */
    fun stop() {
        data.forEach { (player, scoreboard) ->
            scoreboard.destroy()
            player.scoreboard = Bukkit.getScoreboardManager().mainScoreboard
        }
        data.clear()
    }

    /**
     * 向此计分板管理器中添加玩家
     *
     * @param player 添加的玩家
     */
    fun add(player: Player) {
        data[player] = Scoreboard("").also {
            config.update(it)
            it.show(player)
        }
    }

    /**
     * 向此计分板管理器中移除玩家
     *
     * @param player 移除的玩家
     */
    fun remove(player: Player) {
        data.remove(player)
        player.scoreboard = Bukkit.getScoreboardManager().mainScoreboard
    }

    /**
     * 给管理器管理的所有玩家更新计分板
     */
    fun updateAll() {
        data.forEach { (player, scoreboard) ->
            config.update(scoreboard, *placeholders(player))
        }
    }

    /**
     * 提供占位符
     *
     * @param player 对应的玩家
     * @return 占位符
     */
    abstract fun placeholders(player: Player): Array<Pair<String, *>>

    /**
     * 设置其中的玩家没有碰撞
     */
    fun setNoCollision() {
        data.values.forEach { scoreboard ->
            scoreboard.scoreboard.teams.forEach {
                it.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.ALWAYS)
            }
        }
    }
}
