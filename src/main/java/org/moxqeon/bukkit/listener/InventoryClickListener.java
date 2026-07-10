package org.moxqeon.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.bukkit.MoxBukkit;

import java.util.Objects;

public final class InventoryClickListener implements Listener {
    private final MoxBukkit moxBukkit;

    public InventoryClickListener(@NotNull MoxBukkit moxBukkit) {
        this.moxBukkit = moxBukkit;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onInventoryClick(InventoryClickEvent event) {
        HumanEntity humanEntity = event.getView().getPlayer();
        if (humanEntity instanceof Player) {
            Player player = (Player) humanEntity;
            if (!player.isOp())
                Bukkit.getScheduler().runTaskAsynchronously(moxBukkit, player::updateInventory);
        }
    }
}