package top.e404.eplugin.game.util

import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.EPlugin.Companion.placeholder
import top.e404.eplugin.bossbar.CountdownBar
import top.e404.eplugin.config.serialization.CountdownConfig

/**
 * 倒计时管理器
 */
@Suppress("UNUSED")
abstract class CountdownManager(
    val plugin: EPlugin,
    val countdownConfig: CountdownConfig,
    val enableInternalTask: Boolean
) {
    val players = mutableMapOf<Player, CountdownBar>()

    protected open fun create(player: Player, cooldown: Long?): CountdownBar {
        val start = cooldown ?: (countdownConfig.duration * 20L)
        val bossbarConfig = countdownConfig.currentBar(start / 20)
        return CountdownBar(
            plugin, start,
            bossbarConfig?.color ?: BarColor.WHITE,
            bossbarConfig?.style ?: BarStyle.SOLID
        ) {
            bossbarConfig?.template?.placeholder(*getPlaceholder()) ?: ""
        }
    }

    open fun CountdownBar.getPlaceholder(): Array<Pair<String, *>> = arrayOf(
        "duration" to countdownConfig.duration,
        "tick" to tick,
        "second" to tick / 20
    )

    fun add(player: Player, cooldown: Long? = null): CountdownBar {
        create(player, cooldown).apply {
            val existsOnEnd = onEnd
            onEnd = {
                existsOnEnd()
                players.remove(player)
            }
            onTick = {
                try {
                    if (tick % 20 == 0L) {
                        val sec = tick / 20
                        countdownConfig.currentBar(sec)?.let { config ->
                            bossBar.color = config.color
                            bossBar.style = config.style
                            bossBar.setTitle(config.template.placeholder(*getPlaceholder()))
                        } ?: bossBar.setTitle("")
                        countdownConfig.currentMessage(sec)?.sendTo(player, *getPlaceholder())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                bossBar.progress = tick.toDouble() / start
                tick--
            }
            start(player, enableTask = enableInternalTask)
        }.let {
            players[player] = it
            return it
        }
    }

    fun onTick() {
        players.values.forEach { it.onTick() }
    }

    /**
     * 从管理器中移除玩家并取消其显示
     */
    fun remove(player: Player) {
        players.remove(player)?.stop()
    }

    fun shutdown() {
        players.forEach { (_, bar) -> bar.stop() }
        players.clear()
    }
}
