@file:Suppress("UNUSED")

package top.e404.eplugin.game.stage

import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.game.GameConfig
import top.e404.eplugin.game.GameStage
import top.e404.eplugin.game.Gamer
import top.e404.eplugin.game.reset
import top.e404.eplugin.util.parseSecondAsDuration

abstract class StageEndingHandler<Config : GameConfig, GamePlayer : Gamer>(plugin: EPlugin) : GameStageHandler<Config, GamePlayer>(plugin) {
    final override val stage = GameStage.ENDING
    override val stageConfig get() = config.ending

    override fun getPlaceholder(player: Player): Array<Pair<String, *>> = arrayOf(
        "observer_count" to instance.observers.size,
        "gamer_count" to instance.players.size,
        "duration" to stageConfig.duration,
        "duration_parsed" to stageConfig.duration.parseSecondAsDuration()
    )

    override fun onEnter(last: GameStageHandler<Config, GamePlayer>, data: Any?) {
        super.onEnter(last, data)
        // 切换计分板显示
        scoreboard.init(instance.inInstancePlayer)
    }

    override fun onTick() {
        // 游戏阶段控制
        if (tick >= stageConfig.duration) {
            val location = instance.manager.lobby.toLocation()
            instance.inInstancePlayer.forEach {
                it.reset()
                // 传送玩家到大厅
                it.teleport(location)
            }
            // 取消tick任务
            instance.tickerTask?.cancel()
            // 销毁游戏实例
            instance.destroy()
            return
        }
        // 更新计分板
        scoreboard.updateAll()
    }

    override fun onLeave(next: GameStageHandler<Config, GamePlayer>, data: Any?) {}
}
