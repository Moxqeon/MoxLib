package org.moxqeon.bukkit.fix

import org.bukkit.entity.Player
import org.moxqeon.bukkit.MoxBukkit

fun Player.isOnGroundFix() = MoxBukkit.instance().getOnGroundFix().isOnGround(this)