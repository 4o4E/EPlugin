package top.e404.eplugin.particle

import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.entity.Player

/**
 * # 粒子类型序列化器
 *
 * ## 示例
 *
 * ```yaml
 * - type: Normal
 *   particle: EXPLOSION_NORMAL
 *   count: 1
 *   extra: 0
 *
 * - type: DustOptions
 *   particle: REDSTONE
 *   count: 1
 *   extra: 0
 *   data:
 *     color: "#ffcccc"
 *     size: 1
 *
 * - type: ItemStack
 *   particle: ITEM_CRACK
 *   count: 1
 *   extra: 0
 *   data: IRON_SWORD
 *
 * - type: BlockData
 *   particle: BLOCK_CRACK // BLOCK_CRACK, BLOCK_DUST, FALLING_DUST, BLOCK_MARKER
 *   count: 1
 *   extra: 0
 *   data: IRON_BLOCK
 *
 * - type: DustTransition
 *   particle: DUST_COLOR_TRANSITION
 *   count: 1
 *   extra: 0
 *   data:
 *     from: "#ffffff"
 *     to: "#ffcccc"
 *     size: 1
 *
 * - type: DustTransition
 *   particle: DUST_COLOR_TRANSITION
 *   count: 1
 *   extra: 0
 *   data:
 *     from: "#ffffff"
 *     to: "#ffcccc"
 *     size: 1
 *
 * - type: Vibration
 *   particle: VIBRATION
 *   count: 1
 *   extra: 0
 *   data:
 *     origin: world;3;3;3
 *     destination: world;5;5;5
 *     arrival: 1
 *
 * - type: Float
 *   particle: SCULK_CHARGE
 *   count: 1
 *   extra: 0
 *   data: 1
 *
 * - type: Int
 *   particle: SHRIEK
 *   count: 1
 *   extra: 0
 *   data: 1
 * ```
 *
 * @param T data类型
 */
@Serializable
sealed interface ParticleConfig {
    val particle: Particle
    val count: Int
    val extra: Double
    fun generator(): Any?

    fun spawn(
        world: World,
        location: Location,
        offsetX: Double = 0.0,
        offsetY: Double = 0.0,
        offsetZ: Double = 0.0,
    ) = world.spawnParticle(
        particle, location, count,
        offsetX, offsetY, offsetZ,
        extra, generator(), false
    )

    fun spawn(
        player: Player,
        world: World,
        location: Location,
        offsetX: Double = 0.0,
        offsetY: Double = 0.0,
        offsetZ: Double = 0.0,
    ) = player.spawnParticle(
        particle, location, count,
        offsetX, offsetY, offsetZ,
        extra, generator()
    )
}

interface ParticleData {
    fun generator(): Any?
}