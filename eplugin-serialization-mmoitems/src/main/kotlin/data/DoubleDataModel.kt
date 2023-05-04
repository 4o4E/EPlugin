package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.DoubleData

@Serializable
@SerialName("DoubleData")
data class DoubleDataModel(
    var value: Double
) : StatDataModel<DoubleData> {
    companion object {
        fun DoubleData.toDataModel() = DoubleDataModel(value)
    }

    override fun toItemData() = DoubleData(value)
}

