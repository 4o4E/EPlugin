package top.e404.eplugin.game.stage

import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.game.GameConfig
import top.e404.eplugin.game.GameStage
import top.e404.eplugin.game.Gamer
import top.e404.eplugin.util.parseSecondAsDuration

abstract class StageReadyingHandler<Config: GameConfig, GamePlayer : Gamer>(plugin: EPlugin) : GameStageHandler<Config, GamePlayer>(plugin) {
    final override val stage = GameStage.READY
    override val stageConfig get() = config.readying

    @Suppress("UNUSED")
    val countdown get() = enter + stageConfig.duration * 1000 - System.currentTimeMillis()

    override fun getPlaceholder(player: Player): Array<Pair<String, *>> = arrayOf(
        "countdown" to stageConfig.duration - tick,
        "max" to config.info.max,
        "min" to config.info.min,
        "observer_count" to instance.observers.size,
        "gamer_count" to instance.players.size,
        "duration" to stageConfig.duration,
        "duration_parsed" to stageConfig.duration.parseSecondAsDuration(),
        "countdown" to stageConfig.duration - tick,
        "countdown_parsed" to (stageConfig.duration - tick).parseSecondAsDuration(),
    )

    override fun onEnter(last: GameStageHandler<Config, GamePlayer>, data: Map<String, *>) {
        super.onEnter(last, data)
        // 切换计分板显示
        scoreboard.init(instance.inInstancePlayer)
    }

    override fun onTick() {
        // 人数不足则切换到等待阶段
        if (instance.players.size < config.info.min) {
            instance.switch(GameStage.WAITING)
            return
        }
        // 倒计时
        if (stageConfig.duration - tick < stageConfig.limit) {
            stageConfig.countdownMessage?.sendToAll(::getPlaceholder)
        }
        // 更新计分板
        scoreboard.updateAll()
        // 结束等待
        if (stageConfig.duration == tick) instance.switch(GameStage.GAMING, emptyMap())
    }
}
