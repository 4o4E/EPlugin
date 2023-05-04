package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.RestoreData

@Serializable
@SerialName("RestoreData")
data class RestoreDataModel(
    var health: Double,
    var food: Double,
    var saturation: Double,
) : StatDataModel<RestoreData> {
    companion object {
        fun RestoreData.toDataModel() = RestoreDataModel(health, food, saturation)
    }

    override fun toItemData() = RestoreData(health, food, saturation)
}
