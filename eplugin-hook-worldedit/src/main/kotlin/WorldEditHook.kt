package top.e404.eplugin.hook.worldedit

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


@Suppress("UNUSED")
open class WorldEditHook(
    override val plugin: EPlugin,
) : EHook(plugin, "WorldEdit") {
    val we: WorldEdit
        get() = WorldEdit.getInstance()
}

fun loadSchem(file: File) = FileInputStream(file).use { fis ->
    ClipboardFormats.findByFile(file)!!
        .getReader(fis)
        .use { reader -> reader.read()!! }
}

fun saveSchem(file: File, clipboard: Clipboard) {
    FileOutputStream(file).use { fos ->
        BuiltInClipboardFormat.SPONGE_SCHEMATIC
            .getWriter(fos)
            .use { writer -> writer.write(clipboard) }
    }
}