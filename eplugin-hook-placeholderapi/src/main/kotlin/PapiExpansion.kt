package top.e404.eplugin.hook.placeholderapi

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import top.e404.eplugin.EPlugin

@Suppress("UNUSED")
abstract class PapiExpansion(
    val plugin: EPlugin,
    val id: String,
) : PlaceholderExpansion() {
    override fun getIdentifier() = id
    override fun getAuthor() = plugin.description.authors.firstOrNull() ?: "unknown"
    override fun getVersion() = plugin.description.version
    override fun persist() = true
    override fun canRegister() = true
}