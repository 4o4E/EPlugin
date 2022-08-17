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
    regex = "(?i)debug",
    mustByPlayer = false,
    permission = permission
) {
    override fun onCommand(
        sender: CommandSender,
        args: Array<out String>
    ) {
        if (sender !is Player) {
            plugin.sendNotPlayer(sender)
            return
        }
        val senderName = sender.name
        if (senderName in plugin.debuggers) {
            plugin.debuggers.remove(senderName)
            plugin.sendMsgWithPrefix(sender, "&f已&c禁用&bdebug&f, 你不会收到&bdebug&f消息, 再使用一次此指令以&a启用")
        } else {
            plugin.debuggers.add(senderName)
            plugin.sendMsgWithPrefix(sender, "&f已&a启用&bdebug&f, 你将会收到&bdebug&f消息, 再使用一次此指令以&c禁用")
        }
    }
}