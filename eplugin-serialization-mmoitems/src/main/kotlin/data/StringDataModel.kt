package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.StringData

@Serializable
@SerialName("StringData")
data class StringDataModel(
    var value: String?,
) : RandomStatDataModel<StringData, StringData>, StatDataModel<StringData> {
    companion object {
        fun StringData.toDataModel() = StringDataModel(string)
    }

    override fun toRandomStatData() = StringData(value)
    override fun toItemData() = toRandomStatData()
}
