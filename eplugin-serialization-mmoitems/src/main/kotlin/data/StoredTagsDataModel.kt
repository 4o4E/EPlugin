package top.e404.eplugin.serialization.mmoitems.data

import io.lumine.mythic.lib.api.item.ItemTag
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.stat.data.StoredTagsData

@Serializable
@SerialName("StoredTagsData")
data class StoredTagsDataModel(
    var tags: MutableList<@Serializable(ItemTagSerializer::class) ItemTag>,
): StatDataModel<StoredTagsData> {
    companion object {
        fun StoredTagsData.toDataModel() = StoredTagsDataModel(tags)
    }

    override fun toItemData() = StoredTagsData(tags)
}
