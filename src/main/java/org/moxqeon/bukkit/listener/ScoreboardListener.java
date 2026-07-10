package org.moxqeon.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.bukkit.MoxBukkit;

public final class ScoreboardListener implements Listener {
    private final MoxBukkit moxBukkit;

    public ScoreboardListener(@NotNull MoxBukkit moxBukkit) {
        this.moxBukkit = moxBukkit;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        moxBukkit.getScoreboardManager().addBoard(moxBukkit.getScoreboardManager().getOrCreateBoard(event.getPlayer()));
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        moxBukkit.getScoreboardManager().removeBoard(moxBukkit.getScoreboardManager().getOrCreateBoard(event.getPlayer()));
    }
}
