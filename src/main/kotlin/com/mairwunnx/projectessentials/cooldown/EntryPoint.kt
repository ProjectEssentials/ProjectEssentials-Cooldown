package com.mairwunnx.projectessentials.cooldown

import com.mairwunnx.projectessentials.cooldown.commands.CooldownCommand
import com.mairwunnx.projectessentials.core.EssBase
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
        modVersion = "1.14.4-1.0.1.0"
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

    @SubscribeEvent
    fun onServerStarting(it: FMLServerStartingEvent) {
        logger.info("$modName starting mod loading ...")
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
        logger.info("Shutting down $modName mod ...")
        logger.info("    - Saving cooldown configuration ...")
        CooldownConfig.save()
    }
}
