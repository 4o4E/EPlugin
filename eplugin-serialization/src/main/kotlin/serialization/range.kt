@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IntRangeSerialization : KSerializer<IntRange> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().deserializeToIntRange()
    override fun serialize(encoder: Encoder, value: IntRange) = encoder.encodeString(value.serializeToString())
}

fun String.deserializeToIntRange() : IntRange {
    val split = split("..")
    val d = split[0].toInt()
    return if (split.size == 1) d..d
    else d..split[1].toInt()
}

fun IntRange.serializeToString() = "$first..$last"

object DoubleRangeSerialization : KSerializer<ClosedFloatingPointRange<Double>> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().deserializeToDoubleRange()
    override fun serialize(encoder: Encoder, value: ClosedFloatingPointRange<Double>) = encoder.encodeString(value.serializeToString())
}

fun String.deserializeToDoubleRange() : ClosedFloatingPointRange<Double> {
    val split = split("..")
    val d = split[0].toDouble()
    return if (split.size == 1) d..d
    else d..split[1].toDouble()
}

object FloatRangeSerialization : KSerializer<ClosedFloatingPointRange<Float>> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().deserializeToFloatRange()
    override fun serialize(encoder: Encoder, value: ClosedFloatingPointRange<Float>) = encoder.encodeString(value.serializeToString())
}

fun String.deserializeToFloatRange() : ClosedFloatingPointRange<Float> {
    val split = split("..")
    val d = split[0].toFloat()
    return if (split.size == 1) d..d
    else d..split[1].toFloat()
}

fun ClosedFloatingPointRange<*>.serializeToString() = "$start..$endInclusive"
