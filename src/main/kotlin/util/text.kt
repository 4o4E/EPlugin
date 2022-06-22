@file:Suppress("UNUSED")

package top.e404.eplugin.util

import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Location
import top.e404.eplugin.EPlugin.Companion.color

fun String.hover(hover: String) = TextComponent(color()).apply {
    hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, arrayOf(TextComponent(hover)))
}

fun TextComponent.click(action: ClickEvent.Action, value: String) = apply {
    this.clickEvent = ClickEvent(action, value)
}

val Location.asString
    get() = "world: ${world?.name}, x: $blockX, y: $blockY, z: $blockZ"