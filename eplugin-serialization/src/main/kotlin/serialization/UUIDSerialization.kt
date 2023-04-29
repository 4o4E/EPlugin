@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

object UUIDSerialization : KSerializer<UUID> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = UUID.fromString(decoder.decodeString())!!
    override fun serialize(encoder: Encoder, value: UUID) = encoder.encodeString(value.toString())
}
