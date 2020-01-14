package com.mairwunnx.projectessentials.cooldown.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CooldownModel(
    @SerialName("CommandCooldowns")
    val commandCooldowns: List<String> = mutableListOf(),
    @SerialName("IgnoredPlayers")
    val ignoredPlayers: List<String> = mutableListOf()
)
