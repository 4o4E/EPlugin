package top.e404.eplugin.hook.worldedit

import com.sk89q.worldedit.EditSession
import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.extent.clipboard.Clipboard
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats
import com.sk89q.worldedit.function.operation.Operations
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldedit.regions.CuboidRegion
import com.sk89q.worldedit.session.ClipboardHolder
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.inventory.ItemStack
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

    fun clear(
        world: World,
        l1: Location,
        l2: Location
    ) = clear(
        world,
        l1.blockX,
        l1.blockY,
        l1.blockZ,
        l2.blockX,
        l2.blockY,
        l2.blockZ
    )

    fun clear(
        world: World,
        x1: Int,
        y1: Int,
        z1: Int,
        x2: Int,
        y2: Int,
        z2: Int,
    ) {
        val weWorld = BukkitAdapter.adapt(world)
        WorldEdit.getInstance().newEditSession(weWorld).apply {
            blockChangeLimit = -1
        }.use { session ->
            session.setBlocks(
                CuboidRegion(
                    weWorld,
                    BlockVector3.at(x1, y1, z1),
                    BlockVector3.at(x2, y2, z2)
                ),
                BukkitAdapter.asBlockState(ItemStack(Material.AIR))
            )
        }
    }
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