package io.sireto.spring.common.utils

import java.util.*
import kotlin.math.pow

object RandomGenUtils {
    private val random = kotlin.random.Random

    fun randomNumber(digits: Int = 8) = random.nextDouble(10.0.pow(digits - 1), 10.0.pow(digits)).toLong()

    fun randomDouble(): Double = random.nextDouble()

    fun randomString(length: Int = 10): String {
        val allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        val randomString = StringBuilder()
        val rnd = Random()
        while (randomString.length < length) {
            val index = (rnd.nextFloat() * allowedCharacters.length).toInt()
            randomString.append(allowedCharacters[index])
        }
        return randomString.toString()
    }
}