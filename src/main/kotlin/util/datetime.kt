@file:Suppress("UNUSED")

package top.e404.eplugin.util

import java.util.regex.Pattern

/**
 * 格式化单位秒的时长为字符串, 解析最大单位为天
 *
 * @return 时长
 */
fun Long.parseSecondAsDuration(): String {
    var t = this
    var s = ""
    var temp = t % 60
    if (temp != 0L) s = "${temp}秒"
    t /= 60
    if (t == 0L) return s
    temp = t % 60
    if (temp != 0L) s = "${temp}分$s"
    t /= 60
    if (t == 0L) return s
    temp = t % 24
    if (temp != 0L) s = "${temp}时$s"
    t /= 24
    if (t == 0L) return s
    s = "${t}天$s"
    return s
}

private val durationPattern =
    Pattern.compile("((?<d>\\d+)[d天日])?((?<h>\\d+)(h|(小)?时))?((?<m>\\d+)(m(in)?|分(钟)?))?((?<s>\\d+)[s秒]?)?")

private val numberRegex = Regex("\\d+")

/**
 * 解析1d1h1m格式的字符串为单位秒的时长
 *
 * @return 时长, 解析异常返回-1
 */
fun String.parseAsDuration(): Int {
    if (matches(numberRegex)) return toInt()
    val m = durationPattern.matcher(this).apply {
        if (!find()) return -1
    }
    var result = 0 //结果 单位秒
    //天
    result += m.group("d")?.let { it.toInt() * 86400 } ?: 0
    //时
    result += m.group("h")?.let { it.toInt() * 3600 } ?: 0
    //分
    result += m.group("m")?.let { it.toInt() * 60 } ?: 0
    //秒
    result += m.group("s")?.toInt() ?: 0
    return result
}