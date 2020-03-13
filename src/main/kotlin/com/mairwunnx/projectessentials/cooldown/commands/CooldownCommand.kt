package com.mairwunnx.projectessentials.cooldown.commands

import com.mairwunnx.projectessentials.cooldown.CooldownConfig
import com.mairwunnx.projectessentials.cooldown.EntryPoint
import com.mairwunnx.projectessentials.cooldown.EntryPoint.Companion.hasPermission
import com.mairwunnx.projectessentials.cooldown.sendMessage
import com.mairwunnx.projectessentials.core.extensions.isPlayerSender
import com.mairwunnx.projectessentials.core.helpers.throwPermissionLevel
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import org.apache.logging.log4j.LogManager

object CooldownCommand {
    private val logger = LogManager.getLogger()

    fun register(
        dispatcher: CommandDispatcher<CommandSource>
    ) {
        dispatcher.register(
            literal<CommandSource>("cooldown").executes {
                return@executes versionExecute(it)
            }.then(Commands.literal("reload").executes {
                return@executes reloadExecute(it)
            }).then(Commands.literal("save").executes {
                return@executes saveExecute(it)
            }).then(Commands.literal("version").executes {
                return@executes versionExecute(it)
            })
        )
    }

    private fun versionExecute(c: CommandContext<CommandSource>): Int {
        var isServerSender = false
        val commandSender = c.source
        val commandSenderPlayer = if (c.isPlayerSender()) {
            c.source.asPlayer()
        } else {
            isServerSender = true
            null
        }

        if (isServerSender ||
            hasPermission(commandSenderPlayer, "ess.cooldown") ||
            hasPermission(commandSenderPlayer, "ess.cooldown.version")
        ) {
            if (isServerSender) {
                logger.info("        ${EntryPoint.modInstance.modName}")
                logger.info("Version: ${EntryPoint.modInstance.modVersion}")
                logger.info("Maintainer: ${EntryPoint.modInstance.modMaintainer}")
                logger.info("Target Forge version: ${EntryPoint.modInstance.modTargetForge}")
                logger.info("Target Minecraft version: ${EntryPoint.modInstance.modTargetMC}")
                logger.info("Source code: ${EntryPoint.modInstance.modSources}")
                logger.info("Telegram chat: ${EntryPoint.modInstance.modTelegram}")
                logger.info("CurseForge: ${EntryPoint.modInstance.modCurseForge}")
            } else {
                sendMessage(
                    commandSender,
                    "version.success",
                    EntryPoint.modInstance.modName,
                    EntryPoint.modInstance.modVersion,
                    EntryPoint.modInstance.modMaintainer,
                    EntryPoint.modInstance.modTargetForge,
                    EntryPoint.modInstance.modTargetMC,
                    EntryPoint.modInstance.modSources,
                    EntryPoint.modInstance.modTelegram,
                    EntryPoint.modInstance.modCurseForge
                )
            }
            return 0
        } else {
            throwPermissionLevel(commandSenderPlayer!!.name.string, "cooldown")
            sendMessage(commandSender, "version.restricted")
            return 0
        }
    }

    private fun reloadExecute(c: CommandContext<CommandSource>): Int {
        var isServerSender = false
        val commandSender = c.source
        val commandSenderPlayer = if (c.isPlayerSender()) {
            c.source.asPlayer()
        } else {
            isServerSender = true
            null
        }

        if (isServerSender ||
            hasPermission(commandSenderPlayer, "ess.cooldown.reload")
        ) {
            CooldownConfig.load()
            if (isServerSender) {
                logger.info("Successfully reloaded Cooldown configuration")
            } else {
                sendMessage(commandSender, "reload.success")
            }
            return 0
        } else {
            throwPermissionLevel(commandSenderPlayer!!.name.string, "cooldown reload")
            sendMessage(commandSender, "reload.restricted")
            return 0
        }
    }

    private fun saveExecute(c: CommandContext<CommandSource>): Int {
        var isServerSender = false
        val commandSender = c.source
        val commandSenderPlayer = if (c.isPlayerSender()) {
            c.source.asPlayer()
        } else {
            isServerSender = true
            null
        }

        if (isServerSender ||
            hasPermission(commandSenderPlayer, "ess.cooldown.save")
        ) {
            CooldownConfig.save()
            if (isServerSender) {
                logger.info("Successfully saved Cooldown configuration")
            } else {
                sendMessage(commandSender, "save.success")
            }
            return 0
        } else {
            throwPermissionLevel(commandSenderPlayer!!.name.string, "cooldown save")
            sendMessage(commandSender, "save.restricted")
            return 0
        }
    }
}
