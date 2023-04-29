package top.e404.eplugin.particle

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin.Companion.formatAsConst

@Serializable
@SerialName("ItemStack")
data class ItemParticle(
    override val particle: Particle,
    override val count: Int = 1,
    override val extra: Double = 0.0,
    val data: String,
) : ParticleConfig {
    override fun generator() = ItemStack(Material.valueOf(data.formatAsConst()))
}
