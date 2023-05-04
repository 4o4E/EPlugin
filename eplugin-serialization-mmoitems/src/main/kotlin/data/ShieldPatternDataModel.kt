package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.ShieldPatternData
import org.bukkit.DyeColor
import org.bukkit.block.banner.Pattern

@Serializable
@SerialName("ShieldPatternData")
data class ShieldPatternDataModel(
    var base: DyeColor,
    var patterns: MutableList<@Serializable(PatternSerializer::class) Pattern>,
) : RandomStatDataModel<ShieldPatternData, ShieldPatternData>, StatDataModel<ShieldPatternData> {
    companion object {
        fun ShieldPatternData.toDataModel() = ShieldPatternDataModel(baseColor, patterns)
    }

    override fun toRandomStatData() = ShieldPatternData(base, *patterns.toTypedArray())
    override fun toItemData() = toRandomStatData()
}
