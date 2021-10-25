package io.sireto.spring.common.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val dMYFormat = "dd MMMM yyyy"
    val dMYHMSFormat = "dd MMMM yyyy hh:mm"


    fun date(milliseconds: Long):  Date {
        calendar.timeInMillis = milliseconds
        return calendar.time
    }

    fun addMin(date: Date, min: Int): Date{
        calendar.time=date
        calendar.add(Calendar.MINUTE, min)
        return calendar.time
    }

    fun addDay(date: Date, day: Int): Date{
        calendar.time = date
        calendar.add(Calendar.DATE, day)
        return calendar.time
    }

    fun startOfDay(date: Date): Date{
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.time
    }

    fun endOfDay(date: Date): Date{
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.time
    }

    fun Date.formatToStr(pattern: String): String{
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(this)
    }
}