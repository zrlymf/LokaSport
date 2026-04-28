package com.example.lokasport.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lokasport.ui.theme.CreamBg
import com.example.lokasport.ui.theme.DarkOlive
import com.example.lokasport.ui.theme.SageGreen
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    venue: Venue,
    onNavigateToReview: (String, List<String>) -> Unit,
    onBack: () -> Unit
) {
    val currentDate = remember { LocalDate.now() }
    val dates = remember { (0..30).map { currentDate.plusDays(it.toLong()) } }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("EEE, dd MMM", Locale.ENGLISH) }

    var selectedDate by remember { mutableStateOf(currentDate) }

    var selectedTimes by remember { mutableStateOf(setOf<String>()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select Date & Time", fontWeight = FontWeight.Bold, color = DarkOlive) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkOlive) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CreamBg)
            )
        },
        containerColor = CreamBg
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text(venue.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = DarkOlive)
            Spacer(modifier = Modifier.height(24.dp))

            Text("Select Date", fontWeight = FontWeight.Bold, color = DarkOlive, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(dates) { date ->
                    val isSelected = selectedDate == date
                    val isToday = date == currentDate

                    val dateText = date.format(dateFormatter)

                    Box(
                        modifier = Modifier
                            .width(110.dp)
                            .height(65.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) DarkOlive else Color.White)
                            .border(1.dp, if (isSelected) DarkOlive else Color.LightGray, RoundedCornerShape(12.dp))
                            .clickable {
                                selectedDate = date
                                selectedTimes = emptySet()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (isToday) {
                                Text(
                                    text = "Today",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) SageGreen else SageGreen
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                            }
                            Text(
                                text = dateText,
                                color = if (isSelected) CreamBg else DarkOlive,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                item {
                    Text("Available Time", fontWeight = FontWeight.Bold, color = SageGreen, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                val availableSlots = venue.slots.filter { it.isAvailable }
                if (availableSlots.isEmpty()) {
                    item { Text("No available slots for this date.", color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp)) }
                } else {
                    items(availableSlots) { slot ->
                        val isSelected = selectedTimes.contains(slot.time)
                        TimeSlotItem(
                            time = slot.time,
                            isAvailable = true,
                            isSelected = isSelected,
                            onClick = {
                                selectedTimes = if (isSelected) {
                                    selectedTimes - slot.time
                                } else {
                                    selectedTimes + slot.time
                                }
                            }
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                item {
                    Text("Booked / Unavailable", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(venue.slots.filter { !it.isAvailable }) { slot ->
                    TimeSlotItem(time = slot.time, isAvailable = false, isSelected = false) {}
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (selectedTimes.isNotEmpty()) {
                        val dateString = selectedDate.format(dateFormatter)
                        onNavigateToReview(dateString, selectedTimes.toList().sorted())
                    }
                },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                enabled = selectedTimes.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkOlive,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (selectedTimes.isEmpty()) "Select time to continue" else "Review Booking (${selectedTimes.size} Hours)",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun TimeSlotItem(
    time: String,
    isAvailable: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(enabled = isAvailable) { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = when {
                isSelected -> SageGreen.copy(alpha = 0.2f)
                isAvailable -> Color.White
                else -> Color.LightGray.copy(alpha = 0.3f)
            }
        ),
        border = if (isSelected) BorderStroke(2.dp, SageGreen) else null,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isAvailable && !isSelected) 1.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                tint = if (isAvailable) DarkOlive else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = time,
                color = if (isAvailable) DarkOlive else Color.Gray,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
            Spacer(modifier = Modifier.weight(1f))
            if (!isAvailable) {
                Text("Booked", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}