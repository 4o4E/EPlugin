@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.Bukkit
import org.bukkit.Location
import top.e404.eplugin.config.serialization.ELocation.Companion.deserializeToELocation
import kotlin.math.floor

/**
 * 将[Location]序列化成字符串
 */
object InlineLocationSerialization : KSerializer<Location> {
    override val descriptor = primitive()

    override fun deserialize(decoder: Decoder) = decoder.decodeString().serializeToLocation()

    override fun serialize(encoder: Encoder, value: Location) = encoder.encodeString(value.inlineDeserializeToString())
}

fun String.serializeToLocation(): Location {
    val split = split(";")
    return Location(
        Bukkit.getWorld(split[0]) ?: throw InvalidWorldException(split[0]),
        split[1].toDouble(),
        split[2].toDouble(),
        split[3].toDouble(),
        split.getOrNull(4)?.toFloat() ?: 0F,
        split.getOrNull(5)?.toFloat() ?: 0F,
    )
}

fun Location.inlineDeserializeToString() = buildString {
    append(world?.name.toString())
    append(";")
    append(x)
    append(";")
    append(y)
    append(";")
    append(z)
    if (yaw != 0F && pitch != 0F) {
        append(";")
        append(yaw)
        append(";")
        append(pitch)
    }
}

/**
 * 位置序列化器
 */
object LocationSerialization : KSerializer<Location> {
    private const val worldIndex = 0
    private const val xIndex = 1
    private const val yIndex = 2
    private const val zIndex = 3
    private const val yawIndex = 4
    private const val pitchIndex = 5

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(this::class.java.name) {
        element<String>("world")
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
        element<Float>("yaw")
        element<Float>("pitch")
    }

    override fun serialize(encoder: Encoder, value: Location) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, worldIndex, value.world?.name ?: "null")
        encodeDoubleElement(descriptor, xIndex, value.x)
        encodeDoubleElement(descriptor, yIndex, value.y)
        encodeDoubleElement(descriptor, zIndex, value.z)
        if (value.yaw != 0F) encodeFloatElement(descriptor, yawIndex, value.yaw)
        if (value.pitch != 0F) encodeFloatElement(descriptor, pitchIndex, value.pitch)
    }

    override fun deserialize(decoder: Decoder): Location = decoder.decodeStructure(descriptor) {
        var world = ""
        var x = 0.0
        var y = 0.0
        var z = 0.0
        var yaw = 0F
        var pitch = 0F
        while (true) when (val index = decodeElementIndex(descriptor)) {
            worldIndex -> world = decodeStringElement(descriptor, index)
            xIndex -> x = decodeDoubleElement(descriptor, index)
            yIndex -> y = decodeDoubleElement(descriptor, index)
            zIndex -> z = decodeDoubleElement(descriptor, index)
            yawIndex -> yaw = decodeFloatElement(descriptor, index)
            pitchIndex -> pitch = decodeFloatElement(descriptor, index)
            CompositeDecoder.DECODE_DONE -> break
            else -> error("Unexpected index: $index")
        }
        Location(Bukkit.getWorld(world), x, y, z, yaw, pitch)
    }
}

@Serializable
data class ELocation(
    val world: String,
    val x: Double,
    val y: Double,
    val z: Double,
    val yaw: Float = 0f,
    val pitch: Float = 0f,
) {
    companion object {
        fun Location.toELocation() = ELocation(world!!.name, x, y, z, yaw, pitch)

        fun String.deserializeToELocation(): ELocation {
            val split = split(";")
            return ELocation(
                split[0],
                split[1].toDouble(),
                split[2].toDouble(),
                split[3].toDouble(),
                split.getOrNull(4)?.toFloat() ?: 0F,
                split.getOrNull(5)?.toFloat() ?: 0F,
            )
        }
    }

    private val bkWorld get() = Bukkit.getWorld(world) ?: throw InvalidWorldException(world)
    fun toLocation() = Location(bkWorld, x, y, z, yaw, pitch)
    fun inSameBlock(location: Location) = floor(location.x) == floor(x) &&
            floor(location.y) == floor(y) &&
            floor(location.z) == floor(z)

    fun serializeToString() = buildString {
        append(world).append(";").append(x).append(";").append(y).append(";").append(z)
        if (yaw != 0F && pitch != 0F) append(";").append(yaw).append(";").append(pitch)
    }
}

class InvalidWorldException(world: String) : RuntimeException("invalid world: $world")

object InlineELocationSerialization : KSerializer<ELocation> {
    override val descriptor = primitive()
    override fun deserialize(decoder: Decoder) = decoder.decodeString().deserializeToELocation()
    override fun serialize(encoder: Encoder, value: ELocation) = encoder.encodeString(value.serializeToString())
}
