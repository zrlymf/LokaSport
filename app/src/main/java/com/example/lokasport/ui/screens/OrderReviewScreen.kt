package com.example.lokasport.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lokasport.ui.theme.CreamBg
import com.example.lokasport.ui.theme.DarkOlive
import com.example.lokasport.ui.theme.SageGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderReviewScreen(
    venue: Venue,
    date: String,
    times: List<String>,
    onConfirm: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Review Booking", fontWeight = FontWeight.Bold, color = DarkOlive) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkOlive) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CreamBg)
            )
        },
        containerColor = CreamBg
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(24.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Venue Details", fontWeight = FontWeight.Bold, color = SageGreen, fontSize = 18.sp)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = CreamBg)

                    ReviewItem("Venue", venue.name)
                    ReviewItem("Date", date)
                    ReviewItem("Time", times.joinToString("\n"))
                    ReviewItem("Duration", "${times.size} Hour(s)")
                    ReviewItem("Location", venue.location)

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = CreamBg)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total Payment", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("Rp ${venue.pricePerHour * times.size}", fontWeight = FontWeight.Bold, color = DarkOlive, fontSize = 18.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkOlive),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Confirm & Pay Now", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
@Composable
fun ReviewItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            color = Color.Gray,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.SemiBold,
            color = DarkOlive,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Left
        )
    }
}