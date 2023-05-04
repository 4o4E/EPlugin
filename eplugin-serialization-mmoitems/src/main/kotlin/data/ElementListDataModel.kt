package top.e404.eplugin.serialization.mmoitems.data

import io.lumine.mythic.lib.element.Element
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.ElementListData
import net.Indyuce.mmoitems.util.ElementStatType
import net.Indyuce.mmoitems.util.Pair
import top.e404.eplugin.reflect.getPrivateField

@Serializable
@SerialName("ElementListData")
data class ElementListDataModel(
    var stats: Map<@Serializable(PairSerializer::class) Pair<@Serializable(ElementSerializer::class) Element, ElementStatType>, Double>
) : StatDataModel<ElementListData> {
    companion object {
        fun ElementListData.toDataModel() = ElementListDataModel(getPrivateField("stats"))
    }

    override fun toItemData() = ElementListData().also {
        it.getPrivateField<ElementListData, MutableMap<Pair<Element, ElementStatType>, Double>>("stats").putAll(stats)
    }
}
