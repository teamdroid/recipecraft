package ru.teamdroid.recipecraft.data.base

interface Mapper<T1, T2> {
    fun map(value: T1): T2
    fun reverseMap(value: T2): T1
}