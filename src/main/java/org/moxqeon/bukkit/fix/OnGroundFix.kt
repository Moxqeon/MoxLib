package org.moxqeon.bukkit.fix

import com.github.retrooper.packetevents.event.PacketListener
import com.github.retrooper.packetevents.event.PacketReceiveEvent
import com.github.retrooper.packetevents.protocol.packettype.PacketType
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerPosition
import io.github.retrooper.packetevents.util.SpigotConversionUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.moxqeon.bukkit.MoxBukkit
import org.moxqeon.bukkit.api.Constructor
import org.moxqeon.bukkit.event.PlayerLandEvent
import org.moxqeon.bukkit.module.persistent.PersistentBoolean

class OnGroundFix(moxBukkit: MoxBukkit) : PacketListener {
    companion object {
        private val ON_GROUND_KEY = Constructor.namespacedKey("moxlib", "on_ground")
    }

    init {
        Bukkit.getScheduler().runTaskTimer(moxBukkit, Runnable {
            if (eventQueue.isEmpty()) return@Runnable
            for (event in eventQueue) Bukkit.getPluginManager().callEvent(event)
            eventQueue.clear()
        }, 0, 1)
    }

    private val eventQueue = mutableListOf<PlayerLandEvent>()

    override fun onPacketReceive(event: PacketReceiveEvent) {
        val user = event.user
        val player = Bukkit.getPlayer(user.uuid)!!
        if (event.packetType == PacketType.Play.Client.PLAYER_POSITION || event.packetType == PacketType.Play.Client.PLAYER_POSITION_AND_ROTATION) {
            val packet = WrapperPlayClientPlayerPosition(event)
            val onGround = packet.isOnGround
            val container = player.persistentDataContainer
            if (!container.has(ON_GROUND_KEY, PersistentBoolean.INSTANCE) || container.get(
                    ON_GROUND_KEY, PersistentBoolean.INSTANCE
                ) != onGround
            ) container.set(ON_GROUND_KEY, PersistentBoolean.INSTANCE, onGround)
            val from = player.location
            val to = SpigotConversionUtil.toBukkitLocation(player.world, packet.location)
            eventQueue.add(PlayerLandEvent(player, from, to))
        }
    }

    fun isOnGround(player: Player): Boolean {
        val container = player.persistentDataContainer
        return if (container.has(ON_GROUND_KEY, PersistentBoolean.INSTANCE)) container.get(
            ON_GROUND_KEY, PersistentBoolean.INSTANCE
        )!!
        else true
    }

}