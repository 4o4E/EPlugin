package top.e404.eplugin.game

import org.bukkit.entity.Player

/**
 * 游戏玩家信息,
 */
@Suppress("UNUSED")
interface Gamer {
    val player: Player
    val role: Role
}

/**
 * 角色标记, 用来区分不同的阵营
 */
interface Role {
    /**
     * 角色名
     */
    val roleName: String
}