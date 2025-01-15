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
    open val config get() = instance.gameConfig

    /**
     * 当前阶段的游戏设置
     */
    abstract val stageConfig: GameStageConfig

    /**
     * 当前阶段的计分板管理器
     */
    open val scoreboard: ScoreboardManager by lazy {
        object : ScoreboardManager(stageConfig.scoreboard) {
            override fun placeholders(player: Player) = getPlaceholder(player) + instance.getInstancePlaceholder(player)
        }
    }

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
    open fun onEnter(last: GameStageHandler<Config, GamePLayer>, data: Map<String, *>) {
        // 注册自己
        register()
        tick = 0
        enter = System.currentTimeMillis()
        stageConfig.enter?.sendTo(instance.inInstancePlayer, config.info.displayName, ::getPlaceholder)
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
            plugin.warn("游戏${stage.name}阶段进行tick-loop时出现异常", e)
        } finally {
            tick++
        }
    }

    /**
     * 从此游戏阶段切换到其他游戏阶段
     */
    open fun onLeave(next: GameStageHandler<Config, GamePLayer>, data: Map<String, *>) {
        // 注销自己
        unregister()
        stageConfig.leave?.sendTo(instance.inInstancePlayer, ::getPlaceholder)
        // 重置玩家计分板
        scoreboard.stop()
    }

    /**
     * 处理意外退出
     */
    open fun shutdown() {}

    /**
     * 处理玩家中途退出
     */
    open fun onExit(player: Player) {}
}
