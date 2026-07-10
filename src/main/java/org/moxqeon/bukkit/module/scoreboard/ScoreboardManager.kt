package org.moxqeon.bukkit.module.scoreboard

import org.bukkit.entity.Player
import org.moxqeon.bukkit.MoxBukkit
import java.util.*
import java.util.concurrent.ConcurrentHashMap


class ScoreboardManager(private val moxBukkit: MoxBukkit) {
    private val displayedScoreboards: MutableMap<UUID?, Scoreboard?> = ConcurrentHashMap<UUID?, Scoreboard?>()

    fun addBoard(scoreboard: Scoreboard) {
        this.displayedScoreboards[scoreboard.player().getUniqueId()] = scoreboard
        if (scoreboard.isHidden) scoreboard.show()
    }

    fun removeBoard(scoreboard: Scoreboard) {
        this.displayedScoreboards.remove(scoreboard.player().getUniqueId())
        if (!scoreboard.isHidden) scoreboard.hide()
    }

    fun getBoard(player: Player): Scoreboard? {
        return this.displayedScoreboards.get(key = player.uniqueId)
    }

    fun getOrCreateBoard(player: Player): Scoreboard {
        val scoreboard = getBoard(player)
        return scoreboard ?: Scoreboard(this.moxBukkit, player)
    }
}
