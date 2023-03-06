package top.e404.eplugin.particle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import top.e404.eplugin.EPlugin.Companion.formatAsConst

@Serializable
@SerialName("BlockData")
data class BlockParticle(
    override val particle: Particle,
    override val count: Int = 1,
    override val extra: Double = 0.0,
    val data: String,
) : ParticleConfig {
    override fun generator() = Bukkit.createBlockData(Material.valueOf(data.formatAsConst()))
}