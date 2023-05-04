package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.GemSocketsData

@Serializable
@SerialName("GemSocketsData")
data class GemSocketsDataModel(
    val emptySlots: MutableList<String>,
) : RandomStatDataModel<GemSocketsData, GemSocketsData>, StatDataModel<GemSocketsData> {
    companion object {
        fun GemSocketsData.toDataModel() = GemSocketsDataModel(emptySlots)
    }

    override fun toRandomStatData() = GemSocketsData(emptySlots)
    override fun toItemData() = toRandomStatData()
}
