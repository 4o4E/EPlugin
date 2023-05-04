package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.api.util.NumericStatFormula
import net.Indyuce.mmoitems.stat.data.RestoreData
import net.Indyuce.mmoitems.stat.data.random.RandomRestoreData
import org.bukkit.configuration.file.YamlConfiguration

@Serializable
@SerialName("RandomRestoreData")
data class RandomRestoreDataModel(
    @Serializable(NumericStatFormulaSerializer::class) var health: NumericStatFormula,
    @Serializable(NumericStatFormulaSerializer::class) var food: NumericStatFormula,
    @Serializable(NumericStatFormulaSerializer::class) var saturation: NumericStatFormula,
) : RandomStatDataModel<RestoreData, RandomRestoreData> {
    companion object {
        fun RandomRestoreData.toDataModel() = RandomRestoreDataModel(health, food, saturation)
    }

    override fun toRandomStatData() = RandomRestoreData(YamlConfiguration().apply {
        set("health.base", health.base)
        set("health.scale", health.scale)
        set("health.spread", health.spread)
        set("health.maxSpread", health.maxSpread)

        set("food.base", food.base)
        set("food.scale", food.scale)
        set("food.spread", food.spread)
        set("food.maxSpread", food.maxSpread)

        set("saturation.base", saturation.base)
        set("saturation.scale", saturation.scale)
        set("saturation.spread", saturation.spread)
        set("saturation.maxSpread", saturation.maxSpread)
    })
}
