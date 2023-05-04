package top.e404.eplugin.serialization.mmoitems.data

import io.lumine.mythic.lib.api.item.ItemTag
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import top.e404.eplugin.serialization.mmoitems.data.ItemTagModel.Companion.toDataModel

object ItemTagSerializer : KSerializer<ItemTag> {
    private val serializer = ItemTagModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).mmoitemsDeserialize()
    override fun serialize(encoder: Encoder, value: ItemTag) = serializer.serialize(encoder, value.toDataModel())
}
