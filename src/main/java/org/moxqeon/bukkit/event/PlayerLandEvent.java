package org.moxqeon.bukkit.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public final class PlayerLandEvent extends PlayerEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    private final Location from;
    @NotNull
    private final Location to;


    public PlayerLandEvent(@NotNull Player player, @NotNull Location from, @NotNull Location to) {
        super(player);
        this.from = from;
        this.to = to;
    }

    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public Location getFrom() {
        return from;
    }

    @NotNull
    public Location getTo() {
        return to;
    }
}