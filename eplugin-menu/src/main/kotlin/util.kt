package top.e404.eplugin.menu

/**
 * 检查[x], [y], [w], [h]是否在范围呢
 */
fun checkArg(x: Int, y: Int, w: Int, h: Int) {
    require(x in 0..8) { "require x in 0..8" }
    require(y in 0..6) { "require y in 0..{row}" }
    require(w > 0) { "require w > 0" }
    require(h > 0) { "require h > 0" }
    require(x + w < 9) { "require x + w < 9" }
    require(y + h < 6) { "require y + h < {row}" }
}

/**
 * 从原有的菜单中划出一块区域
 *
 * @param x 区域左侧距离菜单左侧的格子数
 * @param y 区域顶部距离菜单顶部的格子数
 * @param w 区域宽度
 * @param h 区域高度
 * @return 下标数组
 */
fun getSubZone(x: Int, y: Int, w: Int, h: Int): Array<Int> {
    checkArg(x, y, w, h)
    val array = Array(w * h) { 0 }
    var i = 0
    for (zy in 0 until h) for (zx in 0 until w) {
        array[i++] = ((zy + y) * 9 + zx + x)
    }
    return array
}

/**
 * 从原有的菜单中划出一块区域
 *
 * @param x 区域左侧距离菜单左侧的格子数
 * @param y 区域顶部距离菜单顶部的格子数
 * @param w 区域宽度
 * @param h 区域高度
 * @return 二维下标数组
 */
fun getSubZoneArray(x: Int, y: Int, w: Int, h: Int): Array<IntArray> {
    checkArg(x, y, w, h)
    val array = Array(h) { IntArray(w) { 0 } }
    for (zy in 0 until h) for (zx in 0 until w) {
        array[zy][zx] = (zy + y) * 9 + zx + x
    }
    return array
}