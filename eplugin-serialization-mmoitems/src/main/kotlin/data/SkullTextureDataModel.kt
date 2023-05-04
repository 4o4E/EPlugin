package top.e404.eplugin.serialization.mmoitems.data

import com.mojang.authlib.GameProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.SkullTextureData

@Serializable
@SerialName("SkullTextureData")
data class SkullTextureDataModel(
    @Serializable(GameProfileSerializer::class) var profile: GameProfile,
) : RandomStatDataModel<SkullTextureData, SkullTextureData>, StatDataModel<SkullTextureData> {
    companion object {
        fun SkullTextureData.toDataModel() = SkullTextureDataModel(gameProfile)
    }

    override fun toRandomStatData() = SkullTextureData(profile)
    override fun toItemData() = toRandomStatData()
}
