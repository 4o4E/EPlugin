@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World

@Serializable
data class LocationRange(
    val world: String,
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
    val center
        get() = Location(
            Bukkit.getWorld(world),
            xRange.start + (xRange.endInclusive - xRange.start) / 2,
            yRange.start + (yRange.endInclusive - yRange.start) / 2,
            zRange.start + (zRange.endInclusive - zRange.start) / 2
        )
    val xLength get() = xRange.endInclusive - xRange.start
    val yLength get() = yRange.endInclusive - yRange.start
    val zLength get() = zRange.endInclusive - zRange.start
}

@Serializable
data class IntLocationRange(
    val world: String,
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
    val center
        get() = Location(
            Bukkit.getWorld(world),
            xRange.first + (xRange.last.toDouble() - xRange.first) / 2,
            yRange.first + (yRange.last.toDouble() - yRange.first) / 2,
            zRange.first + (zRange.last.toDouble() - zRange.first) / 2
        )
    val xLength get() = xRange.last - xRange.first
    val yLength get() = yRange.last - yRange.first
    val zLength get() = zRange.last - zRange.first
    override fun iterator() = iterator {
        val bkw = Bukkit.getWorld(world)
        for (x in xRange) for (y in yRange) for (z in zRange) {
            yield(Location(bkw, x.toDouble(), y.toDouble(), z.toDouble()))
        }
    }
}

@Serializable
data class VLocationRange(
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
    fun getCenter(world: World) = Location(
        world,
        xRange.first + (xRange.last.toDouble() - xRange.first) / 2,
        yRange.first + (yRange.last.toDouble() - yRange.first) / 2,
        zRange.first + (zRange.last.toDouble() - zRange.first) / 2
    )

    val xLength get() = xRange.last - xRange.first
    val yLength get() = yRange.last - yRange.first
    val zLength get() = zRange.last - zRange.first
    inline fun <T : Any> loop(action: (x: Int, y: Int, z: Int) -> T?): T? {
        for (y in yRange) {
            for (z in zRange) {
                for (x in xRange) {
                    action(x, y, z)?.let { return@loop it }
                }
            }
        }
        return null
    }
}
