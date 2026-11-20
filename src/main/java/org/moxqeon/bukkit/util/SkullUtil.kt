package org.moxqeon.bukkit.util

import com.destroystokyo.paper.profile.ProfileProperty
import org.bukkit.Bukkit
import org.bukkit.inventory.meta.SkullMeta
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * 头颅工具类。
 *
 * 用于通过纹理 URL 为头颅元数据设置皮肤。
 */
object SkullUtil {
    @JvmStatic
    fun setSkinURL(skullMeta: SkullMeta, url: String): SkullMeta {
        val base64 = Base64.getEncoder()
            .encodeToString("{\"textures\":{\"SKIN\":{\"url\":\"${url}\"}}}".toByteArray(StandardCharsets.UTF_8))
        val property = ProfileProperty("textures", base64)
        var playerProfile = skullMeta.playerProfile
        if (playerProfile == null) playerProfile = Bukkit.createProfile(UUID.randomUUID());
        else playerProfile.removeProperty("textures")
        playerProfile.setProperty(property)
        skullMeta.playerProfile = playerProfile
        return skullMeta
    }
}