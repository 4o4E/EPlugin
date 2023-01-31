package top.e404.eplugin.bossbar

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import top.e404.eplugin.EPlugin

class ProgressBar(
    val plugin: EPlugin,
    val start: Long,
    val end: Long,
    val step: Long,
    val duration: Long,
    val display: ProgressBar.() -> String,
) {
    var now = start
    lateinit var bossBar: BossBar
        private set
    lateinit var task: BukkitTask
        private set

    fun start(vararg p: Player) {
        bossBar = Bukkit.createBossBar(display(), BarColor.WHITE, BarStyle.SOLID)
        p.forEach { bossBar.addPlayer(it) }
        task = plugin.runTaskTimer(0, duration) {
            now += step
            if (now >= end) {
                stop()
                return@runTaskTimer
            }
            bossBar.setTitle(display())
            bossBar.progress = (end - now).toDouble() / (end - start)
        }
    }

    fun stop() {
        task.cancel()
        bossBar.removeAll()
    }
}