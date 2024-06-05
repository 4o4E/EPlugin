@file:Suppress("UNUSED")

package top.e404.eplugin.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World
import top.e404.eplugin.config.serialization.IntRangeSerialization
import top.e404.eplugin.config.serialization.Message
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
 * @property min 游戏最少玩家数
 * @property max 游戏最多玩家数
 * @property excludeFeatures 排除的特性
 * @property border 游戏区域边界
 */
interface GameInfoConfig {
    val name: String
    val min: Int
    val max: Int
    val excludeFeatures: Set<String>
    val border: VLocationRange
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
 */
interface GameStageConfig {
    val enter: Message?
    val leave: Message?
    val scoreboard: ScoreboardConfig
}

/**
 * 等待阶段的游戏设置
 *
 * @property lobby 大厅(等待区域)的位置
 */
interface WaitingConfig : GameStageConfig {
    val lobby: VLocation
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
}

/**
 * 游戏阶段的游戏设置
 *
 * @property duration 阶段的持续时长
 * @property spawn 开始游戏时的传送点
 */
interface GamingConfig : GameStageConfig {
    val duration: Long
    val spawn: VLocation
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
