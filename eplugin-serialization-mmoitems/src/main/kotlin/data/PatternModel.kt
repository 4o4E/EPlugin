package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.Serializable
import org.bukkit.DyeColor
import org.bukkit.block.banner.Pattern
import org.bukkit.block.banner.PatternType

@Serializable
data class PatternModel(
    val color: DyeColor,
    val pattern: PatternType,
) {
    companion object {
        fun Pattern.toDataModel() = PatternModel(color, pattern)
    }

    fun mmoitemsDeserialize() = Pattern(color, pattern)
}
