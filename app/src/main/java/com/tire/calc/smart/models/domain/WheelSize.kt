package com.tire.calc.smart.models.domain

import java.io.Serializable

data class WheelSize(
    val tireWidth: Double,
    val tireHeight: Double,
    val rimWidth: Double,
    val rimHeight: Double,
    val rimEt: Double,
    val isOffroad: Boolean = false
) : Serializable {
    fun toEntity(): String {
        return "${tireWidth}/${tireHeight} ${rimWidth}x${rimHeight} ET${rimEt}"
    }

    companion object {
        fun fromEntity(src: String): WheelSize {
            val parts = src.split(" ", "/","x")
            return WheelSize(
                parts[0].toDouble(),
                parts[1].toDouble(),
                parts[2].let { it.substring(0, it.length - 2) }.toDouble(),
                parts[3].toDouble(),
                parts[4].substring(2).toDouble()
            )
        }
    }
}