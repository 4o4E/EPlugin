package top.e404.eplugin.hook.placeholderapi

import me.clip.placeholderapi.expansion.PlaceholderExpansion
import top.e404.eplugin.EPlugin

@Suppress("UNUSED")
abstract class PapiExpansion(
    val plugin: EPlugin,
    val id: String,
) : PlaceholderExpansion() {
    final override fun getIdentifier() = id
    final override fun getAuthor() = plugin.description.authors.firstOrNull() ?: "unknown"
    final override fun getVersion() = plugin.description.version
    final override fun persist() = true
    final override fun canRegister() = true
}