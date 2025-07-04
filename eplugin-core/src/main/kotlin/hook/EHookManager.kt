package top.e404.eplugin.hook

import top.e404.eplugin.EPlugin

open class EHookManager(
    val plugin: EPlugin,
    vararg val hooks: EHook,
) : HashMap<String, EHook>() {
    @Suppress("PROPERTY_HIDES_JAVA_FIELD")
    override val size: Int = hooks.size

    open fun register() {
        hooks.forEach { this[it.name] = it }
        checkHooks()
    }

    fun addHook(name: String, hook: EHook): Boolean {
        if (name in this) return false
        this[name] = hook
        return true
    }

    fun delHook(name: String) = remove(name)

    fun checkHooks() = values.forEach { it.checkHook() }
}
