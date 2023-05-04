package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.particle.api.ParticleType
import net.Indyuce.mmoitems.stat.data.ParticleData
import org.bukkit.Particle
import org.bukkit.configuration.file.YamlConfiguration
import top.e404.eplugin.config.serialization.deserializeToString
import top.e404.eplugin.config.serialization.serializeToColor

@Serializable
@SerialName("ParticleData")
data class ParticleDataModel(
    val type: ParticleType,
    val particle: Particle,
    val color: String,
    val modifiers: MutableMap<String, Double>,
) : RandomStatDataModel<ParticleData, ParticleData>, StatDataModel<ParticleData> {
    companion object {
        fun ParticleData.toDataModel() = ParticleDataModel(
            type,
            particle,
            color.deserializeToString(),
            modifiers.associateWith { getModifier(it) }.toMutableMap()
        )
    }

    override fun toRandomStatData() = ParticleData(YamlConfiguration().apply {
        set("type", type.name)
        set("particle", particle.name)
        val c = color.serializeToColor()
        set("color.red", c.red)
        set("color.green", c.green)
        set("color.blue", c.blue)
        modifiers.forEach { (k, v) -> set(k, v) }
    })

    override fun toItemData() = toRandomStatData()
}
