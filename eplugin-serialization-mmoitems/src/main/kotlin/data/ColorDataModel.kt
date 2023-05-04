package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.ColorData

@Serializable
@SerialName("ColorData")
data class ColorDataModel(
    var red: Int,
    var green: Int,
    var blue: Int,
) : RandomStatDataModel<ColorData, ColorData>, StatDataModel<ColorData> {
    companion object {
        fun ColorData.toDataModel() = ColorDataModel(
            red,
            green,
            blue,
        )
    }

    override fun toRandomStatData() = ColorData(red, green, blue)
    override fun toItemData() = toRandomStatData()
}
