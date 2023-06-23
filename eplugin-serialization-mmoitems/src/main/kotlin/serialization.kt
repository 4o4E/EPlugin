@file:Suppress("UNUSED")

package top.e404.eplugin.serialization.mmoitems

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import net.Indyuce.mmoitems.ItemStats
import net.Indyuce.mmoitems.api.util.NumericStatFormula
import net.Indyuce.mmoitems.comp.enchants.advanced_enchants.AdvancedEnchantMap
import net.Indyuce.mmoitems.stat.data.*
import net.Indyuce.mmoitems.stat.data.random.*
import net.Indyuce.mmoitems.stat.data.type.StatData
import net.Indyuce.mmoitems.stat.type.ItemStat
import net.Indyuce.mmoitems.stat.type.NameData
import top.e404.eplugin.config.KtxConfig.Companion.defaultYaml
import top.e404.eplugin.serialization.mmoitems.data.AbilityListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.AdvancedEnchantMapModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.ArrowParticlesDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.BooleanDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.ColorDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.CommandListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.DoubleDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.ElementListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.EnchantListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.GemSocketsDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.MaterialDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.NameDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.NumericStatFormulaModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.ParticleDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.PotionEffectListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.ProjectileParticlesDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.RandomAbilityListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.RandomBooleanDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.RandomElementListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.RandomEnchantListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.RandomPotionEffectListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.RandomRestoreDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.RandomStatDataModel
import top.e404.eplugin.serialization.mmoitems.data.RestoreDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.ShieldPatternDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.SkullTextureDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.SoulboundDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.SoundListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.StatDataModel
import top.e404.eplugin.serialization.mmoitems.data.StoredTagsDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.StringListDataModel.Companion.toDataModel
import top.e404.eplugin.serialization.mmoitems.data.UpgradeDataModel.Companion.toDataModel
import kotlin.math.max
import top.e404.eplugin.serialization.mmoitems.data.StringDataModel.Companion.toDataModel as toStringDataModel

fun RandomStatData<*>.toData(): RandomStatDataModel<*, *> = when (this) {
    is ArrowParticlesData -> toDataModel()
    is ColorData -> toDataModel()
    is CommandListData -> toDataModel()
    is GemSocketsData -> toDataModel()
    is MaterialData -> toDataModel()
    is NameData -> toDataModel()
    is NumericStatFormula -> toDataModel()
    is ParticleData -> toDataModel()
    is ProjectileParticlesData -> toDataModel()
    is RandomAbilityListData -> toDataModel()
    is RandomBooleanData -> toDataModel()
    is RandomElementListData -> toDataModel()
    is RandomEnchantListData -> toDataModel()
    is RandomPotionEffectListData -> toDataModel()
    is RandomRestoreData -> toDataModel()
    is ShieldPatternData -> toDataModel()
    is SkullTextureData -> toDataModel()
    is SoundListData -> toDataModel()
    is StringData -> toStringDataModel()
    is StringListData -> toDataModel()
    is UpgradeData -> toDataModel()
    else -> throw Exception("unknown random stat data: ${javaClass.name}")
}

fun StatData.toData(): StatDataModel<*> = when (this) {
    is AbilityListData -> toDataModel()
    is AdvancedEnchantMap -> toDataModel()
    is ArrowParticlesData -> toDataModel()
    is BooleanData -> toDataModel()
    is ColorData -> toDataModel()
    is CommandListData -> toDataModel()
    is RequiredLevelData -> toDataModel()
    is DoubleData -> toDataModel()
    is ElementListData -> toDataModel()
    is EnchantListData -> toDataModel()
    is GemSocketsData -> toDataModel()
    is MaterialData -> toDataModel()
    is NameData -> toDataModel()
    is ParticleData -> toDataModel()
    is PotionEffectListData -> toDataModel()
    is ProjectileParticlesData -> toDataModel()
    is RestoreData -> toDataModel()
    is ShieldPatternData -> toDataModel()
    is SkullTextureData -> toDataModel()
    is SoulboundData -> toDataModel()
    is SoundListData -> toDataModel()
    is StoredTagsData -> toDataModel()
    is StringData -> toStringDataModel()
    is StringListData -> toDataModel()
    is UpgradeData -> toDataModel()
    else -> throw Exception("unknown stat data: ${javaClass.name}")
}

fun ItemStat<*, *>.serializeToString() = when (this) {
    ItemStats.REVISION_ID -> "REVISION_ID"
    ItemStats.MATERIAL -> "MATERIAL"
    ItemStats.ITEM_DAMAGE -> "ITEM_DAMAGE"
    ItemStats.CUSTOM_MODEL_DATA -> "CUSTOM_MODEL_DATA"
    ItemStats.MAX_DURABILITY -> "MAX_DURABILITY"
    ItemStats.WILL_BREAK -> "WILL_BREAK"
    ItemStats.NAME -> "NAME"
    ItemStats.LORE -> "LORE"
    ItemStats.NBT_TAGS -> "NBT_TAGS"
    ItemStats.LORE_FORMAT -> "LORE_FORMAT"
    ItemStats.BLOCK_ID -> "BLOCK_ID"
    ItemStats.REQUIRED_POWER -> "REQUIRED_POWER"
    ItemStats.REQUIRE_POWER_TO_BREAK -> "REQUIRE_POWER_TO_BREAK"
    ItemStats.MIN_XP -> "MIN_XP"
    ItemStats.MAX_XP -> "MAX_XP"
    ItemStats.GEN_TEMPLATE -> "GEN_TEMPLATE"
    ItemStats.DISPLAYED_TYPE -> "DISPLAYED_TYPE"
    ItemStats.ENCHANTS -> "ENCHANTS"
    ItemStats.HIDE_ENCHANTS -> "HIDE_ENCHANTS"
    ItemStats.PERMISSION -> "PERMISSION"
    ItemStats.ITEM_PARTICLES -> "ITEM_PARTICLES"
    ItemStats.ARROW_PARTICLES -> "ARROW_PARTICLES"
    ItemStats.PROJECTILE_PARTICLES -> "PROJECTILE_PARTICLES"
    ItemStats.DISABLE_INTERACTION -> "DISABLE_INTERACTION"
    ItemStats.DISABLE_CRAFTING -> "DISABLE_CRAFTING"
    ItemStats.DISABLE_SMELTING -> "DISABLE_SMELTING"
    ItemStats.DISABLE_SMITHING -> "DISABLE_SMITHING"
    ItemStats.DISABLE_ENCHANTING -> "DISABLE_ENCHANTING"
    ItemStats.DISABLE_REPAIRING -> "DISABLE_REPAIRING"
    ItemStats.DISABLE_ARROW_SHOOTING -> "DISABLE_ARROW_SHOOTING"
    ItemStats.DISABLE_ATTACK_PASSIVE -> "DISABLE_ATTACK_PASSIVE"
    ItemStats.REQUIRED_LEVEL -> "REQUIRED_LEVEL"
    ItemStats.REQUIRED_CLASS -> "REQUIRED_CLASS"
    ItemStats.ATTACK_DAMAGE -> "ATTACK_DAMAGE"
    ItemStats.ATTACK_SPEED -> "ATTACK_SPEED"
    ItemStats.CRITICAL_STRIKE_CHANCE -> "CRITICAL_STRIKE_CHANCE"
    ItemStats.CRITICAL_STRIKE_POWER -> "CRITICAL_STRIKE_POWER"
    ItemStats.SKILL_CRITICAL_STRIKE_CHANCE -> "SKILL_CRITICAL_STRIKE_CHANCE"
    ItemStats.SKILL_CRITICAL_STRIKE_POWER -> "SKILL_CRITICAL_STRIKE_POWER"
    ItemStats.BLOCK_POWER -> "BLOCK_POWER"
    ItemStats.BLOCK_RATING -> "BLOCK_RATING"
    ItemStats.BLOCK_COOLDOWN_REDUCTION -> "BLOCK_COOLDOWN_REDUCTION"
    ItemStats.DODGE_RATING -> "DODGE_RATING"
    ItemStats.DODGE_COOLDOWN_REDUCTION -> "DODGE_COOLDOWN_REDUCTION"
    ItemStats.PARRY_RATING -> "PARRY_RATING"
    ItemStats.PARRY_COOLDOWN_REDUCTION -> "PARRY_COOLDOWN_REDUCTION"
    ItemStats.COOLDOWN_REDUCTION -> "COOLDOWN_REDUCTION"
    ItemStats.RANGE -> "RANGE"
    ItemStats.MANA_COST -> "MANA_COST"
    ItemStats.STAMINA_COST -> "STAMINA_COST"
    ItemStats.ARROW_VELOCITY -> "ARROW_VELOCITY"
    ItemStats.ARROW_POTION_EFFECTS -> "ARROW_POTION_EFFECTS"
    ItemStats.PVE_DAMAGE -> "PVE_DAMAGE"
    ItemStats.PVP_DAMAGE -> "PVP_DAMAGE"
    ItemStats.BLUNT_POWER -> "BLUNT_POWER"
    ItemStats.BLUNT_RATING -> "BLUNT_RATING"
    ItemStats.WEAPON_DAMAGE -> "WEAPON_DAMAGE"
    ItemStats.SKILL_DAMAGE -> "SKILL_DAMAGE"
    ItemStats.PROJECTILE_DAMAGE -> "PROJECTILE_DAMAGE"
    ItemStats.MAGIC_DAMAGE -> "MAGIC_DAMAGE"
    ItemStats.PHYSICAL_DAMAGE -> "PHYSICAL_DAMAGE"
    ItemStats.DEFENSE -> "DEFENSE"
    ItemStats.DAMAGE_REDUCTION -> "DAMAGE_REDUCTION"
    ItemStats.FALL_DAMAGE_REDUCTION -> "FALL_DAMAGE_REDUCTION"
    ItemStats.PROJECTILE_DAMAGE_REDUCTION -> "PROJECTILE_DAMAGE_REDUCTION"
    ItemStats.PHYSICAL_DAMAGE_REDUCTION -> "PHYSICAL_DAMAGE_REDUCTION"
    ItemStats.FIRE_DAMAGE_REDUCTION -> "FIRE_DAMAGE_REDUCTION"
    ItemStats.MAGIC_DAMAGE_REDUCTION -> "MAGIC_DAMAGE_REDUCTION"
    ItemStats.PVE_DAMAGE_REDUCTION -> "PVE_DAMAGE_REDUCTION"
    ItemStats.PVP_DAMAGE_REDUCTION -> "PVP_DAMAGE_REDUCTION"
    ItemStats.UNDEAD_DAMAGE -> "UNDEAD_DAMAGE"
    ItemStats.LIFESTEAL -> "LIFESTEAL"
    ItemStats.SPELL_VAMPIRISM -> "SPELL_VAMPIRISM"
    ItemStats.UNBREAKABLE -> "UNBREAKABLE"
    ItemStats.TIER -> "TIER"
    ItemStats.SET -> "SET"
    ItemStats.ARMOR -> "ARMOR"
    ItemStats.ARMOR_TOUGHNESS -> "ARMOR_TOUGHNESS"
    ItemStats.MAX_HEALTH -> "MAX_HEALTH"
    ItemStats.UNSTACKABLE -> "UNSTACKABLE"
    ItemStats.MAX_MANA -> "MAX_MANA"
    ItemStats.KNOCKBACK_RESISTANCE -> "KNOCKBACK_RESISTANCE"
    ItemStats.MOVEMENT_SPEED -> "MOVEMENT_SPEED"
    ItemStats.TWO_HANDED -> "TWO_HANDED"
    ItemStats.REQUIRED_BIOMES -> "REQUIRED_BIOMES"
    ItemStats.DROP_ON_DEATH -> "DROP_ON_DEATH"
    ItemStats.DURABILITY_BAR -> "DURABILITY_BAR"
    ItemStats.PERM_EFFECTS -> "PERM_EFFECTS"
    ItemStats.GRANTED_PERMISSIONS -> "GRANTED_PERMISSIONS"
    ItemStats.RESTORE_HEALTH -> "RESTORE_HEALTH"
    ItemStats.RESTORE_FOOD -> "RESTORE_FOOD"
    ItemStats.RESTORE_SATURATION -> "RESTORE_SATURATION"
    ItemStats.RESTORE_MANA -> "RESTORE_MANA"
    ItemStats.RESTORE_STAMINA -> "RESTORE_STAMINA"
    ItemStats.CAN_IDENTIFY -> "CAN_IDENTIFY"
    ItemStats.CAN_DECONSTRUCT -> "CAN_DECONSTRUCT"
    ItemStats.CAN_DESKIN -> "CAN_DESKIN"
    ItemStats.EFFECTS -> "EFFECTS"
    ItemStats.SOULBINDING_CHANCE -> "SOULBINDING_CHANCE"
    ItemStats.SOULBOUND_BREAK_CHANCE -> "SOULBOUND_BREAK_CHANCE"
    ItemStats.SOULBOUND_LEVEL -> "SOULBOUND_LEVEL"
    ItemStats.ITEM_COOLDOWN -> "ITEM_COOLDOWN"
    ItemStats.COOLDOWN_REFERENCE -> "COOLDOWN_REFERENCE"
    ItemStats.VANILLA_EATING_ANIMATION -> "VANILLA_EATING_ANIMATION"
    ItemStats.GEM_COLOR -> "GEM_COLOR"
    ItemStats.GEM_UPGRADE_SCALING -> "GEM_UPGRADE_SCALING"
    ItemStats.ITEM_TYPE_RESTRICTION -> "ITEM_TYPE_RESTRICTION"
    ItemStats.MAX_CONSUME -> "MAX_CONSUME"
    ItemStats.SUCCESS_RATE -> "SUCCESS_RATE"
    ItemStats.CRAFTING -> "CRAFTING"
    ItemStats.CRAFT_PERMISSION -> "CRAFT_PERMISSION"
    ItemStats.AUTOSMELT -> "AUTOSMELT"
    ItemStats.BOUNCING_CRACK -> "BOUNCING_CRACK"
    ItemStats.PICKAXE_POWER -> "PICKAXE_POWER"
    ItemStats.CUSTOM_SOUNDS -> "CUSTOM_SOUNDS"
    ItemStats.ELEMENTS -> "ELEMENTS"
    ItemStats.COMMANDS -> "COMMANDS"
    ItemStats.STAFF_SPIRIT -> "STAFF_SPIRIT"
    ItemStats.LUTE_ATTACK_SOUND -> "LUTE_ATTACK_SOUND"
    ItemStats.LUTE_ATTACK_EFFECT -> "LUTE_ATTACK_EFFECT"
    ItemStats.NOTE_WEIGHT -> "NOTE_WEIGHT"
    ItemStats.REMOVE_ON_CRAFT -> "REMOVE_ON_CRAFT"
    ItemStats.COMPATIBLE_TYPES -> "COMPATIBLE_TYPES"
    ItemStats.COMPATIBLE_IDS -> "COMPATIBLE_IDS"
    ItemStats.COMPATIBLE_MATERIALS -> "COMPATIBLE_MATERIALS"
    ItemStats.GEM_SOCKETS -> "GEM_SOCKETS"
    ItemStats.RANDOM_UNSOCKET -> "RANDOM_UNSOCKET"
    ItemStats.REPAIR -> "REPAIR"
    ItemStats.REPAIR_PERCENT -> "REPAIR_PERCENT"
    ItemStats.REPAIR_TYPE -> "REPAIR_TYPE"
    ItemStats.INEDIBLE -> "INEDIBLE"
    ItemStats.DISABLE_RIGHT_CLICK_CONSUME -> "DISABLE_RIGHT_CLICK_CONSUME"
    ItemStats.KNOCKBACK -> "KNOCKBACK"
    ItemStats.RECOIL -> "RECOIL"
    ItemStats.HANDWORN -> "HANDWORN"
    ItemStats.AMPHIBIAN -> "AMPHIBIAN"
    ItemStats.ABILITIES -> "ABILITIES"
    ItemStats.UPGRADE -> "UPGRADE"
    ItemStats.DOWNGRADE_ON_BREAK -> "DOWNGRADE_ON_BREAK"
    ItemStats.DOWNGRADE_ON_DEATH -> "DOWNGRADE_ON_DEATH"
    ItemStats.DOWNGRADE_ON_DEATH_CHANCE -> "DOWNGRADE_ON_DEATH_CHANCE"
    ItemStats.SKULL_TEXTURE -> "SKULL_TEXTURE"
    ItemStats.DYE_COLOR -> "DYE_COLOR"
    ItemStats.HIDE_DYE -> "HIDE_DYE"
    ItemStats.POTION_EFFECTS -> "POTION_EFFECTS"
    ItemStats.POTION_COLOR -> "POTION_COLOR"
    ItemStats.SHIELD_PATTERN -> "SHIELD_PATTERN"
    ItemStats.HIDE_POTION_EFFECTS -> "HIDE_POTION_EFFECTS"
    ItemStats.SOULBOUND -> "SOULBOUND"
    ItemStats.CUSTOM_DURABILITY -> "CUSTOM_DURABILITY"
    ItemStats.STORED_TAGS -> "STORED_TAGS"
    ItemStats.ITEM_LEVEL -> "ITEM_LEVEL"
    ItemStats.BROWSER_DISPLAY_IDX -> "BROWSER_DISPLAY_IDX"
    else -> throw Exception("unknown item stats ${this.javaClass.name}")
}

fun String.deserializeToItemStat(): ItemStat<RandomStatData<StatData>, StatData> = when (this) {
    "REVISION_ID" -> ItemStats.REVISION_ID
    "MATERIAL" -> ItemStats.MATERIAL
    "ITEM_DAMAGE" -> ItemStats.ITEM_DAMAGE
    "CUSTOM_MODEL_DATA" -> ItemStats.CUSTOM_MODEL_DATA
    "MAX_DURABILITY" -> ItemStats.MAX_DURABILITY
    "WILL_BREAK" -> ItemStats.WILL_BREAK
    "NAME" -> ItemStats.NAME
    "LORE" -> ItemStats.LORE
    "NBT_TAGS" -> ItemStats.NBT_TAGS
    "LORE_FORMAT" -> ItemStats.LORE_FORMAT
    "BLOCK_ID" -> ItemStats.BLOCK_ID
    "REQUIRED_POWER" -> ItemStats.REQUIRED_POWER
    "REQUIRE_POWER_TO_BREAK" -> ItemStats.REQUIRE_POWER_TO_BREAK
    "MIN_XP" -> ItemStats.MIN_XP
    "MAX_XP" -> ItemStats.MAX_XP
    "GEN_TEMPLATE" -> ItemStats.GEN_TEMPLATE
    "DISPLAYED_TYPE" -> ItemStats.DISPLAYED_TYPE
    "ENCHANTS" -> ItemStats.ENCHANTS
    "HIDE_ENCHANTS" -> ItemStats.HIDE_ENCHANTS
    "PERMISSION" -> ItemStats.PERMISSION
    "ITEM_PARTICLES" -> ItemStats.ITEM_PARTICLES
    "ARROW_PARTICLES" -> ItemStats.ARROW_PARTICLES
    "PROJECTILE_PARTICLES" -> ItemStats.PROJECTILE_PARTICLES
    "DISABLE_INTERACTION" -> ItemStats.DISABLE_INTERACTION
    "DISABLE_CRAFTING" -> ItemStats.DISABLE_CRAFTING
    "DISABLE_SMELTING" -> ItemStats.DISABLE_SMELTING
    "DISABLE_SMITHING" -> ItemStats.DISABLE_SMITHING
    "DISABLE_ENCHANTING" -> ItemStats.DISABLE_ENCHANTING
    "DISABLE_REPAIRING" -> ItemStats.DISABLE_REPAIRING
    "DISABLE_ARROW_SHOOTING" -> ItemStats.DISABLE_ARROW_SHOOTING
    "DISABLE_ATTACK_PASSIVE" -> ItemStats.DISABLE_ATTACK_PASSIVE
    "REQUIRED_LEVEL" -> ItemStats.REQUIRED_LEVEL
    "REQUIRED_CLASS" -> ItemStats.REQUIRED_CLASS
    "ATTACK_DAMAGE" -> ItemStats.ATTACK_DAMAGE
    "ATTACK_SPEED" -> ItemStats.ATTACK_SPEED
    "CRITICAL_STRIKE_CHANCE" -> ItemStats.CRITICAL_STRIKE_CHANCE
    "CRITICAL_STRIKE_POWER" -> ItemStats.CRITICAL_STRIKE_POWER
    "SKILL_CRITICAL_STRIKE_CHANCE" -> ItemStats.SKILL_CRITICAL_STRIKE_CHANCE
    "SKILL_CRITICAL_STRIKE_POWER" -> ItemStats.SKILL_CRITICAL_STRIKE_POWER
    "BLOCK_POWER" -> ItemStats.BLOCK_POWER
    "BLOCK_RATING" -> ItemStats.BLOCK_RATING
    "BLOCK_COOLDOWN_REDUCTION" -> ItemStats.BLOCK_COOLDOWN_REDUCTION
    "DODGE_RATING" -> ItemStats.DODGE_RATING
    "DODGE_COOLDOWN_REDUCTION" -> ItemStats.DODGE_COOLDOWN_REDUCTION
    "PARRY_RATING" -> ItemStats.PARRY_RATING
    "PARRY_COOLDOWN_REDUCTION" -> ItemStats.PARRY_COOLDOWN_REDUCTION
    "COOLDOWN_REDUCTION" -> ItemStats.COOLDOWN_REDUCTION
    "RANGE" -> ItemStats.RANGE
    "MANA_COST" -> ItemStats.MANA_COST
    "STAMINA_COST" -> ItemStats.STAMINA_COST
    "ARROW_VELOCITY" -> ItemStats.ARROW_VELOCITY
    "ARROW_POTION_EFFECTS" -> ItemStats.ARROW_POTION_EFFECTS
    "PVE_DAMAGE" -> ItemStats.PVE_DAMAGE
    "PVP_DAMAGE" -> ItemStats.PVP_DAMAGE
    "BLUNT_POWER" -> ItemStats.BLUNT_POWER
    "BLUNT_RATING" -> ItemStats.BLUNT_RATING
    "WEAPON_DAMAGE" -> ItemStats.WEAPON_DAMAGE
    "SKILL_DAMAGE" -> ItemStats.SKILL_DAMAGE
    "PROJECTILE_DAMAGE" -> ItemStats.PROJECTILE_DAMAGE
    "MAGIC_DAMAGE" -> ItemStats.MAGIC_DAMAGE
    "PHYSICAL_DAMAGE" -> ItemStats.PHYSICAL_DAMAGE
    "DEFENSE" -> ItemStats.DEFENSE
    "DAMAGE_REDUCTION" -> ItemStats.DAMAGE_REDUCTION
    "FALL_DAMAGE_REDUCTION" -> ItemStats.FALL_DAMAGE_REDUCTION
    "PROJECTILE_DAMAGE_REDUCTION" -> ItemStats.PROJECTILE_DAMAGE_REDUCTION
    "PHYSICAL_DAMAGE_REDUCTION" -> ItemStats.PHYSICAL_DAMAGE_REDUCTION
    "FIRE_DAMAGE_REDUCTION" -> ItemStats.FIRE_DAMAGE_REDUCTION
    "MAGIC_DAMAGE_REDUCTION" -> ItemStats.MAGIC_DAMAGE_REDUCTION
    "PVE_DAMAGE_REDUCTION" -> ItemStats.PVE_DAMAGE_REDUCTION
    "PVP_DAMAGE_REDUCTION" -> ItemStats.PVP_DAMAGE_REDUCTION
    "UNDEAD_DAMAGE" -> ItemStats.UNDEAD_DAMAGE
    "LIFESTEAL" -> ItemStats.LIFESTEAL
    "SPELL_VAMPIRISM" -> ItemStats.SPELL_VAMPIRISM
    "UNBREAKABLE" -> ItemStats.UNBREAKABLE
    "TIER" -> ItemStats.TIER
    "SET" -> ItemStats.SET
    "ARMOR" -> ItemStats.ARMOR
    "ARMOR_TOUGHNESS" -> ItemStats.ARMOR_TOUGHNESS
    "MAX_HEALTH" -> ItemStats.MAX_HEALTH
    "UNSTACKABLE" -> ItemStats.UNSTACKABLE
    "MAX_MANA" -> ItemStats.MAX_MANA
    "KNOCKBACK_RESISTANCE" -> ItemStats.KNOCKBACK_RESISTANCE
    "MOVEMENT_SPEED" -> ItemStats.MOVEMENT_SPEED
    "TWO_HANDED" -> ItemStats.TWO_HANDED
    "REQUIRED_BIOMES" -> ItemStats.REQUIRED_BIOMES
    "DROP_ON_DEATH" -> ItemStats.DROP_ON_DEATH
    "DURABILITY_BAR" -> ItemStats.DURABILITY_BAR
    "PERM_EFFECTS" -> ItemStats.PERM_EFFECTS
    "GRANTED_PERMISSIONS" -> ItemStats.GRANTED_PERMISSIONS
    "RESTORE_HEALTH" -> ItemStats.RESTORE_HEALTH
    "RESTORE_FOOD" -> ItemStats.RESTORE_FOOD
    "RESTORE_SATURATION" -> ItemStats.RESTORE_SATURATION
    "RESTORE_MANA" -> ItemStats.RESTORE_MANA
    "RESTORE_STAMINA" -> ItemStats.RESTORE_STAMINA
    "CAN_IDENTIFY" -> ItemStats.CAN_IDENTIFY
    "CAN_DECONSTRUCT" -> ItemStats.CAN_DECONSTRUCT
    "CAN_DESKIN" -> ItemStats.CAN_DESKIN
    "EFFECTS" -> ItemStats.EFFECTS
    "SOULBINDING_CHANCE" -> ItemStats.SOULBINDING_CHANCE
    "SOULBOUND_BREAK_CHANCE" -> ItemStats.SOULBOUND_BREAK_CHANCE
    "SOULBOUND_LEVEL" -> ItemStats.SOULBOUND_LEVEL
    "ITEM_COOLDOWN" -> ItemStats.ITEM_COOLDOWN
    "COOLDOWN_REFERENCE" -> ItemStats.COOLDOWN_REFERENCE
    "VANILLA_EATING_ANIMATION" -> ItemStats.VANILLA_EATING_ANIMATION
    "GEM_COLOR" -> ItemStats.GEM_COLOR
    "GEM_UPGRADE_SCALING" -> ItemStats.GEM_UPGRADE_SCALING
    "ITEM_TYPE_RESTRICTION" -> ItemStats.ITEM_TYPE_RESTRICTION
    "MAX_CONSUME" -> ItemStats.MAX_CONSUME
    "SUCCESS_RATE" -> ItemStats.SUCCESS_RATE
    "CRAFTING" -> ItemStats.CRAFTING
    "CRAFT_PERMISSION" -> ItemStats.CRAFT_PERMISSION
    "AUTOSMELT" -> ItemStats.AUTOSMELT
    "BOUNCING_CRACK" -> ItemStats.BOUNCING_CRACK
    "PICKAXE_POWER" -> ItemStats.PICKAXE_POWER
    "CUSTOM_SOUNDS" -> ItemStats.CUSTOM_SOUNDS
    "ELEMENTS" -> ItemStats.ELEMENTS
    "COMMANDS" -> ItemStats.COMMANDS
    "STAFF_SPIRIT" -> ItemStats.STAFF_SPIRIT
    "LUTE_ATTACK_SOUND" -> ItemStats.LUTE_ATTACK_SOUND
    "LUTE_ATTACK_EFFECT" -> ItemStats.LUTE_ATTACK_EFFECT
    "NOTE_WEIGHT" -> ItemStats.NOTE_WEIGHT
    "REMOVE_ON_CRAFT" -> ItemStats.REMOVE_ON_CRAFT
    "COMPATIBLE_TYPES" -> ItemStats.COMPATIBLE_TYPES
    "COMPATIBLE_IDS" -> ItemStats.COMPATIBLE_IDS
    "COMPATIBLE_MATERIALS" -> ItemStats.COMPATIBLE_MATERIALS
    "GEM_SOCKETS" -> ItemStats.GEM_SOCKETS
    "RANDOM_UNSOCKET" -> ItemStats.RANDOM_UNSOCKET
    "REPAIR" -> ItemStats.REPAIR
    "REPAIR_PERCENT" -> ItemStats.REPAIR_PERCENT
    "REPAIR_TYPE" -> ItemStats.REPAIR_TYPE
    "INEDIBLE" -> ItemStats.INEDIBLE
    "DISABLE_RIGHT_CLICK_CONSUME" -> ItemStats.DISABLE_RIGHT_CLICK_CONSUME
    "KNOCKBACK" -> ItemStats.KNOCKBACK
    "RECOIL" -> ItemStats.RECOIL
    "HANDWORN" -> ItemStats.HANDWORN
    "AMPHIBIAN" -> ItemStats.AMPHIBIAN
    "ABILITIES" -> ItemStats.ABILITIES
    "UPGRADE" -> ItemStats.UPGRADE
    "DOWNGRADE_ON_BREAK" -> ItemStats.DOWNGRADE_ON_BREAK
    "DOWNGRADE_ON_DEATH" -> ItemStats.DOWNGRADE_ON_DEATH
    "DOWNGRADE_ON_DEATH_CHANCE" -> ItemStats.DOWNGRADE_ON_DEATH_CHANCE
    "SKULL_TEXTURE" -> ItemStats.SKULL_TEXTURE
    "DYE_COLOR" -> ItemStats.DYE_COLOR
    "HIDE_DYE" -> ItemStats.HIDE_DYE
    "POTION_EFFECTS" -> ItemStats.POTION_EFFECTS
    "POTION_COLOR" -> ItemStats.POTION_COLOR
    "SHIELD_PATTERN" -> ItemStats.SHIELD_PATTERN
    "HIDE_POTION_EFFECTS" -> ItemStats.HIDE_POTION_EFFECTS
    "SOULBOUND" -> ItemStats.SOULBOUND
    "CUSTOM_DURABILITY" -> ItemStats.CUSTOM_DURABILITY
    "STORED_TAGS" -> ItemStats.STORED_TAGS
    "ITEM_LEVEL" -> ItemStats.ITEM_LEVEL
    "BROWSER_DISPLAY_IDX" -> ItemStats.BROWSER_DISPLAY_IDX
    else -> throw Exception("unknown item stats $this")
}

fun Map<ItemStat<*, *>, StatData>.convertToModel() = entries.associate { (k, v) -> k.serializeToString() to v.toData() }

fun Map<ItemStat<*, *>, StatData>.serializerToString() = defaultYaml.encodeToString(convertToModel())

fun Map<String, StatDataModel<*>>.convertToData() = entries.associate { (k, v) ->
    k.deserializeToItemStat() to v.toItemData()
}

fun String.deserializerToStats() = defaultYaml.decodeFromString<Map<String, StatDataModel<*>>>(this).convertToData()

inline fun <reified T : StatData> T.mergeWith(t: T) = apply {
    when (this) {
        is AbilityListData -> abilities.addAll((t as AbilityListData).abilities)
        is AdvancedEnchantMap -> enchants.putAll((t as AdvancedEnchantMap).enchants)
        is CommandListData -> commands.addAll((t as CommandListData).commands)
        is DoubleData -> merge(t as DoubleData)
        is ElementListData -> merge(t)
        is EnchantListData -> merge(t)
        is GemSocketsData -> merge(t as GemSocketsData)
        is NameData -> merge(t as NameData)
        is PotionEffectListData -> merge(t as PotionEffectListData)
        is RestoreData -> merge(t)
        is SoundListData -> merge(t)
        is StoredTagsData -> merge(t as StoredTagsData)
        is StringData -> merge(t as StringData)
        is StringListData -> merge(t as StringListData)
    }
}
