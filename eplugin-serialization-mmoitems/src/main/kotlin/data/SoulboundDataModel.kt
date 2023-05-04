package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.SoulboundData
import top.e404.eplugin.config.serialization.UUIDSerialization
import java.util.*

@Serializable
@SerialName("SoulboundData")
data class SoulboundDataModel(
    @Serializable(UUIDSerialization::class) var uuid: UUID,
    var name: String,
    var level: Int,
): StatDataModel<SoulboundData> {
    companion object {
        fun SoulboundData.toDataModel() = SoulboundDataModel(uniqueId, name, level)
    }

    override fun toItemData() = SoulboundData(uuid, name, level)
}
