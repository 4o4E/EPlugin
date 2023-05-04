package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.UpgradeData
import top.e404.eplugin.reflect.getPrivateField

@Serializable
@SerialName("UpgradeData")
data class UpgradeDataModel(
    var reference: String?,
    var template: String?,
    var workbench: Boolean,
    var destroy: Boolean,
    var success: Double,
    var max: Int,
    var min: Int,
    var level: Int,
) : RandomStatDataModel<UpgradeData, UpgradeData>, StatDataModel<UpgradeData> {
    companion object {
        fun UpgradeData.toDataModel() = UpgradeDataModel(
            reference,
            templateName,
            getPrivateField("workbench"),
            getPrivateField("destroy"),
            success,
            max,
            min,
            level
        )
    }

    override fun toRandomStatData() = UpgradeData(reference, template, workbench, destroy, max, min, success)

    override fun toItemData() = toRandomStatData()
}
