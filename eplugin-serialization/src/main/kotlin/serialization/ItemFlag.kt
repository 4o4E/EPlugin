@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.inventory.ItemFlag
import top.e404.eplugin.EPlugin.Companion.formatAsConst

object ItemFlagSerializer : KSerializer<ItemFlag> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = ItemFlag.valueOf(decoder.decodeString().formatAsConst())
    override fun serialize(encoder: Encoder, value: ItemFlag) = encoder.encodeString(value.name)
}