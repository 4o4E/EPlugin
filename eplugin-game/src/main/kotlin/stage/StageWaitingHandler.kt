package top.e404.eplugin.game.stage

import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.game.GameConfig
import top.e404.eplugin.game.GameStage
import top.e404.eplugin.game.Gamer

abstract class StageWaitingHandler<Config: GameConfig, GamePlayer : Gamer>(plugin: EPlugin) : GameStageHandler<Config, GamePlayer>(plugin) {
    final override val stage = GameStage.WAITING
    override val stageConfig get() = config.waiting

    override fun getPlaceholder(player: Player?): Array<Pair<String, *>> = arrayOf()

    override fun onEnter(last: GameStageHandler<Config, GamePlayer>, data: Map<String, *>) {
        super.onEnter(last, data)
        // 切换计分板显示
        scoreboard.init(instance.inInstancePlayer)
    }

    override fun onTick() {
        // 人数足够则切换到准备阶段
        if (instance.players.size >= config.info.min) {
            instance.switch(GameStage.READY)
            return
        }
        // 更新计分板
        scoreboard.updateAll()
    }
}
