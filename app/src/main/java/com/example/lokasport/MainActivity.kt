package com.example.lokasport

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import com.example.lokasport.ui.screens.HomeScreen
import com.example.lokasport.ui.screens.LoginScreen
import com.example.lokasport.ui.screens.SignUpScreen
import com.example.lokasport.ui.screens.VenueListScreen
import com.example.lokasport.ui.screens.VenueDetailScreen
import com.example.lokasport.ui.screens.allVenues
import com.example.lokasport.ui.screens.BookingScreen
import com.example.lokasport.ui.screens.OrderReviewScreen
import com.example.lokasport.ui.screens.SuccessScreen
import com.example.lokasport.ui.screens.BookingOrdersScreen
import com.example.lokasport.ui.screens.BookingOrder
import com.example.lokasport.ui.screens.ProfileScreen
import com.example.lokasport.ui.screens.FavoriteScreen
import com.example.lokasport.ui.theme.LokaSportTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LokaSportTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current
                    var currentScreen by remember { mutableStateOf("Login") }

                    var registeredUsername by remember { mutableStateOf("") }
                    var registeredPassword by remember { mutableStateOf("") }

                    var selectedCategory by remember { mutableStateOf("") }
                    var selectedVenueId by remember { mutableStateOf(0) }

                    var bookingDate by remember { mutableStateOf("") }
                    var bookingTimes by remember { mutableStateOf<List<String>>(emptyList()) }

                    var orderHistory by remember { mutableStateOf(listOf<BookingOrder>()) }

                    var favoriteVenueIds by remember { mutableStateOf(setOf<Int>()) }

                    when (currentScreen) {
                        "Login" -> {
                            LoginScreen(
                                onNavigateToSignUp = { currentScreen = "SignUp" },
                                onLoginClick = { inputUser, inputPass ->
                                    if (inputUser.isEmpty() || inputPass.isEmpty()) {
                                        Toast.makeText(context, "Username dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                                    } else if (inputUser == registeredUsername && inputPass == registeredPassword) {
                                        Toast.makeText(context, "Login Sukses!", Toast.LENGTH_SHORT).show()
                                        currentScreen = "Home"
                                    } else {
                                        Toast.makeText(context, "Data salah atau belum terdaftar!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                        "SignUp" -> {
                            SignUpScreen(
                                onNavigateToLogin = { currentScreen = "Login" },
                                onRegisterClick = { inputEmail, inputUser, inputPass ->
                                    if (inputEmail.isEmpty() || inputUser.isEmpty() || inputPass.isEmpty()) {
                                        Toast.makeText(context, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        registeredUsername = inputUser
                                        registeredPassword = inputPass
                                        Toast.makeText(context, "Register Berhasil! Silakan Login.", Toast.LENGTH_SHORT).show()
                                        currentScreen = "Login"
                                    }
                                }
                            )
                        }
                        "Home" -> {
                            HomeScreen(
                                username = registeredUsername.ifEmpty { "Guest" },
                                onCategoryClick = { category ->
                                    selectedCategory = category
                                    currentScreen = "VenueList"
                                },
                                onSearch = { query ->
                                    selectedCategory = query
                                    currentScreen = "VenueList"
                                },
                                onVenueClick = { venueId ->
                                    selectedVenueId = venueId
                                    currentScreen = "VenueDetail"
                                },
                                onMenuClick = { menu ->
                                    currentScreen = menu
                                }
                            )
                        }
                        "VenueList" -> {
                            VenueListScreen(
                                categoryName = selectedCategory,
                                onNavigateBack = { currentScreen = "Home" },
                                onVenueClick = { venueId ->
                                    selectedVenueId = venueId
                                    currentScreen = "VenueDetail"
                                }
                            )
                        }
                        "VenueDetail" -> {
                            val venueToDisplay = allVenues.find { it.id == selectedVenueId }
                            if (venueToDisplay != null) {
                                VenueDetailScreen(
                                    venue = venueToDisplay,
                                    isFavorite = favoriteVenueIds.contains(venueToDisplay.id),
                                    onToggleFavorite = {
                                        favoriteVenueIds = if (favoriteVenueIds.contains(venueToDisplay.id)) {
                                            favoriteVenueIds - venueToDisplay.id
                                        } else {
                                            favoriteVenueIds + venueToDisplay.id
                                        }
                                    },
                                    onNavigateBack = {
                                        currentScreen = if (selectedCategory.isNotEmpty()) "VenueList" else "Home"
                                    },
                                    onBookNowClick = {
                                        currentScreen = "Booking"
                                    }
                                )
                            }
                        }
                        "Booking" -> {
                            val venueToDisplay = allVenues.find { it.id == selectedVenueId }
                            if (venueToDisplay != null) {
                                BookingScreen(
                                    venue = venueToDisplay,
                                    onNavigateToReview = { date, times ->
                                        bookingDate = date
                                        bookingTimes = times
                                        currentScreen = "Review"
                                    },
                                    onBack = { currentScreen = "VenueDetail" }
                                )
                            }
                        }
                        "Review" -> {
                            val venueToDisplay = allVenues.find { it.id == selectedVenueId }
                            if (venueToDisplay != null) {
                                OrderReviewScreen(
                                    venue = venueToDisplay,
                                    date = bookingDate,
                                    times = bookingTimes,
                                    onConfirm = {
                                        val newOrder = BookingOrder(
                                            venueName = venueToDisplay.name,
                                            venueLocation = venueToDisplay.location,
                                            date = bookingDate,
                                            time = bookingTimes.joinToString(", "),
                                            price = venueToDisplay.pricePerHour * bookingTimes.size,
                                            imageRes = venueToDisplay.imageRes
                                        )
                                        orderHistory = orderHistory + newOrder

                                        bookingTimes.forEach { bookedTime ->
                                            venueToDisplay.slots.find { it.time == bookedTime }?.isAvailable = false
                                        }
                                        currentScreen = "Success"
                                    },
                                    onBack = { currentScreen = "Booking" }
                                )
                            }
                        }
                        "Success" -> {
                            val venueToDisplay = allVenues.find { it.id == selectedVenueId }
                            SuccessScreen(
                                venueName = venueToDisplay?.name ?: "",
                                date = bookingDate,
                                times = bookingTimes,
                                onHome = {
                                    bookingDate = ""
                                    bookingTimes = emptyList()
                                    currentScreen = "Home"
                                }
                            )
                        }
                        "Orders" -> {
                            BookingOrdersScreen(
                                orderHistory = orderHistory,
                                onMenuClick = { menu -> currentScreen = menu }
                            )
                        }
                        "Profile" -> {
                            ProfileScreen(
                                username = registeredUsername.ifEmpty { "Guest" },
                                onNavigateToFavorite = { currentScreen = "Favorite" },
                                onLogout = {
                                    registeredUsername = ""
                                    registeredPassword = ""
                                    orderHistory = emptyList()
                                    favoriteVenueIds = emptySet()
                                    currentScreen = "Login"
                                    Toast.makeText(context, "Logout Berhasil!", Toast.LENGTH_SHORT).show()
                                },
                                onMenuClick = { menu -> currentScreen = menu }
                            )
                        }
                        "Favorite" -> {
                            val favoriteVenues = allVenues.filter { favoriteVenueIds.contains(it.id) }
                            FavoriteScreen(
                                favoriteVenues = favoriteVenues,
                                onBack = { currentScreen = "Profile" },
                                onVenueClick = { venueId ->
                                    selectedVenueId = venueId
                                    currentScreen = "VenueDetail"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}