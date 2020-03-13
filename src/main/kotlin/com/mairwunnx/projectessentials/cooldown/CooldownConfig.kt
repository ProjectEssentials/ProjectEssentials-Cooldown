package com.mairwunnx.projectessentials.cooldown

import com.mairwunnx.projectessentials.cooldown.models.CooldownModel
import com.mairwunnx.projectessentials.core.helpers.MOD_CONFIG_FOLDER
import com.mairwunnx.projectessentials.core.helpers.jsonInstance
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.FileNotFoundException

internal object CooldownConfig {
    private val logger = LogManager.getLogger()
    internal var config = CooldownModel()
    private val COOLDOWNS_CONFIG = MOD_CONFIG_FOLDER + File.separator + "cooldowns.json"

    internal fun load() {
        logger.info("Loading command cooldown configuration")
        try {
            val configRaw = File(COOLDOWNS_CONFIG).readText()
            config = jsonInstance.parse(CooldownModel.serializer(), configRaw)
            logger.info("Loaded cooldowns (${config.commandCooldowns.size})")
        } catch (_: FileNotFoundException) {
            logger.error("Configuration file ($COOLDOWNS_CONFIG) not found!")
            logger.warn("The default configuration will be used.")
        }
    }

    internal fun save() {
        File(MOD_CONFIG_FOLDER).mkdirs()
        logger.info("Saving command cooldowns configuration")
        val configRaw = jsonInstance.stringify(CooldownModel.serializer(), config)
        try {
            File(COOLDOWNS_CONFIG).writeText(configRaw)
        } catch (ex: SecurityException) {
            logger.error("An error occurred while saving cooldowns configuration", ex)
        }
    }
}
