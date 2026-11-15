package org.moxqeon.bukkit.fix

import org.bukkit.entity.Player
import org.moxqeon.bukkit.MoxBukkit

/**
 * 判断玩家当前是否处于地面。
 *
 * 通过 [OnGroundFix] 记录的着地状态进行判断。
 *
 * @return 玩家是否处于地面
 */
fun Player.isOnGroundFix() = MoxBukkit.instance().onGroundFix.isOnGround(this)