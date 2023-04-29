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

fun Collection<String>.toComponent(
    prefix: TextComponent,
    spacing: TextComponent,
    suffix: TextComponent,
    block: String.() -> TextComponent
): MutableList<TextComponent> {
    val iterator = iterator()
    val list = ArrayList<TextComponent>(size * 2 + 1)
    list.add(prefix)
    while (true) {
        val next = iterator.next()
        list.add(next.block())
        if (!iterator.hasNext()) break
        list.add(spacing)
    }
    list.add(suffix)
    return list
}

fun Collection<TextComponent>.toComponent(
    prefix: TextComponent,
    spacing: TextComponent,
    suffix: TextComponent
): MutableList<TextComponent> {
    val iterator = iterator()
    val list = ArrayList<TextComponent>(size * 2 + 1)
    list.add(prefix)
    while (true) {
        val next = iterator.next()
        list.add(next)
        if (!iterator.hasNext()) break
        list.add(spacing)
    }
    list.add(suffix)
    return list
}

fun Collection<String>.toComponentArray(
    prefix: TextComponent,
    spacing: TextComponent,
    suffix: TextComponent,
    block: String.() -> TextComponent
) = toComponent(prefix, spacing, suffix, block).toTypedArray()

fun Collection<TextComponent>.toComponentArray(
    prefix: TextComponent,
    spacing: TextComponent,
    suffix: TextComponent
) = toComponent(prefix, spacing, suffix).toTypedArray()

val Location.asString
    get() = "world: ${world?.name}, x: $blockX, y: $blockY, z: $blockZ"
