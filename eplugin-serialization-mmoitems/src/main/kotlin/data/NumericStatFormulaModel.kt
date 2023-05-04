package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.api.util.NumericStatFormula
import net.Indyuce.mmoitems.stat.data.DoubleData
import org.bukkit.configuration.file.YamlConfiguration

@Serializable
@SerialName("NumericStatFormula")
data class NumericStatFormulaModel(
    var base: Double,
    var scale: Double,
    var spread: Double,
    var maxSpread: Double,
) : RandomStatDataModel<DoubleData, NumericStatFormula> {
    companion object {
        fun NumericStatFormula.toDataModel() = NumericStatFormulaModel(
            base,
            scale,
            maxSpread,
            spread
        )
    }

    override fun toRandomStatData() = NumericStatFormula(YamlConfiguration().apply {
        set("base", base)
        set("scale", scale)
        set("spread", spread)
        set("maxSpread", maxSpread)
    })
}
