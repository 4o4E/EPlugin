package top.e404.eplugin.util

import kotlinx.serialization.Serializable
import kotlin.math.sqrt
import org.bukkit.Location as BkLocation

@Serializable
data class Location(val x: Double, val z: Double) {
    constructor(l: BkLocation) : this(l.x, l.z)
}

@Serializable
open class Line(
    val point1: Location,
    val point2: Location,
) {
    /**
     * 计算点到线段的距离
     *
     * @param l 点
     * @return 距离
     */
    fun distance(l: Location): Double {
        val a = distance(point1.x, point1.z, point2.x, point2.z)
        // 线段过短
        if (a <= 0.001) return distance(point1.x, point1.z, l.x, l.z)
        val b = distance(point1.x, point1.z, l.x, l.z)
        val c = distance(point2.x, point2.z, l.x, l.z)
        return when {
            c <= 0.001 || b <= 0.001 -> 0.0
            c * c >= a * a + b * b -> b
            b * b >= a * a + c * c -> c
            else -> {
                val p = (a + b + c) / 2 // 半周长
                val s = sqrt(p * (p - a) * (p - b) * (p - c)) // 海伦公式求面积
                2 * s / a // 返回点到线的距离（利用三角形面积公式求高）
            }
        }
    }

    /**
     * 两点之间的距离
     *
     * @param x1 点1的x坐标
     * @param y1 点1的y坐标
     * @param x2 点2的x坐标
     * @param y2 点2的y坐标
     * @return 亮点
     */
    private fun distance(x1: Double, y1: Double, x2: Double, y2: Double) = sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
}
