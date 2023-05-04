package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.CommandData
import net.Indyuce.mmoitems.stat.data.CommandListData

@Serializable
@SerialName("CommandListData")
data class CommandListDataModel(
    var commands: MutableList<@Serializable(CommandDataSerializer::class) CommandData>,
) : RandomStatDataModel<CommandListData, CommandListData>, StatDataModel<CommandListData> {
    companion object {
        fun CommandListData.toDataModel() = CommandListDataModel(commands)
    }

    override fun toRandomStatData() = CommandListData(commands)
    override fun toItemData() = toRandomStatData()
}
