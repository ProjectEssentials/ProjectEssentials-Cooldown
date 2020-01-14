package com.mairwunnx.projectessentials.cooldown

import com.google.common.collect.HashBasedTable
import com.mairwunnx.projectessentials.cooldown.essentials.CommandsAliases
import java.time.Duration
import java.time.ZonedDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.toKotlinDuration

/**
 * Base class for working with command cooldowns.
 * @since 1.14.4-1.0.0.0
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
object CooldownAPI {
    /**
     * Default command cooldown.
     *
     * **Note:** it value be using as callback, e.g
     * if cooldown value is invalid.
     * @since 1.14.4-1.0.0.0
     */
    const val DEFAULT_COOLDOWN = 5
    /**
     * Default cooldown literals.
     * It literals what replaced cooldowns for
     * any command in game, i.e if you set in config
     * `all=10`, then all commands be with cooldown 10 sec.
     *
     * **Note:** it not override existing cooldown
     * for some command.
     * @since 1.14.4-1.0.0.0
     */
    val defaultCooldownLiterals = listOf("*", "all", "any")
    private val cooldownTable = HashBasedTable.create<String, String, ZonedDateTime>()

    /**
     * Just add new or replace cooldown for
     * target player and for target command.
     * @param nickname nickname of target player.
     * @param command target command for cooldown.
     * @since 1.14.4-1.0.0.0
     */
    fun addCooldown(nickname: String, command: String) {
        removeCooldown(nickname, command)
        CommandsAliases.aliases.keys.forEach { baseCommand ->
            val aliasesOfCommands = CommandsAliases.aliases[baseCommand]
            if (aliasesOfCommands != null &&
                aliasesOfCommands.contains(command)
            ) {
                aliasesOfCommands.forEach {
                    cooldownTable.put(nickname, it, ZonedDateTime.now())
                }
            }
        }
        cooldownTable.put(nickname, command, ZonedDateTime.now())
    }

    /**
     * Just remove existing cooldown from target
     * player and target command.
     * @param nickname nickname of target player.
     * @param command target command for cooldown.
     * @since 1.14.4-1.0.0.0
     */
    fun removeCooldown(nickname: String, command: String) {
        if (cooldownTable.get(nickname, command) == null) return
        cooldownTable.remove(nickname, command)
    }

    /**
     * @param nickname nickname of target player.
     * @param command target command for cooldown.
     * @param minSecondsDuration minimal seconds diff
     * between old command execute and new command execute.
     * @return true if command cooldown expired, else return false.
     * @since 1.14.4-1.0.0.0
     */
    fun getCooldownIsExpired(
        nickname: String,
        command: String,
        minSecondsDuration: Int
    ): Boolean = getCooldownTimeLeft(nickname, command) >= minSecondsDuration

    /**
     * @param nickname nickname of target player.
     * @param command target command for cooldown.
     * @return left cooldown time in seconds for command.
     * @since 1.14.4-1.0.0.0
     */
    @UseExperimental(ExperimentalTime::class)
    fun getCooldownTimeLeft(nickname: String, command: String): Double {
        if (cooldownTable.get(nickname, command) != null) {
            val commandExecutionTime = cooldownTable.get(nickname, command)
            val dateTimeNow: ZonedDateTime = ZonedDateTime.now()
            val duration = Duration.between(commandExecutionTime, dateTimeNow)
            return duration.toKotlinDuration().inSeconds
        }
        throw KotlinNullPointerException(
            "An error occurred while getting cooldown date time by nickname ($nickname) with command ($command)"
        )
    }
}
