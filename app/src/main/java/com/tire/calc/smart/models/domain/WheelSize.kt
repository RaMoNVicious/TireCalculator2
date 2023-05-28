package com.tire.calc.smart.models.domain

data class WheelSize(
    val tireWidth: Double,
    val tireHeight: Double,
    val rimWidth: Double,
    val rimHeight: Double,
    val rimEt: Double,
    val isOffroad: Boolean = false
) {
    fun toEntity() : String {
        return "${tireWidth}/${tireHeight} ${rimWidth}x${rimHeight} ET${rimEt}"
    }

    companion object {
        fun fromEntity(src: String) : WheelSize {
            // TODO: make this work
            return WheelSize(195.0, 70.0, 5.5, 14.0, 45.0)
        }
    }
}