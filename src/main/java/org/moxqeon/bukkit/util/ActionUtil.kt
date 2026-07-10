package org.moxqeon.bukkit.util

import org.bukkit.event.block.Action

object ActionUtil {

    @JvmStatic
    fun isRightClick(action: Action): Boolean {
        return when (action) {
            Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK -> true
            else -> false
        }
    }

    @JvmStatic
    fun isLeftClick(action: Action): Boolean {
        return when (action) {
            Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_BLOCK -> true
            else -> false
        }
    }

    @JvmStatic
    fun isOnBlock(action: Action): Boolean {
        return when (action) {
            Action.RIGHT_CLICK_BLOCK, Action.LEFT_CLICK_BLOCK, Action.PHYSICAL -> true
            else -> false
        }
    }

    @JvmStatic
    fun isClickBlock(action: Action): Boolean {
        return when (action) {
            Action.RIGHT_CLICK_BLOCK, Action.LEFT_CLICK_BLOCK -> true
            else -> false
        }
    }

    @JvmStatic
    fun isInAir(action: Action): Boolean {
        return when (action) {
            Action.RIGHT_CLICK_AIR, Action.LEFT_CLICK_AIR -> true
            else -> false
        }
    }
}