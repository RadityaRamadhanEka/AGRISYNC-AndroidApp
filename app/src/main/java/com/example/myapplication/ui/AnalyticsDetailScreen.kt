package com.example.myapplication.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme

// Analytics Screen Colors
val AgriDarkGreenHeader = Color(0xFF0F5A2C)

@Composable
fun AnalyticsDetailScreen(
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedNavTab by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriBgColor,
        bottomBar = {
            AnalyticsBottomBar(
                selectedTab = selectedNavTab,
                onTabSelected = { selectedNavTab = it }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Top Bar AGRISYNC Logo & Back / Profile
            item {
                AnalyticsTopHeader(onBackClick = onBackClick)
            }

            // 2. Biodigester Hero View with Floating Glassmorphic Indicators
            item {
                BiodigesterHeroCard()
            }

            // 3. Efisiensi Biodigester Gauge Card (95% STABIL)
            item {
                EfficiencyGaugeCard()
            }

            // 4. 2 Metric Cards (Suhu Internal & Kelembaban)
            item {
                AnalyticsMetricsRow()
            }

            // 5. Log Siklus Nutrisi Timeline Card
            item {
                NutrientCycleLogCard()
            }

            // 6. Energi Dihasilkan Card
            item {
                EnergyGeneratedCard()
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. TOP HEADER (AGRISYNC LOGO & PROFILE)
// ---------------------------------------------------------------------------
@Composable
fun AnalyticsTopHeader(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = AgriDarkGreenHeader
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Outlined.Eco,
                contentDescription = null,
                tint = AgriDarkGreenHeader,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "AGRISYNC",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = AgriDarkGreenHeader,
                letterSpacing = 1.sp
            )
        }

        // Profile Avatar Badge
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0xFFF3E5F5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color(0xFFAB47BC),
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 2. BIODIGESTER HERO CARD WITH FLOATING GLASSMORPHIC TAGS
// ---------------------------------------------------------------------------
@Composable
fun BiodigesterHeroCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(340.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(28.dp))
        ) {
            // Background Image from res/drawable/img_biodigester
            Image(
                painter = painterResource(id = R.drawable.img_biodigester),
                contentDescription = "Biodigester Device",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // --- Floating Glassmorphic Pill Tags ---

            // Tag 1: Top Left - Tekanan Gas (1.2 atm)
            GlassmorphicPillTag(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 20.dp, top = 36.dp),
                title = "TEKANAN GAS",
                value = "1.2 atm",
                showRightLine = true
            )

            // Tag 2: Top Right / Middle Right - Volume Biogas (75%)
            GlassmorphicPillTag(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 20.dp, top = 80.dp),
                title = "VOLUME BIOGAS",
                value = "75%",
                showLeftLine = true
            )

            // Tag 3: Middle Left - Status Agitator (Aktif)
            GlassmorphicPillTag(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 24.dp)
                    .offset(y = 20.dp),
                title = "STATUS AGITATOR",
                value = "Aktif",
                hasDot = true,
                showRightLine = true
            )

            // Tag 4: Bottom Right - Kualitas Pupuk Cair (Optimal)
            GlassmorphicPillTag(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 45.dp),
                title = "KUALITAS PUPUK CAIR",
                value = "Optimal",
                showLeftLine = true
            )
        }
    }
}

@Composable
fun GlassmorphicPillTag(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    hasDot: Boolean = false,
    showLeftLine: Boolean = false,
    showRightLine: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showLeftLine) {
            Box(
                modifier = Modifier
                    .width(16.dp)
                    .height(1.dp)
                    .background(Color.White.copy(alpha = 0.6f))
            )
        }

        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color.White.copy(alpha = 0.22f),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.45f))
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.85f),
                    letterSpacing = 0.5.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (hasDot) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF22C55E))
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                    Text(
                        text = value,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }
        }

        if (showRightLine) {
            Box(
                modifier = Modifier
                    .width(16.dp)
                    .height(1.dp)
                    .background(Color.White.copy(alpha = 0.6f))
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 3. EFISIENSI BIODIGESTER GAUGE CARD
// ---------------------------------------------------------------------------
@Composable
fun EfficiencyGaugeCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Circular Arc Gauge
            Box(
                modifier = Modifier.size(140.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Track Arc
                    drawArc(
                        color = Color(0xFFE8F5E9),
                        startAngle = 130f,
                        sweepAngle = 280f,
                        useCenter = false,
                        style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
                    )
                    // Progress Arc (95% of 280deg = ~266deg)
                    drawArc(
                        color = Color(0xFF1B5E20),
                        startAngle = 130f,
                        sweepAngle = 266f,
                        useCenter = false,
                        style = Stroke(width = 14.dp.toPx(), cap = StrokeCap.Round)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "95%",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriTextDark
                    )
                    Text(
                        text = "(STABIL)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriGreenPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Efisiensi Biodigester",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextDark
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Kinerja ekstraksi nutrisi berada pada level puncak",
                fontSize = 12.sp,
                color = AgriTextMuted
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 4. ANALYTICS METRICS ROW (SUHU INTERNAL & KELEMBABAN)
// ---------------------------------------------------------------------------
@Composable
fun AnalyticsMetricsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Suhu Internal Card
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AgriGreenLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Thermostat,
                        contentDescription = "Suhu",
                        tint = AgriGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "SUHU INTERNAL",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextMuted
                )
                Text(
                    text = "38.5°C",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriTextDark
                )
            }
        }

        // Kelembaban Card
        Card(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AgriGreenLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = "Kelembaban",
                        tint = AgriGreenPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "KELEMBABAN",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextMuted
                )
                Text(
                    text = "62.1%",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriTextDark
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 5. LOG SIKLUS NUTRISI CARD
// ---------------------------------------------------------------------------
@Composable
fun NutrientCycleLogCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Log Siklus Nutrisi",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTextDark
                )
                Text(
                    text = "Lihat Riwayat",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriGreenDark,
                    modifier = Modifier.clickable { }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Item 1: Pemanenan Metana Selesai
            Row(verticalAlignment = Alignment.Top) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(AgriGreenLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = AgriGreenPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(30.dp)
                            .background(AgriBorderLight)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Pemanenan Metana Selesai",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextDark
                    )
                    Text(
                        text = "14:32 PM • Batch #229-A",
                        fontSize = 12.sp,
                        color = AgriTextMuted
                    )
                }
            }

            // Item 2: Ekstraksi Bio-slurry
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(AgriGreenLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = AgriGreenPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "Ekstraksi Bio-slurry",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTextDark
                    )
                    Text(
                        text = "12:15 PM • Proses aktif",
                        fontSize = 12.sp,
                        color = AgriTextMuted
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 6. ENERGI DIHASILKAN CARD
// ---------------------------------------------------------------------------
@Composable
fun EnergyGeneratedCard() {
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ENERGI DIHASILKAN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.85f),
                        letterSpacing = 0.5.sp
                    )
                    Icon(
                        imageVector = Icons.Default.ElectricBolt,
                        contentDescription = "Energy",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "482",
                        fontSize = 42.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "kWh",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Estimasi nilai: $142.50",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f)
                    )

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.White.copy(alpha = 0.25f)
                    ) {
                        Text(
                            text = "📈 12% VS LY",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 7. BOTTOM BAR FOR ANALYTICS SCREEN
// ---------------------------------------------------------------------------
@Composable
fun AnalyticsBottomBar(
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
                AnalyticsNavItem(
                    label = "DASHBOARD",
                    icon = Icons.Default.GridView,
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )
                AnalyticsNavItem(
                    label = "ANALITIK",
                    icon = Icons.AutoMirrored.Filled.ShowChart,
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )
                Spacer(modifier = Modifier.width(52.dp))
                AnalyticsNavItem(
                    label = "SENSOR",
                    icon = Icons.Default.Sensors,
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )
                AnalyticsNavItem(
                    label = "PENGATURAN",
                    icon = Icons.Default.Settings,
                    isSelected = selectedTab == 3,
                    onClick = { onTabSelected(3) }
                )
            }
        }

        // Center Floating Scan Button
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
fun AnalyticsNavItem(
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
            fontSize = 9.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) AgriGreenDark else AgriTextMuted
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AnalyticsDetailScreenPreview() {
    MyApplicationTheme {
        AnalyticsDetailScreen()
    }
}
