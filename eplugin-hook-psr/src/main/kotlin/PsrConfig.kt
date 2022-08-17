package top.e404.eplugin.hook.psr

import me.rothes.protocolstringreplacer.ProtocolStringReplacer
import me.rothes.protocolstringreplacer.PsrLocalization
import me.rothes.protocolstringreplacer.api.replacer.ReplacerConfig
import me.rothes.protocolstringreplacer.libs.org.apache.commons.collections.map.ListOrderedMap
import me.rothes.protocolstringreplacer.libs.org.neosearch.stringsearcher.StringSearcher
import me.rothes.protocolstringreplacer.replacer.ListenType
import me.rothes.protocolstringreplacer.replacer.MatchMode
import me.rothes.protocolstringreplacer.replacer.ReplaceMode
import org.bukkit.configuration.file.YamlConfiguration
import java.util.regex.Pattern

class ProtocolStringReplacerConfig(
    val name: String,
    val instance: ProtocolStringReplacer,
    val config: YamlConfiguration,
    private val priority: Int = 5
) : ReplacerConfig {
    private val types = config.getStringList("Options.Filter.Listen-Types").let { typeList ->
        val values = ListenType.values().toList()
        if (typeList.isEmpty()) values.toMutableList()
        else {
            typeList.mapNotNull { type ->
                values.firstOrNull { type.equals(it.name, true) }
            }.toMutableList()
        }
    }

    private val matchMode = config.getString("Options.Match-Mode", "Contain")!!.let { mode ->
        MatchMode.values().firstOrNull { it.name.equals(mode, true) }
    } ?: MatchMode.CONTAIN

    fun add() {
        if (this !in instance.replacerManager.replacerConfigList) instance.replacerManager.addReplacerConfig(this)
    }

    private val replaces = HashMap<ReplaceMode, ListOrderedMap>()
    private val blocks = HashMap<ReplaceMode, MutableList<Any>>()

    private val replacesStringSearcher = HashMap<ReplaceMode, StringSearcher<String>>()
    private val blocksStringSearcher = HashMap<ReplaceMode, StringSearcher<String>>()

    init {
        for (replaceMode in ReplaceMode.values()) {
            val mapList = config.getMapList("Replaces.${replaceMode.node}")
            val loMap = ListOrderedMap()
            replaces[replaceMode] = loMap
            if (matchMode == MatchMode.REGEX) for (map in mapList) {
                loMap[Pattern.compile(map["Original"] as String)] = map["Replacement"]
            } else for (map in mapList) {
                loMap[map["Original"]] = map["Replacement"]
            }
            val loadedBlockList = config.getStringList("Blocks." + replaceMode.node)
            val list = if (matchMode == MatchMode.REGEX) loadedBlockList.map { Pattern.compile(it) }
            else loadedBlockList
            blocks[replaceMode] = ArrayList(list)
            updateStringSearcher(replaceMode)
        }
    }

    private fun updateStringSearcher(replaceMode: ReplaceMode) {
        if (matchMode != MatchMode.CONTAIN) return
        val builder = StringSearcher.builder().ignoreOverlaps()
        for (obj in getReplaces(replaceMode).keys) {
            if (obj is String) builder.addSearchString(obj as String?)
            else ProtocolStringReplacer.error(
                PsrLocalization.getLocaledMessage(
                    "Console-Sender.Messages.Replacer-Config.Invalid-Original-Format",
                    relativePath, obj.toString()
                )
            )
        }
        replacesStringSearcher[replaceMode] = builder.build()
        val strings = arrayOfNulls<String>(blocks[replaceMode]!!.size)
        for (i in blocks[replaceMode]!!.indices) {
            strings[i] = blocks[replaceMode]!![i] as String
        }
        blocksStringSearcher[replaceMode] = StringSearcher.builder().ignoreOverlaps().addSearchStrings(*strings).build()
    }

    override fun isEdited() = false

    override fun isEnabled() = config.getBoolean("Options.Enable")
    override fun getPriority() = priority
    override fun getAuthor() = config.getString("Options.Author")
    override fun getVersion() = config.getString("Options.Version")

    override fun getListenTypeList() = types
    override fun getMatchMode() = matchMode

    override fun getReplaces(replaceMode: ReplaceMode) = replaces[replaceMode]!!
    override fun getBlocks(replaceMode: ReplaceMode) = blocks[replaceMode]!!.toMutableList()
    override fun getReplacesStringSearcher(replaceMode: ReplaceMode?) = replacesStringSearcher[replaceMode]!!
    override fun getBlocksStringSearcher(replaceMode: ReplaceMode?) = blocksStringSearcher[replaceMode]!!

    override fun getRelativePath() = "internal"
    override fun getFile() = null
    override fun getConfiguration() = null
    override fun saveConfig() {}
}