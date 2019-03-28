package ru.teamdroid.recipecraft.data.base

import java.util.*

abstract class Mapper<T1, T2> {

    abstract fun map(value: T1): T2

    abstract fun reverseMap(value: T2): T1

    fun map(values: List<T1>): List<T2> {
        val returnValues = ArrayList<T2>(values.size)
        for (value in values) {
            returnValues.add(map(value))
        }
        return returnValues
    }

    fun reverseMap(values: List<T2>): List<T1> {
        val returnValues = ArrayList<T1>(values.size)
        for (value in values) {
            returnValues.add(reverseMap(value))
        }
        return returnValues
    }
}