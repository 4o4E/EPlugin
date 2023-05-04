package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.StringListData

@Serializable
@SerialName("StringListData")
data class StringListDataModel(
    var list: ArrayList<String>,
) : RandomStatDataModel<StringListData, StringListData>, StatDataModel<StringListData> {
    companion object {
        fun StringListData.toDataModel() = StringListDataModel(ArrayList(list))
    }

    override fun toRandomStatData() = StringListData(list)

    override fun toItemData() = toRandomStatData()
}
