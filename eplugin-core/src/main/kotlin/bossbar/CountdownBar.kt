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
 * @property start 初始值, 单位tick
 * @property display 文本显示规则
 */
open class CountdownBar(
    val plugin: EPlugin,
    var start: Long,
    val color: BarColor = BarColor.WHITE,
    val style: BarStyle = BarStyle.SOLID,
    var display: CountdownBar.() -> String,
) {
    var isStarted = false
        private set
    var tick = start
    lateinit var bossBar: BossBar
        private set
    private var task: BukkitTask? = null

    var onEnd: (() -> Unit) = {
        bossBar.removeAll()
    }

    var onTick: (() -> Unit) = {
        bossBar.setTitle(display())
        bossBar.progress = tick.toDouble() / start
        tick--
    }

    fun start(vararg p: Player, enableTask: Boolean) {
        bossBar = Bukkit.createBossBar(display(), color, style)
        p.forEach { bossBar.addPlayer(it) }
        if (enableTask) {
            task = plugin.runTaskTimer(1, 1) {
                if (tick <= 0) {
                    it.cancel()
                    onEnd.invoke()
                    return@runTaskTimer
                }
                onTick()
            }
        }
        isStarted = true
    }

    fun stop() {
        task?.cancel()
        bossBar.removeAll()
    }
}