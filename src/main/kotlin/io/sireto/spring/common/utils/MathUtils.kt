package io.sireto.spring.common.utils

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

object MathUtils {
    fun BigDecimal.truncDecimal(deciDigits: Int = 0 ): BigDecimal {
        return this.setScale(deciDigits, RoundingMode.HALF_EVEN)
    }

    fun bytesToKB(bytesSize: Long) = bytesSize * 10.0.pow(-3)

    fun bytesToMB(bytesSize: Long) = bytesSize * 10.0.pow(-6)

    fun BigDecimal.toAda(): BigDecimal{
        return (this * BigDecimal(10.0.pow(-6))).truncDecimal()
    }
}