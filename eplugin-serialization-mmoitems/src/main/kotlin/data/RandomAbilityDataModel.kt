package top.e404.eplugin.serialization.mmoitems.data

import io.lumine.mythic.lib.skill.trigger.TriggerType
import kotlinx.serialization.Serializable
import net.Indyuce.mmoitems.MMOItems
import net.Indyuce.mmoitems.api.util.NumericStatFormula
import net.Indyuce.mmoitems.stat.data.random.RandomAbilityData
import top.e404.eplugin.reflect.getPrivateField

@Serializable
data class RandomAbilityDataModel(
    var abilityName: String,
    @Serializable(TriggerTypeSerializer::class) var triggerType: TriggerType,
    var modifiers: MutableMap<String, @Serializable(NumericStatFormulaSerializer::class) NumericStatFormula>,
) {
    private inline val ability get() = MMOItems.plugin.skills.getSkill(abilityName)!!

    companion object {
        fun RandomAbilityData.toDataModel() = RandomAbilityDataModel(ability.name, triggerType, getPrivateField("modifiers"))
    }

    fun toItemData() = RandomAbilityData(ability, triggerType).also {
        modifiers.forEach { (path, value) ->
            it.setModifier(path, value)
        }
    }
}
