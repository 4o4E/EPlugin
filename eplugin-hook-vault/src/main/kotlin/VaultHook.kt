package top.e404.eplugin.hook.vault

import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED")
open class VaultHook(
    override val plugin: EPlugin,
) : EHook(plugin, "Vault") {
    val servicesManager inline get() = Bukkit.getServicesManager()
    val economy get() = servicesManager.getRegistration(Economy::class.java)?.provider
    val permission get() = servicesManager.getRegistration(Permission::class.java)?.provider
    val chat get() = servicesManager.getRegistration(Chat::class.java)?.provider
}