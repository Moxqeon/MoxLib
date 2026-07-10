package org.moxqeon.bukkit.util

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.EnumWrappers
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.util.Vector
import org.moxqeon.bukkit.MoxBukkit
import kotlin.math.max

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

    @JvmStatic
    fun sendFakeDamage(entity: Entity) {
        val pm = ProtocolLibrary.getProtocolManager()
        val damagePacket = PacketContainer(PacketType.Play.Server.ENTITY_STATUS)
        damagePacket.integers.write(0, entity.entityId)
        damagePacket.bytes.write(0, 2.toByte())
        pm.broadcastServerPacket(damagePacket)
        val location = entity.location
        val soundPacket = PacketContainer(PacketType.Play.Server.NAMED_SOUND_EFFECT)
        soundPacket.integers.write(0, location.x.toInt() * 8).write(1, location.y.toInt() * 8)
            .write(2, location.z.toInt() * 8)
        soundPacket.float.write(0, 1f).write(1, 1f)
        soundPacket.soundCategories.write(0, EnumWrappers.SoundCategory.PLAYERS)
        soundPacket.soundEffects.write(0, Sound.valueOf(String.format("ENTITY_%s_HURT", entity.type)))
        pm.broadcastServerPacket(soundPacket, entity, false)
    }

    @JvmStatic
    fun directDamage(entity: LivingEntity, damage: Double) {
        sendFakeDamage(entity)
        val runnable = Runnable { entity.health = max(0.0, entity.health - damage) }
        if (Bukkit.isPrimaryThread()) runnable.run()
        else Bukkit.getScheduler().runTask(MoxBukkit.instance(), runnable)
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