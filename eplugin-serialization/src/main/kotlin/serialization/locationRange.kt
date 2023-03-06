@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Location
import org.bukkit.World

@Serializable
data class LocationRange(
    @Serializable(WorldSerializer::class)
    val world: World,
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
    val center by lazy {
        Location(
            world,
            xRange.start + (xRange.endInclusive - xRange.start) / 2,
            yRange.start + (yRange.endInclusive - yRange.start) / 2,
            zRange.start + (zRange.endInclusive - zRange.start) / 2
        )
    }
    val xLength get() = xRange.endInclusive - xRange.start
    val yLength get() = yRange.endInclusive - yRange.start
    val zLength get() = zRange.endInclusive - zRange.start
}

@Serializable
data class IntLocationRange(
    @Serializable(WorldSerializer::class)
    val world: World,
    @SerialName("x")
    @Serializable(IntRangeSerialization::class)
    val xRange: IntRange,
    @SerialName("y")
    @Serializable(IntRangeSerialization::class)
    val yRange: IntRange,
    @SerialName("z")
    @Serializable(IntRangeSerialization::class)
    val zRange: IntRange,
) : Iterable<Location> {
    operator fun contains(l: Location) = contains(l.x, l.y, l.z)
    fun contains(x: Int, y: Int, z: Int) = x in xRange && y in yRange && z in zRange
    fun contains(x: Double, y: Double, z: Double) = contains(x.toInt(), y.toInt(), z.toInt())
    val center by lazy {
        Location(
            world,
            xRange.first + (xRange.last.toDouble() - xRange.first) / 2,
            yRange.first + (yRange.last.toDouble() - yRange.first) / 2,
            zRange.first + (zRange.last.toDouble() - zRange.first) / 2
        )
    }
    val xLength get() = xRange.last - xRange.first
    val yLength get() = yRange.last - yRange.first
    val zLength get() = zRange.last - zRange.first
    override fun iterator() = iterator {
        for (x in xRange) for (y in yRange) for (z in zRange) {
            yield(Location(world, x.toDouble(), y.toDouble(), z.toDouble()))
        }
    }
}