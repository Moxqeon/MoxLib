package org.moxqeon.bukkit.module.damage

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.moxqeon.MoxUtil

/**
 * 伤害管理器。
 *
 * 监听实体伤害事件，对低于阈值的极小伤害进行统一处理。
 */
class DamageManager: Listener {
    companion object {
        const val DAMAGE_THRESHOLD: Double = 0.000000000000000000000000000000000000000000001401298464324818
    }

    @EventHandler
    private fun onEntityDamage(event: EntityDamageEvent) {
//        MoxUtil.info(event.damage)
//        MoxUtil.info(DAMAGE_THRESHOLD)
        if (event.damage < DAMAGE_THRESHOLD)
            Bukkit.broadcast(Component.text("Test"))
    }
}