package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.BooleanData

@Serializable
@SerialName("BooleanData")
data class BooleanDataModel(
    var state: Boolean,
) : StatDataModel<BooleanData> {
    companion object {
        fun BooleanData.toDataModel() = BooleanDataModel(isEnabled)
    }

    override fun toItemData() = BooleanData(state)
}

