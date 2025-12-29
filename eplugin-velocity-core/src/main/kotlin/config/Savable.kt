package top.e404.eplugin.config

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.scheduler.ScheduledTask
import top.e404.eplugin.EPluginVelocity

/**
 * 代表一个支持定时保存的对象
 */
interface Savable {
    val plugin: EPluginVelocity
    var saveTask: ScheduledTask?
    val saveDurationMills: Long
    fun save(sender: CommandSource?)


    /**
     * 计划一次保存, 在[saveDurationMills]ms后执行, 执行完成后移除task, 若在已有task则不处理, 否则创建task
     */
    fun scheduleSave() {
        if (saveTask != null) return
        saveTask = plugin.runTaskLater(saveDurationMills) {
            save(null)
            saveTask = null
        }
    }

    /**
     * 在需要立刻执行保存任务时调用
     */
    fun saveImmediately(sender: CommandSource? = null) {
        saveTask?.run {
            cancel()
            saveTask = null
        }
        save(sender)
    }
}
