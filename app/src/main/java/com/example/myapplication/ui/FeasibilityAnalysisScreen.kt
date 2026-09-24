package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FeasibilityAnalysisScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToControl: () -> Unit = {},
    onNavigateToProductionManagement: () -> Unit = {},
    onNavigateToAnalytics: () -> Unit = {},
    onNavigateToPetani: () -> Unit = {}
) {
    var isVisible by remember { mutableStateOf(true) }
    var targetRoi by remember { mutableIntStateOf(0) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // ROI animated counter: 0% -> 24%
    val animatedRoi by animateIntAsState(
        targetValue = targetRoi,
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
        label = "roiAnimation"
    )

    LaunchedEffect(Unit) {
        isVisible = true
        delay(200)
        targetRoi = 24
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 2, // "Analitik" active
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header with Back Button & Title
            item {
                FeasibilityHeader(
                    onBackClick = onBackClick
                )
            }

            // Top Status Badge: "LAYAK DIJALANKAN"
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(tween(500)) + slideInVertically(tween(500)) { -30 }
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color(0xFF2E7D32).copy(alpha = 0.1f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E7D32).copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = null,
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(18.dp)
                                )
                                Text(
                                    text = "LAYAK DIJALANKAN",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF2E7D32),
                                    letterSpacing = 0.8.sp
                                )
                            }
                        }
                    }
                }
            }

            // Main Highlight Card (Estimated ROI & Financial Breakdown)
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(tween(700)) + slideInVertically(tween(700)) { 50 }
                ) {
                    MainRoiHighlightCard(
                        roiValue = animatedRoi
                    )
                }
            }

            // Section 2: "Rincian Dibalik Layar"
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(tween(900)) + slideInVertically(tween(900)) { 50 }
                ) {
                    BehindTheScenesSection()
                }
            }

            // Download PDF Button
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(tween(1100)) + slideInVertically(tween(1100)) { 50 }
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Laporan PDF berhasil diunduh!")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E7D32),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Download,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Download Laporan PDF",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Spacing for Bottom Bar
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. HEADER SECTION
// ---------------------------------------------------------------------------
@Composable
private fun FeasibilityHeader(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Back Button
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onBackClick() },
                shape = CircleShape,
                color = AgriTheme.colors.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.border),
                shadowElevation = 2.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = AgriTheme.colors.textPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Title Column
            Column {
                Text(
                    text = "Analisis Kelayakan",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary
                )
                Text(
                    text = "BUSINESS INSIGHTS",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.primary,
                    letterSpacing = 1.sp
                )
            }
        }

        // Settings Gear Button
        Surface(
            modifier = Modifier
                .size(40.dp)
                .clickable { },
            shape = CircleShape,
            color = AgriTheme.colors.surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.border),
            shadowElevation = 2.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = AgriTheme.colors.grayIcon,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 2. MAIN ROI HIGHLIGHT CARD
// ---------------------------------------------------------------------------
@Composable
private fun MainRoiHighlightCard(
    roiValue: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Background Ambient Glow
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 40.dp, y = (-40).dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4ADE80).copy(alpha = 0.08f))
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Estimated ROI Big Number
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "ESTIMATED ROI",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5E8760),
                        letterSpacing = 1.sp
                    )

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$roiValue%",
                            fontSize = 46.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF2E7D32)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "/ tahun",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF5E8760),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    // Badge "Above Market Average"
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF4ADE80).copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = "Above Market Average",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                    }
                }

                // Payback Period Card
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    color = AgriTheme.colors.background,
                    border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.border)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(48.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = AgriTheme.colors.surface,
                            shadowElevation = 2.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null,
                                    tint = AgriTheme.colors.primary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = "PAYBACK PERIOD",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriTheme.colors.textSecondary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "8,4 Bulan",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = AgriTheme.colors.textPrimary
                            )
                        }
                    }
                }

                // 2 Metric Tiles: BEP Unit & Net Margin
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Tile 1: BEP Unit
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        color = AgriTheme.colors.background,
                        border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.border)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "BEP UNIT",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriTheme.colors.textSecondary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "1,250 kg",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriTheme.colors.textPrimary
                            )
                        }
                    }

                    // Tile 2: Net Margin
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        color = AgriTheme.colors.background,
                        border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.border)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "NET MARGIN",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriTheme.colors.textSecondary,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "32.4%",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriTheme.colors.textPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 3. RINCIAN DIBALIK LAYAR SECTION
// ---------------------------------------------------------------------------
@Composable
private fun BehindTheScenesSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Section Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Rincian Dibalik Layar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )

            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Info",
                tint = AgriTheme.colors.textMuted,
                modifier = Modifier.size(20.dp)
            )
        }

        // Detail 1: Biaya Energi
        BehindTheScenesCard(
            icon = Icons.Default.ElectricBolt,
            iconBgColor = AgriTheme.colors.yellowWarnBg,
            iconTint = Color(0xFFD97706),
            title = "Biaya Energi",
            subtitle = "Estimasi per bulan",
            value = "Rp 1.2M",
            subValue = "-2.4% Efficient",
            subValueColor = AgriTheme.colors.primary
        )

        // Detail 2: Nutrisi & Air
        BehindTheScenesCard(
            icon = Icons.Default.Science,
            iconBgColor = AgriTheme.colors.blueInfoBg,
            iconTint = Color(0xFF2563EB),
            title = "Nutrisi & Air",
            subtitle = "Kebutuhan sirkulasi",
            value = "Rp 450k",
            subValue = "Optimized",
            subValueColor = AgriTheme.colors.textSecondary
        )

        // Detail 3: Yield per m²
        BehindTheScenesCard(
            icon = Icons.Outlined.LocalFlorist,
            iconBgColor = AgriTheme.colors.mintBg,
            iconTint = Color(0xFF059669),
            title = "Yield per m²",
            subtitle = "Produktivitas lahan",
            value = "4.2 kg",
            subValue = "High Density",
            subValueColor = AgriTheme.colors.primary
        )
    }
}

@Composable
private fun BehindTheScenesCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBgColor: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    value: String,
    subValue: String,
    subValueColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(iconBgColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Column {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTheme.colors.textPrimary
                    )
                    Text(
                        text = subtitle,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = AgriTheme.colors.textSecondary
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary
                )
                Text(
                    text = subValue,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = subValueColor
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 4. BOTTOM NAVIGATION BAR
// ---------------------------------------------------------------------------
@Composable
private fun FeasibilityBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = AgriTheme.colors.surface,
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
                FeasibilityNavItem(
                    label = "Home",
                    icon = Icons.Default.GridView,
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )

                // Item 2: Controls
                FeasibilityNavItem(
                    label = "Controls",
                    icon = Icons.Outlined.LocalFlorist,
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )

                // Spacer for Floating Action Button
                Spacer(modifier = Modifier.width(52.dp))

                // Item 3: Analytics (Active)
                FeasibilityNavItem(
                    label = "Analytics",
                    icon = Icons.Default.BarChart,
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )

                // Item 4: Petani
                FeasibilityNavItem(
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
            containerColor = AgriTheme.colors.primary,
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
private fun FeasibilityNavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (isSelected) AgriTheme.colors.primary else AgriTheme.colors.textMuted

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
fun FeasibilityAnalysisScreenPreview() {
    MyApplicationTheme {
        FeasibilityAnalysisScreen()
    }
}
