@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location

@Serializable
data class LocationRange(
    @SerialName("x")
    @Serializable(DoubleRangeSerialization::class)
    val xRange: ClosedFloatingPointRange<Double>,
    @SerialName("y")
    @Serializable(DoubleRangeSerialization::class)
    val yRange: ClosedFloatingPointRange<Double>,
    @SerialName("z")
    @Serializable(DoubleRangeSerialization::class)
    val zRange: ClosedFloatingPointRange<Double>,
) {
    operator fun contains(l: Location) = contains(l.x, l.y, l.z)
    fun contains(x: Double, y: Double, z: Double) = x in xRange && y in yRange && z in zRange
    fun contains(x: Int, y: Int, z: Int) = contains(x.toDouble(), y.toDouble(), z.toDouble())
}

@Serializable
data class IntLocationRange(
    @SerialName("x")
    @Serializable(IntRangeSerialization::class)
    val xRange: IntRange,
    @SerialName("y")
    @Serializable(IntRangeSerialization::class)
    val yRange: IntRange,
    @SerialName("z")
    @Serializable(IntRangeSerialization::class)
    val zRange: IntRange,
) {
    operator fun contains(l: Location) = contains(l.x, l.y, l.z)
    fun contains(x: Int, y: Int, z: Int) = x in xRange && y in yRange && z in zRange
    fun contains(x: Double, y: Double, z: Double) = contains(x.toInt(), y.toInt(), z.toInt())
}