package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.PotionEffectListData
import org.bukkit.Particle
import top.e404.eplugin.util.asMutableList
import top.e404.eplugin.serialization.mmoitems.data.PotionEffectDataModel.Companion.toDataModel

@Serializable
@SerialName("PotionEffectListData")
data class PotionEffectListDataModel(
    var effects: MutableList<PotionEffectDataModel>
) : StatDataModel<PotionEffectListData> {
    companion object {
        fun PotionEffectListData.toDataModel() = PotionEffectListDataModel(effects.map { it.toDataModel() }.asMutableList())
    }

    override fun toItemData() = PotionEffectListData().also { listData ->
        effects.forEach { listData.effects.add(it.toStatData()) }
    }
}
