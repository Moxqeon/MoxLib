package org.moxqeon.bukkit.api

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.moxqeon.api.compound.ParsableCompound

/**
 * 药水效果复合数据。
 *
 * 用于在字符串与 [PotionEffect] 之间进行序列化与反序列化，
 * 依次存储药水类型、持续时间、等级、是否环境以及是否显示粒子。
 */
class PotionCompound : ParsableCompound<PotionEffect> {
    constructor(potionEffect: PotionEffect) : super(
        potionEffect.type.name,
        potionEffect.duration.toString(),
        potionEffect.amplifier.toString(),
        potionEffect.isAmbient.toString(),
        potionEffect.hasParticles().toString()
    )

    @JvmOverloads
    constructor(compound: String, split: String = DEFAULT_SPLIT) : super(compound, split)

    override fun parse(): PotionEffect {
        return PotionEffect(
            PotionEffectType.getByName(get(0))!!,
            get(1).toInt(),
            get(2).toInt(),
            get(3).toBooleanStrict(),
            get(4).toBooleanStrict()
        )
    }
}