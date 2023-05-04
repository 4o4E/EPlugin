package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.type.StatData

@Serializable
sealed interface StatDataModel<T : StatData> {
    fun toItemData(): T
}


