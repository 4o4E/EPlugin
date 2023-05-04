package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.ProjectileParticlesData
import org.bukkit.Particle
import top.e404.eplugin.reflect.getPrivateField

@Serializable
@SerialName("ProjectileParticlesData")
data class ProjectileParticlesDataModel(
    var particle: Particle,
    var red: Int,
    var green: Int,
    var blue: Int,
    var colored: Boolean,
) : RandomStatDataModel<ProjectileParticlesData, ProjectileParticlesData>, StatDataModel<ProjectileParticlesData> {
    companion object {
        fun ProjectileParticlesData.toDataModel() = ProjectileParticlesDataModel(
            particle,
            red,
            green,
            blue,
            getPrivateField("colored")
        )
    }

    override fun toRandomStatData() =
        if (colored) ProjectileParticlesData(particle, red, green, blue)
        else ProjectileParticlesData(particle)

    override fun toItemData() = toRandomStatData()
}
