package top.e404.eplugin.hook.libsdisguises

import com.comphenix.protocol.wrappers.WrappedGameProfile
import me.libraryaddict.disguise.DisguiseAPI
import me.libraryaddict.disguise.disguisetypes.Disguise
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import top.e404.eplugin.EPlugin
import top.e404.eplugin.hook.EHook

@Suppress("UNUSED", "NOTHING_TO_INLINE")
open class LibsDisguisesHook(
    override val plugin: EPlugin,
) : EHook(plugin, "LibsDisguises") {
    inline fun getEntityAttachmentId() = DisguiseAPI.getEntityAttachmentId()
    inline fun addCustomDisguise(disguiseName: String, disguiseInfo: String) = DisguiseAPI.addCustomDisguise(disguiseName, disguiseInfo)
    inline fun addGameProfile(profileName: String, gameProfile: WrappedGameProfile) = DisguiseAPI.addGameProfile(profileName, gameProfile)
    inline fun getRawCustomDisguise(disguiseName: String): String = DisguiseAPI.getRawCustomDisguise(disguiseName)
    inline fun getCustomDisguise(disguiseName: String): Disguise = DisguiseAPI.getCustomDisguise(disguiseName)
    inline fun removeCustomDisguise(disguiseName: String) = DisguiseAPI.removeCustomDisguise(disguiseName)
    inline fun constructDisguise(entity: Entity): Disguise = DisguiseAPI.constructDisguise(entity)
    inline fun constructDisguise(entity: Entity, doEquipment: Boolean, displayExtraAnimations: Boolean): Disguise = DisguiseAPI.constructDisguise(entity, doEquipment, displayExtraAnimations)
    inline fun disguiseEntity(entity: Entity, disguise: Disguise) = DisguiseAPI.disguiseEntity(entity, disguise)
    inline fun disguiseEntity(commandSender: CommandSender, entity: Entity, disguise: Disguise) = DisguiseAPI.disguiseEntity(commandSender, entity, disguise)
    inline fun disguiseIgnorePlayers(entity: Entity, disguise: Disguise, playersToNotSeeDisguise: Collection<String>) = DisguiseAPI.disguiseIgnorePlayers(entity, disguise, playersToNotSeeDisguise)
    inline fun disguiseNextEntity(disguise: Disguise) = DisguiseAPI.disguiseNextEntity(disguise)
    inline fun disguiseToAll(entity: Entity, disguise: Disguise) = DisguiseAPI.disguiseToAll(entity, disguise)
    inline fun disguiseToPlayers(entity: Entity, disguise: Disguise, playersToViewDisguise: Collection<String>) = DisguiseAPI.disguiseToPlayers(entity, disguise, playersToViewDisguise)
    inline fun getDisguise(entity: Entity): Disguise = DisguiseAPI.getDisguise(entity)
    inline fun parseToString(disguise: Disguise, outputSkin: Boolean): String = DisguiseAPI.parseToString(disguise, outputSkin)
    inline fun parseToString(disguise: Disguise): String = DisguiseAPI.parseToString(disguise)
    inline fun getDisguise(observer: Player, disguised: Entity): Disguise = DisguiseAPI.getDisguise(observer, disguised)
    inline fun getDisguises(disguised: Entity): Array<Disguise> = DisguiseAPI.getDisguises(disguised)
    inline fun getSelfDisguiseId(): Int = DisguiseAPI.getSelfDisguiseId()
    inline fun isDisguised(disguised: Entity): Boolean = DisguiseAPI.isDisguised(disguised)
    inline fun isDisguised(observer: Player, disguised: Entity): Boolean = DisguiseAPI.isDisguised(observer, disguised)
    inline fun isDisguiseInUse(disguise: Disguise): Boolean = DisguiseAPI.isDisguiseInUse(disguise)
    inline fun isSelfDisguised(player: Player): Boolean = DisguiseAPI.isSelfDisguised(player)
    inline fun isViewSelfToggled(entity: Entity): Boolean = DisguiseAPI.isViewSelfToggled(entity)
    inline fun isNotifyBarShown(entity: Entity): Boolean = DisguiseAPI.isNotifyBarShown(entity)
    inline fun hasSelfDisguisePreference(entity: Entity): Boolean = DisguiseAPI.hasSelfDisguisePreference(entity)
    inline fun hasActionBarPreference(entity: Entity): Boolean = DisguiseAPI.hasActionBarPreference(entity)
    inline fun undisguiseToAll(entity: Entity) = DisguiseAPI.undisguiseToAll(entity)
    inline fun undisguiseToAll(sender: CommandSender, entity: Entity) = DisguiseAPI.undisguiseToAll(sender, entity)
    inline fun setViewDisguiseToggled(entity: Entity, canSeeSelfDisguises: Boolean) = DisguiseAPI.setViewDisguiseToggled(entity, canSeeSelfDisguises)
    inline fun setActionBarShown(player: Player, isShown: Boolean) = DisguiseAPI.setActionBarShown(player, isShown)
}
