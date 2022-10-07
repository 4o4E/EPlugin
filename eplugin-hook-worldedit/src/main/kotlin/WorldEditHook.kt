package top.e404.eplugin.hook.worldedit

import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.session.ClipboardHolder
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

fun saveSchem(
    file: File,
    clipboard: Clipboard
) {
    FileOutputStream(file).use { fos ->
        BuiltInClipboardFormat.SPONGE_SCHEMATIC
            .getWriter(fos)
            .use { writer -> writer.write(clipboard) }
    }
}

/**
 * 将剪切板粘贴到指定位置
 */
fun Clipboard.paste(
    session: EditSession,
    x: Int,
    y: Int,
    z: Int
) = Operations.complete(
    ClipboardHolder(this)
        .createPaste(session)
        .to(BlockVector3.at(x, y, z))
        .build()
)

/**
 * 将剪切板粘贴到指定位置, 剪切板xz中心坐标对应给的xz坐标, 剪切板y最低点对应给的z坐标
 */
fun Clipboard.pasteCenter(
    session: EditSession,
    x: Int,
    y: Int,
    z: Int
) = paste(
    session = session,
    x = x + origin.x - (maximumPoint.x + minimumPoint.x) / 2,
    y = y + origin.y - minimumPoint.y,
    z = z + origin.z - (maximumPoint.z + minimumPoint.z) / 2
)