package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Material
import top.e404.eplugin.EPlugin.Companion.formatAsConst

object MaterialSerializer : KSerializer<Material> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = Material.valueOf(decoder.decodeString().formatAsConst())
    override fun serialize(encoder: Encoder, value: Material) = encoder.encodeString(value.name)
}