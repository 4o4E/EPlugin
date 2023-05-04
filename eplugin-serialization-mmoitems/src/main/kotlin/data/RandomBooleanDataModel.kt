package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.BooleanData
import net.Indyuce.mmoitems.stat.data.random.RandomBooleanData

@Serializable
@SerialName("RandomBooleanData")
data class RandomBooleanDataModel(
    var chance: Double,
) : RandomStatDataModel<BooleanData, RandomBooleanData> {
    companion object {
        fun RandomBooleanData.toDataModel() = RandomBooleanDataModel(chance)
    }

    override fun toRandomStatData() = RandomBooleanData(chance)
}
