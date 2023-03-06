package top.e404.eplugin.particle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Particle

@Serializable
@SerialName("Normal")
data class NormalParticle(
    override val particle: Particle,
    override val count: Int = 1,
    override val extra: Double = 0.0,
) : ParticleConfig {
    override fun generator() = null
}