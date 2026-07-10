package org.moxqeon.bukkit.module.protocol

import com.comphenix.protocol.events.PacketContainer
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.ItemFrame
import org.bukkit.util.Consumer

class PacketItemFrame(location: Location, consumer: Consumer<ItemFrame?>?) :
    StaticPacketEntity(ItemFrame::class.java, location, consumer) {
    @get:JvmName("blockFace")
    @set:JvmName("blockFace")
    var blockFace: BlockFace = handle.facing

    override fun generateSpawnPacket(): PacketContainer? {
        super.generateSpawnPacket()
        when (blockFace) {
            BlockFace.SOUTH -> spawnPacket.integers.write(4, 0).write(5, 0)
            BlockFace.NORTH -> spawnPacket.integers.write(4, 0).write(5, 128)
            BlockFace.EAST -> spawnPacket.integers.write(4, 0).write(5, 192)
            BlockFace.WEST -> spawnPacket.integers.write(4, 0).write(5, 64)
            BlockFace.UP -> spawnPacket.integers.write(4, -64).write(5, 0)
            BlockFace.DOWN -> spawnPacket.integers.write(4, 64).write(5, 0)
            else -> {}
        }
        return spawnPacket
    }
}