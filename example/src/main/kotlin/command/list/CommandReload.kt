package top.e404.testplugin.command.list

import org.bukkit.command.CommandSender
import top.e404.eplugin.command.AbstractCommand
import top.e404.testplugin.INSTANCE

object CommandReload : AbstractCommand(
    INSTANCE,
    "reload",
    false,
    "testplugin.admin"
) {
    override fun onCommand(sender: CommandSender, args: Array<out String>) {
        plugin.sendMsgWithPrefix(sender, "awa")
    }

    override val usage = "/reload - reload"

}