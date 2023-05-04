package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.EnchantListData
import org.bukkit.enchantments.Enchantment
import top.e404.eplugin.config.serialization.EnchantmentSerialization
import top.e404.eplugin.reflect.getPrivateField

@Serializable
@SerialName("EnchantListData")
data class EnchantListDataModel(
    var enchants: MutableMap<@Serializable(EnchantmentSerialization::class) Enchantment, Int>
) : StatDataModel<EnchantListData> {
    companion object {
        fun EnchantListData.toDataModel() = EnchantListDataModel(getPrivateField("enchants"))
    }

    override fun toItemData() = EnchantListData().also {
        it.getPrivateField<EnchantListData, MutableMap<Enchantment, Int>>("enchants").putAll(enchants)
    }
}
