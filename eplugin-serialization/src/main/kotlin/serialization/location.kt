@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import top.e404.eplugin.EPlugin.Companion.formatAsConst

/**
 * Example
 * ```kotlin
 * @Serializable
 * data class A(
 *   @Serializable(LocationSerialization::class)
 *   val l: Location,
 * )
 * ```
 */
object LocationMinSerialization : KSerializer<Location> {
    override val descriptor = PrimitiveSerialDescriptor(this::class.java.name, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Location {
        val split = decoder.decodeString().split(";")
        return Location(
            Bukkit.getWorld(split[0]),
            split[1].toDouble(),
            split[2].toDouble(),
            split[3].toDouble(),
            split[4].toFloat(),
            split[5].toFloat(),
        )
    }

    override fun serialize(encoder: Encoder, value: Location) {
        encoder.encodeString("${value.world?.name};${value.x};${value.y};${value.z};${value.yaw};${value.pitch}")
    }
}

/**
 * 位置序列化器
 */
object LocationSerialization : KSerializer<Location> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(this::class.java.name) {
        element<String>("world")
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
        element<Float>("yaw")
        element<Float>("pitch")
    }

    override fun serialize(encoder: Encoder, value: Location) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.world?.name ?: "null")
        encodeDoubleElement(descriptor, 1, value.x)
        encodeDoubleElement(descriptor, 2, value.y)
        encodeDoubleElement(descriptor, 3, value.z)
        encodeFloatElement(descriptor, 4, value.yaw)
        encodeFloatElement(descriptor, 5, value.pitch)
    }

    override fun deserialize(decoder: Decoder): Location = decoder.decodeStructure(descriptor) {
        var world = ""
        var x = 0.0
        var y = 0.0
        var z = 0.0
        var yaw = 0F
        var pitch = 0F
        while (true) when (val index = decodeElementIndex(descriptor)) {
            0 -> world = decodeStringElement(descriptor, index)
            1 -> x = decodeDoubleElement(descriptor, index)
            2 -> y = decodeDoubleElement(descriptor, index)
            3 -> z = decodeDoubleElement(descriptor, index)
            4 -> yaw = decodeFloatElement(descriptor, index)
            5 -> pitch = decodeFloatElement(descriptor, index)
            CompositeDecoder.DECODE_DONE -> break
            else -> error("Unexpected index: $index")
        }
        Location(Bukkit.getWorld(world), x, y, z, yaw, pitch)
    }
}

/**
 * 简短的方块位置序列化器(忽略pitch和yaw)
 */
object LocationMinBlockSerialization : KSerializer<Location> {
    override val descriptor = PrimitiveSerialDescriptor(this::class.java.name, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Location {
        val split = decoder.decodeString().split(";")
        return Location(
            Bukkit.getWorld(split[0]),
            split[1].toDouble(),
            split[2].toDouble(),
            split[3].toDouble(),
        )
    }

    override fun serialize(encoder: Encoder, value: Location) = encoder.encodeString("${value.world?.name};${value.x};${value.y};${value.z}")
}

/**
 * 方块位置序列化器(忽略pitch和yaw)
 */
object LocationBlockSerialization : KSerializer<Location> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(this::class.java.name) {
        element<String>("world")
        element<Int>("x")
        element<Int>("y")
        element<Int>("z")
    }

    override fun serialize(encoder: Encoder, value: Location) = encoder.encodeStructure(descriptor) {
        encodeStringElement(descriptor, 0, value.world?.name ?: "null")
        encodeIntElement(descriptor, 1, value.blockX)
        encodeIntElement(descriptor, 2, value.blockY)
        encodeIntElement(descriptor, 3, value.blockZ)
    }

    override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
        var world = ""
        var x = 0
        var y = 0
        var z = 0
        while (true) when (val index = decodeElementIndex(descriptor)) {
            0 -> world = decodeStringElement(descriptor, 0)
            1 -> x = decodeIntElement(descriptor, 1)
            2 -> y = decodeIntElement(descriptor, 2)
            3 -> z = decodeIntElement(descriptor, 2)
            CompositeDecoder.DECODE_DONE -> break
            else -> error("Unexpected index: $index")
        }
        Location(Bukkit.getWorld(world), x.toDouble(), y.toDouble(), z.toDouble())
    }
}

object MaterialSerializer : KSerializer<Material> {
    override val descriptor = PrimitiveSerialDescriptor(this::class.java.name, PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder) = Material.valueOf(decoder.decodeString().formatAsConst())

    override fun serialize(encoder: Encoder, value: Material) {
        encoder.encodeString(value.name)
    }
}

