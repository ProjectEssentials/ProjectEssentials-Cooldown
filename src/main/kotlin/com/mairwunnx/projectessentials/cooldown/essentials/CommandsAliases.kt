package com.mairwunnx.projectessentials.cooldown.essentials

import com.mairwunnx.projectessentials.core.extensions.empty
import net.minecraft.util.Tuple
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Command aliases basically for working with
 * command cooldowns.
 *
 * **Note:** register your command aliases here,
 * it needed for true handling command cooldowns.
 * @since 1.14.4-1.0.0.0
 */
object CommandsAliases {
    private val logger: Logger = LogManager.getLogger()
    /**
     * This stores all aliases, just add your alias here.
     *
     * Where `String` - command for aliases.
     *
     * Where `MutableList<String>` - aliases of command.
     */
    val aliases: HashMap<String, MutableList<String>> = hashMapOf()

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    internal fun searchForAliasesForCooldown(
        command: String,
        cooldownsMap: HashMap<String, Int>
    ): Tuple<Int?, String> {
        aliases.keys.forEach { baseCommand ->
            val aliasesOfCommands = aliases[baseCommand]
            if (aliasesOfCommands != null &&
                command in aliasesOfCommands
            ) {
                logger.debug("Command: $baseCommand; Cooldown: ${cooldownsMap[baseCommand]}")
                return Tuple(cooldownsMap[baseCommand], baseCommand)
            }
        }
        return Tuple(null, String.empty)
    }
}
