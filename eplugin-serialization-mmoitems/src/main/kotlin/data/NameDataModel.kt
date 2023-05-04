package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.StringData
import net.Indyuce.mmoitems.stat.type.NameData
import top.e404.eplugin.reflect.setPrivateField

@Serializable
@SerialName("NameData")
data class NameDataModel(
    var prefixes: ArrayList<String?> = ArrayList(),
    var suffixes: ArrayList<String?> = ArrayList(),
    var value: String,
) : RandomStatDataModel<StringData, NameData>, StatDataModel<NameData> {
    companion object {
        fun NameData.toDataModel() = NameDataModel(value = string!!).also {
            it.prefixes = prefixes
            it.suffixes = suffixes
        }
    }

    override fun toRandomStatData() = NameData(value).apply {
        setPrivateField("prefixes", prefixes)
        setPrivateField("suffixes", suffixes)
    }

    override fun toItemData() = toRandomStatData()
}
