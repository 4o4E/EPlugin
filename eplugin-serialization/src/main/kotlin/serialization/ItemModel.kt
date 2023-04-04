@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.table.Tableable
import top.e404.eplugin.util.buildItemStack

@Serializable
data class ItemModel(
    override val weight: Int = 1,
    @Serializable(MaterialSerializer::class) val type: Material,
    @Serializable(IntRangeSerialization::class) val amount: IntRange = 1..1,
    val name: String? = null,
    val lore: List<String> = emptyList(),
) : Tableable<ItemStack> {
    override fun generator() = buildItemStack(type, amount.random(), name, lore)
}
