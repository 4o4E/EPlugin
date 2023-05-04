package top.e404.eplugin.serialization.mmoitems.data

import com.mojang.authlib.properties.Property
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import top.e404.eplugin.serialization.mmoitems.data.PropertyModel.Companion.toDataModel

object PropertySerializer : KSerializer<Property> {
    private val serializer = PropertyModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).mmoitemsDeserialize()
    override fun serialize(encoder: Encoder, value: Property) = serializer.serialize(encoder, value.toDataModel())
}
