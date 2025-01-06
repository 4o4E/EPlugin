@file:Suppress("UNUSED")

package top.e404.eplugin.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import top.e404.eplugin.config.serialization.IntRangeSerialization
import top.e404.eplugin.config.serialization.Message
import top.e404.eplugin.config.serialization.RegexSerialization
import top.e404.eplugin.config.serialization.ScoreboardConfig

/**
 * 代表一个小游戏房间的设置
 *
 * @property info 游戏信息
 * @property waiting 等待阶段的设置
 * @property readying 准备阶段的设置
 * @property gaming 游戏阶段的设置
 * @property ending 结束阶段的设置
 */
interface GameConfig {
    val info: GameInfoConfig
    val waiting: WaitingConfig
    val readying: ReadyingConfig
    val gaming: GamingConfig
    val ending: EndingConfig
}

/**
 * 游戏的基本信息
 *
 * @property name 游戏的名字
 * @property nameColor 名字的颜色, 名字要用于指令输入, 不能带颜色
 * @property mapName 使用的地图名字
 * @property min 游戏最少玩家数
 * @property max 游戏最多玩家数
 * @property excludeFeatures 排除的特性
 * @property border 游戏区域边界
 */
interface GameInfoConfig {
    val name: String
    val nameColor: String
    val mapName: String
    val min: Int
    val max: Int
    val excludeFeatures: Set<String>
    val border: VLocationRange

    val displayName get() = "&$nameColor$name"
}

@Serializable
data class VLocationRange(
    @SerialName("x")
    @Serializable(IntRangeSerialization::class)
    val xRange: IntRange,
    @SerialName("y")
    @Serializable(IntRangeSerialization::class)
    val yRange: IntRange,
    @SerialName("z")
    @Serializable(IntRangeSerialization::class)
    val zRange: IntRange,
) {
    operator fun contains(l: Location) = contains(l.x, l.y, l.z)
    fun contains(x: Int, y: Int, z: Int) = x in xRange && y in yRange && z in zRange
    fun contains(x: Double, y: Double, z: Double) = contains(x.toInt(), y.toInt(), z.toInt())
    fun getCenter(world: World) = Location(
        world,
        xRange.first + (xRange.last.toDouble() - xRange.first) / 2,
        yRange.first + (yRange.last.toDouble() - yRange.first) / 2,
        zRange.first + (zRange.last.toDouble() - zRange.first) / 2
    )

    val xLength get() = xRange.last - xRange.first
    val yLength get() = yRange.last - yRange.first
    val zLength get() = zRange.last - zRange.first
}

/**
 * 游戏阶段的基本设置
 *
 * @property enter 进入该阶段时发送的消息
 * @property leave 退出该阶段时发送的消息
 * @property scoreboard 该阶段的计分板显示
 * @property chat 该阶段的聊天频道配置
 */
interface GameStageConfig {
    val enter: Message?
    val leave: Message?
    val scoreboard: ScoreboardConfig
    val chat: ChatConfig
}

/**
 * 聊天配置
 * @param enableGlobal 启用全局频道
 * @param global 全局频道配置
 * @param channels 私有频道配置
 * @param defaults 配置角色的默认发言频道
 */
@Serializable
data class ChatConfig(
    val enableGlobal: Boolean,
    val global: ChatChannelConfig,
    val channels: List<ChatChannelConfig>,
    val defaults: Map<String, String>
)

/**
 * 聊天频道配置
 * @param name 频道名称
 * @param pattern 发言前缀匹配频道 ""意味匹配无前缀
 * @param allowRoles 允许使用该频道的角色
 * @param allowObserver 允许旁观者看到该频道
 */
@Serializable
data class ChatChannelConfig(
    val name: String,
    val pattern: String,
    @Serializable(RegexSerialization::class)
    val allowRoles: Regex,
    val allowObserver: Boolean,
)

/**
 * 等待阶段的游戏设置
 *
 * @property lobby 大厅(等待区域)的位置
 */
interface WaitingConfig : GameStageConfig {
    val lobby: VLocation
    val join: Message
}

@Serializable
data class VLocation(
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float = 0f,
    val pitch: Float = 0f,
) {
    fun toLocation(world: World) = Location(world, x, y, z, yaw, pitch)
}

/**
 * 准备阶段的游戏设置
 *
 * @property duration 准备阶段的时长
 * @property countdownMessage 倒计时的消息
 * @property limit 倒计时在[limit]之内显示倒计时
 */
interface ReadyingConfig : GameStageConfig {
    val duration: Long
    val countdownMessage: Message?
    val limit: Long
    val join: Message
}

/**
 * 游戏阶段的游戏设置
 *
 * @property duration 阶段的持续时长
 * @property spawn 开始游戏时的传送点
 * @property countdownMessage 游戏剩余时间的消息
 */
interface GamingConfig : GameStageConfig {
    val duration: Long
    val spawn: VLocation
    val countdownMessage: Map<Long, Message>
}

/**
 * 结束阶段的游戏设置
 *
 * @property duration 阶段的持续时长
 * @property auto 是否在结束阶段结束后传送回大厅
 */
interface EndingConfig : GameStageConfig {
    val duration: Long
    val auto: Boolean
}
