package top.e404.eplugin.game.config

import kotlinx.serialization.Serializable
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.PlayerInventory
import top.e404.eplugin.config.serialization.Item

@Serializable
data class InvConfig(
    val helmet: Item? = null,
    val chestplate: Item? = null,
    val leggings: Item? = null,
    val boots: Item? = null,
    val inv: Map<Int, Item>
) {
    @Suppress("UNUSED")
    fun apply(inv: PlayerInventory) {
        helmet?.let { inv.setItem(EquipmentSlot.HEAD, it.item) }
        chestplate?.let { inv.setItem(EquipmentSlot.CHEST, it.item) }
        leggings?.let { inv.setItem(EquipmentSlot.LEGS, it.item) }
        boots?.let { inv.setItem(EquipmentSlot.FEET, it.item) }
        this.inv.forEach { (index, item) -> inv.setItem(index, item.item) }
    }
}
