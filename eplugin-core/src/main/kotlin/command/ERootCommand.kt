@file:Suppress("UNUSED")

package top.e404.eplugin.command

import org.bukkit.command.CommandSender
import top.e404.eplugin.EPlugin

/**
 * 代表一个复合指令(包含多个子指令), 作为subCommand的root
 *
 * @property plugin 指令所属于的插件实例
 * @property name 指令名字(补全时使用)
 * @param regex 匹配正则
 * @property usage 使用方法
 * @property mustByPlayer 是否只能由玩家使用
 * @param perms 所需权限
 */
abstract class ERootCommand(
    override val plugin: EPlugin,
    override val name: String,
    regex: String,
    override val mustByPlayer: Boolean,
    vararg perms: String
) : ESubCommand(plugin, name, regex, mustByPlayer, *perms) {
    override fun onTabComplete(
        sender: CommandSender,
        args: Array<out String>,
        complete: MutableList<String>
    ) {
        // root command name
        val args = args.toMutableList().apply {
            removeAt(0)
        }
        super.onTabComplete(sender, args, complete)
    }


    override fun onCommand(
        sender: CommandSender,
        args: Array<out String>
    ) = onCommand(sender, args.toMutableList().apply {
        removeAt(0)
    })
}
