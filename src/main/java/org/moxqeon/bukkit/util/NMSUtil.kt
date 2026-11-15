package org.moxqeon.bukkit.util

//import com.comphenix.protocol.utility.MinecraftVersion
import org.bukkit.Bukkit

/**
 * NMS 工具类。
 *
 * 通过反射获取 CraftBukkit（OBC）与 NMS 相关类，辅助跨版本兼容处理。
 */
object NMSUtil {
    @JvmField
    val LEGACY: Boolean = true// !MinecraftVersion.CAVES_CLIFFS_1.atOrAbove()
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
        return if (!LEGACY) {
            Class.forName("net.minecraft.nbt.$className")
        } else {
            nmsClass(className)
        }
    }
}