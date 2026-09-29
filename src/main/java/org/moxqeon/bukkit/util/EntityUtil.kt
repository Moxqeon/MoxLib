package org.moxqeon.bukkit.util

import org.bukkit.entity.Entity
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.util.Vector

object EntityUtil {
    @JvmStatic
    fun horizontalVelocity(entity: Entity): Vector {
        val v = entity.velocity
        return Vector(v.getX(), 0.0, v.getZ())
    }

    @JvmStatic
    fun horizontalVelocity(entity: Entity, absVelocity: Double) {
        entity.velocity = VectorUtil.fromAbs(absVelocity, entity.location.yaw.toDouble(), 0.0)
    }

    @JvmStatic
    fun horizontalVelocity(entity: Entity, x: Double, z: Double) {
        entity.velocity = Vector(x, 0.0, z)
    }

    @Suppress("DEPRECATION")
    @JvmStatic
    fun setFinalDamage(event: EntityDamageEvent, finalDamage: Double) {
        for (modifier in EntityDamageEvent.DamageModifier.entries) {
            if (modifier != EntityDamageEvent.DamageModifier.BASE) event.setDamage(modifier, 0.0)
            else event.setDamage(modifier, finalDamage)
        }
    }
}