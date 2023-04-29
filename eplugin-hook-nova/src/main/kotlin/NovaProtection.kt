package top.e404.eplugin.hook.nova

import xyz.xenondevs.nova.api.Nova
import xyz.xenondevs.nova.api.protection.ProtectionIntegration

interface NovaProtection : ProtectionIntegration {
    fun register() {
        Nova.registerProtectionIntegration(this)
    }
}
