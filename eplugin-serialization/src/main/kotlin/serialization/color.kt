@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Color

object ColorSerializer : KSerializer<Color> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().serializeToColor()
    override fun serialize(encoder: Encoder, value: Color) = encoder.encodeString(value.deserializeToString())
}

fun String.serializeToColor() = removePrefix("#").run {
    Color.fromRGB(
        substring(0, 2).toInt(16),
        substring(2, 4).toInt(16),
        substring(4, 6).toInt(16)
    )
}

fun Color.deserializeToString() = buildString {
    append("#")
    append(red.toString(16).padStart(2, '0'))
    append(green.toString(16).padStart(2, '0'))
    append(blue.toString(16).padStart(2, '0'))
}
