@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object RegexSerialization : KSerializer<Regex> {
    override val descriptor = PrimitiveSerialDescriptor(this::class.java.name, PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = Regex(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: Regex) = encoder.encodeString(value.pattern)
}