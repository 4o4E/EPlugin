package top.e404.eplugin.serialization.mmoitems.data

import com.mojang.authlib.GameProfile
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import top.e404.eplugin.serialization.mmoitems.data.GameProfileModel.Companion.toDataModel

object GameProfileSerializer : KSerializer<GameProfile> {
    private val serializer = GameProfileModel.serializer()
    override val descriptor = serializer.descriptor
    override fun deserialize(decoder: Decoder) = serializer.deserialize(decoder).mmoitemsDeserialize()
    override fun serialize(encoder: Encoder, value: GameProfile) = serializer.serialize(encoder, value.toDataModel())
}
