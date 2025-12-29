package top.e404.eplugin.command

import com.velocitypowered.api.command.CommandSource
import top.e404.eplugin.EPluginVelocity

/**
 * 代表一个指令
 *
 * @property plugin 指令所属于的插件实例
 * @property name 指令的名字
 * @property permission 指令所需权限
 * @since 1.0.0
 */
@Suppress("UNUSED")
abstract class ECommand(
    open val plugin: EPluginVelocity,
    open val name: String,
    regex: String,
    open val mustByPlayer: Boolean,
    open vararg val permission: String,
) {
    @EPluginDsl
    fun fail(message: String): Nothing = throw FailException(message)

    val regex = Regex(regex)

    /**
     * 指令处理器
     *
     * @param sender 发送者
     * @param args 参数
     * @since 1.0.0
     */
    abstract fun onCommand(sender: CommandSource, args: Array<out String>)

    /**
     * tab补全处理器
     *
     * @param sender 发送者
     * @param args 参数
     * @return 补全结果
     * @since 1.0.0
     */
    open fun onTabComplete(sender: CommandSource, args: Array<out String>, complete: MutableList<String>) {}

    /**
     * 帮助内容
     *
     * @return 帮助内容, 开头和结尾不要带\n
     * @since 1.0.0
     */
    abstract val usage: String

    /**
     * 检查sender权限
     *
     * @param sender 对象
     * @return 若sender有所有需要的权限则返回true
     * @since 1.0.0
     */
    fun hasPerm(sender: CommandSource): Boolean {
        return permission.all { sender.hasPermission(it) }
    }

    /**
     * 检测第一个参数是否匹配指令名字
     *
     * @param head 第一个参数
     * @return 若匹配则返回true
     * @since 1.0.0
     */
    fun matchHead(head: String): Boolean {
        return regex.matches(head)
    }
}
