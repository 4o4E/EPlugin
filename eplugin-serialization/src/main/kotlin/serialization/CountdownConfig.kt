package top.e404.eplugin.config.serialization

import kotlinx.serialization.Serializable
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle

/**
 * 倒计时配置, 配置的时间重叠时取选到的第一个
 *
 * @param duration 倒计时总时长(单位秒)
 * @param notify 剩余多少秒时开始通知
 * @param message 通知内容
 * @param bossbar 显示的bossbar模板, 用{}作为占位符
 *
 * ```yaml
 * # 总时长60秒
 * duration: 60
 * # 通知内容
 * message:
 *   # 倒计时0-10秒
 *   "0..10":
 *     chat: "&7[&f{prefix}&7] &f这是一条通知"
 *     action_bar: "&f这是一条action bar"
 *     title:
 *       title: "&f这是一条标题"
 *       subtitle: "&f这是一条副标题"
 *       fade_in: 10
 *       stay: 20
 *       fade_out: 10
 *     sound:
 *       type: "block.stone_button.click_on"
 *       volume: 1.0
 *       pitch: 1.0
 * # 显示的bossbar模板
 * bossbar:
 *   # 倒计时0-10秒
 *   0..10:
 *     # 文本模板, 用{}作为占位符
 *     template: "&7[&f{prefix}&7] &f{time}"
 *     # PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE
 *     color: "WHITE"
 *     # SOLID, SEGMENTED_6, SEGMENTED_10, SEGMENTED_12, SEGMENTED_20,
 *     style: "SOLID"
 * ```
 */
@Serializable
data class CountdownConfig(
    val duration: Long,
    val message: Map<@Serializable(IntRangeSerialization::class) IntRange, Message>,
    val bossbar: Map<@Serializable(IntRangeSerialization::class) IntRange, BossbarConfig>,
) {
    private fun <T : Any> Map<IntRange, T>.current(tick: Long) = entries.firstOrNull { tick in it.key }?.value

    fun currentMessage(tick: Long) = message.current(tick)
    fun currentBar(tick: Long) = bossbar.current(tick)
}

@Serializable
data class BossbarConfig(
    val template: String,
    val color: @Serializable(BarColorSerialization::class) BarColor,
    val style: @Serializable(BarStyleSerialization::class) BarStyle,
)

object BarColorSerialization : EnumSerialization<BarColor>(BarColor::valueOf)
object BarStyleSerialization : EnumSerialization<BarStyle>(BarStyle::valueOf)