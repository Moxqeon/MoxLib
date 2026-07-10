package org.moxqeon.bukkit.util

import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector
import org.moxqeon.util.MathUtil
import kotlin.math.hypot

object VectorUtil {
    @JvmStatic
    fun fromAbs(abs: Double, yaw: Double, pitch: Double): Vector {
        val y = MathUtil.sinWithDegree(-pitch) * abs
        val horizontal = MathUtil.cosWithDegree(pitch) * abs
        val x = MathUtil.sinWithDegree(-yaw) * horizontal
        val z = MathUtil.cosWithDegree(-yaw) * horizontal
        return Vector(x, y, z)
    }

    @JvmStatic
    fun createVector(eulerAngle: EulerAngle, abs: Double): Vector {
        val multiple = abs / hypot(hypot(eulerAngle.x, eulerAngle.y), eulerAngle.z)
        return Vector(eulerAngle.x * multiple, eulerAngle.y * multiple, eulerAngle.z * multiple)
    }

    @JvmStatic
    fun fromArray(array: IntArray): Vector {
        return Vector(array[0], array[1], array[2])
    }

    @JvmStatic
    fun fromArray(array: DoubleArray): Vector {
        return Vector(array[0], array[1], array[2])
    }
}