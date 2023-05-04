package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.MaterialData
import org.bukkit.Material

@Serializable
@SerialName("MaterialData")
data class MaterialDataModel(
    val material: Material,
) : RandomStatDataModel<MaterialData, MaterialData>, StatDataModel<MaterialData> {
    companion object {
        fun MaterialData.toDataModel() = MaterialDataModel(material)
    }

    override fun toRandomStatData() = MaterialData(material)
    override fun toItemData() = toRandomStatData()
}
