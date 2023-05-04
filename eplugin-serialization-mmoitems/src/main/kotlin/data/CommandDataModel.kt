package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.CommandData
import top.e404.eplugin.reflect.getPrivateField

@Serializable
data class CommandDataModel(
    val command: String,
    val delay: Double,
    val console: Boolean,
    val op: Boolean,
) {
    companion object {
        fun CommandData.toDataModel() = CommandDataModel(
            command,
            delay,
            getPrivateField("console"),
            getPrivateField("op"),
        )
    }

    fun mmoitemsDeserialize() = CommandData(command, delay, console, op)
}
