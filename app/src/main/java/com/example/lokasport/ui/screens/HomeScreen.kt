package com.example.lokasport.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lokasport.R
import com.example.lokasport.ui.theme.CreamBg
import com.example.lokasport.ui.theme.DarkOlive
import com.example.lokasport.ui.theme.SageGreen
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import kotlin.random.Random
data class BookingOrder(
    val venueName: String,
    val venueLocation: String,
    val date: String,
    val time: String,
    val price: Int,
    val imageRes: Int,
    val status: String = "Upcoming"
)

data class SportCategory(val id: Int, val name: String, val iconRes: Int)

data class TimeSlot(
    val time: String,
    var isAvailable: Boolean
)

fun generateRandomSlots(): MutableList<TimeSlot> {
    val allTimes = listOf(
        "08:00 - 09:00",
        "09:00 - 10:00",
        "10:00 - 11:00",
        "11:00 - 12:00",
        "12:00 - 13:00",
        "13:00 - 14:00",
        "14:00 - 15:00",
        "15:00 - 16:00",
        "16:00 - 17:00",
        "17:00 - 18:00",
        "18:00 - 19:00",
        "19:00 - 20:00",
        "20:00 - 21:00",
        "21:00 - 22:00",
        "22:00 - 23:00",
        "23:00 - 24:00"
    )

    return allTimes.map { time ->
        val isAvailable = Random.nextFloat() > 0.3f
        TimeSlot(time, isAvailable)
    }.toMutableList()
}

data class Venue(
    val id: Int,
    val name: String,
    val rating: Double,
    val location: String,
    val category: String,
    val distanceKm: Double,
    val pricePerHour: Int,
    val reviews: Int,
    val imageRes: Int,

    val galleryImages: List<Int> = listOf(imageRes, imageRes, imageRes),
    val reviewsList: List<String> = listOf(),

    val slots: MutableList<TimeSlot> = generateRandomSlots()
)

val allVenues = listOf(
    Venue(
        id = 1, name = "Baskhara Futsal Arena", rating = 4.6, location = "Jl. Manyar Jaya Praja I No.47, Menur Pumpungan, Sukolilo, Surabaya, Jawa Timur.", category = "Futsal", distanceKm = 6.3, pricePerHour = 150000, reviews = 1700, imageRes = R.drawable.futsal_bhaskara_main,
        galleryImages = listOf(R.drawable.futsal_baskhara1, R.drawable.futsal_baskhara2, R.drawable.futsal_baskhara3),
        reviewsList = listOf("Rumput sintetisnya tebal dan ngga licin.", "Parkiran motornya luas banget.", "Sering main di sini buat turnamen kampus, mantap!", "Ada musholanya bersih dan nyaman.", "Bisa sewa sepatu futsal juga di sini, ukurannya lengkap.")
    ),
    Venue(
        id = 2, name = "Fiva Sport Futsal", rating = 4.1, location = "Jl. Bumi Marina Emas Barat I/15, Keputih, Sukolilo, Surabaya, Jawa Timur.", category = "Futsal", distanceKm = 2.2, pricePerHour = 150000, reviews = 416, imageRes = R.drawable.futsal_fiva_main,
        galleryImages = listOf(R.drawable.futsal_fiva1, R.drawable.futsal_fiva2, R.drawable.futsal_fiva3),
        reviewsList = listOf("Harganya lumayan terjangkau buat mahasiswa ITS.", "Jaring gawangnya ada yang bolong dikit, tapi overall oke.", "Sirkulasi udaranya bagus, ngga terlalu pengap.", "Ibu kantinnya ramah, jualan es tehnya seger banget.", "Lokasi strategis masuk gang dikit dari jalan raya.")
    ),
    Venue(
        id = 3, name = "Surabaya Futsal Center (SFC)", rating = 4.6, location = "Jl. Raya Tandes No.50, Surabaya, Jawa Timur.", category = "Futsal", distanceKm = 14.0, pricePerHour = 180000, reviews = 286, imageRes = R.drawable.futsal_sfc_main,
        galleryImages = listOf(R.drawable.futsal_sfc1, R.drawable.futsal_sfc2, R.drawable.futsal_sfc3),
        reviewsList = listOf("Standar lapangannya pro banget.", "Fasilitas kamar mandinya bersih dan airnya kenceng.", "Penjaganya ramah.", "Papan skor digitalnya terang dan gampang dilihat.", "Sering ada promo diskon kalau main pagi hari.")
    ),
    Venue(
        id = 4, name = "Hokky Futsal", rating = 4.2, location = "Jl. Nginden II No.109, Surabaya, Jawa Timur.", category = "Futsal", distanceKm = 6.9, pricePerHour = 79000, reviews = 850, imageRes = R.drawable.futsal_hokky_main,
        galleryImages = listOf(R.drawable.futsal_hokky1, R.drawable.futsal_hokky2, R.drawable.futsal_hokky3),
        reviewsList = listOf("Paling murah di daerah sini!", "Agak ramai kalau weekend, harus booking dari jauh hari.", "Lantai interlock-nya enak buat lari.", "Cahaya lampunya rata, ngga ada spot yang gelap.", "Admin WA-nya fast respon kalau mau tanya jadwal kosong.")
    ),
    Venue(
        id = 5, name = "Primavera Futsal Wiyung", rating = 4.2, location = "Jl. Raya Menganti No.52, Kedurus, Karangpilang, Surabaya, Jawa Timur.", category = "Futsal", distanceKm = 16.0, pricePerHour = 50000, reviews = 845, imageRes = R.drawable.futsal_primavera_main,
        galleryImages = listOf(R.drawable.futsal_primavera1, R.drawable.futsal_primavera2, R.drawable.futsal_prima3),
        reviewsList = listOf("Cocok buat main santai bareng temen.", "Bola sewaannya kadang kurang angin.", "Akses ke lokasinya gampang dicari.", "Banyak tempat duduk buat tim yang lagi nunggu giliran.", "Disediain air mineral galon gratis di pinggir lapangan.")
    ),

    Venue(
        id = 6, name = "Wonderkid Basketball Indoor Court", rating = 4.4, location = "Jalan Siwalankerto Selatan No. 4/19D, Kecamatan Wonocolo, Surabaya, Jawa Timur.", category = "Basketball", distanceKm = 15.0, pricePerHour = 175000, reviews = 179, imageRes = R.drawable.basket_wonder_main,
        galleryImages = listOf(R.drawable.basket_wonder1, R.drawable.basket_wonder2, R.drawable.basket_wonder3),
        reviewsList = listOf("Lantai parket kayunya kesat, ngga gampang kepleset.", "Ringnya standar FIBA.", "Pencahayaannya pas, ngga bikin silau pas mau shoot.", "Bola basket sewaannya bagus, merk Spalding.", "Ada shower air hangatnya buat mandi habis main.")
    ),
    Venue(
        id = 7, name = "Mayasi Basketball Court", rating = 4.5, location = "Jl. Kenjeran no. 546, Surabaya, Jawa Timur.", category = "Basketball", distanceKm = 5.3, pricePerHour = 125000, reviews = 334, imageRes = R.drawable.bakset_mayasi_main,
        galleryImages = listOf(R.drawable.basket_mayasi1, R.drawable.basket_mayasi2, R.drawable.basket_mayasi3),
        reviewsList = listOf("Tempatnya luas dan tribun penontonnya nyaman.", "Ada kantinnya jadi gampang kalau mau beli minum.", "Papan skor digitalnya kadang error, tapi gapapa.", "Garis lapangannya baru dicat ulang, kelihatan jelas.", "Tempat parkir mobil lumayan lega, ngga repot.")
    ),
    Venue(
        id = 8, name = "JPS Basketball Court", rating = 4.6, location = "Jl. Gayungsari VII no. 16-18, Gayungan, Kec. Gayungan, Surabaya, Jawa Timur.", category = "Basketball", distanceKm = 17.0, pricePerHour = 100000, reviews = 152, imageRes = R.drawable.basket_jps_main,
        galleryImages = listOf(R.drawable.basket_jps1, R.drawable.basket_jps2, R.drawable.basket_jps3),
        reviewsList = listOf("Hidden gem buat anak basket Surabaya Selatan.", "Ringnya empuk.", "Sering dipakai latihan tim lokal.", "Ventilasi udaranya bagus, angin sepoi-sepoi masuk.", "Sewa lapangannya gampang dan ngga ribet.")
    ),
    Venue(
        id = 9, name = "Lapangan Basket Sahabat", rating = 4.5, location = "Jl. Simolawang Baru IV no. 2, Simokerto, Kec. Simokerto, Surabaya, Jawa Timur.", category = "Basketball", distanceKm = 9.9, pricePerHour = 250000, reviews = 243, imageRes = R.drawable.basket_sahabat_main,
        galleryImages = listOf(R.drawable.basket_sahabat1, R.drawable.basket_sahabat2, R.drawable.basket_sahabat3),
        reviewsList = listOf("Fasilitas bintang lima, harga lumayan pricey tapi worth it.", "AC-nya dingin banget buat ukuran lapangan indoor.", "Ruang gantinya premium.", "Toiletnya wangi dan dibersihkan berkala.", "Ada loker aman buat nyimpen barang berharga pas main.")
    ),

    Venue(
        id = 10, name = "GOR Bulutangkis Soedirman", rating = 4.4, location = "Jl. Dr. Ir. H. Soekarno No.8, Manyar Sabrangan, Kec. Mulyorejo, Surabaya, Jawa Timur.", category = "Badminton", distanceKm = 3.3, pricePerHour = 80000, reviews = 837, imageRes = R.drawable.badmin_soedirman_main,
        galleryImages = listOf(R.drawable.badmin_soe1, R.drawable.badmin_soe2, R.drawable.badmin_soe3),
        reviewsList = listOf("Lapangannya banyak banget jadi gampang kebagian jadwal.", "Karpetnya standar BWF.", "Angin dari kipas kadang kerasa sampai ke tengah lapangan.", "Jual shuttlecock juga di dalem, harga miring.", "Tribunnya cukup luas buat bawa temen-temen nonton.")
    ),
    Venue(
        id = 11, name = "Weston Sports", rating = 4.7, location = "Diamond Hill DR1 No.2, Citraland, Surabaya, Jawa Timur.", category = "Badminton", distanceKm = 22.0, pricePerHour = 75000, reviews = 80, imageRes = R.drawable.badmin_weston_main,
        galleryImages = listOf(R.drawable.badmin_weston1, R.drawable.badmin_weston2, R.drawable.badmin_weston3),
        reviewsList = listOf("Tempat elit, bersih dan wangi.", "Lantainya ngga bikin lutut sakit pas loncat.", "Jauh dari pusat kota tapi worth the drive.", "Lampu sorotnya pas, ngga bikin silau pas lihat kok di atas.", "Sering full booked, wajib pesen dari minggu lalu.")
    ),
    Venue(
        id = 12, name = "GOR Suryanaga", rating = 4.4, location = "Jl. Dharmahusada Indah Barat III, Mojo, Kec. Gubeng, Surabaya, Jawa Timur.", category = "Badminton", distanceKm = 5.3, pricePerHour = 70000, reviews = 372, imageRes = R.drawable.badmin_suryanaga_main,
        galleryImages = listOf(R.drawable.badmin_surya1, R.drawable.badmin_surya2, R.drawable.badmin_surya3),
        reviewsList = listOf("Legend banget di Surabaya.", "Lantai kayunya masih terawat dengan baik.", "Banyak atlet lokal yang latihan di sini.", "Hawanya dapet banget vibes pertandingannya.", "Ada tukang senar raket yang stand by di lokasi.")
    ),

    Venue(
        id = 13, name = "Margomulyo Sport Center", rating = 4.7, location = "Jl. Raya Margomulyo No.20, Kecamatan Tandes, Surabaya, Jawa Timur", category = "Tennis", distanceKm = 18.0, pricePerHour = 100000, reviews = 213, imageRes = R.drawable.tennis_msc_main,
        galleryImages = listOf(R.drawable.tennis_margo1, R.drawable.tennis_margo2, R.drawable.tennis_margo3),
        reviewsList = listOf("Hard court-nya mulus, pantulan bola konsisten.", "Pencahayaan malamnya sangat terang.", "Area tunggunya nyaman.", "Cat lapangannya baru, seger dilihat mata.", "Sewa ballboy-nya murah dan kerjanya gesit.")
    ),
    Venue(
        id = 14, name = "Metro Margomulyo Tennis Indoor", rating = 4.6, location = "Jl. Margomulyo No.7, Kecamatan Tandes, Surabaya, Jawa Timur", category = "Tennis", distanceKm = 16.0, pricePerHour = 150000, reviews = 140, imageRes = R.drawable.tennis_indoor_main,
        galleryImages = listOf(R.drawable.tennis_metro1, R.drawable.tennis_metro2, R.drawable.tennis_metro3),
        reviewsList = listOf("Enak buat main siang bolong karena indoor.", "Langit-langitnya tinggi jadi asik buat main lob.", "Toiletnya bersih.", "Aman dari ujan dan panas matahari.", "Ruang tunggunya full AC, asik buat ngadem.")
    ),
    Venue(
        id = 15, name = "Lapangan Tenis SIER", rating = 4.8, location = "Jl. Rungkut Industri Raya, Kutisari, Kecamatan Tenggilis Mejoyo, Surabaya, Jawa Timur", category = "Tennis", distanceKm = 12.0, pricePerHour = 40000, reviews = 73, imageRes = R.drawable.tennis_sier_main,
        galleryImages = listOf(R.drawable.tennis_sier3, R.drawable.tennis_sier1, R.drawable.tennis_sier2),
        reviewsList = listOf("Murah meriah dan gampang diakses dari kampus.", "Kalau sore hawanya sejuk.", "Net-nya kadang perlu dikencengin sendiri.", "Banyak pohon rindang di sekelilingnya, asri banget.", "Ada instruktur buat pemula yang mau belajar tenis dari nol.")
    ),

    Venue(
        id = 16, name = "Graha Padel Club", rating = 4.7, location = "Jl. Taman Perkantoran II No.9, Pradahkalikendal, Kec. Dukuhpakis, Surabaya, Jawa Timur.", category = "Padel", distanceKm = 15.0, pricePerHour = 175000, reviews = 81, imageRes = R.drawable.padel_graha_main,
        galleryImages = listOf(R.drawable.padel_graha1, R.drawable.padel_graha2, R.drawable.padel_graha3),
        reviewsList = listOf("Kacanya bening banget, pantulan bolanya sempurna.", "Vibenya asik buat nongkrong abis main.", "Raket sewanya merk bagus.", "Bisa sewa bola padel yang masih baru buka dari kaleng.", "Ada jual minuman isotonik dingin lengkap.")
    ),
    Venue(
        id = 17, name = "Playground Padel Club", rating = 4.6, location = "Lenmarc Fairway Nine Mall Lantai 2-05A Jl. Mayjend. Jonosewojo No.9, Kec. Dukuhpakis, Surabaya, Jawa Timur.", category = "Padel", distanceKm = 16.0, pricePerHour = 250000, reviews = 90, imageRes = R.drawable.padel_play_main,
        galleryImages = listOf(R.drawable.padel_play1, R.drawable.padel_play2, R.drawable.padel_play3),
        reviewsList = listOf("Satu-satunya yang ada di dalem mall, unik banget!", "Harganya premium tapi pengalamannya beda.", "Coach-nya ramah kalau mau minta ajarin rules-nya.", "Habis main bisa langsung cari makan di mall.", "Sering ada promo kalau pakai kartu kredit tertentu.")
    ),
    Venue(
        id = 18, name = "Jungle Padel Surabaya", rating = 4.9, location = "Jl. Citra Tirta Made, Made, Kec. Sambikerep, Surabaya, Jawa Timur.", category = "Padel", distanceKm = 22.0, pricePerHour = 350000, reviews = 174, imageRes = R.drawable.padel_jungle_main,
        galleryImages = listOf(R.drawable.padel_jungle1, R.drawable.padel_jungle2, R.drawable.padel_jungle3),
        reviewsList = listOf("Desain tempatnya estetik parah, berasa main di Bali.", "Fasilitas lengkap, ada cafe yang kopinya enak.", "Agak jauh di ujung Barat, tapi sangat recommended.", "Spot fotonya banyak, instagramable banget.", "Loker room-nya luas, wangi, dan sangat premium.")
    ),
    Venue(
        id = 19, name = "Homeground Padel Premiere TGK", rating = 4.8, location = "The Grand Kenjeran, Kalijudan, Kec. Mulyorejo, Surabaya, Jawa Timur.", category = "Padel", distanceKm = 6.1, pricePerHour = 300000, reviews = 120, imageRes = R.drawable.padel_home_main,
        galleryImages = listOf(R.drawable.padel_home1, R.drawable.padel_home2, R.drawable.padel_home3),
        reviewsList = listOf("Lokasi super strategis di Timur.", "Rumputnya empuk, kualitas WPT.", "Bookingnya rebutan, harus gercep.", "Komunitasnya asik-asik, sering diajak join main bareng.", "Pencahayaan kalau main sore ke malamnya juara banget.")
    ),

    Venue(
        id = 20, name = "Krida Tirta Gunungsari Kolatmar Kodikmar", rating = 4.4, location = "Jl. Golf 1 Surabaya, Gunung Sari, Dukuhpakis, Surabaya, Jawa Timur", category = "Swimming", distanceKm = 14.0, pricePerHour = 15000, reviews = 150, imageRes = R.drawable.swimming_krida_main,
        galleryImages = listOf(R.drawable.swimming_krida1, R.drawable.swimming_krida2, R.drawable.swimming_krida3),
        reviewsList = listOf("Kolam standar militer, kedalamannya menantang.", "Airnya jernih banget.", "Sangat disiplin soal aturan berenang.", "Aman karena ada lifeguard yang selalu stand by mantau.", "Area ganti bajunya cukup luas dan fungsional.")
    ),
    Venue(
        id = 21, name = "Manyar Public Swimming Pool", rating = 4.5, location = "Jl. Manyar Tirtoyoso No.6-8, Klampis Ngasem, Sukolilo, Surabaya, Jawa Timur", category = "Swimming", distanceKm = 6.0, pricePerHour = 50000, reviews = 1300, imageRes = R.drawable.swimming_manyar_main,
        galleryImages = listOf(R.drawable.swimming_manyar1, R.drawable.swimming_manyar2, R.drawable.swimming_manyar3),
        reviewsList = listOf("Kolamnya luas, ada area buat anak-anak juga.", "Kaporitnya ngga bikin mata perih.", "Sering banget rame kalau minggu pagi.", "Ada tempat sewa ban dan kacamata renang.", "Jajanan di kantin depannya banyak dan murah meriah.")
    ),
    Venue(
        id = 22, name = "Kolam Renang KKO Usman Harun Karang Pilang", rating = 4.4, location = "Jl. Karang Pilang, Kec. KarangPilang, Sukolilo, Surabaya, Jawa Timur", category = "Swimming", distanceKm = 21.0, pricePerHour = 25000, reviews = 238, imageRes = R.drawable.swimming_kko_main,
        galleryImages = listOf(R.drawable.swimming_kko1, R.drawable.swimming_kko2, R.drawable.swimming_kko3),
        reviewsList = listOf("Harganya ramah di kantong.", "Cocok buat latihan endurance.", "Kamar bilasnya cukup banyak jadi ngga antri lama.", "Airnya rutin dikuras dan dibersihkan jadi jarang keruh.", "Area parkirnya sangat luas, lega buat bawa mobil.")
    )
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    username: String,
    onCategoryClick: (String) -> Unit,
    onSearch: (String) -> Unit,
    onVenueClick: (Int) -> Unit,
    onMenuClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val categories = listOf(
        SportCategory(1, "Futsal", R.drawable.iko_futsal),
        SportCategory(2, "Basketball", R.drawable.iko_basketball),
        SportCategory(3, "Badminton", R.drawable.iko_badminton),
        SportCategory(4, "Tennis", R.drawable.iko_tennis),
        SportCategory(5, "Padel", R.drawable.iko_padel),
        SportCategory(6, "Swimming", R.drawable.iko_swimming)
    )

    val recommendedVenues = allVenues.sortedByDescending { it.rating }.take(4)

    Scaffold(bottomBar = { LokaSportFooter(currentMenu = "Home", onMenuClick = onMenuClick) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CreamBg)
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.header_home),
                    contentDescription = "Header",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search sports venue", fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = { Icon(Icons.Default.Settings, contentDescription = "Filter") },
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 24.dp)
                        .align(Alignment.TopCenter),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.9f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.9f),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),

                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (searchQuery.isNotBlank()) {
                                onSearch(searchQuery)
                            }
                        }
                    )
                )
            }

            Column(modifier = Modifier.padding(24.dp)) {
                WelcomeHeaderCard(username = username)

                Spacer(modifier = Modifier.height(32.dp))

                Text("Categories", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkOlive)
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(categories) { category ->
                        CategoryItem(
                            category = category,
                            onClick = { onCategoryClick(category.name) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text("Recommendation", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkOlive)
                Spacer(modifier = Modifier.height(16.dp))
                recommendedVenues.forEach { venue ->
                    Box(modifier = Modifier.clickable { onVenueClick(venue.id) }) {
                        HomeVenueCard(venue)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WelcomeHeaderCard(username: String) {
    val currentDate = remember { LocalDate.now() }
    val dates = remember { (0..14).map { currentDate.plusDays(it.toLong()) } }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SageGreen.copy(alpha = 0.15f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Hello, $username!", fontWeight = FontWeight.Bold, color = DarkOlive, fontSize = 16.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = "Location", tint = DarkOlive, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Surabaya", fontSize = 14.sp, color = DarkOlive, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(dates) { date ->
                    DateItem(date = date, isSelected = date == currentDate, onClick = { })
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateItem(date: LocalDate, isSelected: Boolean, onClick: () -> Unit) {
    val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
    val dayNumber = date.dayOfMonth.toString()
    val bgColor = if (isSelected) DarkOlive else Color.Transparent
    val textColor = if (isSelected) Color.White else DarkOlive.copy(alpha = 0.6f)
    val outlineColor = if (isSelected) Color.Transparent else DarkOlive.copy(alpha = 0.3f)

    Column(
        modifier = Modifier
            .width(55.dp).height(80.dp).clip(RoundedCornerShape(30.dp)).background(bgColor).border(1.dp, outlineColor, RoundedCornerShape(30.dp)).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isSelected) {
            Box(modifier = Modifier.size(4.dp).clip(CircleShape).background(Color.White))
            Spacer(modifier = Modifier.height(4.dp))
        } else {
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(text = dayName, fontSize = 12.sp, color = textColor)
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = dayNumber, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}

@Composable
fun CategoryItem(category: SportCategory, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp).clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = category.iconRes),
            contentDescription = null,
            modifier = Modifier.size(72.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = category.name, fontSize = 12.sp, color = DarkOlive, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun HomeVenueCard(venue: Venue) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(150.dp).background(SageGreen.copy(alpha = 0.3f)), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = venue.imageRes), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.2f)))
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = venue.name,
                        fontWeight = FontWeight.Bold,
                        color = DarkOlive,
                        fontSize = 16.sp,
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFBF00), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = venue.rating.toString(), color = Color.Gray, fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = venue.location, color = Color.Gray, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

@Composable
fun LokaSportFooter(currentMenu: String, onMenuClick: (String) -> Unit) {
    val items = listOf(
        Triple(Icons.Default.Home, "Home", "Home"),
        Triple(Icons.Default.List, "Booking Orders", "Orders"),
        Triple(Icons.Default.Person, "Profile", "Profile")
    )

    NavigationBar(containerColor = Color.White, tonalElevation = 8.dp) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.first as androidx.compose.ui.graphics.vector.ImageVector,
                        contentDescription = item.second
                    )
                },
                label = { Text(item.second, fontSize = 12.sp) },
                selected = currentMenu == item.third,
                onClick = { onMenuClick(item.third) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = SageGreen,
                    selectedTextColor = SageGreen,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = SageGreen.copy(alpha = 0.1f)
                )
            )
        }
    }
}