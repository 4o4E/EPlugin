@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import com.google.gson.reflect.TypeToken
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.util.gson
import top.e404.eplugin.util.toItemStack
import top.e404.eplugin.util.toJson

object ItemStackMinSerialization : KSerializer<ItemStack> {
    private val type by lazy { object : TypeToken<Map<String, Any>>() {}.type }
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = ItemStack.deserialize(gson.fromJson<Map<String, Any>>(decoder.decodeString(), type))
    override fun serialize(encoder: Encoder, value: ItemStack) = encoder.encodeString(value.serialize().toJson())
}

object ItemStackPlainSerialization : KSerializer<ItemStack> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = MaterialSerializer.deserialize(decoder).toItemStack()
    override fun serialize(encoder: Encoder, value: ItemStack) = encoder.encodeString(value.type.name)
}
