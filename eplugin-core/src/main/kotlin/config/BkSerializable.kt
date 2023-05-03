package top.e404.eplugin.config

import org.bukkit.configuration.ConfigurationSection
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import kotlin.reflect.typeOf

/**
 * 在`companion object`实现
 * ```kotlin
 * fun <T : BkSerializable> deserialize(configurationSection: ConfigurationSection): T
 * ```
 */
interface BkSerializable {
    companion object {
        val registered = mutableMapOf<Class<*>, Method>()

        inline fun <reified T : BkSerializable?> deserialize(configurationSection: ConfigurationSection) =
            deserialize(configurationSection, T::class.java)

        fun <T : BkSerializable?> deserialize(configurationSection: ConfigurationSection, cls: Class<T>): T {
            val method = registered[cls] ?: regBkSerializable(cls)
            @Suppress("UNCHECKED_CAST")
            return method.invoke(null, configurationSection) as T
        }

        fun regBkSerializable(cls: Class<out BkSerializable?>): Method {
            val method = cls.getDeclaredMethod("deserialize", ConfigurationSection::class.java)
            if (!Modifier.isStatic(method.modifiers)) throw Exception("${cls.name} not impl a static deserialize method")
            registered[cls] = method
            return method
        }

        inline fun <reified T : BkSerializable> regBkSerializable() {
            regBkSerializable(T::class.java)
        }
    }

    fun serialize(): ConfigurationSection
}

inline fun <reified T : BkSerializable?> ConfigurationSection?.deserializeTo() =
    this?.let { BkSerializable.deserialize<T>(it) }

inline fun <reified T : BkSerializable?> ConfigurationSection.getBkSerializable(path: String): T {
    val bks = getConfigurationSection(path).deserializeTo<T>()
    if (typeOf<T>().isMarkedNullable) return bks as T
    return bks ?: throw Exception("require non null data(${T::class.java}) at path: $currentPath.$path")
}

inline fun <reified T : BkSerializable?> ConfigurationSection.getBkSerializableMap(path: String): Map<String, T> {
    val section = getConfigurationSection(path) ?: throw Exception("require not null value on path: $path")
    val result = section.getKeys(false).associateWith<String, T>(section::getBkSerializable)
    if (!typeOf<T>().isMarkedNullable) return result
    return result.onEach { (k, v) ->
        if (v == null) throw Exception("require not null value but got null on path: ${getPath(path)}.$k")
    }
}

fun ConfigurationSection.getPath(path: String) = currentPath?.let { "$it.$path" } ?: path

inline fun <reified V> ConfigurationSection.getMap(path: String, cast: (Any?) -> V): Map<String, V> =
    getConfigurationSection(path)?.run {
        getKeys(false).associateWith { cast(get(it)) }
    } ?: emptyMap()
