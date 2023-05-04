package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.AbilityData
import net.Indyuce.mmoitems.stat.data.AbilityListData

@Serializable
@SerialName("AbilityListData")
data class AbilityListDataModel(
    var abilities: MutableSet<@Serializable(AbilityDataSerializer::class) AbilityData>,
) : StatDataModel<AbilityListData> {
    companion object {
        fun AbilityListData.toDataModel() = AbilityListDataModel(abilities)
    }

    override fun toItemData() = AbilityListData(abilities)
}

