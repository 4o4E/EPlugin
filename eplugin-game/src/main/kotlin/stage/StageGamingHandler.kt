package top.e404.eplugin.game.stage

import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.game.GameConfig
import top.e404.eplugin.game.GameStage
import top.e404.eplugin.game.Gamer
import top.e404.eplugin.util.parseSecondAsDuration

abstract class StageGamingHandler<Config : GameConfig, GamePlayer : Gamer>(plugin: EPlugin) : GameStageHandler<Config, GamePlayer>(plugin) {
    final override val stage = GameStage.GAMING
    override val stageConfig get() = config.gaming

    override fun getPlaceholder(player: Player): Array<Pair<String, *>> = arrayOf(
        "observer_count" to instance.observers.size,
        "gamer_count" to instance.players.size,
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
        // 检测倒计时消息
        stageConfig.countdownMessage[tick]?.let { message ->
            message.sendTo(instance.inInstancePlayer, instance.gameConfig.info.displayName) {
                getPlaceholder(it) + instance.getInstancePlaceholder(it)
            }
        }
    }
}
