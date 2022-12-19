package top.e404.eplugin.hook.slimefun

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import org.bukkit.block.Block
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import top.e404.eplugin.util.take

@Suppress("UNUSED")
open class SlimefunHook(
    override val plugin: EPlugin,
) : EHook(plugin, "Slimefun") {
    val sf: Slimefun
        get() = Slimefun.instance()!!

    fun getItem(id: String) = SlimefunItem.getById(id)
    fun getId(itemStack: ItemStack) = SlimefunItem.getByItem(itemStack)?.id
    fun hasBlock(block: Block) = Slimefun.getBlockDataService()
        .getBlockData(block)
        .isPresent

    fun getIdByBlock(block: Block) = Slimefun.getBlockDataService()
        .getBlockData(block)
        .let { if (it.isPresent) it.get() else null }

    fun count(
        inv: Inventory,
        id: String,
    ) = inv.count { item ->
        getId(item) == id
    }

    fun take(
        inv: Inventory,
        id: String,
        amount: Int
    ) = inv.take(amount) { item ->
        getId(item) == id
    }
}