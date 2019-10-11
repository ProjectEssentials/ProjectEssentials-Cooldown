package com.mairwunnx.projectessentialscooldown

import com.mairwunnx.projectessentialscore.EssBase
import com.mairwunnx.projectessentialscore.extensions.commandName
import com.mairwunnx.projectessentialscore.extensions.player
import com.mairwunnx.projectessentialspermissions.permissions.PermissionsAPI
import net.minecraft.entity.player.ServerPlayerEntity
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.CommandEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent
import org.apache.logging.log4j.LogManager

@Suppress("unused")
@Mod("project_essentials_cooldown")
internal class EntryPoint : EssBase() {
    private val logger = LogManager.getLogger()

    init {
        modInstance = this
        modVersion = "1.14.4-1.0.0.0"
        logBaseInfo()
        validateForgeVersion()
        logger.debug("Register event bus for $modName mod ...")
        MinecraftForge.EVENT_BUS.register(this)
        logger.info("Loading $modName permissions data ...")
        CooldownConfig.load()
    }

    internal companion object {
        internal lateinit var modInstance: EntryPoint
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    internal fun onPlayerCommand(event: CommandEvent) {
        if (event.parseResults.context.source.entity is ServerPlayerEntity) {
            val commandName = event.commandName
            val commandSender = event.player
            val commandSenderNickName = commandSender.name.string
            try {
                if (
                    !CooldownConfig.config.ignoredPlayers.contains(commandSenderNickName) &&
                    !PermissionsAPI.hasPermission(commandSenderNickName, "ess.cooldown.bypass")
                ) {
                    event.isCanceled = CooldownUtils.processCooldownOfCommand(
                        commandName, commandSenderNickName, event
                    )
                    return
                }
            } catch (_: KotlinNullPointerException) {
                CooldownAPI.addCooldown(commandSenderNickName, commandName)
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    @SubscribeEvent
    internal fun onServerStopping(it: FMLServerStoppingEvent) {
        logger.info("Shutting down $modName mod ...")
        logger.info("    - Saving cooldown configuration ...")
        CooldownConfig.save()
    }
}
