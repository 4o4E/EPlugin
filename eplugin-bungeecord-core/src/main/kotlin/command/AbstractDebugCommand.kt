@file:Suppress("UNUSED")

package top.e404.eplugin.bungeecord.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import top.e404.eplugin.bungeecord.EPlugin

abstract class AbstractDebugCommand(
    override val plugin: EPlugin,
    override vararg val permission: String
) : ECommand(
    plugin = plugin,
    name = "debug",
    regex = "(?i)debug",
    mustByPlayer = false,
    permission = permission
) {
    override fun onCommand(
        sender: CommandSender,
        args: Array<out String>
    ) {
        if (sender !is ProxiedPlayer) {
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
            plugin.sendMsgWithPrefix(sender, plugin.langManager["debug.player_enable"])
        } else {
            plugin.debuggers.add(senderName)
            plugin.sendMsgWithPrefix(sender, plugin.langManager["debug.player_disable"])
        }
    }
}