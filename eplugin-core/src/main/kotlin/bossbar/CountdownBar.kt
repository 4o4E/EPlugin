package top.e404.eplugin.bossbar

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import top.e404.eplugin.EPlugin

/**
 * 倒计时 BossBar
 *
 * @property plugin 注册的插件
 * @property start 初始值
 * @property display
 */
open class CountdownBar(
    val plugin: EPlugin,
    val start: Long,
    val color: BarColor = BarColor.WHITE,
    val style: BarStyle = BarStyle.SOLID,
    val display: CountdownBar.() -> String,
) {
    var isStarted = false
        private set
    var tick = start
        private set
    lateinit var bossBar: BossBar
        private set
    private lateinit var task: BukkitTask

    var onEnd: (() -> Unit)? = null

    fun start(vararg p: Player) {
        bossBar = Bukkit.createBossBar(display(), color, style)
        p.forEach { bossBar.addPlayer(it) }
        task = plugin.runTaskTimer(0, 20) {
            if (tick <= 0) {
                it.cancel()
                bossBar.removeAll()
                onEnd?.invoke()
                return@runTaskTimer
            }
            bossBar.setTitle(display())
            bossBar.progress = tick.toDouble() / start
            tick--
        }
        isStarted = true
    }

    fun stop() {
        task.cancel()
        bossBar.removeAll()
    }
}

abstract class CountdownManager {
    val players = mutableMapOf<Player, CountdownBar>()

    abstract fun create(player: Player): CountdownBar

    fun add(player: Player) {
        players[player] = create(player).apply {
            onEnd = {
                players.remove(player)
            }
            start(player)
        }
    }

    fun shutdown() {
        players.forEach { (_, bar) -> bar.stop() }
        players.clear()
    }
}
