package top.e404.eplugin.particle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Particle

@Serializable
@SerialName("Int")
data class IntParticle(
    override val particle: Particle,
    override val count: Int = 1,
    override val extra: Double = 0.0,
    val data: Int = 0,
) : ParticleConfig {
    override fun generator() = data
}
