package top.e404.eplugin.channel

import com.google.common.io.ByteArrayDataInput
import com.google.common.io.ByteStreams
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener
import top.e404.eplugin.EPlugin

/**
 * 对BungeeCord数据频道的监听器
 *
 * @property plugin 属于的插件
 */
abstract class EChannelHandler(
    open val plugin: EPlugin,
    open val channel: String = "BungeeCord",
    open vararg val subHandlers: ESubChannelHandler
) : PluginMessageListener {
    /**
     * 注册此数据包监听器
     */
    fun register() {
        plugin.server.messenger.let { messenger ->
            // 接收数据包
            messenger.registerIncomingPluginChannel(plugin, channel, this)
            // 发送数据包
            messenger.registerOutgoingPluginChannel(plugin, channel)
        }
    }

    /**
     * 注销此数据包监听器
     */
    fun unregister() {
        plugin.server.messenger.let { messenger ->
            // 接收数据包
            messenger.unregisterIncomingPluginChannel(plugin, channel, this)
            // 发送数据包
            messenger.unregisterOutgoingPluginChannel(plugin, channel)
        }
    }

    fun parent() {
        subHandlers.forEach {
            it.parent(this)
        }
    }

    override fun onPluginMessageReceived(
        channel: String,
        player: Player,
        message: ByteArray
    ) {
        if (channel != this@EChannelHandler.channel) return
        val input = ByteStreams.newDataInput(message)
        val subChannel = input.readUTF()
        for (subHandler in subHandlers) if (subHandler.name == subChannel) {
            subHandler.onPluginMessageReceived(channel, player, input)
            return
        }
        onUnprocessedPluginMessageReceived(channel, player, input)
    }

    /**
     * 当接收到数据包并且没有现有的子频道处理器处理此数据包时调用此方法, 默认处理会抛出[NoSubChannelHandlerMatchedException]
     *
     * @param channel 频道
     * @param player 玩家
     * @param message 数据
     */
    open fun onUnprocessedPluginMessageReceived(
        channel: String,
        player: Player,
        message: ByteArrayDataInput
    ) {
        throw NoSubChannelHandlerMatchedException(channel, player, message)
    }

    /**
     * 发送数据
     *
     * @param subChannel 子频道名称
     * @param data 数据
     * @return 若成功发送则返回true
     */
    fun sendMessage(subChannel: String, data: String): Boolean {
        val out = ByteStreams.newDataOutput()
        out.writeUTF(subChannel)
        out.writeUTF(data)
        val players = Bukkit.getOnlinePlayers()
        if (players.isEmpty()) return false // 无在线玩家
        players.first().sendPluginMessage(plugin, channel, out.toByteArray())
        return true
    }
}