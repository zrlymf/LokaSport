package com.example.lokasport.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueListScreen(
    categoryName: String,
    onNavigateBack: () -> Unit,
    onVenueClick: (Int) -> Unit
) {
    val categoryVenues = allVenues.filter { venue ->
        venue.category.contains(categoryName, ignoreCase = true) ||
                venue.name.contains(categoryName, ignoreCase = true)
    }

    var selectedFilter by remember { mutableStateOf<String?>(null) }
    val filters = listOf("Nearest", "Highest Rated", "Cheapest")

    val displayedVenues = when (selectedFilter) {
        "Nearest" -> categoryVenues.sortedBy { it.distanceKm }
        "Highest Rated" -> categoryVenues.sortedByDescending { it.rating }
        "Cheapest" -> categoryVenues.sortedBy { it.pricePerHour }
        else -> categoryVenues
    }

    val isSearchQuery = categoryVenues.isNotEmpty() && !categoryVenues.first().category.equals(categoryName, ignoreCase = true)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "LokaSport", fontWeight = FontWeight.Bold, color = DarkOlive) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = DarkOlive)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CreamBg)
            )
        },
        containerColor = CreamBg
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isSearchQuery) "Search Results:" else "$categoryName Venues:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkOlive
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (isSearchQuery) "Showing results for \"$categoryName\"" else "Choose your preferred $categoryName venue",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filters) { filterName ->
                    val isSelected = selectedFilter == filterName
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) DarkOlive else Color.Transparent)
                            .border(
                                width = 1.dp,
                                color = DarkOlive,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable {
                                selectedFilter = if (isSelected) null else filterName
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = filterName,
                            color = if (isSelected) CreamBg else DarkOlive,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (displayedVenues.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No venues available for \"$categoryName\".", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(displayedVenues) { venue ->
                        Box(modifier = Modifier.clickable { onVenueClick(venue.id) }) {
                            DetailVenueCard(venue = venue)
                        }
                    }
                    item { Spacer(modifier = Modifier.height(24.dp)) }
                }
            }
        }
    }
}

@Composable
fun DetailVenueCard(venue: Venue) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0EBE1)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = venue.imageRes),
                contentDescription = "Venue Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = venue.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = DarkOlive,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location", tint = Color.Gray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = venue.location,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFE5B05C), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "${venue.rating} (${venue.reviews} Reviews)", fontSize = 12.sp, color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .border(1.dp, SageGreen, RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "From Rp${venue.pricePerHour/1000}k/hr",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SageGreen,
                        maxLines = 1
                    )
                }
            }
        }
    }
}