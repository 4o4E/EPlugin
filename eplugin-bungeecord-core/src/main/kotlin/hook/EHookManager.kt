package top.e404.eplugin.bungeecord.hook

import top.e404.eplugin.bungeecord.EPlugin

open class EHookManager(
    val plugin: EPlugin,
    vararg val hooks: EHook<*>,
) : HashMap<String, EHook<*>>() {
    override val size: Int = hooks.size

    fun register() {
        hooks.forEach { this[it.name] = it }
        checkHooks()
    }

    fun addHook(name: String, hook: EHook<*>): Boolean {
        if (name in this) return false
        this[name] = hook
        return true
    }

    fun delHook(name: String) = remove(name)

    fun checkHooks() = values.forEach { it.checkHook() }
}
