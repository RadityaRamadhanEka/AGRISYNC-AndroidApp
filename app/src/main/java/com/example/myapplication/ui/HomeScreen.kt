package com.example.myapplication.ui

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

// Design System Colors from Figma Design
val AgriBgColor = Color(0xFFF9FBF9)
val AgriTextDark = Color(0xFF141916)
val AgriTextMuted = Color(0xFF67736C)
val AgriGreenPrimary = Color(0xFF1DAA55)
val AgriGreenDark = Color(0xFF158340)
val AgriGreenMintCard = Color(0xFFE5F5EC)
val AgriGreenLight = Color(0xFFEEF8F2)
val AgriBlueWater = Color(0xFF2196F3)
val AgriOrangeSun = Color(0xFFFFA000)
val AgriBorderLight = Color(0xFFEAEFEA)

@Composable
fun AgriSyncHomeScreen(
    onNavigateToAnalytics: () -> Unit = {},
    onNavigateToDigitalTwin: () -> Unit = {},
    onNavigateToRecommendation: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriBgColor,
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Top Header Profile & Weather
            item {
                TopHeaderSection()
            }

            // 2. Dampak ECO Banner Card (Green Gradient)
            item {
                EcoImpactBannerCard()
            }

            // 3. Digital Twin Feature Card
            item {
                DigitalTwinCard(onNavigateToDigitalTwin = onNavigateToDigitalTwin)
            }

            // 4. Manajemen Produksi Feature Card
            item {
                ProductionManagementCard()
            }

            // 5. 3 Metrics Grid Row (Suhu Udara, Kelembapan, pH Tanah)
            item {
                ThreeMetricsRow()
            }

            // 6. Active Plant Card (Selada Rom)
            item {
                ActivePlantCard(onNavigateToAnalytics = onNavigateToAnalytics)
            }

            // 7. Rekomendasi AI Card
            item {
                AiRecommendationCard(onNavigateToRecommendation = onNavigateToRecommendation)
            }

            // 8. Aktivitas Timeline Section
            item {
                RecentActivitiesSection()
            }

            // Bottom Spacing for floating navigation bar
            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. TOP HEADER SECTION
// ---------------------------------------------------------------------------
@Composable
fun TopHeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // User Avatar & Name
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFD9E2DC)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User Avatar",
                        tint = AgriGreenDark,
                        modifier = Modifier.size(28.dp)
                    )
                }
                // Online Dot
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(AgriGreenPrimary)
                        .border(2.dp, Color.White, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Selamat Pagi,",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextDark
                )
                Text(
                    text = "Alex",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriTextDark
                )
                Text(
                    text = "Smart Farm Manager",
                    fontSize = 12.sp,
                    color = AgriTextMuted
                )
            }
        }

        // Right side: Weather Chip & Settings Icon
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Weather Chip
            Surface(
                shape = RoundedCornerShape(50),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = "Weather",
                        tint = AgriOrangeSun,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "28°C",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextDark
                        )
                        Text(
                            text = "Cerah",
                            fontSize = 10.sp,
                            color = AgriTextMuted
                        )
                    }
                }
            }

            // Settings Gear Icon
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                tint = AgriTextMuted,
                modifier = Modifier
                    .size(22.dp)
                    .clickable { }
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 2. DAMPAK ECO BANNER CARD
// ---------------------------------------------------------------------------
@Composable
fun EcoImpactBannerCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF158340),
                            Color(0xFF28C265)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                // Eco Badge
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.White.copy(alpha = 0.25f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Eco,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "DAMPAK ECO",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Stat Big Number
                Text(
                    text = "1.5 kg",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )

                Text(
                    text = "CO2 Dihemat Hari Ini",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Lihat Detail Link
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { }
                ) {
                    Text(
                        text = "Lihat Detail",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 3. DIGITAL TWIN CARD
// ---------------------------------------------------------------------------
@Composable
fun DigitalTwinCard(onNavigateToDigitalTwin: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToDigitalTwin() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AgriGreenMintCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Box {
                        Icon(
                            imageVector = Icons.Default.Layers,
                            contentDescription = null,
                            tint = AgriGreenPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        // Status Indicator Dot
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(AgriGreenPrimary)
                                .align(Alignment.TopEnd)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Digital Twin",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextDark
                    )
                    Text(
                        text = "Lihat visualisasi 3D pertanian Anda",
                        fontSize = 12.sp,
                        color = AgriTextMuted
                    )
                }
            }

            // Circle Arrow Button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable { onNavigateToDigitalTwin() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = AgriGreenPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 4. MANAJEMEN PRODUKSI CARD
// ---------------------------------------------------------------------------
@Composable
fun ProductionManagementCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AgriGreenPrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocalFlorist,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Manajemen Produksi",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextDark
                    )
                    Text(
                        text = "Kelola Jadwal & Bibit Tanaman",
                        fontSize = 12.sp,
                        color = AgriTextMuted
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(AgriBgColor)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = AgriTextMuted,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 5. THREE METRICS ROW (Suhu Udara, Kelembapan, pH Tanah)
// ---------------------------------------------------------------------------
@Composable
fun ThreeMetricsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        MetricBoxItem(
            modifier = Modifier.weight(1f),
            title = "SUHU UDARA",
            value = "24°C",
            icon = Icons.Default.Thermostat,
            iconBgColor = AgriGreenLight,
            iconTint = AgriGreenPrimary
        )
        MetricBoxItem(
            modifier = Modifier.weight(1f),
            title = "KELEMBAPAN",
            value = "65%",
            icon = Icons.Default.WaterDrop,
            iconBgColor = Color(0xFFE3F2FD),
            iconTint = AgriBlueWater
        )
        MetricBoxItem(
            modifier = Modifier.weight(1f),
            title = "PH TANAH",
            value = "6.5",
            icon = Icons.Outlined.LocalFlorist,
            iconBgColor = AgriGreenLight,
            iconTint = AgriGreenPrimary
        )
    }
}

@Composable
fun MetricBoxItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector,
    iconBgColor: Color,
    iconTint: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTextDark
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextMuted
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 6. ACTIVE PLANT CARD (Selada Rom)
// ---------------------------------------------------------------------------
@Composable
fun ActivePlantCard(onNavigateToAnalytics: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            // Header Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AgriGreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocalFlorist,
                            contentDescription = "Selada Rom",
                            tint = AgriGreenDark,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Selada Romaine",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextDark
                        )
                        Text(
                            text = "TANAMAN AKTIF",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriGreenPrimary
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(50),
                    color = AgriGreenLight
                ) {
                    Text(
                        text = "Hari 18 dari 30",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AgriGreenDark,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Step Progress Bar (Semai -> Vegetatif -> Panen)
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Semai",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriGreenDark
                    )
                    Text(
                        text = "Vegetatif",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriGreenDark
                    )
                    Text(
                        text = "Panen",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFB0BEC5)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(AgriGreenDark)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(AgriGreenDark)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color(0xFFE2E8F0))
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 3 Sub Metric Chips (HUM, PH, SUHU)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PlantSubMetric(
                    modifier = Modifier.weight(1f),
                    value = "62%",
                    label = "HUM",
                    icon = Icons.Default.WaterDrop,
                    iconTint = AgriBlueWater
                )
                PlantSubMetric(
                    modifier = Modifier.weight(1f),
                    value = "6.0",
                    label = "PH",
                    icon = Icons.Outlined.LocalFlorist,
                    iconTint = AgriGreenPrimary
                )
                PlantSubMetric(
                    modifier = Modifier.weight(1f),
                    value = "23°C",
                    label = "SUHU",
                    icon = Icons.Default.Thermostat,
                    iconTint = AgriOrangeSun
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Bottom Link Action
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToAnalytics() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Lihat Analitik Detail",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriGreenDark
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = AgriGreenDark,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
fun PlantSubMetric(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    icon: ImageVector,
    iconTint: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = AgriBgColor
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextDark
            )
            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextMuted
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 7. REKOMENDASI AI CARD
// ---------------------------------------------------------------------------
@Composable
fun AiRecommendationCard(
    onNavigateToRecommendation: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateToRecommendation() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AgriGreenMintCard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = AgriGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Rekomendasi AI",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextDark
                    )
                    Text(
                        text = "Optimalkan panen kamu",
                        fontSize = 11.sp,
                        color = AgriTextMuted
                    )
                }
            }

            Button(
                onClick = onNavigateToRecommendation,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = AgriTextDark
                ),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "Cek Rekomendasi",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 8. RECENT ACTIVITIES TIMELINE SECTION
// ---------------------------------------------------------------------------
@Composable
fun RecentActivitiesSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Aktivitas",
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTextDark
            )
            Text(
                text = "Lihat Semua",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AgriGreenDark,
                modifier = Modifier.clickable { }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Item 1: Level air
                Row(verticalAlignment = Alignment.Top) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE3F2FD)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = AgriBlueWater,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(28.dp)
                                .background(AgriBorderLight)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Sistem memeriksa level air",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextDark
                        )
                        Text(
                            text = "10m yang lalu",
                            fontSize = 11.sp,
                            color = AgriTextMuted
                        )
                    }
                }

                // Item 2: Mode Siang
                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFF8E1)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = AgriOrangeSun,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Pencahayaan beralih ke Mode Siang",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextDark
                        )
                        Text(
                            text = "2j yang lalu",
                            fontSize = 11.sp,
                            color = AgriTextMuted
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 9. BOTTOM NAVIGATION BAR WITH ELEVATED CENTER SCAN BUTTON
// ---------------------------------------------------------------------------
@Composable
fun AgriSyncBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 12.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Item 1: Home
                BottomNavItem(
                    label = "Home",
                    icon = Icons.Default.GridView,
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )

                // Item 2: Controls
                BottomNavItem(
                    label = "Controls",
                    icon = Icons.Default.Tune,
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )

                // Item 3: Center FAB Placeholder Spacer
                Spacer(modifier = Modifier.width(52.dp))

                // Item 4: Tanam
                BottomNavItem(
                    label = "Tanam",
                    icon = Icons.Outlined.LocalFlorist,
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )

                // Item 5: Petani
                BottomNavItem(
                    label = "Petani",
                    icon = Icons.Default.Person,
                    isSelected = selectedTab == 3,
                    onClick = { onTabSelected(3) }
                )
            }
        }

        // Center Floating Elevated Green Scan Button
        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .offset(y = (-20).dp)
                .size(56.dp),
            shape = CircleShape,
            containerColor = AgriGreenDark,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.QrCodeScanner,
                contentDescription = "Scan QR",
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

@Composable
fun BottomNavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) AgriGreenDark else AgriTextMuted,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) AgriGreenDark else AgriTextMuted
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgriSyncHomeScreenFigmaPreview() {
    MyApplicationTheme {
        AgriSyncHomeScreen()
    }
}
