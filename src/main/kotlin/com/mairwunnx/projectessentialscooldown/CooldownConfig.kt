package com.mairwunnx.projectessentialscooldown

import com.mairwunnx.projectessentialscooldown.models.CooldownModel
import com.mairwunnx.projectessentialscore.helpers.MOD_CONFIG_FOLDER
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.FileNotFoundException

@UseExperimental(UnstableDefault::class)
internal object CooldownConfig {
    private val logger = LogManager.getLogger()
    internal var config = CooldownModel()
    private val COOLDOWNS_CONFIG = MOD_CONFIG_FOLDER + File.separator + "cooldowns.json"

    internal fun load() {
        logger.info("    - loading command cooldown configuration ...")
        try {
            val configRaw = File(COOLDOWNS_CONFIG).readText()
            config = Json.parse(CooldownModel.serializer(), configRaw)
            logger.info("    - loaded cooldowns (${config.commandCooldowns.size})")
            config.commandCooldowns.forEach {
                try {
                    val command = it.split("=")[0]
                    val cooldown = it.split("=")[1]
                    logger.info("        - command: ${command}; cooldown: $cooldown")
                } catch (_: IndexOutOfBoundsException) {
                    logger.error("Cooldown $it loaded with error, please check your configuration.")
                }
            }
        } catch (_: FileNotFoundException) {
            logger.error("Configuration file ($COOLDOWNS_CONFIG) not found!")
            logger.warn("The default configuration will be used.")
        }
    }

    internal fun save() {
        logger.debug("        - setup json configuration for parsing ...")
        val json = Json(
            JsonConfiguration(
                encodeDefaults = true,
                strictMode = true,
                unquoted = false,
                allowStructuredMapKeys = true,
                prettyPrint = true,
                useArrayPolymorphism = false
            )
        )
        createConfigDirs()
        logger.info("        - saving command cooldowns configuration ...")
        val configRaw = json.stringify(CooldownModel.serializer(), config)
        try {
            File(COOLDOWNS_CONFIG).writeText(configRaw)
        } catch (ex: SecurityException) {
            logger.error("An error occurred while saving cooldowns configuration", ex)
        }
    }

    private fun createConfigDirs() {
        logger.info("        - creating directories for configurations ...")
        val configDirectory = File(MOD_CONFIG_FOLDER)
        if (!configDirectory.exists()) configDirectory.mkdirs()
    }
}
