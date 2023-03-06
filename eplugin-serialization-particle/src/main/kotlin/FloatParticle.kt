package top.e404.eplugin.particle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Particle

@Serializable
@SerialName("Float")
data class FloatParticle(
    override val particle: Particle,
    override val count: Int = 1,
    override val extra: Double = 0.0,
    val data: Float = 0f,
) : ParticleConfig {
    override fun generator() = data
}