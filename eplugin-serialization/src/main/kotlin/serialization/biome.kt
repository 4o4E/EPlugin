@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.block.Biome
import top.e404.eplugin.EPlugin.Companion.formatAsConst

object BiomeSerialization : KSerializer<Biome> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().toBiome()
    override fun serialize(encoder: Encoder, value: Biome) = encoder.encodeString(value.name)
}

fun String.toBiome() = Biome.valueOf(formatAsConst())
