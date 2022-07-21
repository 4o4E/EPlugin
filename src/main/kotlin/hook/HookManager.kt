package top.e404.eplugin.hook

import top.e404.eplugin.EPlugin

class HookManager(
    val plugin: EPlugin,
    vararg hooks: EHook<*>,
) : HashMap<String, EHook<*>>() {
    override val size: Int = hooks.size

    init {
        hooks.forEach { this[it.name] = it }
    }

    fun checkHooks() = values.forEach { it.checkHook() }
}