package top.e404.eplugin.particle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Vibration

@Serializable
@SerialName("Vibration")
data class VibrationParticle(
    override val particle: Particle,
    override val count: Int = 1,
    override val extra: Double = 0.0,
    val data: V,
) : ParticleConfig {
    override fun generator(): Vibration {
        val origin = data.origin.split(";").let {
            Location(
                Bukkit.getWorld(it[0]),
                it[1].toDouble(),
                it[2].toDouble(),
                it[3].toDouble(),
            )
        }
        val destination = data.destination.split(";").let {
            Location(
                Bukkit.getWorld(it[0]),
                it[1].toDouble(),
                it[2].toDouble(),
                it[3].toDouble(),
            )
        }.let { Vibration.Destination.BlockDestination(it) }
        return Vibration(
            origin,
            destination,
            data.arrival
        )
    }
}

@Serializable
data class V(
    val origin: String,
    val destination: String,
    val arrival: Int,
)
