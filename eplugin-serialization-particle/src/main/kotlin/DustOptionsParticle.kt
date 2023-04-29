package top.e404.eplugin.particle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Particle

@Serializable
@SerialName("DustOptions")
data class DustOptionsParticle(
    override val particle: Particle,
    override val count: Int = 1,
    override val extra: Double = 0.0,
    val data: DustOption,
) : ParticleConfig {
    override fun generator() = Particle.DustOptions(toColor(data.color), data.size)
}


@Serializable
data class DustOption(
    val color: String,
    val size: Float,
)
