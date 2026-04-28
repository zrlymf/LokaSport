package com.example.lokasport.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lokasport.ui.theme.CreamBg
import com.example.lokasport.ui.theme.DarkOlive
import com.example.lokasport.ui.theme.SageGreen

@Composable
fun VenueDetailScreen(
    venue: Venue,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onNavigateBack: () -> Unit,
    onBookNowClick: () -> Unit
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Maps", "Gallery", "Review")
    val context = LocalContext.current

    var zoomedImageRes by remember { mutableStateOf<Int?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            bottomBar = {
                Surface(shadowElevation = 16.dp, color = Color.White) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onToggleFavorite,
                            modifier = Modifier.background(CreamBg, RoundedCornerShape(12.dp))
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color.Red else DarkOlive
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = onBookNowClick,
                            modifier = Modifier.weight(1f).height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SageGreen),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Book Venue Now", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CreamBg)
                    .padding(paddingValues)
            ) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                        Image(
                            painter = painterResource(id = venue.imageRes),
                            contentDescription = venue.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        IconButton(
                            onClick = onNavigateBack,
                            modifier = Modifier
                                .statusBarsPadding()
                                .padding(16.dp)
                                .size(40.dp)
                                .background(Color.White.copy(alpha = 0.8f), CircleShape)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkOlive)
                        }
                    }
                }

                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = venue.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DarkOlive)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFFFBF00), modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${venue.rating} (${venue.reviews} reviews)", fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                }

                item {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,
                        containerColor = CreamBg,
                        contentColor = SageGreen
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Text(
                                        text = title,
                                        fontWeight = FontWeight.Bold,
                                        color = if (selectedTabIndex == index) DarkOlive else Color.Gray
                                    )
                                }
                            )
                        }
                    }
                }

                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        when (selectedTabIndex) {
                            0 -> {
                                Text("Location Details:", fontWeight = FontWeight.Bold, color = DarkOlive, fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(venue.location, color = Color.Gray, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {
                                        val uri = Uri.parse("geo:0,0?q=${Uri.encode(venue.name + ", " + venue.location)}")
                                        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                                        mapIntent.setPackage("com.google.android.apps.maps")
                                        context.startActivity(mapIntent)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = DarkOlive),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Icon(Icons.Default.LocationOn, contentDescription = "Map", tint = Color.White)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Open in Google Maps", color = Color.White)
                                }
                            }

                            1 -> {
                                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    items(venue.galleryImages) { imageRes ->
                                        Image(
                                            painter = painterResource(id = imageRes),
                                            contentDescription = "Gallery",
                                            modifier = Modifier
                                                .size(120.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .clickable { zoomedImageRes = imageRes },
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }

                            2 -> {
                                venue.reviewsList.forEach { review ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(16.dp))
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text("LokaSport User", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(review, fontSize = 14.sp, color = Color.Gray)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        zoomedImageRes?.let { currentImage ->

            BackHandler {
                zoomedImageRes = null
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {}
                    .clickable { zoomedImageRes = null },
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painter = painterResource(id = currentImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(radius = 40.dp),
                    contentScale = ContentScale.Crop
                )

                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.4f)))

                Image(
                    painter = painterResource(id = currentImage),
                    contentDescription = "Zoomed Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}