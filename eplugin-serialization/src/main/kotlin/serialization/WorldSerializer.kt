package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Bukkit
import org.bukkit.World

object WorldSerializer : KSerializer<World> {
    override val descriptor: SerialDescriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().let { Bukkit.getWorld(it) ?: throw NoSuchElementException("invalid world: $it") }
    override fun serialize(encoder: Encoder, value: World) = encoder.encodeString(value.name)
}