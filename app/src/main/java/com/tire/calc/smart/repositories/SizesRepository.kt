package com.tire.calc.smart.repositories

class SizesRepository {
    val tireWidth: List<Int> = (135..355 step 10).toList()

    val tireHeight: List<Int> = (25..85 step 5).toList()

    val rimWidth: List<Float> = (45..150 step 5).toList().map { it.toFloat() / 10F }

    val rimHeight: List<Int> = (12..26).toList()

    val rimEt: List<Int> = (-55..55).toList()
}