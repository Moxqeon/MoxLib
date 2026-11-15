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

/**
 * 物品拾取监听器。
 * <p>
 * 在玩家拾取物品后异步刷新其背包。
 */
public class ItemPickUpListener implements Listener {
    private final MoxBukkit moxBukkit;

    public ItemPickUpListener(@NotNull MoxBukkit moxBukkit) {
        this.moxBukkit = moxBukkit;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onItemPickUp(EntityPickupItemEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity instanceof Player player) {
            Bukkit.getScheduler().runTaskAsynchronously(moxBukkit, player::updateInventory);
        }
    }
}