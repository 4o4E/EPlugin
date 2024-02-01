@file:Suppress("UNUSED")

package top.e404.eplugin.serialization.adventure

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer
import top.e404.eplugin.config.serialization.primitive

fun Component.serializeToString() = JSONComponentSerializer.json().serialize(this)
fun String.deserializeToComponent() = JSONComponentSerializer.json().deserialize(this)

object ComponentSerializer : KSerializer<Component> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().deserializeToComponent()
    override fun serialize(encoder: Encoder, value: Component) = encoder.encodeString(value.serializeToString())
}