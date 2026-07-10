package org.moxqeon.bukkit.util

import com.google.gson.JsonObject
import org.bukkit.Bukkit
import org.bukkit.inventory.PlayerInventory
import org.moxqeon.util.EncodeUtil

object InventoryUtil {
    @Suppress("DEPRECATION")
    private val unsafe = Bukkit.getUnsafe()

    @JvmStatic
    fun serializeAsJson(inv: PlayerInventory): JsonObject {
        val result = JsonObject()
        for (i in 0..<inv.size) {
            result.addProperty(i.toString(), EncodeUtil.bytesToHex(unsafe.serializeItem(inv.getItem(i))))
        }
        return result
    }
}