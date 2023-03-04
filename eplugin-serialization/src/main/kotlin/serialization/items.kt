package top.e404.eplugin.config.serialization

import com.google.gson.reflect.TypeToken
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.util.gson
import top.e404.eplugin.util.toJson

object ItemStackMinSerialization : KSerializer<ItemStack> {
    private val type by lazy { object : TypeToken<Map<String, Any>>() {}.type }
    override val descriptor = PrimitiveSerialDescriptor(this::class.java.name, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ItemStack {
        val data = gson.fromJson<Map<String, Any>>(decoder.decodeString(), type)
        return ItemStack.deserialize(data)
    }

    override fun serialize(encoder: Encoder, value: ItemStack) {
        encoder.encodeString(value.serialize().toJson())
    }
}