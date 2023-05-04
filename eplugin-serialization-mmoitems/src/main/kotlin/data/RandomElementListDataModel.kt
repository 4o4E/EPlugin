package top.e404.eplugin.serialization.mmoitems.data

import io.lumine.mythic.lib.element.Element
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.api.util.NumericStatFormula
import net.Indyuce.mmoitems.stat.data.ElementListData
import net.Indyuce.mmoitems.stat.data.random.RandomElementListData
import net.Indyuce.mmoitems.util.ElementStatType
import net.Indyuce.mmoitems.util.Pair
import org.bukkit.configuration.file.YamlConfiguration
import top.e404.eplugin.reflect.getPrivateField

@Serializable
@SerialName("RandomElementListData")
data class RandomElementListDataModel(
    var stats: Map<@Serializable(PairSerializer::class) Pair<@Serializable(ElementSerializer::class) Element, ElementStatType>, @Serializable(NumericStatFormulaSerializer::class) NumericStatFormula>,
) : RandomStatDataModel<ElementListData, RandomElementListData> {
    companion object {
        fun RandomElementListData.toDataModel() = RandomElementListDataModel(getPrivateField("stats"))
    }

    override fun toRandomStatData() = RandomElementListData(YamlConfiguration()).also {
        stats.forEach { (pair, formula) ->
            it.setStat(pair.key, pair.value, formula)
        }
    }
}
