package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.ArrowParticlesData
import org.bukkit.Particle
import top.e404.eplugin.reflect.getPrivateField

@Serializable
@SerialName("ArrowParticlesData")
data class ArrowParticlesDataModel(
    var particle: Particle? = null,
    var amount: Int = 0,
    var red: Int = 0,
    var green: Int = 0,
    var blue: Int = 0,
    var speed: Double = 0.0,
    var offset: Double = 0.0,
    var colored: Boolean = false,
) : RandomStatDataModel<ArrowParticlesData, ArrowParticlesData>, StatDataModel<ArrowParticlesData> {
    companion object {
        fun ArrowParticlesData.toDataModel() = ArrowParticlesDataModel(
            particle,
            amount,
            red,
            green,
            blue,
            speed,
            offset,
            getPrivateField("colored"),
        )
    }

    override fun toRandomStatData() =
        if (colored) ArrowParticlesData(particle, amount, offset, red, green, blue)
        else ArrowParticlesData(particle, amount, offset, speed)

    override fun toItemData() = toRandomStatData()
}
