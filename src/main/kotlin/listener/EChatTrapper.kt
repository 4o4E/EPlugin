package top.e404.eplugin.listener

import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin

abstract class EChatTrapper(
    open val plugin: EPlugin,
    open val target: Player,
    open val manager: ETrapperManager,
) {
    /**
     * 当监听的玩家发送消息时处理
     *
     * @param message 发送的消息
     * @return 若结束监听则返回true
     */
    abstract fun onChat(message: String): Boolean

    fun start() {
        manager.addTrapper(this)
    }
}