package top.e404.eplugin.hook.bentobox

import dev.lone.itemsadder.api.CustomStack
import dev.lone.itemsadder.api.ItemsAdder
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import top.e404.eplugin.util.take

@Suppress("UNUSED")
open class ItemsAdderHook(
    override val plugin: EPlugin,
) : EHook(plugin, "ItemsAdder") {
    fun getIaItem(namespaceId: String) = CustomStack.getInstance(namespaceId)
    fun getIaItemInfo(item: ItemStack) = CustomStack.byItemStack(item)

    fun getAllItem() = ItemsAdder.getAllItems()!!
    fun getAllItem(namespace: String) = ItemsAdder.getAllItems(namespace)!!
    fun getAllItem(material: Material) = ItemsAdder.getAllItems(material)!!

    fun getAllNamespace() = mutableSetOf<String>().apply {
        ItemsAdder.getAllItems().forEach {
            add(it.namespace)
        }
    }

    fun count(
        inv: Inventory,
        namespacedID: String,
    ) = inv.count { item ->
        getIaItemInfo(item).let {
            it?.namespacedID == namespacedID
        }
    }

    fun take(
        inv: Inventory,
        namespacedID: String,
        amount: Int
    ) = inv.take(amount) { item ->
        getIaItemInfo(item).let {
            it?.namespacedID == namespacedID
        }
    }
}
