package ch.heigvd.iict.daa.template.converter

import androidx.room.TypeConverter
import java.util.*

/**
 * Convertisseur pour Room pour gérer le type `Calendar`.
 */
class Date {

    /**
     * Convertit un long en objet Calendar.
     */
    @TypeConverter
    fun toCalendar(dateLong: Long): Calendar =
        Calendar.getInstance().apply { time = Date(dateLong) }

    /**
     * Convertit un objet Calendar en long.
     */
    @TypeConverter
    fun fromCalendar(date: Calendar): Long = date.time.time
}
