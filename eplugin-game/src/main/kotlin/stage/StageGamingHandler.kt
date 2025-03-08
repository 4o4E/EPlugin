package top.e404.eplugin.game.stage

import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.config.serialization.CountdownConfig
import top.e404.eplugin.game.GameConfig
import top.e404.eplugin.game.GameStage
import top.e404.eplugin.game.Gamer
import top.e404.eplugin.game.util.CountdownManager
import top.e404.eplugin.util.parseSecondAsDuration

abstract class StageGamingHandler<Config : GameConfig, GamePlayer : Gamer>(plugin: EPlugin) : GameStageHandler<Config, GamePlayer>(plugin) {
    final override val stage = GameStage.GAMING
    override val stageConfig get() = config.gaming

    override fun getPlaceholder(player: Player?): Array<Pair<String, *>> = arrayOf(
        "duration" to stageConfig.duration,
        "duration_parsed" to stageConfig.duration.parseSecondAsDuration(),
        "countdown" to stageConfig.duration - tick,
        "countdown_parsed" to (stageConfig.duration - tick).parseSecondAsDuration()
    )

    override fun onEnter(last: GameStageHandler<Config, GamePlayer>, data: Map<String, *>) {
        super.onEnter(last, data)
        // 切换计分板显示
        scoreboard.init(instance.inInstancePlayer)
        // 记录开始事件
        instance.gameStart = System.currentTimeMillis()
    }

    override fun onTick() {
        // 更新计分板
        scoreboard.updateAll()
        // 倒计时
        val last = stageConfig.duration - tick
        if (last == stageConfig.countdown.duration) {
            instance.inInstancePlayer.forEach {
                gamingCountdown.add(it)
            }
        }
    }

    val gamingCountdown by lazy { GamingCountdownManager(plugin, stageConfig.countdown) }

    override val countdownList by lazy {
        mutableListOf<CountdownManager>(gamingCountdown)
    }
}

class GamingCountdownManager(
    plugin: EPlugin,
    config: CountdownConfig
) : CountdownManager(plugin, config, true)