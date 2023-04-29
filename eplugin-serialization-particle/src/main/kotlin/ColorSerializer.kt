package top.e404.eplugin.particle

import org.bukkit.Color

fun toColor(content: String): Color {
    val s = content.removePrefix("#")
    return when (s.length) {
        3 -> buildString { for (c in s) repeat(2) { append(c) } }.toIntOrNull(16)
        6 -> s.toIntOrNull(16)
        else -> null
    }?.let { Color.fromRGB(it) } ?: throw InvalidColorException(content)
}

fun toColor(color: Color) = buildString {
    append("#")
    append(color.red.toString(16).padStart(2, '0'))
    append(color.green.toString(16).padStart(2, '0'))
    append(color.blue.toString(16).padStart(2, '0'))
}

class InvalidColorException(content: String) : Exception("invalid color: $content")
