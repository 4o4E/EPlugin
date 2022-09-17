package top.e404.eplugin.channel

import org.bukkit.entity.Player

/**
 * 处理时遇到没有对应子频道处理器时抛出
 *
 * @param channel 频道
 * @param player 玩家
 * @param data 数据
 */
class NoSubChannelHandlerMatchedException(
    val channel: String,
    val player: Player,
    val data: ByteArray
) : Exception()