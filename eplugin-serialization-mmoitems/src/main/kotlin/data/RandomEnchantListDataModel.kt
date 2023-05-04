package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.api.util.NumericStatFormula
import net.Indyuce.mmoitems.stat.data.EnchantListData
import net.Indyuce.mmoitems.stat.data.random.RandomEnchantListData
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.enchantments.Enchantment
import top.e404.eplugin.config.serialization.EnchantmentSerialization
import top.e404.eplugin.reflect.getPrivateField

@Serializable
@SerialName("RandomEnchantListData")
data class RandomEnchantListDataModel(
    var enchants: MutableMap<@Serializable(EnchantmentSerialization::class) Enchantment, @Serializable(NumericStatFormulaSerializer::class) NumericStatFormula>,
) : RandomStatDataModel<EnchantListData, RandomEnchantListData> {
    companion object {
        fun RandomEnchantListData.toDataModel() = RandomEnchantListDataModel(getPrivateField("enchants"))
    }

    override fun toRandomStatData() = RandomEnchantListData(YamlConfiguration()).also {
        enchants.forEach { (enchantment, formula) ->
            it.addEnchant(enchantment, formula)
        }
    }
}
