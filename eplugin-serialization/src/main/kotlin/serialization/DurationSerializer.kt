package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.time.Duration

@Suppress("UNUSED")
object DurationSerializer : KSerializer<Duration> {
    override val descriptor = primitive()
    override fun serialize(encoder: Encoder, value: Duration) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder) = Duration.parse(decoder.decodeString())
}