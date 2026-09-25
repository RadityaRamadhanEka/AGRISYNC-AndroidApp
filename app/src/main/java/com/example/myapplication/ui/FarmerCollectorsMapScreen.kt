package com.example.myapplication.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

data class CollectorBuyerItem(
    val id: String,
    val name: String,
    val isVerified: Boolean = true,
    val ratingVal: Float,
    val rating: String,
    val reviewCount: String,
    val distanceKm: Float,
    val distance: String,
    val capacityKg: Int,
    val capacityText: String,
    val commodities: List<String>,
    val bgHex: Long = 0xFFDCFCE7,
    val tintHex: Long = 0xFF15803D
)

@Composable
fun FarmerCollectorsMapScreen(
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableIntStateOf(0) } // 0: Terdekat, 1: Rating, 2: Kapasitas

    val allBuyers = remember {
        listOf(
            CollectorBuyerItem(
                id = "1",
                name = "UD Jaya Makmur",
                isVerified = true,
                ratingVal = 4.8f,
                rating = "4.8",
                reviewCount = "(124)",
                distanceKm = 2.4f,
                distance = "2.4 km",
                capacityKg = 2000,
                capacityText = "2 Ton/Bulan",
                commodities = listOf("Selada Keriting", "Bayam Hijau", "+2 Lainnya"),
                bgHex = 0xFFDCFCE7,
                tintHex = 0xFF15803D
            ),
            CollectorBuyerItem(
                id = "2",
                name = "Koperasi Tani Nusantara",
                isVerified = true,
                ratingVal = 4.9f,
                rating = "4.9",
                reviewCount = "(210)",
                distanceKm = 1.8f,
                distance = "1.8 km",
                capacityKg = 8000,
                capacityText = "8 Ton/Bulan",
                commodities = listOf("Selada Romaine", "Cabai Merah"),
                bgHex = 0xFFFEF3C7,
                tintHex = 0xFFD97706
            ),
            CollectorBuyerItem(
                id = "3",
                name = "CV Tani Sejahtera",
                isVerified = true,
                ratingVal = 4.5f,
                rating = "4.5",
                reviewCount = "(86)",
                distanceKm = 3.8f,
                distance = "3.8 km",
                capacityKg = 1500,
                capacityText = "1.5 Ton/Bulan",
                commodities = listOf("Cabai Rawit", "Tomat Merah"),
                bgHex = 0xFFFFEDD5,
                tintHex = 0xFFEA580C
            ),
            CollectorBuyerItem(
                id = "4",
                name = "PT Agro Mandiri",
                isVerified = true,
                ratingVal = 4.9f,
                rating = "4.9",
                reviewCount = "(312)",
                distanceKm = 5.2f,
                distance = "5.2 km",
                capacityKg = 10000,
                capacityText = "10 Ton/Bulan",
                commodities = listOf("Pakcoy", "Kangkung", "Sawi Putih"),
                bgHex = 0xFFDBEAFE,
                tintHex = 0xFF2563EB
            ),
            CollectorBuyerItem(
                id = "5",
                name = "Pengepul Pak Haji Mulyono",
                isVerified = false,
                ratingVal = 4.7f,
                rating = "4.7",
                reviewCount = "(64)",
                distanceKm = 4.1f,
                distance = "4.1 km",
                capacityKg = 800,
                capacityText = "800 kg/Bulan",
                commodities = listOf("Bawang Merah", "Tomat Ceri"),
                bgHex = 0xFFF3E8FF,
                tintHex = 0xFF7C3AED
            )
        )
    }

    // Dynamic Filter & Search Logic
    val filteredBuyers = remember(searchQuery, selectedFilter) {
        allBuyers.filter { buyer ->
            searchQuery.isBlank() ||
                    buyer.name.contains(searchQuery, ignoreCase = true) ||
                    buyer.commodities.any { it.contains(searchQuery, ignoreCase = true) }
        }.let { list ->
            when (selectedFilter) {
                0 -> list.sortedBy { it.distanceKm } // Terdekat
                1 -> list.sortedByDescending { it.ratingVal } // Rating Tertinggi
                2 -> list.sortedByDescending { it.capacityKg } // Kapasitas Besar
                else -> list
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background,
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 3,
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateHome()
                        1 -> onNavigateControl()
                        2 -> onNavigateAnalytics()
                        3 -> { /* Current screen */ }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White.copy(alpha = 0.95f),
                    shadowElevation = 1.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White,
                                border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                                shadowElevation = 1.dp,
                                modifier = Modifier.clickable { onBackClick() }
                            ) {
                                Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF111827), modifier = Modifier.size(20.dp))
                                }
                            }

                            Text("Pengepul Sekitar", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                        }

                        // Search Input
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Cari nama pengepul atau komoditas...", fontSize = 14.sp, color = Color(0xFF9CA3AF)) },
                            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color(0xFF9CA3AF)) },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(imageVector = Icons.Default.Clear, contentDescription = null, tint = Color(0xFF6B7280))
                                    }
                                } else {
                                    Icon(imageVector = Icons.Default.FilterList, contentDescription = null, tint = Color(0xFF6B7280))
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF2E7D32),
                                unfocusedBorderColor = AgriTheme.colors.grayBorder,
                    unfocusedContainerColor = AgriTheme.colors.surface
                            ),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Filter Pills Horizontal
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            item {
                                FilterChipPill(
                                    label = "Terdekat",
                                    isSelected = selectedFilter == 0,
                                    icon = Icons.Default.NearMe,
                                    onClick = { selectedFilter = 0 }
                                )
                            }
                            item {
                                FilterChipPill(
                                    label = "Rating Tertinggi",
                                    isSelected = selectedFilter == 1,
                                    icon = Icons.Default.Star,
                                    onClick = { selectedFilter = 1 }
                                )
                            }
                            item {
                                FilterChipPill(
                                    label = "Kapasitas Besar",
                                    isSelected = selectedFilter == 2,
                                    icon = Icons.Default.Storefront,
                                    onClick = { selectedFilter = 2 }
                                )
                            }
                        }
                    }
                }
            }

            // Interactive Simulated Map View Box
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE2E8F0)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Map Background Canvas Grid
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawRect(color = Color(0xFFE5E7EB))
                            // Road paths
                            drawLine(color = Color.White, start = Offset(0f, size.height * 0.4f), end = Offset(size.width, size.height * 0.6f), strokeWidth = 12.dp.toPx())
                            drawLine(color = Color.White, start = Offset(size.width * 0.3f, 0f), end = Offset(size.width * 0.4f, size.height), strokeWidth = 10.dp.toPx())
                            // Pulse user circle
                            drawCircle(color = Color(0xFF3B82F6).copy(alpha = 0.2f), radius = 30.dp.toPx(), center = Offset(size.width * 0.45f, size.height * 0.5f))
                            drawCircle(color = Color(0xFF3B82F6), radius = 8.dp.toPx(), center = Offset(size.width * 0.45f, size.height * 0.5f))
                        }

                        // Map Marker Pin 1: UD Jaya
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(start = 60.dp, top = 40.dp),
                            shape = CircleShape,
                            color = Color(0xFF2E7D32),
                            shadowElevation = 4.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                Text("UD Jaya", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }

                        // Map Marker Pin 2: CV Tani
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 50.dp, bottom = 40.dp),
                            shape = CircleShape,
                            color = Color(0xFF2E7D32),
                            shadowElevation = 4.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                Text("CV Tani", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }
                }
            }

            // Buyer Cards List or Empty State
            if (filteredBuyers.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(36.dp))
                            Text("Tidak ada pengepul ditemukan", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                            Text("Coba atur ulang kata kunci atau filter di atas", fontSize = 12.sp, color = Color(0xFF6B7280))
                        }
                    }
                }
            } else {
                items(filteredBuyers) { buyer ->
                    CollectorBuyerCard(
                        item = buyer,
                        onDirectionsClick = { /* Open maps for directions */ },
                        onCallClick = { /* Open phone dialer */ }
                    )
                }
            }
        }
    }
}

@Composable
fun FilterChipPill(
    label: String,
    isSelected: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = CircleShape,
        color = if (isSelected) Color(0xFF2E7D32) else Color.White,
        border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE5E7EB)),
        shadowElevation = if (isSelected) 3.dp else 1.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = if (isSelected) Color.White else Color(0xFF4B5563), modifier = Modifier.size(14.dp))
            Text(label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium, color = if (isSelected) Color.White else Color(0xFF4B5563))
        }
    }
}

@Composable
fun CollectorBuyerCard(
    item: CollectorBuyerItem,
    onDirectionsClick: () -> Unit = {},
    onCallClick: () -> Unit = {}
) {
    var isBookmarked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Row: Icon + Name + Rating + Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(item.bgHex)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Storefront, contentDescription = null, tint = Color(item.tintHex), modifier = Modifier.size(24.dp))
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                            if (item.isVerified) {
                                Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = Color(0xFF2563EB), modifier = Modifier.size(16.dp))
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(12.dp))
                            Text(item.rating, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                            Text(item.reviewCount, fontSize = 11.sp, color = Color(0xFF9CA3AF))
                            Text("•", fontSize = 12.sp, color = Color(0xFFD1D5DB))
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF5E8760), modifier = Modifier.size(12.dp))
                            Text(item.distance, fontSize = 12.sp, color = Color(0xFF5E8760))
                        }

                        // Kapasitas Badge
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF3F4F6)
                        ) {
                            Text(
                                text = "Kapasitas: ${item.capacityText}",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF4B5563),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                IconButton(
                    onClick = { isBookmarked = !isBookmarked },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        tint = if (isBookmarked) Color(0xFF2E7D32) else Color(0xFF9CA3AF)
                    )
                }
            }

            // Commodities Badges
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "MENERIMA KOMODITAS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9CA3AF),
                    letterSpacing = 0.5.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (comm in item.commodities) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFECFDF5),
                            border = BorderStroke(1.dp, Color(0xFFD1FAE5))
                        ) {
                            Text(comm, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFF047857), modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                    }
                }
            }

            // Action Buttons: Petunjuk Jalan & Hubungi
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onDirectionsClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFF2E7D32))
                ) {
                    Icon(imageVector = Icons.Default.Directions, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Petunjuk Jalan", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                }

                Button(
                    onClick = onCallClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Hubungi", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmerCollectorsMapScreenPreview() {
    MyApplicationTheme {
        FarmerCollectorsMapScreen()
    }
}
