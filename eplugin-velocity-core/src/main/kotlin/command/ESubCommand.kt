@file:Suppress("UNUSED")

package top.e404.eplugin.command

import com.velocitypowered.api.command.CommandSource
import top.e404.eplugin.EPluginVelocity
import top.e404.eplugin.EPluginVelocity.Companion.component

/**
 * 代表一个复合指令(包含多个子指令)
 *
 * @property plugin 指令所属于的插件实例
 * @property name 指令名字(补全时使用)
 * @param regex 匹配正则
 * @property usage 使用方法
 * @property mustByPlayer 是否只能由玩家使用
 * @param perms 所需权限
 */
abstract class ESubCommand(
    override val plugin: EPluginVelocity,
    override val name: String,
    regex: String,
    override val mustByPlayer: Boolean,
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
    open val subCommands: List<ESubCommand> = ArrayList()

    override val usage: String get() = subCommands.joinToString("\n") { it.usage }

    override fun onTabComplete(
        sender: CommandSource,
        args: Array<out String>,
        complete: MutableList<String>
    ) = onTabComplete(sender, args.toMutableList(), complete)

    open fun onTabComplete(
        sender: CommandSource,
        args: MutableList<String>,
        complete: MutableList<String>
    ) {
        if (args.size == 1) {
            complete.addAll(subCommands.map { it.name })
            return
        }
        // this command name
        val arg = args.removeAt(0)
        for (subCommand in subCommands) {
            if (!subCommand.match(arg)) continue
            subCommand.onTabComplete(sender, args, complete)
            return
        }
    }

    override fun onCommand(
        sender: CommandSource,
        args: Array<out String>
    ) = onCommand(sender, args.toMutableList())

    open fun onCommand(
        sender: CommandSource,
        args: MutableList<String>,
    ) {
        val arg1 = args.removeAt(0)
        for (subCommand in subCommands) {
            if (!subCommand.match(arg1) || !subCommand.hasPerm(sender)) continue
            subCommand.onCommand(sender, args)
            return
        }
        sender.sendMessage(usage.component)
    }
}
