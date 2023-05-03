@file:Suppress("UNUSED")

package top.e404.eplugin.util

import org.bukkit.configuration.serialization.ConfigurationSerializable
import org.bukkit.configuration.serialization.ConfigurationSerialization

fun Any.castToString() = when (this) {
    is String -> this
    else -> toString()
}

fun Any.castToBoolean() = when (this) {
    is Boolean -> this
    is String -> toBoolean()
    else -> toString().toBoolean()
}

fun Any.castToShort() = when (this) {
    is Short -> this
    is Number -> toShort()
    is String -> toShort()
    else -> toString().toShort()
}

fun Any.castToInt() = when (this) {
    is Int -> this
    is Number -> toInt()
    is String -> toInt()
    else -> toString().toInt()
}

fun Any.castToLong() = when (this) {
    is Long -> this
    is Number -> toLong()
    is String -> toLong()
    else -> toString().toLong()
}

fun Any.castToFloat() = when (this) {
    is Float -> this
    is Number -> toFloat()
    is String -> toFloat()
    else -> toString().toFloat()
}

fun Any.castToDouble() = when (this) {
    is Double -> this
    is Number -> toDouble()
    is String -> toDouble()
    else -> toString().toDouble()
}

// nullable

fun Any.castToBooleanOrNull() = when (this) {
    is Boolean -> this
    is String -> toBooleanStrictOrNull()
    else -> toString().toBooleanStrictOrNull()
}

fun Any.castToShortOrNull() = when (this) {
    is Short -> this
    is Number -> toShort()
    is String -> toShortOrNull()
    else -> toString().toShortOrNull()
}

fun Any.castToIntOrNull() = when (this) {
    is Int -> this
    is Number -> toInt()
    is String -> toIntOrNull()
    else -> toString().toIntOrNull()
}

fun Any.castToLongOrNull() = when (this) {
    is Long -> this
    is Number -> toLong()
    is String -> toLongOrNull()
    else -> toString().toLongOrNull()
}

fun Any.castToFloatOrNull() = when (this) {
    is Float -> this
    is Number -> toFloat()
    is String -> toFloatOrNull()
    else -> toString().toFloatOrNull()
}

fun Any.castToDoubleOrNull() = when (this) {
    is Double -> this
    is Number -> toDouble()
    is String -> toDoubleOrNull()
    else -> toString().toDoubleOrNull()
}

// collection

fun Any.requireCollection(): Collection<*> = this as? Collection<*>
    ?: throw Exception("${this.javaClass.name} is not collection($this)")

fun Any.requireMap(): Map<*, *> = this as? Map<*, *> ?: throw Exception("${this.javaClass.name} is not map($this)")

fun <T> Collection<T>.asSet() = when (this) {
    is MutableSet<T> -> this
    else -> toMutableSet()
}

fun <T> Collection<T>.asList() = when (this) {
    is MutableList<T> -> this
    else -> toMutableList()
}

inline fun <reified T> Collection<*>.check(): Collection<T> {
    for (item in this) {
        if (item !is T) throw Exception("require ${T::class.java.name} in collection but got ${item?.javaClass?.name}")
    }
    @Suppress("UNCHECKED_CAST")
    return this as Collection<T>
}

inline fun <reified K : Any, reified V : Any> Map<*, *>.check(): Map<K, V> {
    for ((key, value) in entries) {
        if (key !is K) throw Exception("require map key type ${K::class.java.name} but got ${key?.javaClass?.name}")
        if (value !is V) throw Exception("require map key type ${V::class.java.name} but got ${value?.javaClass?.name}")
    }
    @Suppress("UNCHECKED_CAST")
    return this as Map<K, V>
}

inline fun <reified R : Any, V : R?> Map<String, V>.safeGet(k: String): R = get(k)?.let { return it }
    ?: throw Exception("map must contains key $k")

@Suppress("UNCHECKED_CAST")
inline fun <reified K, reified V> Map<K, V>.getSerializeMap(k: K) = get(k) as MutableMap<String, *>

inline fun <reified T : ConfigurationSerializable> regSerialization() =
    ConfigurationSerialization.registerClass(T::class.java)
