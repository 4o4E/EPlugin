package top.e404.eplugin.game.stage

import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.config.serialization.CountdownConfig
import top.e404.eplugin.game.GameConfig
import top.e404.eplugin.game.GameStage
import top.e404.eplugin.game.Gamer
import top.e404.eplugin.game.util.CountdownManager
import top.e404.eplugin.util.parseSecondAsDuration

abstract class StageReadyingHandler<Config: GameConfig, GamePlayer : Gamer>(plugin: EPlugin) : GameStageHandler<Config, GamePlayer>(plugin) {
    final override val stage = GameStage.READY
    override val stageConfig get() = config.readying

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
        // 进度条
        instance.inInstancePlayer.forEach { readyCountdown.add(it) }
    }

    override fun onTick() {
        // 人数不足则切换到等待阶段
        if (instance.players.size < config.info.min) {
            instance.switch(GameStage.WAITING)
            return
        }
        // 更新计分板
        scoreboard.updateAll()
        // 结束等待
        if (stageConfig.duration == tick) instance.switch(GameStage.GAMING, emptyMap())
    }

    override fun onLeave(next: GameStageHandler<Config, GamePlayer>, data: Map<String, *>) {
        super.onLeave(next, data)
        readyCountdown.players.values.forEach { it.stop() }
        readyCountdown.players.clear()
    }

    val readyCountdown by lazy { ReadyCountdownManager(plugin, stageConfig.countdown) }

    override val countdownList by lazy {
        mutableListOf<CountdownManager>(readyCountdown)
    }
}

class ReadyCountdownManager(
    plugin: EPlugin,
    config: CountdownConfig
) : CountdownManager(plugin, config, true)

