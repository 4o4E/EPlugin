@file:Suppress("UNCHECKED_CAST", "UNUSED")

package top.e404.eplugin.reflect

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap

val reflectDataMap = ConcurrentHashMap<Class<*>, ReflectData<*>>()

data class MethodKey(
    val name: String,
    val params: Array<Class<*>>,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MethodKey

        if (name != other.name) return false
        if (!params.contentEquals(other.params)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + params.contentHashCode()
        return result
    }
}

class InvalidFieldException(
    clazz: Class<*>,
    static: Boolean,
    field: String,
    e: NoSuchFieldException,
) : Exception(
    """invalid${if (static) " static" else ""} field($field) in class(${clazz.name})
        |valid${if (static) " static" else ""} field: ${(if (static) clazz.declaredFields else clazz.fields).joinToString(", ") { it.name }}
    """.trimMargin(),
    e
)

class InvalidMethodException(
    clazz: Class<*>,
    static: Boolean,
    method: String,
    paramTypes: Array<Class<*>>,
    e: NoSuchMethodException,
) : Exception() {
    private val params = paramTypes.joinToString(", ") { it.name }
    private val validMethods = (if (static) clazz.methods else clazz.declaredMethods).joinToString("") { m ->
        "\n  ${m.returnType.name} ${m.name}(${m.parameterTypes.joinToString(", ") { it.name }}"
    }
    override val message = """invalid${if (static) " static" else ""} method($method)
        |  params[$params] in class(${clazz.name})
        |valid${if (static) " static" else ""} method: $validMethods""".trimMargin()
    override val cause = e
}

class ReflectData<T>(val clazz: Class<T>) {
    val name: String = clazz.name
    val fields = mutableMapOf<String, Field>()
    val methods = mutableMapOf<MethodKey, Method>()
    val annotation get() = clazz.annotations

    fun <R> getField(
        obj: T,
        name: String,
        static: Boolean = false,
    ): R {
        val exists = fields[name]
        if (exists != null) return exists.get(obj) as R
        val field = try {
            if (static) clazz.getField(name)
            else clazz.getDeclaredField(name)
        } catch (e: NoSuchFieldException) {
            throw InvalidFieldException(clazz, static, name, e)
        }
        field.isAccessible = true
        fields[name] = field
        return field.get(obj) as R
    }

    fun getMethod(
        name: String,
        static: Boolean = false,
        paramTypes: Array<Class<*>>,
    ): Method {
        val key = MethodKey(name, paramTypes)
        val exists = methods[key]
        if (exists != null) return exists
        val method = try {
            if (static) clazz.getMethod(name, *paramTypes)
            else clazz.getDeclaredMethod(name)
        } catch (e: NoSuchMethodException) {
            throw InvalidMethodException(clazz, static, name, paramTypes, e)
        }
        method.isAccessible = true
        methods[key] = method
        return method
    }

    fun <R> invokeMethod(
        obj: T,
        name: String,
        static: Boolean = false,
        paramTypes: Array<Class<*>>,
        params: Array<*>,
    ) = getMethod(name, static, paramTypes).invoke(obj, *params) as R
}

inline val <reified T> Class<T>.reflectData get() = reflectDataMap.getOrPut(this) { ReflectData(this) } as ReflectData<T>
inline val <reified T : Any> T.reflectData get() = T::class.java.reflectData

inline fun <reified T : Any, reified R> T.getPrivateField(
    name: String,
    static: Boolean = false,
) = reflectData.getField(this, name, static) as R

inline fun <reified T : Any, reified R> T.getPrivateMethod(
    name: String,
    static: Boolean = false,
    paramTypes: Array<Class<*>>,
): Method = reflectData.getMethod(name, static, paramTypes)

inline fun <reified T : Any, reified R> T.invokePrivateMethod(
    obj: T,
    name: String,
    static: Boolean = false,
    paramTypes: Array<Class<*>>,
    params: Array<*>,
) = reflectData.invokeMethod(obj, name, static, paramTypes, params) as R