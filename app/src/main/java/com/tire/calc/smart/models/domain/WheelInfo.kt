package com.tire.calc.smart.models.domain

import com.tire.calc.smart.R
import com.tire.calc.smart.app.Constants

class WheelInfo(
    val wheelSize: WheelSize,
    val isImperial: Boolean = false
) {

    private fun tireSideHeight(): Double = when (wheelSize.isOffroad) {
        true -> 0.5 * (wheelSize.tireHeight - wheelSize.rimHeight) * Constants.INCH_TO_MM
        else -> 0.01 * wheelSize.tireWidth * wheelSize.tireHeight
    }

    private fun wheelHeight(): Double = when (wheelSize.isOffroad) {
        true -> wheelSize.tireHeight * Constants.INCH_TO_MM
        else -> wheelSize.rimHeight * Constants.INCH_TO_MM + 2 * 0.01 * wheelSize.tireWidth * wheelSize.tireHeight
    }

    fun getRimWidth(): Double = getDimension(wheelSize.rimWidth * Constants.INCH_TO_MM)

    fun getRimDiameter(): Double = getDimension(wheelSize.rimHeight * Constants.INCH_TO_MM)

    fun getTireWidth(): Double = getDimension(
        when (wheelSize.isOffroad) {
            true -> wheelSize.tireWidth * Constants.INCH_TO_MM
            else -> wheelSize.tireWidth
        }
    )

    fun getTireSideHeight(): Double = getDimension(tireSideHeight())

    fun getWheelHeight(): Double = getDimension(wheelHeight())

    fun getWheelCircumference(): Double = getDimension(wheelHeight() * Math.PI)

    fun getSpeedAt(): Double {
        return 0.0
    }

    fun getRevsPer(): Double = when (isImperial) {
        true -> Constants.MI_IN_MM
        else -> Constants.KM_IN_MM
    } / wheelHeight() * Math.PI

    fun getDistanceToArch(): Int {
        return R.string.app_name
    }

    fun getDistanceToSuspension(): Int {
        return R.string.app_name
    }

    fun getGroundClearance(): Int {
        return R.string.app_name
    }

    fun getTireLabel(): String = when (wheelSize.isOffroad) {
        true -> String.format(
            "%1$.1f\"x%2$.1f\" R%3$.0f",
            wheelSize.tireWidth,
            wheelSize.tireHeight,
            wheelSize.rimHeight
        )
        else -> String.format(
            "%1$.0f/%2$.0f R%3$.0f",
            wheelSize.tireWidth,
            wheelSize.tireHeight,
            wheelSize.rimHeight
        )
    }

    fun getRimLabel(): String = String.format(
        "%1$.0fx%2$.1f ET%3$.0f",
        wheelSize.rimHeight,
        wheelSize.rimWidth,
        wheelSize.rimEt
    )

    private fun getDimension(value: Double): Double = when (isImperial) {
        true -> value / Constants.INCH_TO_MM
        else -> value
    }
}