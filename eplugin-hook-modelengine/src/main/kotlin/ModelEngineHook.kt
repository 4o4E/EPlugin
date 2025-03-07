package top.e404.eplugin.hook.modelengine

import com.ticxo.modelengine.api.ModelEngineAPI
import com.ticxo.modelengine.api.model.ActiveModel
import com.ticxo.modelengine.api.model.ModeledEntity
import org.bukkit.entity.Entity
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class ModelEngineHook(
    override val plugin: EPlugin,
) : EHook(plugin, "ModelEngine") {
    val pl inline get() = ModelEngineAPI.getAPI()!!

    fun getModeledEntity(entity: Entity): ModeledEntity = ModelEngineAPI.createModeledEntity(entity)
    fun getModel(model: String): ActiveModel = ModelEngineAPI.createActiveModel(model)
    fun applyModel(entity: Entity, model: String) {
        val modeledEntity = getModeledEntity(entity)
        val activeModel = getModel(model)
        modeledEntity.addModel(activeModel, true)
    }
    fun unapply(entity: Entity, model: String) {
        val modeledEntity = getModeledEntity(entity)
        modeledEntity.removeModel(model)
    }
}