package org.moxqeon.bukkit.util

import com.comphenix.protocol.wrappers.EnumWrappers
import com.comphenix.protocol.wrappers.EnumWrappers.Hand
import org.bukkit.block.BlockFace
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.EquipmentSlot.HAND
import org.bukkit.inventory.EquipmentSlot.OFF_HAND

object WrapperUtil {
    @JvmStatic
    fun toBukkit(direction: EnumWrappers.Direction): BlockFace {
        return BlockFace.valueOf(direction.name)
    }

    @JvmStatic
    fun toWrapper(blockFace: BlockFace): EnumWrappers.Direction {
        return EnumWrappers.Direction.valueOf(blockFace.name)
    }

    @JvmStatic
    fun toBukkit(hand: Hand): EquipmentSlot {
        return when (hand) {
            Hand.MAIN_HAND -> HAND
            Hand.OFF_HAND -> OFF_HAND
        }
    }

    @JvmStatic
    fun toWrapper(slot: EquipmentSlot): Hand {
        return when (slot) {
            HAND -> Hand.MAIN_HAND
            OFF_HAND -> Hand.OFF_HAND
            else -> throw IllegalArgumentException()
        }
    }
}