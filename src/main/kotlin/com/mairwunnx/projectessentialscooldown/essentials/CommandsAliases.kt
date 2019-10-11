package com.mairwunnx.projectessentialscooldown.essentials

import com.mairwunnx.projectessentialscore.extensions.empty
import net.minecraft.util.Tuple

/**
 * Command aliases basically for working with
 * command cooldowns.
 *
 * **Note:** register your command aliases here,
 * it needed for true handling command cooldowns.
 * @since 1.14.4-1.0.0.0
 */
object CommandsAliases {
    /**
     * This stores all aliases, just add your alias here.
     *
     * Where `String` - command for aliases.
     *
     * Where `MutableList<String>` - aliases of command.
     */
    val aliases: HashMap<String, MutableList<String>> = hashMapOf()

    internal fun searchForAliasesForCooldown(
        command: String,
        cooldownsMap: HashMap<String, Int>
    ): Tuple<Int?, String> {
        return try {
            aliases.keys.forEach { baseCommand ->
                val aliasesOfCommands = aliases[baseCommand]
                if (aliasesOfCommands != null &&
                    aliasesOfCommands.contains(command)
                ) {
                    Tuple(cooldownsMap[baseCommand]!!, baseCommand)
                }
            }
            Tuple(0, String.empty)
        } catch (ex: KotlinNullPointerException) {
            Tuple(0, String.empty)
        }
    }
}
