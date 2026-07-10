package org.moxqeon.bukkit.util

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.moxqeon.bukkit.util.ItemUtil.editMeta
import org.moxqeon.module.reflect.Reflect
import java.util.function.Consumer

object ItemUtil {
    @JvmStatic
    fun isPotion(material: Material) = material == Material.POTION || material == Material.SPLASH_POTION
            || material == Material.LINGERING_POTION

    @JvmStatic
    fun <T : ItemMeta> editMeta(itemStack: ItemStack, tClass: Class<T>, consumer: Consumer<T>): T {
        val meta: T = Reflect.cast(itemStack.itemMeta, tClass)
        consumer.accept(meta)
        itemStack.itemMeta = meta
        return meta
    }
}

@JvmSynthetic
fun Material.isPotion() = ItemUtil.isPotion(this)

@JvmSynthetic
fun <T : ItemMeta> ItemStack.editMeta(tClass: Class<T>, consumer: Consumer<T>) = editMeta(this, tClass, consumer)
