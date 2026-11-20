package org.moxqeon.bukkit.api.compound

import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.potion.PotionEffect
import org.moxqeon.api.compound.ParsableCompound

/**
 * 药水效果复合数据。
 *
 * 用于在字符串与 [org.bukkit.potion.PotionEffect] 之间进行序列化与反序列化，
 * 依次存储药水类型、持续时间、等级、是否环境以及是否显示粒子。
 */
class PotionCompound : ParsableCompound<PotionEffect> {
    constructor(potionEffect: PotionEffect) : super(
        potionEffect.type.key.toString(),
        potionEffect.duration.toString(),
        potionEffect.amplifier.toString(),
        potionEffect.isAmbient.toString(),
        potionEffect.hasParticles().toString()
    )

    @JvmOverloads
    constructor(compound: String, split: String = DEFAULT_SPLIT) : super(compound, split)

    override fun parse(): PotionEffect {
        return PotionEffect(
            Registry.POTION_EFFECT_TYPE.get(NamespacedKey.fromString(get(0))!!)!!,
            get(1).toInt(),
            get(2).toInt(),
            get(3).toBooleanStrict(),
            get(4).toBooleanStrict()
        )
    }
}