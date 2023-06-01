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
        return String.format(
            "%.0f/%.0f %.1fJx%.0f ET%.0f",
            tireWidth,
            tireHeight,
            rimWidth,
            rimHeight,
            rimEt
        )
    }

    companion object {

        fun defaultReference()= fromEntity("215/40 7.0Jx17 ET49")

        fun defaultCandidate() = fromEntity("195/60 6.0Jx15 ET50")

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