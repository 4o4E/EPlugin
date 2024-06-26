package top.e404.eplugin.game.stage

import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.game.*
import top.e404.eplugin.listener.EListener

abstract class GameStageHandler<Config: GameConfig, GamePLayer : Gamer>(plugin: EPlugin) : EListener(plugin) {
    /**
     * 此处理器对应的阶段
     */
    abstract val stage: GameStage

    /**
     * 注册到的游戏管理器
     */
    open lateinit var instance: GameInstance<Config, GamePLayer>

    /**
     * 游戏设置
     */
    protected open val config get() = instance.gameConfig

    /**
     * 当前阶段的游戏设置
     */
    protected abstract val stageConfig: GameStageConfig

    /**
     * 当前阶段的计分板管理器
     */
    abstract val scoreboard: ScoreboardManager

    /**
     * 获取此阶段的占位符
     */
    protected abstract fun getPlaceholder(player: Player): Array<Pair<String, *>>

    /**
     * 当前阶段的tick计数
     */
    var tick = 0L
        private set

    /**
     * 当前阶段的进入时间戳
     */
    var enter = 0L
        private set

    /**
     * 当前的游戏阶段是否是本控制器控制的
     */
    @Suppress("UNUSED")
    inline val isActive get() = instance.currentStageHandler == this

    /**
     * 从其他游戏阶段切换至此游戏阶段
     */
    open fun onEnter(last: GameStageHandler<Config, GamePLayer>, data: Any?) {
        // 注册自己
        register()
        tick = 0
        enter = System.currentTimeMillis()
    }

    /**
     * 执行此游戏阶段的游戏tick
     */
    abstract fun onTick()

    /**
     * 捕获onTick中抛出的异常, 执行tick自增
     */
    fun safeOnTick() {
        try {
            onTick()
        } catch (e: Exception) {
            plugin.warn("游戏进行tick-loop时出现异常, stage: ${stage.name}", e)
        } finally {
            tick++
        }
    }

    /**
     * 从此游戏阶段切换到其他游戏阶段
     */
    open fun onLeave(next: GameStageHandler<Config, GamePLayer>, data: Any?) {
        // 注销自己
        unregister()
    }
}
