@file:Suppress("UNUSED")

package top.e404.eplugin.game

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import top.e404.dynamicmap.dyn.DynamicMap
import top.e404.eplugin.EPlugin
import top.e404.eplugin.game.stage.*
import top.e404.eplugin.listener.EListener

/**
 * 一个游戏实例
 *
 * @param GamePLayer 玩家信息
 * @property plugin 对应的插件
 * @property manager 对应的实例管理器
 */
abstract class GameInstance<Config : GameConfig, GamePLayer : Gamer>(
    plugin: EPlugin,
    val manager: GameManager<Config, GamePLayer>,
    val map: DynamicMap
) : EListener(plugin) {
    /**
     * 玩家进入游戏的等待处理器
     */
    abstract val waiting: StageWaitingHandler<Config, GamePLayer>

    /**
     * 玩家准备游戏的准备处理器
     */
    abstract val readying: StageReadyingHandler<Config, GamePLayer>

    /**
     * 游戏进行中的处理器
     */
    abstract val gaming: StageGamingHandler<Config, GamePLayer>

    /**
     * 游戏结束的处理器
     */
    abstract val ending: StageEndingHandler<Config, GamePLayer>

    /**
     * 当前的游戏阶段
     */
    @Volatile
    var currentStage = GameStage.INITIAL
        protected set

    /**
     * 当前的游戏阶段处理器, 保证在玩家进入游戏前初始化完成
     */
    @Volatile
    lateinit var currentStageHandler: GameStageHandler<Config, GamePLayer>
        protected set

    /**
     * 游戏房间信息
     */
    abstract val gameConfig: GameConfig

    /**
     * 房间中参与游戏的玩家
     */
    abstract val players: MutableMap<Player, GamePLayer>

    /**
     * 旁观玩家, 不参与游戏, 死亡后的玩家应另行处理
     */
    abstract val observers: MutableList<Player>

    var tickerTask: BukkitTask? = null

    open val gamers get() = players.keys

    inline val allowJoin get() = currentStage == GameStage.WAITING || currentStage == GameStage.READY

    protected open val handlers: List<GameStageHandler<Config, GamePLayer>> by lazy {
        listOf(
            waiting,
            readying,
            gaming,
            ending
        )
    }

    val inInstancePlayer
        get() = ArrayList<Player>(gamers.size + observers.size).apply {
            addAll(gamers)
            addAll(observers)
        }

    /**
     * 创建完成后调用, 确保游戏正常初始化
     */
    override fun register() {
        super.register()
        handlers.forEach {
            it.instance = this
            // 轮到哪个哪个才注册
            // it.register()
        }
        plugin.debug { "register game manager, init stage to waiting, start game tick loop" }
        currentStage = GameStage.WAITING
        currentStageHandler = waiting
        waiting.onEnter(waiting, null)
        tickerTask = plugin.runTaskTimer(20, 20) {
            currentStageHandler.safeOnTick()
        }
    }

    /**
     * 将玩家添加进游戏
     *
     * @param player 玩家
     */
    abstract fun addPlayer(player: Player)

    /**
     * 将玩家添加进游戏
     *
     * @param players 玩家集合
     */
    abstract fun addPlayer(players: Collection<Player>)

    /**
     * 将玩家添加为旁观者
     *
     * @param players 玩家集合
     */
    abstract fun addObserver(players: Collection<Player>)

    /**
     * 将玩家添加为旁观者
     *
     * @param player 玩家
     */
    abstract fun addObserver(player: Player)

    /**
     * 切换游戏阶段
     *
     * @param next 下一游戏阶段
     */
    fun switch(next: GameStage, data: Any? = null) {
        plugin.debug { "switch game stage from $currentStage to $next" }
        val nextStageHandler = when (next) {
            GameStage.WAITING -> waiting
            GameStage.READY -> readying
            GameStage.GAMING -> gaming
            GameStage.ENDING -> ending
            else -> throw Exception("unexpected game stage: ${next.name}")
        }
        try {
            plugin.debug { "${currentStageHandler.stage.name}.onLeave" }
            currentStageHandler.onLeave(nextStageHandler, data)
        } catch (e: Exception) {
            throw Exception("unexpected exception occur when leave game stage ${currentStage.name}", e)
        }
        val lastStageHandler = currentStageHandler
        currentStage = next
        currentStageHandler = nextStageHandler
        try {
            plugin.debug { "${currentStageHandler.stage.name}.onEnter" }
            currentStageHandler.onEnter(lastStageHandler, data)
        } catch (e: Exception) {
            throw Exception("unexpected exception occur when enter game stage ${currentStage.name}", e)
        }
    }

    /**
     * 销毁该实例
     *
     * 删除地图, 在游戏管理器中注销该实例
     */
    abstract fun destroy()

    /**
     * 移除玩家
     */
    open fun removePlayer(player: Player) {
        players.remove(player)
    }
}