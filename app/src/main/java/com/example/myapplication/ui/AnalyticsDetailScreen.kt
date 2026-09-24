package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.util.Locale

// Color Palette for Analitik - Analisis Tanaman Screen (theme-aware for dark mode support)
val PlantDarkGreen: Color
    @Composable get() = AgriTheme.colors.primary
val PlantLightGreen: Color
    @Composable get() = AgriTheme.colors.accent
val PlantBgLight: Color
    @Composable get() = AgriTheme.colors.background
val PlantTextPrimary: Color
    @Composable get() = AgriTheme.colors.textPrimary
val PlantTextSecondary: Color
    @Composable get() = AgriTheme.colors.grayIcon
val PlantTextMuted: Color
    @Composable get() = AgriTheme.colors.textMuted
val PlantBlueAccent: Color
    @Composable get() = AgriTheme.colors.blueAccent

// Kept static (saturated accents that work on both light and dark backgrounds)
val PlantPurpleAccent = Color(0xFF9333EA)
val PlantRedAccent = Color(0xFFEF4444)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsDetailScreen(
    onBackClick: () -> Unit = {},
    onNavigateToHome: () -> Unit = onBackClick,
    onNavigateToProductionManagement: () -> Unit = {},
    onNavigateToControl: () -> Unit = {},
    onNavigateToPetani: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTimeFilter by remember { mutableIntStateOf(0) } // 0: 7D, 1: 14D, 2: 30D
    var showHealthBottomSheet by remember { mutableStateOf(false) }
    var selectedCycleStage by remember { mutableIntStateOf(1) } // 0: Seeding, 1: Vegetative, 2: Harvest
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    // Screen Entrance Animation State
    var isVisible by remember { mutableStateOf(true) }

    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = PlantBgLight,
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 2, // 2 is Active Analytics Tab ("Analitik")
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateToHome()
                        1 -> onNavigateToControl()
                        2 -> { /* Already on Analytics */ }
                        3 -> onNavigateToPetani()
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Ambient Blurred Gradient Glow Backgrounds
            AmbientGlowBackground()

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // 1. Top Bar Header
                item {
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(animationSpec = tween(400)) + slideInVertically(
                            initialOffsetY = { -40 },
                            animationSpec = tween(400)
                        )
                    ) {
                        PlantAnalyticsHeader(
                            onBackClick = onBackClick,
                            isRefreshing = isRefreshing,
                            onRefreshClick = {
                                coroutineScope.launch {
                                    isRefreshing = true
                                    kotlinx.coroutines.delay(1000)
                                    isRefreshing = false
                                }
                            }
                        )
                    }
                }

                // 2. Main Plant Hero Card (Romaine Lettuce)
                item {
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(animationSpec = tween(500, delayMillis = 100)) + slideInVertically(
                            initialOffsetY = { 60 },
                            animationSpec = tween(500, delayMillis = 100)
                        )
                    ) {
                        RomaineLettuceHeroCard(
                            onCardClick = { showHealthBottomSheet = true }
                        )
                    }
                }

                // 3. Growth Cycle Timeline Card
                item {
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(animationSpec = tween(500, delayMillis = 200)) + slideInVertically(
                            initialOffsetY = { 60 },
                            animationSpec = tween(500, delayMillis = 200)
                        )
                    ) {
                        GrowthCycleCard(
                            selectedStage = selectedCycleStage,
                            onStageClick = { stage -> selectedCycleStage = stage }
                        )
                    }
                }

                // 4. Growth Rate & Nutrient Uptake Animated Charts Row
                item {
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(animationSpec = tween(500, delayMillis = 300)) + slideInVertically(
                            initialOffsetY = { 60 },
                            animationSpec = tween(500, delayMillis = 300)
                        )
                    ) {
                        Column {
                            // Time Filter Selector
                            TimeFilterRow(
                                selectedFilter = selectedTimeFilter,
                                onFilterSelected = { selectedTimeFilter = it }
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            GrowthRateAndNutrientRow(timeFilterIndex = selectedTimeFilter)
                        }
                    }
                }

                // 5. Sensor Metrics Grid (Humidity, pH Level, Temp)
                item {
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(animationSpec = tween(500, delayMillis = 400)) + slideInVertically(
                            initialOffsetY = { 60 },
                            animationSpec = tween(500, delayMillis = 400)
                        )
                    ) {
                        SensorMetricsTripletRow()
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }
    }

    // Modal Bottom Sheet for Detailed Plant Health Analysis
    if (showHealthBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showHealthBottomSheet = false },
            sheetState = sheetState,
            containerColor = AgriTheme.colors.surface,
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
        ) {
            PlantHealthDetailBottomSheetContent(
                onClose = {
                    coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) showHealthBottomSheet = false
                    }
                }
            )
        }
    }
}

// ---------------------------------------------------------------------------
// AMBIENT BLURRED GLOW BACKGROUND
// ---------------------------------------------------------------------------
@Composable
fun AmbientGlowBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        PlantBgLight,
                        PlantLightGreen.copy(alpha = 0.15f),
                        PlantBgLight
                    )
                )
            )
    )
}

// ---------------------------------------------------------------------------
// 1. TOP HEADER SECTION
// ---------------------------------------------------------------------------
@Composable
fun PlantAnalyticsHeader(
    onBackClick: () -> Unit,
    isRefreshing: Boolean,
    onRefreshClick: () -> Unit
) {
    val spinAnimation by animateFloatAsState(
        targetValue = if (isRefreshing) 360f else 0f,
        animationSpec = if (isRefreshing) infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ) else tween(300),
        label = "spin"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Glassmorphic Circular Back Button
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clickableWithScale { onBackClick() },
                shape = CircleShape,
                color = AgriTheme.colors.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.grayBorder),
                shadowElevation = 2.dp
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = PlantTextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Analisis Tanaman",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PlantTextPrimary,
                letterSpacing = (-0.3).sp
            )
        }

        // Action Refresh Icon
        Surface(
            modifier = Modifier
                .size(40.dp)
                .clickableWithScale { onRefreshClick() },
            shape = CircleShape,
            color = AgriTheme.colors.surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.grayBorder),
            shadowElevation = 2.dp
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh Data",
                    tint = PlantDarkGreen,
                    modifier = Modifier
                        .size(20.dp)
                        .graphicsLayer { rotationZ = spinAnimation }
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 2. ROMAINE LETTUCE HERO CARD WITH PULSING PHASE BADGE
// ---------------------------------------------------------------------------
@Composable
fun RomaineLettuceHeroCard(
    onCardClick: () -> Unit
) {
    // Infinite Pulsing Animation for Vegetative Phase Badge Glow
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(210.dp)
            .clickableWithScale { onCardClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
        ) {
            // Background Image from res/drawable/img_romaine
            Image(
                painter = painterResource(id = R.drawable.img_romaine),
                contentDescription = "Romaine Lettuce Plant",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Gradient Overlay for readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.2f),
                                Color.Black.copy(alpha = 0.8f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )

            // Info Button Top Right
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(32.dp),
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.35f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Plant Health Details",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Bottom Left Plant Title & Phase Pill
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Romaine Lettuce",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = (-0.6).sp
                )

                // Glassmorphic Phase Badge with Pulsing Dot
                Surface(
                    shape = RoundedCornerShape(50),
                    color = PlantLightGreen.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        PlantLightGreen.copy(alpha = 0.45f)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Pulsing Outer Aura Dot
                        Box(contentAlignment = Alignment.Center) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .scale(pulseScale)
                                    .clip(CircleShape)
                                    .background(PlantLightGreen.copy(alpha = pulseAlpha * 0.5f))
                            )
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(PlantLightGreen)
                            )
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = "Vegetative Phase",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PlantLightGreen
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 3. GROWTH CYCLE TIMELINE CARD WITH KELAP-KELIP ANIMATED ACTIVE NODE
// ---------------------------------------------------------------------------
@Composable
fun GrowthCycleCard(
    selectedStage: Int,
    onStageClick: (Int) -> Unit
) {
    // Animated progress fill (0 to 0.55 for Day 18 of 35)
    val progressAnim = remember { Animatable(0f) }
    LaunchedEffect(selectedStage) {
        val target = when (selectedStage) {
            0 -> 0.15f
            1 -> 0.55f
            else -> 1.0f
        }
        progressAnim.animateTo(
            targetValue = target,
            animationSpec = tween(1000, easing = FastOutSlowInEasing)
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Growth Cycle",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PlantTextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(5.dp)
                            .clip(CircleShape)
                            .background(PlantDarkGreen)
                    )
                }

                // Day 18 of 35 Badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PlantDarkGreen.copy(alpha = 0.1f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PlantDarkGreen.copy(alpha = 0.25f))
                ) {
                    Text(
                        text = "Day 18 of 35",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PlantDarkGreen,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Timeline Progress Track Container (Circles centered directly on the 4dp line)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                // Background Track Line
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(AgriTheme.colors.grayBorder)
                )

                // Active Gradient Fill Line
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progressAnim.value)
                        .height(4.dp)
                        .align(Alignment.CenterStart)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(PlantDarkGreen, PlantLightGreen)
                            )
                        )
                )

                // 3 Timeline Node Circles (Vertically Centered)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Node 1: Seeding Circle
                    TimelineCircleNode(
                        title = "Seeding",
                        isCompleted = true,
                        isActive = selectedStage == 0,
                        onClick = { onStageClick(0) }
                    )

                    // Node 2: Vegetative Circle (Current Active with Kelap-Kelip Animation)
                    TimelineCircleNode(
                        title = "Vegetative",
                        isCompleted = selectedStage > 1,
                        isActive = selectedStage == 1,
                        onClick = { onStageClick(1) }
                    )

                    // Node 3: Harvest Circle
                    TimelineCircleNode(
                        title = "Harvest",
                        isCompleted = selectedStage > 2,
                        isActive = selectedStage == 2,
                        onClick = { onStageClick(2) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp)) // Proper spacing for text labels

            // Text Labels Row (Cleanly separated below the line & circles)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TimelineTextLabel(
                    title = "Seeding",
                    isCompleted = true,
                    isActive = selectedStage == 0,
                    onClick = { onStageClick(0) }
                )
                TimelineTextLabel(
                    title = "Vegetative",
                    isCompleted = selectedStage > 1,
                    isActive = selectedStage == 1,
                    onClick = { onStageClick(1) }
                )
                TimelineTextLabel(
                    title = "Harvest",
                    isCompleted = selectedStage > 2,
                    isActive = selectedStage == 2,
                    onClick = { onStageClick(2) }
                )
            }
        }
    }
}

@Composable
fun TimelineCircleNode(
    title: String,
    isCompleted: Boolean,
    isActive: Boolean,
    onClick: () -> Unit
) {
    // Kelap-Kelip (Blinking / Pulsing) Animation for Active Node
    val infiniteTransition = rememberInfiniteTransition(label = "nodeBlinking")
    val blinkAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blinkAlpha"
    )
    val blinkScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(650, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blinkScale"
    )

    Box(
        modifier = Modifier.clickableWithScale { onClick() },
        contentAlignment = Alignment.Center
    ) {
        when {
            isActive -> {
                // Outer Pulsing / Kelap-Kelip Glowing Aura
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .scale(blinkScale)
                        .clip(CircleShape)
                        .background(PlantLightGreen.copy(alpha = blinkAlpha * 0.5f))
                )

                // Active Glowing Main Circle
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AgriTheme.colors.surface)
                        .border(2.5.dp, PlantDarkGreen, CircleShape)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(PlantDarkGreen)
                    )
                }
            }
            isCompleted -> {
                // Completed Checkmark Circle Node
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(PlantDarkGreen)
                        .border(2.dp, AgriTheme.colors.surface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = title,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            else -> {
                // Upcoming Phase Node Circle
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(AgriTheme.colors.grayBgAlt)
                        .border(2.dp, AgriTheme.colors.surface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Eco,
                        contentDescription = title,
                        tint = PlantTextMuted,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TimelineTextLabel(
    title: String,
    isCompleted: Boolean,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(72.dp)
            .clickableWithScale { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = if (isActive || isCompleted) FontWeight.Bold else FontWeight.Medium,
            color = if (isActive) PlantTextPrimary else if (isCompleted) PlantDarkGreen else PlantTextMuted
        )
    }
}

// ---------------------------------------------------------------------------
// 4. TIME FILTER ROW & DISTINCT CHARTS FOR 7, 14, AND 35 HARI
// ---------------------------------------------------------------------------
@Composable
fun TimeFilterRow(
    selectedFilter: Int,
    onFilterSelected: (Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Analisis Grafik & Tren",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PlantTextPrimary
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val filters = listOf("7 Hari", "14 Hari", "35 Hari")
                filters.forEachIndexed { index, label ->
                    val isSelected = selectedFilter == index
                    Surface(
                        modifier = Modifier.clickableWithScale { onFilterSelected(index) },
                        shape = RoundedCornerShape(20),
                        color = if (isSelected) PlantDarkGreen else AgriTheme.colors.surface,
                        border = if (!isSelected) androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.grayBorder) else null,
                        shadowElevation = if (isSelected) 3.dp else 0.dp
                    ) {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else PlantTextSecondary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        )
                    }
                }
            }
        }

        // Mode Description Sub-Caption
        val modeDesc = when (selectedFilter) {
            0 -> "Mode Mingguan (7D) • Rata-rata laju harian"
            1 -> "Mode Dua Mingguan (14D) • Akumulasi laju 14 hari"
            else -> "Mode Siklus Penuh (35D) • Proyeksi siklus panen"
        }
        Text(
            text = modeDesc,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = PlantTextSecondary
        )
    }
}

@Composable
fun GrowthRateAndNutrientRow(timeFilterIndex: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Growth Rate Card
        GrowthRateChartCard(
            modifier = Modifier.weight(1f),
            timeFilterIndex = timeFilterIndex
        )

        // Nutrient Uptake Card
        NutrientUptakeChartCard(
            modifier = Modifier.weight(1f),
            timeFilterIndex = timeFilterIndex
        )
    }
}

// Data Classes for Chart Configuration
private data class GrowthChartConfig(
    val valueText: String,
    val unitText: String,
    val badgeText: String,
    val badgeIcon: ImageVector,
    val badgeColor: Color,
    val chartPoints: List<Float>
)

private data class NutrientChartConfig(
    val valueText: String,
    val unitText: String,
    val badgeText: String,
    val badgeIcon: ImageVector,
    val badgeColor: Color,
    val chartPoints: List<Float>
)

// --- Growth Rate Card with Distinct Views for 7, 14, 35 Hari ---
@Composable
fun GrowthRateChartCard(
    modifier: Modifier = Modifier,
    timeFilterIndex: Int
) {
    // Resolve theme colors in composable scope; AgriTheme.colors cannot be read inside remember {}
    val chartPrimaryColor = PlantDarkGreen
    val chartEmphasisColor = AgriTheme.colors.greenEmphasis
    val config = remember(timeFilterIndex, chartPrimaryColor, chartEmphasisColor) {
        when (timeFilterIndex) {
            0 -> GrowthChartConfig(
                valueText = "1.2 cm",
                unitText = "/ day",
                badgeText = "+5% vs last week",
                badgeIcon = Icons.AutoMirrored.Filled.TrendingUp,
                badgeColor = chartPrimaryColor,
                chartPoints = listOf(0.3f, 0.45f, 0.6f, 0.75f, 0.85f, 1.0f, 1.2f)
            )
            1 -> GrowthChartConfig(
                valueText = "8.4 cm",
                unitText = "total tumbuh",
                badgeText = "⚡ +12% akselerasi",
                badgeIcon = Icons.AutoMirrored.Filled.TrendingUp,
                badgeColor = chartEmphasisColor,
                chartPoints = listOf(0.2f, 0.35f, 0.5f, 0.65f, 0.8f, 0.95f, 1.1f, 1.05f, 1.2f, 1.35f, 1.45f, 1.55f, 1.65f, 1.8f)
            )
            else -> GrowthChartConfig(
                valueText = "18.5 cm",
                unitText = "proyeksi panen",
                badgeText = "🎯 Target +18%",
                badgeIcon = Icons.AutoMirrored.Filled.TrendingUp,
                badgeColor = Color(0xFF9333EA),
                chartPoints = listOf(0.1f, 0.2f, 0.35f, 0.55f, 0.8f, 1.1f, 1.4f, 1.65f, 1.85f, 1.95f, 2.05f)
            )
        }
    }

    // Animation progress for chart path drawing
    val pathProgress = remember { Animatable(0f) }
    LaunchedEffect(timeFilterIndex) {
        pathProgress.snapTo(0f)
        pathProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(1200, easing = FastOutSlowInEasing)
        )
    }

    var selectedPointVal by remember { mutableStateOf<Float?>(null) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header Title
            Text(
                text = "GROWTH RATE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PlantTextSecondary,
                letterSpacing = 0.5.sp
            )

            // Value & Unit
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = if (selectedPointVal != null) String.format(Locale.getDefault(), "%.1f cm", selectedPointVal) else config.valueText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PlantTextPrimary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = config.unitText,
                    fontSize = 10.sp,
                    color = PlantTextSecondary,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            // Smooth Canvas Spline Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .pointerInput(config.chartPoints) {
                        detectTapGestures { offset ->
                            val index = ((offset.x / size.width) * (config.chartPoints.size - 1))
                                .coerceIn(0f, (config.chartPoints.size - 1).toFloat())
                                .toInt()
                            selectedPointVal = config.chartPoints[index]
                        }
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val points = config.chartPoints
                    val maxVal = (points.maxOrNull() ?: 1f).coerceAtLeast(1f)
                    val spacing = width / (points.size - 1)

                    val strokePath = Path()
                    val fillPath = Path()

                    val firstY = height - (points[0] / (maxVal * 1.25f) * height)
                    strokePath.moveTo(0f, firstY)
                    fillPath.moveTo(0f, height)
                    fillPath.lineTo(0f, firstY)

                    for (i in 0 until points.size - 1) {
                        val x1 = i * spacing
                        val y1 = height - (points[i] / (maxVal * 1.25f) * height)
                        val x2 = (i + 1) * spacing
                        val y2 = height - (points[i + 1] / (maxVal * 1.25f) * height)

                        val cx = (x1 + x2) / 2f
                        strokePath.cubicTo(cx, y1, cx, y2, x2, y2)
                        fillPath.cubicTo(cx, y1, cx, y2, x2, y2)
                    }

                    fillPath.lineTo(width, height)
                    fillPath.close()

                    // Measure Path length for smooth draw animation
                    val pathMeasure = PathMeasure()
                    pathMeasure.setPath(strokePath, false)
                    val animatedPath = Path()
                    pathMeasure.getSegment(0f, pathMeasure.length * pathProgress.value, animatedPath, true)

                    // Draw Area Gradient Fill
                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                config.badgeColor.copy(alpha = 0.35f * pathProgress.value),
                                Color.Transparent
                            )
                        )
                    )

                    // Draw Animated Green/Accent Line
                    drawPath(
                        path = animatedPath,
                        brush = Brush.horizontalGradient(listOf(chartPrimaryColor, config.badgeColor)),
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Draw Glowing End Point
                    if (pathProgress.value > 0.9f) {
                        val lastX = width
                        val lastY = height - (points.last() / (maxVal * 1.25f) * height)
                        drawCircle(
                            color = config.badgeColor.copy(alpha = 0.4f),
                            radius = 7.dp.toPx(),
                            center = Offset(lastX, lastY)
                        )
                        drawCircle(
                            color = config.badgeColor,
                            radius = 4.dp.toPx(),
                            center = Offset(lastX, lastY)
                        )
                    }
                }
            }

            // Footer Trend Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = config.badgeIcon,
                    contentDescription = null,
                    tint = config.badgeColor,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = config.badgeText,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = config.badgeColor
                )
            }
        }
    }
}

// --- Nutrient Uptake Card with Distinct Views for 7, 14, 35 Hari ---
@Composable
fun NutrientUptakeChartCard(
    modifier: Modifier = Modifier,
    timeFilterIndex: Int
) {
    // Resolve theme colors in composable scope; AgriTheme.colors cannot be read inside remember {}
    val chartBlueAccent = PlantBlueAccent
    val chartPrimaryColor = PlantDarkGreen
    val config = remember(timeFilterIndex, chartBlueAccent, chartPrimaryColor) {
        when (timeFilterIndex) {
            0 -> NutrientChartConfig(
                valueText = "High",
                unitText = "efficiency",
                badgeText = "✓ Optimal level",
                badgeIcon = Icons.Default.Check,
                badgeColor = chartBlueAccent,
                chartPoints = listOf(0.4f, 0.55f, 0.45f, 0.7f, 0.6f, 0.85f, 0.95f)
            )
            1 -> NutrientChartConfig(
                valueText = "92%",
                unitText = "serapan puncak",
                badgeText = "🔥 Puncak Serapan",
                badgeIcon = Icons.Default.Check,
                badgeColor = Color(0xFFEA580C),
                chartPoints = listOf(0.3f, 0.45f, 0.6f, 0.75f, 0.9f, 0.98f, 0.88f, 0.92f, 0.85f, 0.94f, 0.96f, 0.92f, 0.95f, 0.98f)
            )
            else -> NutrientChartConfig(
                valueText = "95%",
                unitText = "efisiensi total",
                badgeText = "✨ Siklus Sempurna",
                badgeIcon = Icons.Default.Check,
                badgeColor = chartPrimaryColor,
                chartPoints = listOf(0.15f, 0.3f, 0.5f, 0.75f, 0.92f, 0.95f, 0.92f, 0.88f, 0.85f, 0.82f, 0.8f)
            )
        }
    }

    val pathProgress = remember { Animatable(0f) }
    LaunchedEffect(timeFilterIndex) {
        pathProgress.snapTo(0f)
        pathProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(1200, easing = FastOutSlowInEasing)
        )
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "NUTRIENT UPTAKE",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = PlantTextSecondary,
                letterSpacing = 0.5.sp
            )

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = config.valueText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = PlantTextPrimary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = config.unitText,
                    fontSize = 10.sp,
                    color = PlantTextSecondary,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }

            // Smooth Canvas Wave Line Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val width = size.width
                    val height = size.height
                    val points = config.chartPoints
                    val spacing = width / (points.size - 1)

                    val strokePath = Path()
                    val fillPath = Path()

                    val firstY = height - (points[0] * height * 0.8f)
                    strokePath.moveTo(0f, firstY)
                    fillPath.moveTo(0f, height)
                    fillPath.lineTo(0f, firstY)

                    for (i in 0 until points.size - 1) {
                        val x1 = i * spacing
                        val y1 = height - (points[i] * height * 0.8f)
                        val x2 = (i + 1) * spacing
                        val y2 = height - (points[i + 1] * height * 0.8f)

                        val cx = (x1 + x2) / 2f
                        strokePath.cubicTo(cx, y1, cx, y2, x2, y2)
                        fillPath.cubicTo(cx, y1, cx, y2, x2, y2)
                    }

                    fillPath.lineTo(width, height)
                    fillPath.close()

                    val pathMeasure = PathMeasure()
                    pathMeasure.setPath(strokePath, false)
                    val animatedPath = Path()
                    pathMeasure.getSegment(0f, pathMeasure.length * pathProgress.value, animatedPath, true)

                    // Draw Area Gradient Fill
                    drawPath(
                        path = fillPath,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                config.badgeColor.copy(alpha = 0.3f * pathProgress.value),
                                Color.Transparent
                            )
                        )
                    )

                    // Draw Wave Line
                    drawPath(
                        path = animatedPath,
                        color = config.badgeColor,
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )

                    if (pathProgress.value > 0.9f) {
                        val lastX = width
                        val lastY = height - (points.last() * height * 0.8f)
                        drawCircle(
                            color = config.badgeColor.copy(alpha = 0.35f),
                            radius = 7.dp.toPx(),
                            center = Offset(lastX, lastY)
                        )
                        drawCircle(
                            color = config.badgeColor,
                            radius = 4.dp.toPx(),
                            center = Offset(lastX, lastY)
                        )
                    }
                }
            }

            // Footer Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = config.badgeIcon,
                    contentDescription = null,
                    tint = config.badgeColor,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = config.badgeText,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = config.badgeColor
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 5. SENSOR METRICS TRIPLET ROW (HUMIDITY, PH LEVEL, TEMP)
// ---------------------------------------------------------------------------
@Composable
fun SensorMetricsTripletRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Card 1: Humidity
        SensorMetricBoxItem(
            modifier = Modifier.weight(1f),
            title = "HUMIDITY",
            value = "65%",
            badgeText = "~2%",
            badgeColor = PlantBlueAccent,
            bgColor = AgriTheme.colors.blueInfoBg,
            borderColor = AgriTheme.colors.blueInfoBg,
            icon = Icons.Default.WaterDrop,
            iconTint = PlantBlueAccent
        )

        // Card 2: pH Level
        SensorMetricBoxItem(
            modifier = Modifier.weight(1f),
            title = "PH LEVEL",
            value = "6.5",
            badgeText = "~0%",
            badgeColor = PlantTextMuted,
            bgColor = AgriTheme.colors.purpleInfoBg,
            borderColor = AgriTheme.colors.purpleInfoBg,
            icon = Icons.Default.Science,
            iconTint = PlantPurpleAccent
        )

        // Card 3: Temperature
        SensorMetricBoxItem(
            modifier = Modifier.weight(1f),
            title = "TEMP",
            value = "24°C",
            badgeText = "~1°",
            badgeColor = PlantRedAccent,
            bgColor = AgriTheme.colors.yellowWarnBg,
            borderColor = AgriTheme.colors.yellowWarnBg,
            icon = Icons.Default.Thermostat,
            iconTint = PlantRedAccent
        )
    }
}

@Composable
fun SensorMetricBoxItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    badgeText: String,
    badgeColor: Color,
    bgColor: Color,
    borderColor: Color,
    icon: ImageVector,
    iconTint: Color
) {
    Card(
        modifier = modifier
            .height(116.dp)
            .clickableWithScale { },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Row Icon & Delta Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )

                Surface(
                    shape = RoundedCornerShape(50),
                    color = AgriTheme.colors.surface
                ) {
                    Text(
                        text = badgeText,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = badgeColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Value & Label
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PlantTextPrimary,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = title,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PlantTextSecondary,
                    letterSpacing = 0.25.sp
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 6. PLANT HEALTH DETAIL MODAL BOTTOM SHEET
// ---------------------------------------------------------------------------
@Composable
fun PlantHealthDetailBottomSheetContent(onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Detail Kesehatan Tanaman",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PlantTextPrimary
                )
                Text(
                    text = "Romaine Lettuce • Batch #RL-2026",
                    fontSize = 12.sp,
                    color = PlantTextSecondary
                )
            }

            Surface(
                modifier = Modifier
                    .size(32.dp)
                    .clickableWithScale { onClose() },
                shape = CircleShape,
                color = AgriTheme.colors.grayBgAlt
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(text = "✕", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PlantTextSecondary)
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Health Specs
        HealthStatRow(label = "Kadar Klorofil (SPAD)", value = "42.8", status = "Optimal", statusColor = PlantDarkGreen)
        HealthStatRow(label = "Kesehatan Akar", value = "98%", status = "Sangat Baik", statusColor = PlantDarkGreen)
        HealthStatRow(label = "Suhu Lingkungan Akar", value = "21.5°C", status = "Normal", statusColor = PlantBlueAccent)
        HealthStatRow(label = "Intensitas Cahaya (PPFD)", value = "320 µmol", status = "Cukup", statusColor = PlantDarkGreen)

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun HealthStatRow(
    label: String,
    value: String,
    status: String,
    statusColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AgriTheme.colors.iconBgLight, RoundedCornerShape(16.dp))
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = label, fontSize = 12.sp, color = PlantTextSecondary)
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = PlantTextPrimary)
        }

        Surface(
            shape = RoundedCornerShape(50),
            color = statusColor.copy(alpha = 0.12f)
        ) {
            Text(
                text = status,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = statusColor,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 7. BOTTOM NAVIGATION BAR FOR ANALYTICS SCREEN
// ---------------------------------------------------------------------------
@Composable
fun PlantAnalyticsBottomBar(
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
                // Item 0: Home
                PlantNavItem(
                    label = "Home",
                    icon = Icons.Default.GridView,
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )

                // Item 1: Controls / Tanam
                PlantNavItem(
                    label = "Controls",
                    icon = Icons.Outlined.LocalFlorist,
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )

                // Item 2: Center Floating FAB Spacer
                Spacer(modifier = Modifier.width(52.dp))

                // Item 3: Analytics (ACTIVE)
                PlantNavItem(
                    label = "Analytics",
                    icon = Icons.AutoMirrored.Filled.ShowChart,
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )

                // Item 4: Settings
                PlantNavItem(
                    label = "Settings",
                    icon = Icons.Default.Settings,
                    isSelected = selectedTab == 3,
                    onClick = { onTabSelected(3) }
                )
            }
        }

        // Elevated Center Green Gradient QR Scan Floating Button
        FloatingActionButton(
            onClick = { },
            modifier = Modifier
                .offset(y = (-22).dp)
                .size(58.dp)
                .clickableWithScale { },
            shape = CircleShape,
            containerColor = PlantDarkGreen,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(PlantDarkGreen, PlantLightGreen)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = "Quick Scan QR",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}

@Composable
fun PlantNavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickableWithScale { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) PlantDarkGreen else PlantTextMuted,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) PlantDarkGreen else PlantTextMuted
        )
    }
}

// ---------------------------------------------------------------------------
// HELPER EXTENSION FOR SPRING SCALE PRESS EFFECT
// ---------------------------------------------------------------------------
@Composable
fun Modifier.clickableWithScale(
    onClick: () -> Unit
): Modifier {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1.0f,
        animationSpec = tween(150),
        label = "pressScale"
    )

    return this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onClick
        )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AnalyticsDetailScreenFigmaPreview() {
    MyApplicationTheme {
        AnalyticsDetailScreen()
    }
}
