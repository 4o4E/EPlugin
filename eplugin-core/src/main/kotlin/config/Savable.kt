package top.e404.eplugin.config

import org.bukkit.command.CommandSender
import org.bukkit.scheduler.BukkitTask
import top.e404.eplugin.EPlugin

/**
 * 代表一个支持定时保存的对象
 */
interface Savable {
    val plugin: EPlugin
    var saveTask: BukkitTask?
    val saveDurationTick: Long
    fun save(sender: CommandSender?)


    /**
     * 计划一次保存, 在[saveDurationTick]刻后执行, 执行完成后移除task, 若在已有task则不处理, 否则创建task
     */
    fun scheduleSave() {
        if (saveTask != null) return
        saveTask = plugin.runTaskLater(saveDurationTick) {
            save(null)
            saveTask = null
        }
    }

    /**
     * 在需要立刻执行保存任务时调用
     */
    fun saveImmediately() {
        saveTask?.cancel()
        save(null)
    }
}
