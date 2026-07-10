package org.moxqeon.bukkit.util

import com.comphenix.protocol.utility.MinecraftVersion
import org.bukkit.Bukkit

object NMSUtil {
    @JvmField
    val LEGACY: Boolean = !MinecraftVersion.CAVES_CLIFFS_1.atOrAbove()
    private val SERVER_CLASS_NAME: String = Bukkit.getServer().javaClass.getName()

    @JvmStatic
    fun obcClass(className: String): Class<*> =
        Class.forName(SERVER_CLASS_NAME.replaceFirst("CraftServer".toRegex(), className))

    @JvmStatic
    fun nmsClass(className: String): Class<*> {
        return Class.forName(
            SERVER_CLASS_NAME.replaceFirst(
                "org.bukkit.craftbukkit".toRegex(), "net.minecraft.server"
            ).replaceFirst("CraftServer".toRegex(), className)
        )
    }

    @JvmStatic
    fun nbtClass(className: String): Class<*> {
        return if (MinecraftVersion.getCurrentVersion().isAtLeast(MinecraftVersion.CAVES_CLIFFS_1)) {
            Class.forName("net.minecraft.nbt.$className")
        } else {
            nmsClass(className)
        }
    }
}