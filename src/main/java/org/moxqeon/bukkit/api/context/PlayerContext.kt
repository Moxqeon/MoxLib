package org.moxqeon.bukkit.api.context

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class PlayerContext(plugin: Plugin, key: String, player: Player) : LanguageContext(plugin, key) {
    private val uuid = player.uniqueId
    override fun get(s: String): Any? {
        if (s.equals("uuid", ignoreCase = true)) return uuid
        return null
    }
}