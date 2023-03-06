@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.enchantments.Enchantment

object EnchantmentSerialization : KSerializer<Enchantment> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = Enchantment.getByKey(NamespacedKeySerialization.deserialize(decoder))!!
    override fun serialize(encoder: Encoder, value: Enchantment) = encoder.encodeString(value.key.toString())
}