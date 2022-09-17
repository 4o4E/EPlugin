package top.e404.eplugin.channel

import org.bukkit.entity.Player

/**
 * 抽象对频道监听器子频道数据的处理
 *
 * @property parent 属于的[EChannelHandler]
 * @property name 子频道名字
 */
abstract class ESubChannelHandler(
    open val parent: EChannelHandler,
    open val name: String,
) {
    /**
     * 处理此子频道接收到的数据
     *
     * @param channel 频道
     * @param player 玩家
     * @param message 数据
     */
    abstract fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray)

    /**
     * 发送数据
     *
     * @param data 数据
     */
    fun sendMessage(data: String) = parent.sendMessage(name, data)
}