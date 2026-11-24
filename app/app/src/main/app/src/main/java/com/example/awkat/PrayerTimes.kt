package com.example.awkat

import android.content.Context
import org.json.JSONObject
import java.util.Calendar

data class PrayerTime(
    val fajr: String,
    val sunrise: String,
    val dhuhr: String,
    val asr: String,
    val maghrib: String,
    val isha: String
)

object PrayerTimesLoader {
    fun getTodayPrayerTimes(context: Context): PrayerTime? {
        return try {
            val jsonString = context.assets.open("prayer_times.json").bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            val calendar = Calendar.getInstance()
            val currentMonth = (calendar.get(Calendar.MONTH) + 1).toString()
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH).toString()

            val monthData = jsonObject.getJSONObject(currentMonth)
            val dayData = monthData.getJSONObject(currentDay)

            PrayerTime(
                fajr = dayData.getString("fajr"),
                sunrise = dayData.getString("sunrise"),
                dhuhr = dayData.getString("dhuhr"),
                asr = dayData.getString("asr"),
                maghrib = dayData.getString("maghrib"),
                isha = dayData.getString("isha")
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
