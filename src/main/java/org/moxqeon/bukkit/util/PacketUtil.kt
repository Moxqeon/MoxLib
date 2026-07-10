package org.moxqeon.bukkit.util

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.BlockPosition
import org.bukkit.Bukkit
import org.bukkit.block.Block

object PacketUtil {
    @JvmStatic
    fun sendBlockDamage(block: Block, damage: Int) {
        if (damage < -1 || damage > 9) return
        val packet = PacketContainer(PacketType.Play.Server.BLOCK_BREAK_ANIMATION)
        val location = block.getLocation()
        val blockPosition = BlockPosition(location.toVector())
        packet.getBlockPositionModifier().write(0, blockPosition)
        packet.getIntegers().write(0, blockPosition.hashCode()).write(1, damage)
        val protocolManager = ProtocolLibrary.getProtocolManager()
        for (player in location.getNearbyPlayers((Bukkit.getViewDistance() * 16).toDouble())) {
            protocolManager.sendServerPacket(player, packet)
        }
    }
}