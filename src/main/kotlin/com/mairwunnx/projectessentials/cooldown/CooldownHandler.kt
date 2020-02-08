package com.mairwunnx.projectessentials.cooldown

import com.mairwunnx.projectessentials.cooldown.EntryPoint.Companion.hasPermission
import com.mairwunnx.projectessentials.core.extensions.commandName
import com.mairwunnx.projectessentials.core.extensions.player
import net.minecraftforge.event.CommandEvent

internal object CooldownHandler {
    internal fun handle(event: CommandEvent) {
        val commandName = event.commandName
        val commandSender = event.player
        val commandSenderNickName = commandSender!!.name.string
        try {
            if (
                !CooldownConfig.config.ignoredPlayers.contains(commandSenderNickName) &&
                !hasPermission(commandSender, "ess.cooldown.bypass", 3)
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
