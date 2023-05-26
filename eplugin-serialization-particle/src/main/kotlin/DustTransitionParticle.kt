package top.e404.eplugin.particle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Particle

@Serializable
@SerialName("DustTransition")
data class DustTransitionParticle(
    override val particle: Particle,
    override val count: Int = 1,
    override val extra: Double = 0.0,
    override val chance: Double? = null,
    val data: DustTransition,
) : ParticleConfig {
    override fun generator() = Particle.DustTransition(
        toColor(data.from),
        toColor(data.to),
        data.size,
    )
}

@Serializable
data class DustTransition(
    val from: String,
    val to: String,
    val size: Float,
)
