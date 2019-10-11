package com.mairwunnx.projectessentialscooldown

import com.mairwunnx.projectessentialscooldown.essentials.CommandsAliases
import com.mairwunnx.projectessentialscore.extensions.empty
import com.mairwunnx.projectessentialscore.extensions.sendMsg
import com.mairwunnx.projectessentialscore.extensions.source
import com.mairwunnx.projectessentialscore.helpers.COOLDOWN_NOT_EXPIRED
import net.minecraftforge.event.CommandEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

internal object CooldownUtils {
    private val logger: Logger = LogManager.getLogger()

    internal fun processCooldownsCommandsList(
        cooldowns: List<String>
    ): HashMap<String, Int> {
        val hashMap = hashMapOf<String, Int>()
        cooldowns.forEach {
            try {
                val command = it.split("=")[0].toLowerCase()
                val cooldown = it.split("=")[1].toInt()
                hashMap[command] = cooldown
            } catch (_: IndexOutOfBoundsException) {
                logger.error(
                    "Cooldown $it processing done with error, please check your configuration."
                )
            }
        }
        return hashMap
    }

    internal fun processCooldownOfCommand(
        commandName: String,
        commandSenderNickName: String,
        commandEvent: CommandEvent
    ): Boolean {
        var originalCommand = String.empty
        val cooldownsMap = processCooldownsCommandsList(
            CooldownConfig.config.commandCooldowns
        )
        val command = when {
            originalCommand != String.empty -> originalCommand
            else -> commandName
        }
        val commandCooldown: Int = cooldownsMap[commandName]
            ?: CommandsAliases.searchForAliasesForCooldown(
                commandName, cooldownsMap
            ).let {
                originalCommand = it.b
                return@let it.a
            }
            ?: cooldownsMap[CooldownAPI.defaultCooldownLiterals.iterator().next()]
            ?: CooldownAPI.DEFAULT_COOLDOWN

        if (!CooldownAPI.getCooldownIsExpired(commandSenderNickName, command, commandCooldown)) {
            logger.warn(
                COOLDOWN_NOT_EXPIRED
                    .replace("%0", commandSenderNickName)
                    .replace("%1", command)
            )
            sendMsg(
                "cooldown",
                commandEvent.source,
                "not_expired",
                commandCooldown.minus(
                    CooldownAPI.getCooldownTimeLeft(commandSenderNickName, command)
                ).toInt().toString()
            )
            return true
        } else {
            CooldownAPI.addCooldown(commandSenderNickName, command)
            return false
        }
    }
}
