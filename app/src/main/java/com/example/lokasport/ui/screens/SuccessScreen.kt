package com.example.lokasport.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lokasport.R
import com.example.lokasport.ui.theme.CreamBg
import com.example.lokasport.ui.theme.DarkOlive
import com.example.lokasport.ui.theme.SageGreen

@Composable
fun SuccessScreen(
    venueName: String,
    date: String,
    times: List<String>,
    onHome: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.my_checklist),
            contentDescription = "Success Checklist",
            modifier = Modifier.size(150.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text("Booking Successful!", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = SageGreen)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Your court is ready for you.", color = Color.Gray)

        Spacer(modifier = Modifier.height(40.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CreamBg),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Booking Details:", fontWeight = FontWeight.Bold, color = DarkOlive, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))

                SuccessDetailItem(label = "Venue", value = venueName)
                SuccessDetailItem(label = "Date", value = date)
                SuccessDetailItem(label = "Time", value = times.joinToString("\n"))
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onHome,
            modifier = Modifier.fillMaxWidth().height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkOlive),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Back to Home", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }
    }
}

@Composable
fun SuccessDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
    ) {
        Text(text = "$label:", color = Color.Gray, modifier = Modifier.width(80.dp))
        Text(text = value, fontWeight = FontWeight.Bold, color = DarkOlive)
    }
}