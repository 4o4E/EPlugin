package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.RequiredLevelData

@Serializable
@SerialName("RequiredLevelData")
data class RequiredLevelDataModel(
    var value: Double,
) : StatDataModel<RequiredLevelData> {
    companion object {
        fun RequiredLevelData.toDataModel() = RequiredLevelDataModel(value)
    }

    override fun toItemData() = RequiredLevelData(value)
}
