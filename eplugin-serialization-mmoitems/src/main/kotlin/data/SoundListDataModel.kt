package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.api.CustomSound
import net.Indyuce.mmoitems.stat.data.SoundData
import net.Indyuce.mmoitems.stat.data.SoundListData

@Serializable
@SerialName("SoundListData")
data class SoundListDataModel(
    var sounds: Map<CustomSound, @Serializable(SoundDataSerializer::class) SoundData>,
) : RandomStatDataModel<SoundListData, SoundListData>, StatDataModel<SoundListData> {
    companion object {
        fun SoundListData.toDataModel() = SoundListDataModel(mapData())
    }

    override fun toRandomStatData() = SoundListData(sounds)
    override fun toItemData() = toRandomStatData()
}
