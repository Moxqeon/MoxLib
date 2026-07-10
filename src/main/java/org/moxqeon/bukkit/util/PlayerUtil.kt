package org.moxqeon.bukkit.util

import org.bukkit.FluidCollisionMode
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

object PlayerUtil {
    @JvmStatic
    fun headLocation(player: Player): Location {
        return player.location.clone().add(0.0, 1.62, 0.0)
    }

    @JvmStatic
    fun targetBlock(player: Player, considerFluid: Boolean): Block? {
        val result =
            player.rayTraceBlocks(4.5, if (considerFluid) FluidCollisionMode.ALWAYS else FluidCollisionMode.NEVER)
        return result?.hitBlock
    }

    @JvmStatic
    fun targetBlock(player: Player): Block? {
        return targetBlock(player, false)
    }

    @JvmStatic
    fun targetBlockFace(player: Player): BlockFace? {
        val result = player.rayTraceBlocks(4.5)
        return result?.hitBlockFace
    }

    @JvmStatic
    fun sendFakeDamage(player: Player) {
    }
}