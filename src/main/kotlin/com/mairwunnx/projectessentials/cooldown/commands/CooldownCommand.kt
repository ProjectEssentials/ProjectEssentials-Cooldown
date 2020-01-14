package com.mairwunnx.projectessentials.cooldown.commands

import com.mairwunnx.projectessentials.cooldown.CooldownConfig
import com.mairwunnx.projectessentials.cooldown.EntryPoint
import com.mairwunnx.projectessentials.core.extensions.isPlayerSender
import com.mairwunnx.projectessentials.core.extensions.sendMsg
import com.mairwunnx.projectessentials.core.helpers.PERMISSION_LEVEL
import com.mairwunnx.projectessentials.permissions.permissions.PermissionsAPI
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandSource
import net.minecraft.command.Commands
import org.apache.logging.log4j.LogManager

object CooldownCommand {
    private val logger = LogManager.getLogger()

    fun register(
        dispatcher: CommandDispatcher<CommandSource>
    ) {
        logger.info("    - register \"/essentials cooldown [...]\" command ...")

        dispatcher.register(
            LiteralArgumentBuilder.literal<CommandSource>("essentials").then(
                Commands.literal("cooldown").executes {
                    return@executes versionExecute(it)
                }.then(Commands.literal("reload").executes {
                    return@executes reloadExecute(it)
                }).then(Commands.literal("save").executes {
                    return@executes saveExecute(it)
                }).then(Commands.literal("version").executes {
                    return@executes versionExecute(it)
                })
            )
        )
    }

    private fun versionExecute(c: CommandContext<CommandSource>): Int {
        var isServerSender = false
        val commandSender = c.source
        val commandSenderNickName = if (c.isPlayerSender()) {
            c.source.asPlayer().name.string
        } else {
            isServerSender = true
            "server"
        }

        if (isServerSender ||
            PermissionsAPI.hasPermission(commandSenderNickName, "ess.cooldown") ||
            PermissionsAPI.hasPermission(commandSenderNickName, "ess.stuff") ||
            PermissionsAPI.hasPermission(commandSenderNickName, "ess.cooldown.version")
        ) {
            if (isServerSender) {
                logger.info("        ${EntryPoint.modInstance.modName}")
                logger.info("Version: ${EntryPoint.modInstance.modVersion}")
                logger.info("Maintainer: ${EntryPoint.modInstance.modMaintainer}")
                logger.info("Target Forge version: ${EntryPoint.modInstance.modTargetForge}")
                logger.info("Target Minecraft version: ${EntryPoint.modInstance.modTargetMC}")
                logger.info("Source code: ${EntryPoint.modInstance.modSources}")
                logger.info("Telegram chat: ${EntryPoint.modInstance.modTelegram}")
            } else {
                sendMsg(
                    "cooldown",
                    commandSender,
                    "version.success",
                    EntryPoint.modInstance.modName,
                    EntryPoint.modInstance.modVersion,
                    EntryPoint.modInstance.modMaintainer,
                    EntryPoint.modInstance.modTargetForge,
                    EntryPoint.modInstance.modTargetMC,
                    EntryPoint.modInstance.modSources,
                    EntryPoint.modInstance.modTelegram
                )
            }
            return 0
        } else {
            logger.warn(
                PERMISSION_LEVEL
                    .replace("%0", commandSenderNickName)
                    .replace("%1", "/essentials cooldown about")
            )
            sendMsg("cooldown", commandSender, "version.restricted")
            return 0
        }
    }

    private fun reloadExecute(c: CommandContext<CommandSource>): Int {
        var isServerSender = false
        val commandSender = c.source
        val commandSenderNickName = if (c.isPlayerSender()) {
            c.source.asPlayer().name.string
        } else {
            isServerSender = true
            "server"
        }

        if (isServerSender ||
            PermissionsAPI.hasPermission(commandSenderNickName, "ess.cooldown.reload") ||
            PermissionsAPI.hasPermission(commandSenderNickName, "ess.stuff")
        ) {
            CooldownConfig.load()
            if (isServerSender) {
                logger.info("Successfully reloaded Project Essentials Cooldown configuration")
            } else {
                sendMsg("cooldown", commandSender, "reload.success")
            }
            return 0
        } else {
            logger.warn(
                PERMISSION_LEVEL
                    .replace("%0", commandSenderNickName)
                    .replace("%1", "/essentials cooldown reload")
            )
            sendMsg("cooldown", commandSender, "reload.restricted")
            return 0
        }
    }

    private fun saveExecute(c: CommandContext<CommandSource>): Int {
        var isServerSender = false
        val commandSender = c.source
        val commandSenderNickName = if (c.isPlayerSender()) {
            c.source.asPlayer().name.string
        } else {
            isServerSender = true
            "server"
        }

        if (isServerSender ||
            PermissionsAPI.hasPermission(commandSenderNickName, "ess.cooldown.save") ||
            PermissionsAPI.hasPermission(commandSenderNickName, "ess.stuff")
        ) {
            CooldownConfig.save()
            if (isServerSender) {
                logger.info("Successfully saved Project Essentials configuration")
            } else {
                sendMsg("cooldown", commandSender, "save.success")
            }
            return 0
        } else {
            logger.warn(
                PERMISSION_LEVEL
                    .replace("%0", commandSenderNickName)
                    .replace("%1", "/essentials cooldown save")
            )
            sendMsg("cooldown", commandSender, "save.restricted")
            return 0
        }
    }
}
