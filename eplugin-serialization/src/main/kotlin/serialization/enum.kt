package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

open class EnumSerialization<T : Enum<*>>(val valueOf: (String) -> T) : KSerializer<T> {
    override val descriptor = primitive()
    override fun serialize(encoder: Encoder, value: T) = encoder.encodeString(value.name)
    override fun deserialize(decoder: Decoder) = valueOf(decoder.decodeString())
}