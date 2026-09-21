package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
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
fun BreakEvenSimulatorScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToControl: () -> Unit = {},
    onNavigateToProductionManagement: () -> Unit = {},
    onNavigateToAnalytics: () -> Unit = {},
    onNavigateToPetani: () -> Unit = {}
) {
    var isVisible by remember { mutableStateOf(false) }
    var simulatedHarvestKg by remember { mutableFloatStateOf(112f) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
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
            // Header: Simulator Titik Impas + Weather & Settings
            item {
                BreakEvenHeader(
                    onBackClick = onBackClick
                )
            }

            // Card 1: Analisis Ambang Batas (Break-Even Analysis & Loss/Profit Animated Bar)
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(tween(600)) + slideInVertically(tween(600)) { 40 }
                ) {
                    BreakEvenThresholdCard(
                        currentHarvestKg = simulatedHarvestKg,
                        onHarvestChanged = { simulatedHarvestKg = it }
                    )
                }
            }

            // Card 2: Target Produksi (Panen Minimal)
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(tween(800)) + slideInVertically(tween(800)) { 40 }
                ) {
                    ProductionTargetCard(
                        targetKg = 112
                    )
                }
            }

            // Card 3: Harga Jual Minimal
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(tween(1000)) + slideInVertically(tween(1000)) { 40 }
                ) {
                    MinimumPriceCard(
                        pricePerKg = "Rp 14.200"
                    )
                }
            }

            // Card 4: Rekomendasi Skala
            item {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(tween(1200)) + slideInVertically(tween(1200)) { 40 }
                ) {
                    ScaleRecommendationCard(
                        onApplyStrategyClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Strategi Skala 500+ Tanaman Berhasil Diterapkan!")
                            }
                        }
                    )
                }
            }

            // Extra Spacing for Bottom Bar
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. HEADER SECTION
// ---------------------------------------------------------------------------
@Composable
private fun BreakEvenHeader(
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
                    text = "Simulator Titik",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary,
                    lineHeight = 25.sp
                )
                Text(
                    text = "Impas",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary,
                    lineHeight = 25.sp
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Weather Badge
            Surface(
                shape = RoundedCornerShape(50),
                color = AgriTheme.colors.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.border),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 13.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.WbSunny,
                        contentDescription = "Weather",
                        tint = AgriTheme.colors.orangeAccent,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "28°C",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTheme.colors.textPrimary
                    )
                }
            }

            // Settings Button
            Surface(
                modifier = Modifier
                    .size(38.dp)
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
}

// ---------------------------------------------------------------------------
// 2. ANALISIS AMBANG BATAS CARD WITH ANIMATED LOSS & PROFIT BARS
// ---------------------------------------------------------------------------
@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
private fun BreakEvenThresholdCard(
    currentHarvestKg: Float,
    onHarvestChanged: (Float) -> Unit
) {
    // BEP is 112 Kg
    val bepKg = 112f

    // Calculate loss and profit proportions for animation
    // At default 112kg: Loss = 45%, Profit = 55%
    val targetLossRatio = remember(currentHarvestKg) {
        if (currentHarvestKg < bepKg) {
            0.45f + (bepKg - currentHarvestKg) / bepKg * 0.45f
        } else {
            (0.45f - (currentHarvestKg - bepKg) / bepKg * 0.35f).coerceAtLeast(0.10f)
        }
    }

    val targetProfitRatio = remember(currentHarvestKg) {
        1.0f - targetLossRatio
    }

    // Smooth width ratio animations
    val animatedLossRatio by animateFloatAsState(
        targetValue = targetLossRatio,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "lossRatioAnimation"
    )

    val animatedProfitRatio by animateFloatAsState(
        targetValue = targetProfitRatio,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "profitRatioAnimation"
    )

    // Infinite breathing glow animation for Loss/Profit emphasis
    val infiniteTransition = rememberInfiniteTransition(label = "pulseTransition")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha"
    )

    val isLossState = currentHarvestKg < bepKg

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Card Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Analisis Ambang Batas",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AgriTheme.colors.textPrimary
                    )
                    Text(
                        text = "Break-Even Point (BEP) Analysis",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = AgriTheme.colors.textSecondary
                    )
                }

                // Top Right Icon Overlay
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(AgriTheme.colors.mintBg.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.BarChart,
                        contentDescription = null,
                        tint = AgriTheme.colors.greenEmphasis,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Labels Row: KERUGIAN (LOSS) vs KEUNTUNGAN (PROFIT)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (isLossState) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingDown,
                            contentDescription = null,
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = "KERUGIAN (LOSS)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEF4444),
                        letterSpacing = 1.sp,
                        modifier = Modifier.alpha(if (isLossState) pulseAlpha else 1f)
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "KEUNTUNGAN (PROFIT)",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTheme.colors.greenEmphasis,
                        letterSpacing = 1.sp,
                        modifier = Modifier.alpha(if (!isLossState) pulseAlpha else 1f)
                    )
                    if (!isLossState) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = AgriTheme.colors.greenEmphasis,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            // ANIMATED BAR CONTAINER (Height 56dp, clip rounded 24dp)
            // Left side = Pure Red Gradient (Loss), Center = White Divider, Right side = Pure Green Gradient (Profit)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(AgriTheme.colors.grayBgAlt)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 1. Left Loss Segment (Pure Red Gradient)
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(animatedLossRatio.coerceAtLeast(0.05f))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFFEF4444),
                                        Color(0xFFF87171)
                                    )
                                )
                            )
                    )

                    // 2. Middle BEP Center Divider (White background gap with green indicator)
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(28.dp)
                            .background(AgriTheme.colors.surface),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .width(6.dp)
                                .height(14.dp)
                                .clip(CircleShape)
                                .background(AgriTheme.colors.surface)
                                .border(2.dp, AgriTheme.colors.greenEmphasis, CircleShape)
                        )
                    }

                    // 3. Right Profit Segment (Pure Green Gradient)
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(animatedProfitRatio.coerceAtLeast(0.05f))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF34D399),
                                        Color(0xFF059669)
                                    )
                                )
                            )
                    )
                }
            }

            // Bottom Tag: TITIK IMPAS: 112 Kg
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = RoundedCornerShape(50),
                    color = AgriTheme.colors.greenEmphasis.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.greenEmphasis.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "TITIK IMPAS:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.greenEmphasis
                        )
                        Text(
                            text = "${bepKg.toInt()} Kg",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriTheme.colors.greenEmphasis
                        )
                    }
                }
            }

            // Interactive Simulation Controls (Simulasi Panen UI/UX enhancement)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Simulasi Hasil Panen:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AgriTheme.colors.textSecondary
                    )
                    Text(
                        text = "${currentHarvestKg.toInt()} Kg",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isLossState) Color(0xFFEF4444) else AgriTheme.colors.greenEmphasis
                    )
                }

                Slider(
                    value = currentHarvestKg,
                    onValueChange = onHarvestChanged,
                    valueRange = 50f..180f,
                    colors = SliderDefaults.colors(
                        thumbColor = AgriTheme.colors.greenEmphasis,
                        activeTrackColor = AgriTheme.colors.accent,
                        inactiveTrackColor = AgriTheme.colors.grayBorder
                    ),
                    thumb = {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(AgriTheme.colors.greenEmphasis)
                                .border(2.dp, Color.White, CircleShape)
                        )
                    }
                )

                // Quick Preset Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = currentHarvestKg.toInt() == 80,
                        onClick = { onHarvestChanged(80f) },
                        label = { Text("80 Kg (Rugi)", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AgriTheme.colors.redAlertBg,
                            selectedLabelColor = Color(0xFFDC2626)
                        )
                    )

                    FilterChip(
                        selected = currentHarvestKg.toInt() == 112,
                        onClick = { onHarvestChanged(112f) },
                        label = { Text("112 Kg (BEP)", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AgriTheme.colors.greenEmphasis.copy(alpha = 0.15f),
                            selectedLabelColor = AgriTheme.colors.greenEmphasis
                        )
                    )

                    FilterChip(
                        selected = currentHarvestKg.toInt() == 150,
                        onClick = { onHarvestChanged(150f) },
                        label = { Text("150 Kg (Untung)", fontSize = 11.sp, fontWeight = FontWeight.Bold) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AgriTheme.colors.mintBg,
                            selectedLabelColor = AgriTheme.colors.greenEmphasis
                        )
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 3. TARGET PRODUKSI CARD
// ---------------------------------------------------------------------------
@Composable
private fun ProductionTargetCard(
    targetKg: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(21.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Frame (#E8F5E9)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(AgriTheme.colors.mintBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Scale,
                    contentDescription = null,
                    tint = AgriTheme.colors.greenEmphasis,
                    modifier = Modifier.size(25.dp)
                )
            }

            // Text Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "TARGET PRODUKSI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textMuted,
                    letterSpacing = 0.3.sp
                )
                Text(
                    text = "Panen Minimal: $targetKg Kg",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriTheme.colors.textPrimary,
                    lineHeight = 28.sp
                )
            }

            // Arrow Right Chevron Icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = AgriTheme.colors.textMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 4. HARGA JUAL MINIMAL CARD
// ---------------------------------------------------------------------------
@Composable
private fun MinimumPriceCard(
    pricePerKg: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(21.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Frame (#FFF7ED)
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(AgriTheme.colors.yellowWarnBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalOffer,
                    contentDescription = null,
                    tint = Color(0xFFF97316),
                    modifier = Modifier.size(25.dp)
                )
            }

            // Text Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "HARGA JUAL MINIMAL",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textMuted,
                    letterSpacing = 0.3.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$pricePerKg ",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        text = "/kg",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = AgriTheme.colors.textSecondary
                    )
                }
            }

            // Info Exclamation Badge (#FFEDD5)
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(AgriTheme.colors.yellowWarnBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFFD97706),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 5. REKOMENDASI SKALA CARD
// ---------------------------------------------------------------------------
@Composable
private fun ScaleRecommendationCard(
    onApplyStrategyClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF2E7D32),
                        Color(0xFF1B4D1E)
                    )
                )
            )
            .padding(24.dp)
    ) {
        // Background Glow Effect
        Box(
            modifier = Modifier
                .size(192.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 48.dp, y = 48.dp)
                .clip(CircleShape)
                .background(Color(0xFF4ADE80).copy(alpha = 0.2f))
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Text(
                    text = "Rekomendasi Skala",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Description
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Berdasarkan simulasi biaya operasional, kami menyarankan untuk menanam ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 22.sp
                )
                Row {
                    Text(
                        text = "500+ tanaman ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF4ADE80)
                    )
                    Text(
                        text = "untuk mencapai margin",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
                Text(
                    text = "keuntungan maksimal di siklus berikutnya.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 22.sp
                )
            }

            // Terapkan Strategi Skala Action Button
            Button(
                onClick = onApplyStrategyClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4ADE80),
                    contentColor = Color(0xFF2E7D32)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Terapkan Strategi Skala",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.RocketLaunch,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 6. BOTTOM NAVIGATION BAR
// ---------------------------------------------------------------------------
@Composable
private fun BreakEvenBottomBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = AgriTheme.colors.surface.copy(alpha = 0.95f),
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
                BreakEvenNavItem(
                    label = "Home",
                    icon = Icons.Default.GridView,
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )

                // Item 2: Controls
                BreakEvenNavItem(
                    label = "Controls",
                    icon = Icons.Outlined.LocalFlorist,
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )

                // Spacer for Floating Action Button
                Spacer(modifier = Modifier.width(52.dp))

                // Item 3: Analytics (Active)
                BreakEvenNavItem(
                    label = "Analytics",
                    icon = Icons.Default.BarChart,
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )

                // Item 4: Petani
                BreakEvenNavItem(
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
private fun BreakEvenNavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val tint = if (isSelected) AgriTheme.colors.greenEmphasis else AgriTheme.colors.textMuted

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
fun BreakEvenSimulatorScreenPreview() {
    MyApplicationTheme {
        BreakEvenSimulatorScreen()
    }
}
