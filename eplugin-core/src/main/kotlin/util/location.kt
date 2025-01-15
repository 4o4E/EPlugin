package top.e404.eplugin.util

import org.bukkit.Location
import org.bukkit.util.Vector
import kotlin.math.ceil
import kotlin.math.floor

operator fun Location.plus(other: Location) = apply {
    x += other.x
    y += other.y
    z += other.z
}

operator fun Location.minus(other: Location) = apply {
    x -= other.x
    y -= other.y
    z -= other.z
}

operator fun Location.plus(value: Double) = apply {
    x += value
    y += value
    z += value
}

operator fun Location.minus(value: Double) = apply {
    x -= value
    y -= value
    z -= value
}

operator fun Location.times(value: Double) = apply {
    x *= value
    y *= value
    z *= value
}

operator fun Location.div(value: Double) = apply {
    x /= value
    y /= value
    z /= value
}

operator fun Location.rem(value: Double) = apply {
    x %= value
    y %= value
    z %= value
}

operator fun Vector.plus(other: Vector) = apply {
    x += other.x
    y += other.y
    z += other.z
}

operator fun Vector.minus(other: Vector) = apply {
    x -= other.x
    y -= other.y
    z -= other.z
}

operator fun Vector.plus(value: Double) = apply {
    x += value
    y += value
    z += value
}

operator fun Vector.minus(value: Double) = apply {
    x -= value
    y -= value
    z -= value
}

operator fun Vector.times(value: Double) = apply {
    x *= value
    y *= value
    z *= value
}

operator fun Vector.div(value: Double) = apply {
    x /= value
    y /= value
    z /= value
}

operator fun Vector.rem(value: Double) = apply {
    x %= value
    y %= value
    z %= value
}

infix fun Location.locationEquals(other: Location) = world == other.world
        && blockX == other.blockX
        && blockY == other.blockY
        && blockZ == other.blockZ

infix fun Location.distanceTo(other: Location) = if (world == other.world) distance(other) else Double.MAX_VALUE

fun Location.ceil() = apply {
    x = ceil(x)
    y = ceil(y)
    z = ceil(z)
}

fun Location.floor() = apply {
    x = floor(x)
    y = floor(y)
    z = floor(z)
}
