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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Eco
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme

// Data class to represent each Plant Recommendation
data class PlantRecommendation(
    val id: String,
    val name: String,
    val latinName: String,
    val matchPercentage: Int,
    val estPanen: String,
    val idealPh: String,
    val isBestFit: Boolean = false,
    val imageResId: Int? = null,
    val gradientColors: List<Color> = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32))
)

@Composable
fun AiRecommendationScreen(
    onBackClick: () -> Unit = {},
    onSelectPlant: (PlantRecommendation) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    val recommendationList = remember {
        listOf(
            PlantRecommendation(
                id = "1",
                name = "Bayam Hijau",
                latinName = "Spinacia oleracea",
                matchPercentage = 98,
                estPanen = "25 Hari",
                idealPh = "6.0 - 7.0",
                isBestFit = true,
                imageResId = R.drawable.img_bayam,
                gradientColors = listOf(Color(0xFF1E5631), Color(0xFF4C9A2A))
            ),
            PlantRecommendation(
                id = "2",
                name = "Kale Keriting",
                latinName = "Brassica oleracea",
                matchPercentage = 94,
                estPanen = "45 Hari",
                idealPh = "6.0 - 7.5",
                isBestFit = false,
                imageResId = R.drawable.img_kale,
                gradientColors = listOf(Color(0xFF0F4026), Color(0xFF1D7846))
            ),
            PlantRecommendation(
                id = "3",
                name = "Basil Manis",
                latinName = "Ocimum basilicum",
                matchPercentage = 88,
                estPanen = "20 Hari",
                idealPh = "5.5 - 6.5",
                isBestFit = false,
                imageResId = R.drawable.img_basil,
                gradientColors = listOf(Color(0xFF2D6A4F), Color(0xFF52B788))
            )
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriBgColor,
        bottomBar = {
            AiRecommendationBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                onHomeClick = onBackClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 1. Top Bar & Title Header Section
            item {
                AiRecommendationHeader(onBackClick = onBackClick)
            }

            // 2. List of Recommendation Cards
            items(recommendationList, key = { it.id }) { plant ->
                RecommendationPlantCard(
                    plant = plant,
                    onSelectPlant = { onSelectPlant(plant) }
                )
            }

            // Bottom Spacing for floating navigation bar
            item {
                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. TOP HEADER SECTION (Back Button + Title + Subtitle)
// ---------------------------------------------------------------------------
@Composable
fun AiRecommendationHeader(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Back Button & Screen Title
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular Back Button
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, Color(0xFFF3F4F6), CircleShape)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = AgriTextDark,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Rekomendasi AI",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )
        }

        // Subtitle text
        Text(
            text = "Berdasarkan kondisi farm kamu saat ini, kami merekomendasikan:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF5E8760),
            lineHeight = 22.sp
        )
    }
}

// ---------------------------------------------------------------------------
// 2. RECOMMENDATION PLANT CARD ITEM
// ---------------------------------------------------------------------------
@Composable
fun RecommendationPlantCard(
    plant: PlantRecommendation,
    onSelectPlant: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            // Image / Graphic Container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(192.dp)
                    .clip(RoundedCornerShape(24.dp))
            ) {
                if (plant.imageResId != null) {
                    Image(
                        painter = painterResource(id = plant.imageResId),
                        contentDescription = plant.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    // Stylized Graphic Background for vegetable preview
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = plant.gradientColors
                                )
                            )
                    ) {
                        // Background Leaf Decorative Icon Overlay
                        Icon(
                            imageVector = Icons.Outlined.Eco,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.15f),
                            modifier = Modifier
                                .size(160.dp)
                                .align(Alignment.CenterEnd)
                                .offset(x = 30.dp, y = (-20).dp)
                        )
                    }
                }

                // Gradient Overlay at bottom of image for text legibility
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(112.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Black.copy(alpha = 0.8f)
                                )
                            )
                        )
                )

                // Top Right: "Best Fit" Badge Tag (if applicable)
                if (plant.isBestFit) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 12.dp, end = 12.dp),
                        shape = RoundedCornerShape(50),
                        color = Color.White.copy(alpha = 0.95f),
                        shadowElevation = 2.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Best Fit",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                }

                // Bottom Left: Plant Name & Latin Name
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = plant.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = plant.latinName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // KECOCOKAN Progress Bar Section
            Column(
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "KECOCOKAN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF9CA3AF),
                        letterSpacing = 0.6.sp
                    )
                    Text(
                        text = "${plant.matchPercentage}% Match",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF22C55E)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Custom Match Progress Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3F4F6))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(plant.matchPercentage / 100f)
                            .height(10.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4ADE80))
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 2 Metric Sub-Cards: EST. PANEN & IDEAL PH
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Metric 1: EST. PANEN
                PlantMetricBox(
                    modifier = Modifier.weight(1f),
                    label = "EST. PANEN",
                    value = plant.estPanen,
                    icon = Icons.Default.DateRange,
                    iconBgColor = Color(0xFFE8F5E9),
                    iconTint = Color(0xFF2E7D32)
                )

                // Metric 2: IDEAL PH
                PlantMetricBox(
                    modifier = Modifier.weight(1f),
                    label = "IDEAL PH",
                    value = plant.idealPh,
                    icon = Icons.Default.WaterDrop,
                    iconBgColor = Color(0xFFEFF6FF),
                    iconTint = Color(0xFF2563EB)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Main Action Button: "Pilih Tanaman Ini"
            Button(
                onClick = onSelectPlant,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32),
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Pilih Tanaman Ini",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 3. PLANT METRIC BOX COMPONENT (EST. PANEN / IDEAL PH)
// ---------------------------------------------------------------------------
@Composable
fun PlantMetricBox(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: ImageVector,
    iconBgColor: Color,
    iconTint: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFFFAFAFA),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF9CA3AF),
                letterSpacing = 0.25.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = iconTint,
                        modifier = Modifier.size(14.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 4. BOTTOM NAVIGATION BAR
// ---------------------------------------------------------------------------
@Composable
fun AiRecommendationBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    onHomeClick: () -> Unit = {}
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
                RecommendationNavItem(
                    label = "Home",
                    icon = Icons.Default.GridView,
                    isSelected = selectedTab == 0,
                    onClick = {
                        onTabSelected(0)
                        onHomeClick()
                    }
                )

                // Item 2: Controls
                RecommendationNavItem(
                    label = "Controls",
                    icon = Icons.Default.Tune,
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )

                // Item 3: Center FAB Placeholder Spacer
                Spacer(modifier = Modifier.width(52.dp))

                // Item 4: Analytics
                RecommendationNavItem(
                    label = "Analytics",
                    icon = Icons.AutoMirrored.Filled.ShowChart,
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )

                // Item 5: Settings
                RecommendationNavItem(
                    label = "Settings",
                    icon = Icons.Default.Settings,
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
fun RecommendationNavItem(
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
fun AiRecommendationScreenPreview() {
    MyApplicationTheme {
        AiRecommendationScreen()
    }
}
