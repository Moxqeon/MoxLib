package org.moxqeon.bukkit.util

import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

object BlockUtil {
    @JvmStatic
    fun center(block: Block): Location {
        return block.boundingBox.center.toLocation(block.world)
    }

    @JvmStatic
    fun surfaceCenter(block: Block, blockFace: BlockFace): Location {
        val boundingBox = block.boundingBox
        when (blockFace) {
            BlockFace.UP -> return Location(
                block.world,
                boundingBox.centerX,
                boundingBox.maxY,
                boundingBox.centerZ
            )

            BlockFace.DOWN -> return Location(
                block.world,
                boundingBox.centerX,
                boundingBox.minY,
                boundingBox.centerZ
            )

            BlockFace.EAST -> return Location(
                block.world,
                boundingBox.maxX,
                boundingBox.centerY,
                boundingBox.centerZ
            )

            BlockFace.WEST -> return Location(
                block.world,
                boundingBox.minX,
                boundingBox.centerY,
                boundingBox.centerZ
            )

            BlockFace.SOUTH -> return Location(
                block.world,
                boundingBox.centerX,
                boundingBox.centerY,
                boundingBox.maxZ
            )

            BlockFace.NORTH -> return Location(
                block.world,
                boundingBox.centerX,
                boundingBox.centerY,
                boundingBox.minZ
            )

            else -> throw IllegalArgumentException()
        }
    }

    @JvmStatic
    fun locationString(block: Block): String {
        val loc = block.location
        return "${loc.blockX}_${loc.blockY}_${loc.blockZ}"
    }
}

@JvmSynthetic
fun Block.center() = BlockUtil.center(this)

@JvmSynthetic
fun Block.surfaceCenter(blockFace: BlockFace) = BlockUtil.surfaceCenter(this, blockFace)

@JvmSynthetic
fun Block.locationString() = BlockUtil.locationString(this)

