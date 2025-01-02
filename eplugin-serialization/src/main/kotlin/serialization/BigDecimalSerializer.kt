package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

@Suppress("UNUSED")
object BigDecimalSerializer : KSerializer<BigDecimal> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = BigDecimal(decoder.decodeString())
    override fun serialize(encoder: Encoder, value: BigDecimal) = encoder.encodeString(value.toString())
}