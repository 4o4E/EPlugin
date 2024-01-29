package top.e404.eplugin.adventure

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.inventory.ItemStack

val ItemStack.display get() = (itemMeta.displayName() ?: Component.translatable(translationKey()))
        .style(Style.style(TextColor.color(0xFFAA00)).decoration(TextDecoration.ITALIC, false))