package org.moxqeon.bukkit.api.compound

import org.bukkit.Bukkit
import org.bukkit.Location
import org.moxqeon.api.compound.ParsableCompound

/**
 * 坐标复合数据。
 *
 * 用于在字符串与 [Location] 之间进行序列化与反序列化，
 * 依次存储世界名称、X、Y、Z、俯仰角（pitch）以及偏航角（yaw）。
 */
class LocationCompound : ParsableCompound<Location> {
    constructor(location: Location):super(
        location.world.name,
        location.x.toString(),
        location.y.toString(),
        location.z.toString(),
        location.pitch.toString(),
        location.yaw.toString()
    )

    @JvmOverloads
    constructor(compound: String, split: String = DEFAULT_SPLIT) : super(compound, split)
    override fun parse(): Location {


        return if (size < 6) Location(
            Bukkit.getWorld(get(0)), get(1).toDouble(), get(2).toDouble(), get(3).toDouble()
        ) else Location(
            Bukkit.getWorld(get(0)),
            get(1).toDouble(),
            get(2).toDouble(),
            get(3).toDouble(),
            get(4).toFloat(),
            get(5).toFloat()
        )
    }

}