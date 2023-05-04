package top.e404.eplugin.serialization.mmoitems.data

import io.lumine.mythic.lib.element.Element
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import top.e404.eplugin.serialization.mmoitems.data.ElementModel.Companion.toDataModel

object ElementSerializer : KSerializer<Element> {
    private val serializer = ElementModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).toItemData()
    override fun serialize(encoder: Encoder, value: Element) = serializer.serialize(encoder, value.toDataModel())
}
