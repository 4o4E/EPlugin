package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.api.util.NumericStatFormula
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectData
import org.bukkit.potion.PotionEffectType
import top.e404.eplugin.config.serialization.PotionEffectTypeSerializer

@Serializable
data class RandomPotionEffectDataModel(
    @Serializable(PotionEffectTypeSerializer::class) var type: PotionEffectType,
    @Serializable(NumericStatFormulaSerializer::class) var duration: NumericStatFormula,
    @Serializable(NumericStatFormulaSerializer::class) var amplifier: NumericStatFormula,
) {
    companion object {
        fun RandomPotionEffectData.toDataModel() = RandomPotionEffectDataModel(type, duration, amplifier)
    }

    fun toItemData() = RandomPotionEffectData(type, duration, amplifier)
}
