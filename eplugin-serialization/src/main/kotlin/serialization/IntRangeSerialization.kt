@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IntRangeSerialization : KSerializer<IntRange> {
    override val descriptor =
        PrimitiveSerialDescriptor("top.e404.eplugin.config.serialization.IntRangeSerialization", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder) = decoder.decodeString().split("..").let { it[0].toInt()..it[1].toInt() }

    override fun serialize(encoder: Encoder, value: IntRange) = encoder.encodeString("${value.first}..${value.last}")
}