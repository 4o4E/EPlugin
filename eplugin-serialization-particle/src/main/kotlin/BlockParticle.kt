package top.e404.eplugin.particle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.block.Block
import top.e404.eplugin.EPlugin.Companion.formatAsConst
import kotlin.random.Random

@Serializable
@SerialName("BlockData")
data class BlockParticle(
    override val particle: Particle,
    override val count: Int = 1,
    override val extra: Double = 0.0,
    override val chance: Double? = null,
    val data: String,
) : ParticleConfig {
    override fun generator() = Bukkit.createBlockData(Material.valueOf(data.formatAsConst()))

    fun spawnOnBlock(
        block: Block,
        offsetX: Double = 0.0,
        offsetY: Double = 0.0,
        offsetZ: Double = 0.0,
    ) {
        chance?.let { if (Random.nextDouble() > it) return }
        block.world.spawnParticle(
            particle, block.location.add(0.5, 0.5, 0.5), count,
            offsetX, offsetY, offsetZ,
            extra, Bukkit.createBlockData(block.type), false
        )
    }
}
