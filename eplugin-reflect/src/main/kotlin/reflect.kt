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

class InvalidConstructorException(
    cls: Class<*>,
    parameters: Array<Class<*>>,
) : Exception() {
    override val message = run {
        val params = parameters.joinToString(", ") { it.name }
        val valid = cls.declaredConstructors.joinToString("\n  ") { "${it.name}()" }
        """invalid constructor($params) in class(${cls.name})
            |valid constructor: $valid
        """.trimMargin()
    }
}

class InvalidFieldException(
    cls: Class<*>,
    field: String,
    e: Exception,
) : Exception() {
    override val message = run {
        val fields = cls.declaredFields.joinToString(", ") { it.name }
        """invalid field($field) in class(${cls.name})
            |valid field: [$fields]
        """.trimMargin()
    }
    override val cause = e
}

class InvalidMethodException(
    cls: Class<*>,
    method: String,
    paramTypes: Array<Class<*>>,
    e: Exception,
) : Exception() {
    override val message = run {
        val params = paramTypes.joinToString(", ") { it.name }
        val validMethods = cls.declaredMethods.joinToString("") { m ->
            "\n  ${m.returnType.name} ${m.name}(${m.parameterTypes.joinToString(", ") { it.name }}"
        }
        """invalid method($method)
            |  params[$params] in class(${cls.name})
            |valid method: $validMethods
        """.trimMargin()
    }
    override val cause = e
}

class ReflectData<T>(val cls: Class<T>) {
    val name: String = cls.name
    val fields = mutableMapOf<String, Field>()
    val methods = mutableMapOf<MethodKey, Method>()
    val annotation get() = cls.annotations

    fun newInstance(): T = try {
        cls.getDeclaredConstructor()
    } catch (e: Exception) {
        throw InvalidConstructorException(cls, emptyArray())
    }.newInstance()

    inline fun <reified P0: Any> newInstance(arg0: P0): T {
        val arg0cls = arg0.javaClass
        val constructor = cls.constructors.firstOrNull {
            it.parameterCount == 1 && it.parameters[0].type == arg0cls
        } ?: throw InvalidConstructorException(cls, arrayOf(arg0cls))
        return constructor.newInstance(arg0) as T
    }

    inline fun <reified P0: Any, reified P1: Any> newInstance(arg0: P0, arg1: P1): T {
        val arg0cls = arg0.javaClass
        val arg1cls = arg1.javaClass
        val constructor = cls.constructors.firstOrNull {
            it.parameterCount == 2
                    && it.parameters[0].type == arg0cls
                    && it.parameters[1].type == arg1cls
        } ?: throw InvalidConstructorException(cls, arrayOf(arg0cls, arg1cls))
        return constructor.newInstance(arg0, arg1) as T
    }

    inline fun <reified P0: Any, reified P1: Any, reified P2: Any> newInstance(arg0: P0, arg1: P1, arg2: P2): T {
        val arg0cls = arg0.javaClass
        val arg1cls = arg1.javaClass
        val arg2cls = arg2.javaClass
        val constructor = cls.constructors.firstOrNull {
            it.parameterCount == 3
                    && it.parameters[0].type == arg0cls
                    && it.parameters[1].type == arg1cls
                    && it.parameters[2].type == arg2cls
        } ?: throw InvalidConstructorException(cls, arrayOf(arg0cls, arg1cls, arg2cls))
        return constructor.newInstance(arg0, arg1, arg2) as T
    }

    inline fun <reified P0: Any, reified P1: Any, reified P2: Any, reified P3: Any> newInstance(
        arg0: P0,
        arg1: P1,
        arg2: P2,
        arg3: P3,
    ): T {
        val arg0cls = arg0.javaClass
        val arg1cls = arg1.javaClass
        val arg2cls = arg2.javaClass
        val arg3cls = arg3.javaClass
        val constructor = cls.constructors.firstOrNull {
            it.parameterCount == 4
                    && it.parameters[0].type == arg0cls
                    && it.parameters[1].type == arg1cls
                    && it.parameters[2].type == arg2cls
                    && it.parameters[3].type == arg3cls
        } ?: throw InvalidConstructorException(cls, arrayOf(arg0cls, arg1cls, arg2cls, arg3cls))
        return constructor.newInstance(arg0, arg1, arg2, arg3) as T
    }

    inline fun <reified P0: Any, reified P1: Any, reified P2: Any, reified P3: Any, reified P4: Any> newInstance(
        arg0: P0,
        arg1: P1,
        arg2: P2,
        arg3: P3,
        arg4: P4,
    ): T {
        val arg0cls = arg0.javaClass
        val arg1cls = arg1.javaClass
        val arg2cls = arg2.javaClass
        val arg3cls = arg3.javaClass
        val arg4cls = arg4.javaClass
        val constructor = cls.constructors.firstOrNull {
            it.parameterCount == 5
                    && it.parameters[0].type == arg0cls
                    && it.parameters[1].type == arg1cls
                    && it.parameters[2].type == arg2cls
                    && it.parameters[3].type == arg3cls
                    && it.parameters[4].type == arg4cls
        } ?: throw InvalidConstructorException(cls, arrayOf(arg0cls, arg1cls, arg2cls, arg3cls, arg4cls))
        return constructor.newInstance(arg0, arg1, arg2, arg3, arg4) as T
    }

    fun <R> getField(
        obj: T,
        name: String,
    ): R {
        val exists = fields[name]
        if (exists != null) return exists.get(obj) as R
        val field = try {
            cls.getDeclaredField(name)
        } catch (e: NoSuchFieldException) {
            throw InvalidFieldException(cls, name, e)
        }
        field.isAccessible = true
        fields[name] = field
        return field.get(obj) as R
    }

    fun <R> setField(
        obj: T,
        name: String,
        value: R,
    ) {
        val exists = fields[name]
        if (exists != null) return exists.set(obj, value)
        val field = try {
            cls.getDeclaredField(name)
        } catch (e: NoSuchFieldException) {
            throw InvalidFieldException(cls, name, e)
        }
        field.isAccessible = true
        fields[name] = field
        return field.set(obj, value)
    }

    fun getMethod(
        name: String,
        paramTypes: Array<Class<*>>,
    ): Method {
        val key = MethodKey(name, paramTypes)
        val exists = methods[key]
        if (exists != null) return exists
        val method = try {
            cls.getDeclaredMethod(name)
        } catch (e: NoSuchMethodException) {
            throw InvalidMethodException(cls, name, paramTypes, e)
        }
        method.isAccessible = true
        methods[key] = method
        return method
    }

    fun <R> invokeMethod(
        obj: T,
        name: String,
        paramTypes: Array<Class<*>>,
        params: Array<*>,
    ) = getMethod(name, paramTypes).invoke(obj, *params) as R
}

inline val <reified T> Class<T>.reflectData get() = reflectDataMap.getOrPut(this) { ReflectData(this) } as ReflectData<T>
inline val <reified T : Any> T.reflectData get() = T::class.java.reflectData

inline fun <reified T : Any, reified R> T.getPrivateField(
    name: String,
) = reflectData.getField(this, name) as R

inline fun <reified T : Any, reified R> T.setPrivateField(
    name: String,
    value: R,
) = reflectData.setField(this, name, value)

inline fun <reified T : Any, reified R> T.getPrivateMethod(
    name: String,
    paramTypes: Array<Class<*>>,
): Method = reflectData.getMethod(name, paramTypes)

inline fun <reified T : Any, reified R> T.invokePrivateMethod(
    obj: T,
    name: String,
    paramTypes: Array<Class<*>>,
    params: Array<*>,
) = reflectData.invokeMethod(obj, name, paramTypes, params) as R
