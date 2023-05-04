package top.e404.eplugin.serialization.mmoitems.data

import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.random.RandomStatData
import net.Indyuce.mmoitems.stat.data.type.StatData

@Serializable
sealed interface RandomStatDataModel<T : StatData, out R : RandomStatData<T>> {
    fun toRandomStatData(): R
}
