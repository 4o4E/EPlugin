@file:Suppress("UNUSED")

package top.e404.eplugin.command

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin

abstract class AbstractDebugCommand(
    override val plugin: EPlugin,
    override vararg val permission: String
) : ECommand(
    plugin = plugin,
    name = "debug",
    regex = "(?i)debug|d",
    mustByPlayer = false,
    permission = permission
) {
    override fun onCommand(
        sender: CommandSender,
        args: Array<out String>
    ) {
        if (sender !is Player) {
            plugin.debug = !plugin.debug
            plugin.sendMsgWithPrefix(
                sender,
                plugin.langManager[
                        if (plugin.debug) "debug.console_enable"
                        else "debug.console_disable"
                ]
            )
            return
        }
        val senderName = sender.name
        if (senderName in plugin.debuggers) {
            plugin.debuggers.remove(senderName)
            plugin.sendMsgWithPrefix(sender, plugin.langManager["debug.player_disable"])
        } else {
            plugin.debuggers.add(senderName)
            plugin.sendMsgWithPrefix(sender, plugin.langManager["debug.player_enable"])
        }
    }
}
