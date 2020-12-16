package me.dgahn.example.summary

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import java.time.ZonedDateTime

data class SummaryData (
    val summary: List<Summary>
)

data class Summary(
    val index: Int,
    val name: String,
    val triple: List<Triple<ZonedDateTime, String, Int>>,
    val description: String
)

fun ZonedDateTime.toFormatString(pattern: String = "yyyy-MM-dd HH:mm"): String {
    val zone = DateTimeZone.forID(this.zone.id)
    val dateTime = DateTime(this.toInstant().toEpochMilli(), zone)
    return DateTimeFormat.forPattern(pattern).print(dateTime)!!
}