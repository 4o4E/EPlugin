package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.PotionEffectData
import org.bukkit.potion.PotionEffectType
import top.e404.eplugin.config.serialization.PotionEffectTypeSerializer

@Serializable
data class PotionEffectDataModel(
    @Serializable(PotionEffectTypeSerializer::class) var potionEffectType: PotionEffectType,
    var duration: Double,
    var level: Int
) {
    companion object {
        fun PotionEffectData.toDataModel() = PotionEffectDataModel(
            type,
            duration,
            level
        )
    }

    fun toStatData() = PotionEffectData(potionEffectType, duration, level)
}
