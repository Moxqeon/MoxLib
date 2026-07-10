package org.moxqeon.bukkit.util

import io.lumine.mythic.api.adapters.AbstractEntity
import io.lumine.mythic.api.adapters.AbstractLocation
import io.lumine.mythic.api.config.MythicConfig
import io.lumine.mythic.api.mobs.GenericCaster
import io.lumine.mythic.api.mobs.MythicMob
import io.lumine.mythic.api.skills.SkillMetadata
import io.lumine.mythic.bukkit.BukkitAdapter
import io.lumine.mythic.bukkit.MythicBukkit
import io.lumine.mythic.core.skills.MetaSkill
import io.lumine.mythic.core.skills.SkillMetadataImpl
import io.lumine.mythic.core.skills.SkillTriggers
import org.bukkit.block.Campfire
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import org.bukkit.plugin.Plugin
import org.moxqeon.bukkit.api.Constructor.namespacedKey

object MythicUtil {

    private const val NAMESPACE = "mythicmobs"

    @JvmStatic
    fun isMythicMob(entity: Entity, type: String): Boolean {
        val mobType = entity.persistentDataContainer
            .get(namespacedKey(NAMESPACE, "type"), PersistentDataType.STRING)
        return mobType != null && mobType == type
    }

    @JvmStatic
    fun getMobLevel(entity: Entity): Int {
        val mobLevel = entity.persistentDataContainer
            .get(namespacedKey(NAMESPACE, "level"), PersistentDataType.INTEGER)
        return mobLevel ?: 0
    }

    @JvmStatic
    fun cooldownInSeconds(player: Player, skillName: String): Float {
        val optionalSkill = MythicBukkit.inst().skillManager.getSkill(skillName)
        if (optionalSkill.isEmpty) return -1f
        val skill = optionalSkill.get() as MetaSkill
        return skill.getCooldown(GenericCaster(BukkitAdapter.adapt(player)))
    }

    @JvmStatic
    fun castMythicSkill(player: Player, skillName: String, skillPower: Float, skillMana: Int): Boolean {
        val abstractPlayer = BukkitAdapter.adapt(player)
        val caster = GenericCaster(abstractPlayer)
        val optionalSkill = MythicBukkit.inst().skillManager.getSkill(skillName)
        if (optionalSkill.isEmpty) return false
        val skill = optionalSkill.get()
        val skillMetadata: SkillMetadata = SkillMetadataImpl(
            SkillTriggers.API,
            caster,
            abstractPlayer,
            BukkitAdapter.adapt(player.location),
            HashSet<AbstractEntity?>(),
            HashSet<AbstractLocation?>(),
            skillPower
        )
        if (skill.isUsable(skillMetadata)) {
            skill.execute(skillMetadata)
            return true
        } else return false
    }

    @JvmStatic
    fun castMythicSkill(player: Player, skillName: String, skillPower: Float): Boolean {
        return castMythicSkill(player, skillName, skillPower, 0)
    }

    @JvmStatic
    fun castMythicSkill(player: Player, skillName: String, skillMana: Int): Boolean {
        return castMythicSkill(player, skillName, 1.0f, skillMana)
    }

    @JvmStatic
    fun castMythicSkill(player: Player, skillName: String): Boolean {
        return castMythicSkill(player, skillName, 1.0f, 0)
    }

    @JvmStatic
    fun getConfig(mobType: MythicMob, plugin: Plugin): MythicConfig? {
        return mobType.config.getNestedConfig(plugin.name)
    }
}