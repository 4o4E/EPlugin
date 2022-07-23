package top.e404.eplugin.hook.slimefun

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

open class SlimefunHook(
    override val plugin: EPlugin,
) : EHook<Slimefun>(plugin, "Slimefun") {
    val sf: Slimefun
        get() = Slimefun.instance()!!

    fun getItem(id: String) = SlimefunItem.getById(id)
    fun getId(itemStack: ItemStack) = SlimefunItem.getByItem(itemStack)?.id
}