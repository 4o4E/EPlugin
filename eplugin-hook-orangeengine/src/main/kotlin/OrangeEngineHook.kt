package top.e404.eplugin.hook.oe

import ltd.icecold.orangeengine.api.OrangeEngineAPI
import ltd.icecold.orangeengine.api.data.model.ModelType
import ltd.icecold.orangeengine.api.model.ModelManager
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook
import java.util.*

@Suppress("UNUSED")
open class OrangeEngineHook(
    override val plugin: EPlugin,
) : EHook(plugin, "OrangeEngine") {
    val modelManager: ModelManager
        get() = OrangeEngineAPI.getModelManager()!!

    fun setModel(
        id: UUID,
        model: String,
        type: ModelType = ModelType.BLOCKBENCH,
    ) = modelManager.addModelEntity(id, model, type)!!
}