package com.example.awkat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFFF0F4F8)) {
                    PrayerTimesScreen()
                }
            }
        }
    }
}

@Composable
fun PrayerTimesScreen() {
    val context = LocalContext.current
    val todayTimes = remember { PrayerTimesLoader.getTodayPrayerTimes(context) }
    val locale = Locale("ar")
    val dateFormat = SimpleDateFormat("EEEE، d MMMM yyyy", locale)
    val todayDate = dateFormat.format(Date())

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "أوقات الصلاة", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = todayDate, fontSize = 18.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))

        if (todayTimes != null) {
            PrayerCard("الفجر", todayTimes.fajr)
            PrayerCard("الشروق", todayTimes.sunrise)
            PrayerCard("الظهر", todayTimes.dhuhr)
            PrayerCard("العصر", todayTimes.asr)
            PrayerCard("المغرب", todayTimes.maghrib)
            PrayerCard("العشاء", todayTimes.isha)
        } else {
            Text(text = "لم يتم العثور على بيانات لهذا اليوم.", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun PrayerCard(name: String, time: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF333333))
            Text(text = time, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E3A8A))
        }
    }
}
