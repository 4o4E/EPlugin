package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.DoubleData
import net.Indyuce.mmoitems.stat.data.random.RandomRequiredLevelData
import org.bukkit.configuration.file.YamlConfiguration

@Serializable
@SerialName("RandomRequiredLevelData")
data class RandomRequiredLevelDataModel(
    var base: Double,
    var scale: Double,
    var spread: Double,
    var maxSpread: Double,
) : RandomStatDataModel<DoubleData, RandomRequiredLevelData> {
    companion object {
        fun RandomRequiredLevelData.toDataModel() = RandomRequiredLevelDataModel(
            base,
            scale,
            maxSpread,
            spread
        )
    }

    override fun toRandomStatData() = RandomRequiredLevelData(YamlConfiguration().apply {
        set("base", base)
        set("scale", scale)
        set("spread", spread)
        set("maxSpread", maxSpread)
    })
}
