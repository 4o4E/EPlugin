package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.NamespacedKey

object NamespacedKeySerialization : KSerializer<NamespacedKey> {
    override val descriptor = PrimitiveSerialDescriptor(this::class.java.name, PrimitiveKind.STRING)

    @Suppress("DEPRECATION")
    override fun deserialize(decoder: Decoder) = decoder.decodeString().let {
        val index = it.indexOf(":")
        if (index == -1) return@let NamespacedKey.minecraft(it)
        NamespacedKey(it.substring(0, index), it.substring(index + 1))
    }

    override fun serialize(encoder: Encoder, value: NamespacedKey) = encoder.encodeString(value.toString())
}