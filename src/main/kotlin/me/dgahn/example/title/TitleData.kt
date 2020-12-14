package me.dgahn.example.title

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.time.ZonedDateTime

data class TitleData (
    val title: String,
    val createdTime: ZonedDateTime
)

fun ZonedDateTime.toFormatString(pattern: String = "yyyy-MM-dd HH:mm"): String {
    val zone = DateTimeZone.forID(this.zone.id)
    val dateTime = DateTime(this.toInstant().toEpochMilli(), zone)
    return DateTimeFormat.forPattern(pattern).print(dateTime)!!
}