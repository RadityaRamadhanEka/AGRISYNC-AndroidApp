package com.example.myapplication.ui

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Thermostat
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
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun ProductionManagementScreen(
    modifier: Modifier = Modifier,
    onNavigateToHome: () -> Unit = {},
    onNavigateToAnalytics: () -> Unit = {},
    onNavigateToDigitalTwin: () -> Unit = {},
    onNavigateToRecommendation: () -> Unit = {},
    onNavigateToControl: () -> Unit = {},
    onNavigateToPetani: () -> Unit = {},
    onNavigateToFeasibility: () -> Unit = {},
    onNavigateToBEPSimulator: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF9FAFB),
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 1, // Tab "Kontrol"
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateToHome()
                        1 -> onNavigateToControl()
                        2 -> onNavigateToAnalytics()
                        3 -> onNavigateToPetani()
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Top Header: Manajemen Produksi + Weather/Settings
            item {
                ProductionHeaderSection()
            }

            // Section 1: Monitor Tanam Aktif
            item {
                ActivePlantMonitorSection(
                    onPlantClick = onNavigateToDigitalTwin
                )
            }

            // Section 2: Perencanaan Bisnis
            item {
                BusinessPlanningSection(
                    onCheckFeasibilityClick = onNavigateToFeasibility,
                    onBEPSimulatorClick = onNavigateToBEPSimulator
                )
            }

            // Spacing for Bottom Bar
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. TOP HEADER SECTION
// ---------------------------------------------------------------------------
@Composable
private fun ProductionHeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Manajemen",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111827),
                lineHeight = 30.sp
            )
            Text(
                text = "Produksi",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111827),
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Optimalisasi Hasil & Bisnis",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF5E8760)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Weather Badge
            Surface(
                shape = RoundedCornerShape(50),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6)),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = "Weather",
                        tint = Color(0xFFFFA000),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "28°C",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF374151)
                    )
                }
            }

            // Settings Gear Button
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { },
                shape = CircleShape,
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6)),
                shadowElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color(0xFF6B7280),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 2. MONITOR TANAM AKTIF SECTION
// ---------------------------------------------------------------------------
@Composable
private fun ActivePlantMonitorSection(
    onPlantClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Eco,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Monitor Tanam Aktif",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
            }

            // Tag "1 TANAMAN"
            Surface(
                shape = RoundedCornerShape(50),
                color = Color(0xFF2E7D32).copy(alpha = 0.1f)
            ) {
                Text(
                    text = "1 TANAMAN",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32),
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        // Active Plant Card Container
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onPlantClick() },
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.95f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Background Glow Circle
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.TopEnd)
                        .offset(x = 40.dp, y = (-40).dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32).copy(alpha = 0.06f))
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Top Plant Info Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            // Plant Image Frame
                            Surface(
                                modifier = Modifier.size(76.dp),
                                shape = RoundedCornerShape(20.dp),
                                color = Color(0xFFF3F4F6),
                                shadowElevation = 1.dp
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.img_romaine),
                                    contentDescription = "Romaine Lettuce",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            // Plant Name & Status
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "Romaine Lettuce",
                                    fontSize = 19.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF111827)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF4ADE80))
                                    )
                                    Text(
                                        text = "KONDISI PRIMA",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF5E8760),
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            }
                        }

                        // Progress Percent Stack
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "60%",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF2E7D32)
                            )
                            Text(
                                text = "PROGRESS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9CA3AF)
                            )
                        }
                    }

                    // Progress Stages & Bar
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Stage Labels Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "SEEDING",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF2E7D32),
                                    letterSpacing = 0.8.sp
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "VEGETATIVE",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color(0xFF2E7D32),
                                    letterSpacing = 0.8.sp
                                )
                            }

                            Text(
                                text = "HARVEST",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF9CA3AF),
                                letterSpacing = 0.8.sp
                            )
                        }

                        // Custom Progress Bar Track & Fill
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(14.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color(0xFFF3F4F6))
                                .border(1.dp, Color(0xFFE5E7EB).copy(alpha = 0.5f), RoundedCornerShape(50))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.6f)
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(50))
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFF2E7D32),
                                                Color(0xFF4ADE80)
                                            )
                                        )
                                    )
                            )
                        }

                        // Days Info Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Hari ke-18",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6B7280)
                            )
                            Text(
                                text = "Target: 30 Hari",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9CA3AF)
                            )
                        }
                    }

                    // 3 Metric Tiles Row (Moisture, Temp, Lux)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Metric 1: Moisture
                        MetricTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.WaterDrop,
                            iconTint = Color(0xFF2196F3),
                            value = "62%",
                            label = "MOISTURE"
                        )

                        // Metric 2: Temp
                        MetricTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.Thermostat,
                            iconTint = Color(0xFFFF9800),
                            value = "24.2°",
                            label = "TEMP"
                        )

                        // Metric 3: Lux
                        MetricTile(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Default.WbSunny,
                            iconTint = Color(0xFFFFA000),
                            value = "12.5k",
                            label = "LUX"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MetricTile(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    value: String,
    label: String
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF2E7D32).copy(alpha = 0.05f),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E7D32).copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF111827)
            )
            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9CA3AF),
                letterSpacing = (-0.2).sp
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 3. PERENCANAAN BISNIS SECTION
// ---------------------------------------------------------------------------
@Composable
private fun BusinessPlanningSection(
    onCheckFeasibilityClick: () -> Unit = {},
    onBEPSimulatorClick: () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header Row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.BarChart,
                contentDescription = null,
                tint = Color(0xFF111827),
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Perencanaan Bisnis",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        }

        // Card 1: Cek Kelayakan Usaha
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFFEF3C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ShowChart,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Cek Kelayakan Usaha",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Text(
                            text = "Validasi rencana bisnis indoor farming Anda",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF5E8760)
                        )
                    }
                }

                // Checklist Items
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Analisis ROI (Return on Investment)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF4B5563)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Prediksi Harga Pasar Real-time",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF4B5563)
                        )
                    }
                }

                // Gradient Button: Analisis Potensi Bisnis
                Button(
                    onClick = onCheckFeasibilityClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF2E7D32),
                                        Color(0xFF4ADE80)
                                    )
                                ),
                                shape = RoundedCornerShape(16.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✨ Analisis Potensi Bisnis",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Card 2: Simulator BEP
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Row
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFDBEAFE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Calculate,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Simulator BEP",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Text(
                            text = "Ketahui titik impas modal operasional",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF5E8760)
                        )
                    }
                }

                // Inner Gray Stat Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF9FAFB),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ESTIMASI PANEN",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9CA3AF),
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "120 kg/cycle",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "HARGA JUAL RERATA",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF9CA3AF),
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Rp 45.000 /kg",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF111827)
                            )
                        }
                    }
                }

                // Outlined Green Button: Hitung Target Minimal
                OutlinedButton(
                    onClick = onBEPSimulatorClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF2E7D32)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2E7D32))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Adjust,
                            contentDescription = null,
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Hitung Target Minimal",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF2E7D32)
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 4. BOTTOM NAVIGATION BAR
// ---------------------------------------------------------------------------
@Composable
private fun ProductionManagementBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White.copy(alpha = 0.95f),
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
                // Item 1: Beranda
                ProductionNavItem(
                    label = "Beranda",
                    icon = Icons.Default.GridView,
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )

                // Item 2: Tanam (Active)
                ProductionNavItem(
                    label = "Tanam",
                    icon = Icons.Outlined.LocalFlorist,
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )

                // Spacer for Floating Action Button
                Spacer(modifier = Modifier.width(52.dp))

                // Item 3: Analitik
                ProductionNavItem(
                    label = "Analitik",
                    icon = Icons.Default.BarChart,
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )

                // Item 4: Petani
                ProductionNavItem(
                    label = "Petani",
                    icon = Icons.Default.Person,
                    isSelected = selectedTab == 3,
                    onClick = { onTabSelected(3) }
                )
            }
        }

        // Center Elevated Scan FAB
        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .offset(y = (-20).dp)
                .size(56.dp),
            shape = CircleShape,
            containerColor = Color(0xFF2E7D32),
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
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
private fun ProductionNavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (isSelected) Color(0xFF2E7D32) else Color(0xFF9CA3AF)

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = tint
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductionManagementScreenPreview() {
    MyApplicationTheme {
        ProductionManagementScreen()
    }
}
