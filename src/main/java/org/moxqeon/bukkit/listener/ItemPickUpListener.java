package org.moxqeon.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.jetbrains.annotations.NotNull;
import org.moxqeon.bukkit.MoxBukkit;

public class ItemPickUpListener implements Listener {
    private MoxBukkit moxBukkit;

    public ItemPickUpListener(@NotNull MoxBukkit moxBukkit) {
        this.moxBukkit = moxBukkit;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onItemPickUp(EntityPickupItemEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            Bukkit.getScheduler().runTaskAsynchronously(moxBukkit, player::updateInventory);
        }
    }
}
