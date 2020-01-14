package com.mairwunnx.projectessentials.cooldown

import com.mairwunnx.projectessentials.core.extensions.commandName
import com.mairwunnx.projectessentials.core.extensions.player
import com.mairwunnx.projectessentials.permissions.permissions.PermissionsAPI
import net.minecraftforge.event.CommandEvent

internal object CooldownHandler {
    internal fun handle(event: CommandEvent) {
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
