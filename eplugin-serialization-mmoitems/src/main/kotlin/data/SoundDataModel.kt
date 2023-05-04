package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.SoundData

@Serializable
data class SoundDataModel(
    var sound: String,
    var volume: Double,
    var pitch: Double,
) {
    companion object {
        fun SoundData.toDataModel() = SoundDataModel(sound, volume, pitch)
    }

    fun mmoitemsDeserialize() = SoundData(sound, volume, pitch)
}
