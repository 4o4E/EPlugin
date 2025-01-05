@file:Suppress("UNUSED")

package top.e404.eplugin.config.serialization

import kotlinx.serialization.Serializable
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.FireworkMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionEffect
import top.e404.eplugin.EPlugin.Companion.formatAsConst
import top.e404.eplugin.util.buildItemStack
import java.util.*

private val POTION_MATERIALS = listOf(Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION, Material.TIPPED_ARROW)

@Serializable
data class Item(
    @Serializable(MaterialSerializer::class) val material: Material,
    val amount: Int = 1,
    val name: String? = null,
    val lore: List<String> = emptyList(),
    val enchant: Map<@Serializable(EnchantmentSerialization::class) Enchantment, Int> = emptyMap(),
    val flag: Set<ItemFlag> = emptySet(),
    val attribute: Map<String, Double> = emptyMap(),
    val unbreakable: Boolean = false,
    val potion: List<@Serializable(PotionEffectSerializer::class) PotionEffect> = listOf(),
    val firework: FireworkData? = null
) {
    private val origin by lazy {
        buildItemStack(material, amount, name, lore) {
            enchant.forEach { (enchant, level) -> addEnchant(enchant, level, true) }
            flag.forEach { addItemFlags(it) }
            attribute.forEach { (attr, amount) ->
                addAttributeModifier(
                    try {
                        Attribute.valueOf(attr.formatAsConst())
                    } catch (e: IllegalArgumentException) {
                        throw Exception("can't find attribute with name of $attr")
                    },
                    AttributeModifier(UUID.randomUUID(), "", amount, AttributeModifier.Operation.ADD_NUMBER)
                )
            }
            if (material in POTION_MATERIALS) potion.forEach {
                (this as PotionMeta).addCustomEffect(it, true)
            }
            if (material == Material.FIREWORK_ROCKET) {
                firework?.apply(this as FireworkMeta)
            }
            if (unbreakable) isUnbreakable = true
        }
    }
    val item get() = origin.clone()
}

@Serializable
data class FireworkData(
    val power: Int,
    val effects: List<FireworkEffectData> = listOf()
) {
    fun apply(meta: FireworkMeta) {
        meta.power = power
        effects.map { it.toEffect() }.let {
            meta.addEffects(it)
        }
    }
}

@Serializable
data class FireworkEffectData(
    val type: FireworkEffect.Type,
    val flicker: Boolean = false,
    val trail: Boolean = false,
    val colors: List<@Serializable(ColorSerializer::class) Color> = listOf(),
    val fadeColors: List<@Serializable(ColorSerializer::class) Color> = listOf(),
) {
    fun toEffect() = FireworkEffect.builder()
        .with(type)
        .flicker(flicker)
        .trail(trail)
        .withColor(colors)
        .withFade(fadeColors)
        .build()
}

@Serializable
data class AttributeModifierModel(
    val name: String = "",
    val amount: Double,
    val operation: String
) {
    fun toAttributeModifier() = AttributeModifier(UUID.randomUUID(), name, amount, AttributeModifier.Operation.valueOf(operation.formatAsConst()))
}
