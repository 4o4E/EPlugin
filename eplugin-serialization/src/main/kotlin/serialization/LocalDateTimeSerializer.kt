package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("UNUSED")
object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    fun serialize(value: LocalDateTime): String = formatter.format(value)
    fun deserialize(value: String): LocalDateTime = LocalDateTime.parse(value, formatter)

    override val descriptor = primitive()
    override fun serialize(encoder: Encoder, value: LocalDateTime) = encoder.encodeString(serialize(value))
    override fun deserialize(decoder: Decoder): LocalDateTime = deserialize(decoder.decodeString())
}