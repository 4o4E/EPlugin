@file:Suppress("UNUSED")

package top.e404.eplugin.game.stage

import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.game.*
import top.e404.eplugin.game.util.CountdownManager
import top.e404.eplugin.util.parseSecondAsDuration

abstract class StageEndingHandler<Config : GameConfig, GamePlayer : Gamer>(plugin: EPlugin) :
    GameStageHandler<Config, GamePlayer>(plugin) {
    final override val stage = GameStage.ENDING
    override val stageConfig get() = config.ending

    override fun getPlaceholder(player: Player?): Array<Pair<String, *>> = arrayOf(
        "duration" to stageConfig.duration,
        "duration_parsed" to stageConfig.duration.parseSecondAsDuration(),
        "countdown" to stageConfig.duration - tick,
        "countdown_parsed" to (stageConfig.duration - tick).parseSecondAsDuration(),
    )

    override fun onEnter(last: GameStageHandler<Config, GamePlayer>, data: Map<String, *>) {
        super.onEnter(last, data)
        // 切换计分板显示
        scoreboard.init(instance.inInstancePlayer)
        instance.inInstancePlayer.forEach {
            endCountdown.add(it)
        }
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

    /**
     * 离开游戏
     */
    open fun onQuit() {
        // 注销自己
        unregister()
        stageConfig.leave?.sendTo(instance.inInstancePlayer, ::getPlaceholder)
        // 重置玩家计分板
        scoreboard.stop()
        endCountdown.shutdown()
    }

    val endCountdown by lazy { EndCountdown(plugin, stageConfig) }

    override val countdownList by lazy {
        mutableListOf<CountdownManager>(endCountdown)
    }
}

class EndCountdown(plugin: EPlugin, end: EndingConfig) : CountdownManager(plugin, end.countdown, true)
