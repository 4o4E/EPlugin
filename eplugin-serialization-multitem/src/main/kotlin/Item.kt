package top.e404.eplugin.multitem

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import top.e404.eplugin.config.serialization.MaterialSerializer
import top.e404.eplugin.hook.bentobox.ItemsAdderHook
import top.e404.eplugin.hook.mmoitems.MmoitemsHook
import top.e404.eplugin.hook.mmoitems.equals
import top.e404.eplugin.hook.mmoitems.id

lateinit var iaHook: ItemsAdderHook
lateinit var miHook: MmoitemsHook

@Serializable
sealed interface Item {
    val amount: Int
    fun toItemStack(): ItemStack
    fun match(item: ItemStack): Boolean
}

@Serializable
@SerialName("mc")
data class McItem(
    @Serializable(MaterialSerializer::class)
    val id: Material,
    override val amount: Int = 1,
) : Item {
    override fun toItemStack() = ItemStack(id).also { it.amount = amount }
    override fun match(item: ItemStack) = if (iaHook.getIaItemInfo(item) != null
        || miHook.getNbtItem(item).id.isNotBlank()
    ) false else item.type == id
}

@Serializable
@SerialName("mi")
data class MiItem(
    val category: String,
    val id: String,
    override val amount: Int = 1,
) : Item {
    override fun toItemStack() = miHook.getItem(category, id)?.also { it.amount = amount } ?: throw UnknownMmoItemsItem(category, id)
    override fun match(item: ItemStack) = miHook.getNbtItem(item).equals(category, id)
}

class UnknownMmoItemsItem(type: String, id: String) : RuntimeException("无法找到类别为${type}, id为${id}的MmoItems物品")

@Serializable
@SerialName("ia")
data class IaItem(
    val namespaceId: String,
    override val amount: Int = 1,
) : Item {
    override fun toItemStack() = iaHook.getIaItem(namespaceId)?.itemStack?.also { it.amount = amount } ?: throw UnknownItemsAdderItem(namespaceId)
    override fun match(item: ItemStack) = iaHook.getIaItemInfo(item)?.namespacedID == namespaceId
}

class UnknownItemsAdderItem(namespaceId: String) : RuntimeException("无法找到id为${namespaceId}的ItemsAdder物品")