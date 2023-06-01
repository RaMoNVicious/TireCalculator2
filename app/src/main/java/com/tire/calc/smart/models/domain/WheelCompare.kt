package com.tire.calc.smart.models.domain

data class WheelCompare(
    val nameResId: Int,
    val valueReference: Double,
    val valueCandidate: Double,
) {
    fun getDifference() : Double {
        return valueCandidate - valueReference
    }
}