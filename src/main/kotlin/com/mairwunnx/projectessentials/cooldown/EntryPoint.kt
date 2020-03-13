package com.mairwunnx.projectessentials.cooldown

import com.mairwunnx.projectessentials.cooldown.commands.CooldownCommand
import com.mairwunnx.projectessentials.core.EssBase
import com.mairwunnx.projectessentials.permissions.permissions.PermissionsAPI
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.CommandEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.server.FMLServerStartingEvent
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent
import org.apache.logging.log4j.LogManager

@Suppress("unused")
@Mod("project_essentials_cooldown")
internal class EntryPoint : EssBase() {
    private val logger = LogManager.getLogger()

    init {
        modInstance = this
        modVersion = "1.14.4-1.0.4"
        logBaseInfo()
        validateForgeVersion()
        MinecraftForge.EVENT_BUS.register(this)
        CooldownConfig.load()
    }

    internal companion object {
        internal lateinit var modInstance: EntryPoint
        var permissionsInstalled: Boolean = false

        fun hasPermission(player: ServerPlayerEntity?, node: String, opLevel: Int = 4): Boolean =
            if (permissionsInstalled) {
                if (player == null) {
                    true
                } else {
                    PermissionsAPI.hasPermission(player.name.string, node)
                }
            } else {
                player?.server?.opPermissionLevel ?: 4 >= opLevel
            }
    }

    @SubscribeEvent
    fun onServerStarting(it: FMLServerStartingEvent) {
        loadAdditionalModules()
        CooldownCommand.register(it.server.commandManager.dispatcher)
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    internal fun onPlayerCommand(event: CommandEvent) {
        if (event.parseResults.context.source.entity is ServerPlayerEntity) {
            CooldownHandler.handle(event)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    @SubscribeEvent
    internal fun onServerStopping(it: FMLServerStoppingEvent) {
        CooldownConfig.save()
    }

    private fun loadAdditionalModules() {
        try {
            Class.forName(permissionAPIClassPath)
            permissionsInstalled = true
        } catch (_: ClassNotFoundException) {
            // ignored
        }
    }
}
