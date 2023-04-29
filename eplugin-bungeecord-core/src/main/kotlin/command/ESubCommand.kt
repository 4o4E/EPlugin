@file:Suppress("UNUSED")

package top.e404.eplugin.bungeecord.command

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import top.e404.eplugin.bungeecord.EPlugin

/**
 * 代表一个复合指令(包含多个子指令)
 *
 * @property plugin 指令所属于的插件实例
 * @property name 指令名字(补全时使用)
 * @property usage 使用方法
 * @property perms 所需权限
 * @param regex 匹配正则
 */
abstract class ESubCommand(
    override val plugin: EPlugin,
    override val name: String,
    regex: String,
    override val mustByPlayer: Boolean,
    override val usage: String,
    vararg perms: String
) : ECommand(plugin, name, regex, mustByPlayer, *perms) {
    /**
     * 判断是否匹配
     *
     * @param string 子指令名字
     */
    fun match(string: String) = regex.matches(string)

    /**
     * 包含的子指令
     */
    val subCommands: List<ESubCommand> = ArrayList()

    override fun onTabComplete(
        sender: CommandSender,
        args: Array<out String>,
        complete: MutableList<String>
    ) = onTabComplete(sender, args.toMutableList(), complete)

    open fun onTabComplete(
        sender: CommandSender,
        args: MutableList<String>,
        complete: MutableList<String>
    ) {
        val arg = args.removeAt(0)
        for (subCommand in subCommands) {
            if (!subCommand.match(arg)) continue
            subCommand.onTabComplete(sender, args, complete)
            return
        }
    }

    override fun onCommand(
        sender: CommandSender,
        args: Array<out String>
    ) = onCommand(sender, args.toMutableList())

    open fun onCommand(
        sender: CommandSender,
        args: MutableList<String>,
    ) {
        val arg1 = args.removeAt(0)
        for (subCommand in subCommands) {
            if (!subCommand.match(arg1) || !subCommand.hasPerm(sender)) continue
            subCommand.onCommand(sender, args)
            return
        }
        sender.sendMessage(TextComponent(usage))
    }
}
