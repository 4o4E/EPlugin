package top.e404.eplugin.game.mini.games

import org.bukkit.Material

/**
 * 方块矩阵
 */
class BlocksMatrix(
    val width: Int = 3,
    val height: Int = 3,
    blockTypes: List<Material>
) {
    private val chain = BlockChain.init(blockTypes)
    val blocks = Array(width) { Array(height) { chain } }

    private val updateOffsets = arrayOf(
        Pair(0, 0),
        Pair(0, 1),
        Pair(1, 0),
        Pair(0, -1),
        Pair(-1, 0),
    )

    /**
     * 触发交互
     *
     * @param x x坐标
     * @param y y坐标
     * @return 更新的位置偏移量
     */
    fun click(x: Int, y: Int) = updateOffsets.map { (dx, dy) ->
        val nx = x + dx
        val ny = y + dy
        if (nx in 0 until width && ny in 0 until height) {
            blocks[nx][ny] = blocks[nx][ny].next
            return@map nx to ny
        }
        null
    }.filterNotNull()

    val allLocation = buildList {
        for (x in 0 until width) {
            for (y in 0 until height) {
                add(Pair(x, y))
            }
        }
    }

    /**
     * 打乱顺序
     *
     * @param step 步数
     */
    fun reversed(step: Int) {
        (0..step).map { allLocation.random() }.forEach { (x, y) ->
            click(x, y)
        }
    }

    val isDone get() = allLocation.map { (x, y) ->
        blocks[x][y].block
    }.toSet().size == 1
}

data class BlockChain(val block: Material) {
    lateinit var next: BlockChain
        internal set

    companion object {
        fun init(blocks: List<Material>): BlockChain {
            require(blocks.isNotEmpty()) { "must not empty" }
            val nodes = blocks.map { BlockChain(it) }
            nodes.mapIndexed { index, node ->
                node.next = nodes[(index + 1) % nodes.size]
            }
            return nodes.first()
        }
    }
}