@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.enchantments.Enchantment

object EnchantmentSerialization : KSerializer<Enchantment> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().deserializeToEnchantment()
    override fun serialize(encoder: Encoder, value: Enchantment) = encoder.encodeString(value.key.namespace)
}

@Suppress("DEPRECATION")
fun String.deserializeToEnchantment() = Enchantment.getByKey(deserializeToNamespacedKey()) ?: throw Exception("unknown enchantment: $this")
