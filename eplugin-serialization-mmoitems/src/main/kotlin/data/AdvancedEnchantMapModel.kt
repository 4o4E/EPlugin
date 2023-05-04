package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.comp.enchants.advanced_enchants.AdvancedEnchantMap

@Serializable
@SerialName("AdvancedEnchantMap")
data class AdvancedEnchantMapModel(
    var enchants: MutableMap<String, Int>
) : StatDataModel<AdvancedEnchantMap> {
    companion object {
        fun AdvancedEnchantMap.toDataModel() = AdvancedEnchantMapModel(enchants)
    }

    override fun toItemData() = AdvancedEnchantMap().also {
        it.enchants.putAll(enchants)
    }
}
