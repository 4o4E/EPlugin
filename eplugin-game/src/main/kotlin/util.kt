package top.e404.eplugin.game

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player

/**
 * 重置玩家状态
 */
fun Player.reset() {
    // 重置玩家生命值
    health = 20.0
    // 移除药水效果
    activePotionEffects.forEach { removePotionEffect(it.type) }
    // 重置游戏模式
    gameMode = GameMode.SURVIVAL
    // 清空玩家背包
    inventory.clear()
    // 重置玩家计分板
    scoreboard = Bukkit.getScoreboardManager().mainScoreboard
}