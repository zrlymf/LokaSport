package com.example.lokasport.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lokasport.ui.theme.CreamBg
import com.example.lokasport.ui.theme.DarkOlive
import com.example.lokasport.ui.theme.SageGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingOrdersScreen(
    orderHistory: List<BookingOrder>,
    onMenuClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Booking Orders", fontWeight = FontWeight.Bold, color = DarkOlive) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CreamBg)
            )
        },
        containerColor = CreamBg,
        bottomBar = {
            LokaSportFooter(currentMenu = "Orders", onMenuClick = onMenuClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            if (orderHistory.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("No active bookings yet.", color = Color.Gray, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { onMenuClick("Home") },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkOlive)
                        ) {
                            Text("Find a Court")
                        }
                    }
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(orderHistory.reversed()) { order ->
                        OrderCardItem(order)
                    }
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
        }
    }
}

@Composable
fun OrderCardItem(order: BookingOrder) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SageGreen.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = order.status,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = DarkOlive,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text("Rp ${order.price}", fontWeight = FontWeight.Bold, color = DarkOlive)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = order.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = order.venueName, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = DarkOlive)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = order.venueLocation, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "${order.date} • ${order.time}", color = SageGreen, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
            }
        }
    }
}
