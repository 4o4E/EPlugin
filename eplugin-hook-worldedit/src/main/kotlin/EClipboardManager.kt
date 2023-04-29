package top.e404.eplugin.hook.worldedit

import com.sk89q.worldedit.extent.clipboard.Clipboard
import top.e404.eplugin.EPlugin
import java.io.File

@Suppress("UNUSED")
open class EClipboardManager(
    open val plugin: EPlugin,
    open val folder: File,
) {
    val map = mutableMapOf<String, Clipboard>()
    fun load() = folder.also {
        if (it.isFile) it.delete()
        it.mkdirs()
    }.loadAsSchem()

    private fun File.loadAsSchem() {
        if (isFile) {
            map[name] = loadSchem(this)
            return
        }
        listFiles()?.forEach { it.loadAsSchem() }
    }

    operator fun get(name: String) = map[name]
    operator fun set(name: String, clipboard: Clipboard) {
        map[name] = clipboard
    }
}
