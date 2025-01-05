@file:Suppress("UNUSED")

package top.e404.eplugin.game

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitTask
import top.e404.dynamicmap.dyn.DynamicMap
import top.e404.eplugin.EPlugin
import top.e404.eplugin.EPlugin.Companion.color
import top.e404.eplugin.game.stage.*
import top.e404.eplugin.listener.EListener
import top.e404.eplugin.util.EResult

/**
 * 一个游戏实例
 *
 * @param GamePlayer 玩家信息
 * @property plugin 对应的插件
 * @property manager 对应的实例管理器
 */
abstract class GameInstance<Config : GameConfig, GamePlayer : Gamer>(
    plugin: EPlugin,
    val manager: GameManager<Config, GamePlayer>,
    val map: DynamicMap
) : EListener(plugin) {
    /**
     * 游戏实例的唯一id -1是未初始化
     */
    var gameId = -1L

    /**
     * 玩家进入游戏的等待处理器
     */
    abstract val waiting: StageWaitingHandler<Config, GamePlayer>

    /**
     * 玩家准备游戏的准备处理器
     */
    abstract val readying: StageReadyingHandler<Config, GamePlayer>

    /**
     * 游戏进行中的处理器
     */
    abstract val gaming: StageGamingHandler<Config, GamePlayer>

    /**
     * 游戏结束的处理器
     */
    abstract val ending: StageEndingHandler<Config, GamePlayer>

    /**
     * 获取此游戏的占位符
     */
    abstract fun getInstancePlaceholder(player: Player): Array<Pair<String, *>>

    /**
     * 游戏开始时间
     */
    var gameStart = -1L

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
    lateinit var currentStageHandler: GameStageHandler<Config, GamePlayer>
        protected set

    /**
     * 游戏房间信息
     */
    abstract val gameConfig: GameConfig

    /**
     * 房间中参与游戏的玩家
     */
    abstract val players: MutableMap<Player, GamePlayer>

    /**
     * 旁观玩家, 不参与游戏, 死亡后的玩家应另行处理
     */
    abstract val observers: MutableList<Player>

    var tickerTask: BukkitTask? = null

    open val gamers get() = players.keys

    inline val allowJoin get() = currentStage == GameStage.WAITING || currentStage == GameStage.READY

    protected open val handlers: List<GameStageHandler<Config, GamePlayer>> by lazy {
        listOf(waiting, readying, gaming, ending)
    }

    val inInstancePlayer get() = observers + gamers

    /**
     * 创建完成后调用, 确保游戏正常初始化
     */
    override fun register() {
        super.register()
        manager.instances.add(this)
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
     * 检查是否允许玩家加入游戏
     */
    abstract fun allowJoin(players: Collection<Player>): Boolean

    /**
     * 检查是否允许玩家旁观游戏
     */
    abstract fun allowObserver(players: Collection<Player>): Boolean

    /**
     * 将玩家添加进游戏
     *
     * @param player 玩家
     * @return 是否成功添加, 若拒绝则返回false
     */
    abstract fun addPlayer(player: Player): EResult<Unit>

    /**
     * 将玩家添加进游戏
     *
     * @param players 玩家集合
     * @return 是否成功添加, 若拒绝则返回false
     */
    abstract fun addPlayer(players: Collection<Player>): EResult<Unit>

    /**
     * 将玩家添加为旁观者
     *
     * @param players 玩家集合
     * @return 是否成功添加, 若拒绝则返回false
     */
    abstract fun addObserver(players: Collection<Player>): EResult<Unit>

    /**
     * 将玩家添加为旁观者
     *
     * @param player 玩家
     * @return 是否成功添加, 若拒绝则返回false
     */
    abstract fun addObserver(player: Player): EResult<Unit>

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

    private val currentChatConfig inline get() = currentStageHandler.stageConfig.chat

    private fun getCurrentChatChannel(player: Player, prefix: String): ChatChannelConfig {
        val gamer = players[player]!!
        // 允许发言的频道
        val allowedChannels = currentChatConfig.channels.filter {
            it.allowRoles.matches(gamer.role.roleName)
        }
        return if (prefix == "") {
            currentChatConfig.defaults[gamer.role.roleName]?.let { channelName ->
                allowedChannels.firstOrNull { it.name == channelName }
            }
        } else {
            allowedChannels.firstOrNull {
                it.pattern === prefix
            }
        } ?: currentChatConfig.global
    }

    /**
     * 处理聊天事件
     */
    open fun onChat(player: Player, message: String) {
        val prefix = message.substringBefore(" ", "")
        // 没有前缀 默认频道
        val channel = getCurrentChatChannel(player, prefix)
        val sendMessage = "&7[${channel.name}&7] &f".color + message.removePrefix("!all ")
        if (channel.allowObserver) {
            for (p in observers) {
                p.sendMessage(sendMessage)
            }
        }
        players.values.filter {
            channel.allowRoles.matches(it.role.roleName)
        }.forEach {
            it.player.sendMessage(sendMessage)
        }
    }

    open fun shutdown() {
        tickerTask?.cancel()
        inInstancePlayer.forEach {
            it.reset()
            it.teleport(manager.lobby.toLocation())
            it.sendMessage("shutdown")
        }
        destroy()
    }

    @EventHandler
    fun PlayerQuitEvent.onEvent() {
        if (player in gamers) {
            removePlayer(player)
            player.reset()
            player.teleport(manager.lobby.toLocation())
            if (players.isEmpty()) destroy()
            return
        }
        if (player in observers) {
            observers.remove(player)
            player.reset()
            player.teleport(manager.lobby.toLocation())
        }
    }
}