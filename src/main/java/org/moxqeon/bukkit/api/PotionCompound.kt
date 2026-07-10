package org.moxqeon.bukkit.api

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.moxqeon.api.compound.ParsableCompound
import java.lang.Boolean
import kotlin.String

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
            Boolean.getBoolean(get(3)),
            Boolean.getBoolean(get(4))
        )
    }
}