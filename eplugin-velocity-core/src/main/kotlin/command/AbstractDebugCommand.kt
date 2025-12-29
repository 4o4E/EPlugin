@file:Suppress("UNUSED")

package top.e404.eplugin.command

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.proxy.Player
import top.e404.eplugin.EPluginVelocity

abstract class AbstractDebugCommand(
    override val plugin: EPluginVelocity,
    override vararg val permission: String
) : ECommand(
    plugin = plugin,
    name = "debug",
    regex = "(?i)debug|d",
    mustByPlayer = false,
    permission = permission
) {
    override fun onCommand(
        sender: CommandSource,
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
        val senderName = sender.username
        if (senderName in plugin.debuggers) {
            plugin.debuggers.remove(senderName)
            plugin.sendMsgWithPrefix(sender, plugin.langManager["debug.player_disable"])
        } else {
            plugin.debuggers.add(senderName)
            plugin.sendMsgWithPrefix(sender, plugin.langManager["debug.player_enable"])
        }
    }
}
