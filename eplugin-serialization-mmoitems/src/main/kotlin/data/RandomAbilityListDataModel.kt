package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.AbilityListData
import net.Indyuce.mmoitems.stat.data.random.RandomAbilityData
import net.Indyuce.mmoitems.stat.data.random.RandomAbilityListData

@Serializable
@SerialName("RandomAbilityListData")
data class RandomAbilityListDataModel(
    var abilities: MutableSet<@Serializable(RandomAbilityDataSerializer::class) RandomAbilityData>,
) : RandomStatDataModel<AbilityListData, RandomAbilityListData> {
    companion object {
        fun RandomAbilityListData.toDataModel() = RandomAbilityListDataModel(abilities)
    }

    override fun toRandomStatData() = RandomAbilityListData().also { it.abilities.addAll(abilities) }
}
