@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DoubleRangeSerialization : KSerializer<ClosedFloatingPointRange<Double>> {
    override val descriptor =
        PrimitiveSerialDescriptor("top.e404.eplugin.config.serialization.IntRangeSerialization", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder) =
        decoder.decodeString().split("..").let { it[0].toDouble()..it[1].toDouble() }

    override fun serialize(encoder: Encoder, value: ClosedFloatingPointRange<Double>) =
        encoder.encodeString("${value.start}..${value.endInclusive}")
}