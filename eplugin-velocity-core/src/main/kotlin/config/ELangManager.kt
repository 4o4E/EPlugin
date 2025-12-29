@file:Suppress("UNUSED")

package top.e404.eplugin.config

import com.charleskorn.kaml.YamlMap
import com.charleskorn.kaml.YamlPathSegment
import com.charleskorn.kaml.YamlScalar
import com.charleskorn.kaml.yamlMap
import com.velocitypowered.api.command.CommandSource
import top.e404.eplugin.EPluginVelocity
import top.e404.eplugin.EPluginVelocity.Companion.color
import top.e404.eplugin.EPluginVelocity.Companion.placeholder
import top.e404.eplugin.config.KtxConfig.Companion.defaultYaml

abstract class ELangManager(
    override val plugin: EPluginVelocity,
) : AbstractEConfig(
    plugin,
    "lang.yml",
    JarConfigDefault(plugin, "lang.yml"),
) {
    protected val defaultLang by lazy {
        parseAsLang(default.string)
    }
    protected var cacheLang = mutableMapOf<String, String>()

    override fun saveDefault(sender: CommandSource?) {
        if (!file.exists()) file.writeText(default.string)
    }

    override fun load(sender: CommandSource?) {
        saveDefault(sender)
        cacheLang = parseAsLang(file.readText())
    }

    fun getString(path: String) = cacheLang[path] ?: defaultLang[path]

    operator fun get(path: String) = cacheLang[path]?.color
        ?: defaultLang[path]?.color
        ?: path

    operator fun get(path: String, vararg placeholder: Pair<String, Any?>) = cacheLang[path]?.placeholder(*placeholder)
        ?: defaultLang[path]?.placeholder(*placeholder)
        ?: path


    fun getOrDefault(path: String, default: String) = getString(path) ?: default
    fun getOrSelf(path: String) = getOrDefault(path, path)
    fun getOrElse(path: String, block: () -> String) = getString(path) ?: block()
    override fun save(sender: CommandSource?) = Unit

    protected fun parseAsLang(text: String): MutableMap<String, String> {
        val root = defaultYaml.parseToYamlNode(text).yamlMap
        val result = mutableMapOf<String, String>()
        flatten(root, result)
        return result
    }

    protected fun flatten(node: YamlMap, result: MutableMap<String, String>) {
        for ((keyNode, valueNode) in node.entries) {
            val key = keyNode.path.segments
                .filterIsInstance<YamlPathSegment.MapElementKey>()
                .joinToString(".") { it.key }
            when (valueNode) {
                is YamlMap -> flatten(valueNode, result)
                else -> result[key] = (valueNode as YamlScalar).content
            }
        }
    }
}
