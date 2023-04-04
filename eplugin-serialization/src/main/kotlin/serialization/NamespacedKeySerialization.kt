@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.NamespacedKey

object NamespacedKeySerialization : KSerializer<NamespacedKey> {
    override val descriptor = primitive()

    override fun deserialize(decoder: Decoder) = decoder.decodeString().deserializeToNamespacedKey()

    override fun serialize(encoder: Encoder, value: NamespacedKey) = encoder.encodeString(value.toString())
}

@Suppress("DEPRECATION")
fun String.deserializeToNamespacedKey() = let {
    val index = it.indexOf(":")
    if (index == -1) return@let NamespacedKey.minecraft(it)
    NamespacedKey(it.substring(0, index), it.substring(index + 1))
}
