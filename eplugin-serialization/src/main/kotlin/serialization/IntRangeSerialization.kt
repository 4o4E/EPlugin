@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IntRangeSerialization : KSerializer<IntRange> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().split("..").let { list -> list[0].toInt()..list.getOrElse(1) { list[0] }.toInt() }
    override fun serialize(encoder: Encoder, value: IntRange) = encoder.encodeString("${value.first}..${value.last}")
}