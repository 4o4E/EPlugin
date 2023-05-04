package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.PotionEffectListData
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectData
import net.Indyuce.mmoitems.stat.data.random.RandomPotionEffectListData
import org.bukkit.configuration.file.YamlConfiguration

@Serializable
@SerialName("RandomPotionEffectListData")
data class RandomPotionEffectListDataModel(
    var effects: MutableList<@Serializable(RandomPotionEffectDataSerializer::class) RandomPotionEffectData>,
) : RandomStatDataModel<PotionEffectListData, RandomPotionEffectListData> {
    companion object {
        fun RandomPotionEffectListData.toDataModel() = RandomPotionEffectListDataModel(effects)
    }

    override fun toRandomStatData() = RandomPotionEffectListData(YamlConfiguration()).also { data ->
        effects.forEach { data.add(it) }
    }
}
