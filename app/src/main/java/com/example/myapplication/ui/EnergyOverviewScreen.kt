package com.example.myapplication.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

// ---------------------------------------------------------------------------
// ENERGY OVERVIEW PALETTE (from Figma "Laporan Energi")
// Theme-aware tokens resolve via AgriTheme.colors; saturated accents stay static.
// ---------------------------------------------------------------------------
private val EnergyGreen: Color
    @Composable get() = AgriTheme.colors.accent
private val EnergyGreenText: Color
    @Composable get() = AgriTheme.colors.greenEmphasis
private val EnergyAmber = Color(0xFFF59E0B)
private val EnergyAmberText = Color(0xFFB45309)
private val EnergyOrange = Color(0xFFF97316)
private val EnergyTextDark: Color
    @Composable get() = AgriTheme.colors.textPrimary
private val EnergyTextMid: Color
    @Composable get() = AgriTheme.colors.textPrimary
private val EnergyTextSecondary: Color
    @Composable get() = AgriTheme.colors.textSecondary
private val EnergyTextMuted: Color
    @Composable get() = AgriTheme.colors.grayIcon
private val EnergyTextFaint: Color
    @Composable get() = AgriTheme.colors.textMuted
private val EnergyBorder: Color
    @Composable get() = AgriTheme.colors.border
private val EnergyGridLine: Color
    @Composable get() = AgriTheme.colors.grayBorder
private val EnergyGridBase: Color
    @Composable get() = AgriTheme.colors.inputBorder
private val EnergyBg: Color
    @Composable get() = AgriTheme.colors.background
private val EnergyTrack: Color
    @Composable get() = AgriTheme.colors.grayBgAlt
private val EnergyBlue = Color(0xFF3B82F6)
private val EnergyBlueBg: Color
    @Composable get() = AgriTheme.colors.blueInfoBg
private val EnergyPurple = Color(0xFF8B5CF6)
private val EnergyPurpleBg: Color
    @Composable get() = AgriTheme.colors.purpleInfoBg
private val EnergySlate = Color(0xFF64748B)
private val EnergySlateBg: Color
    @Composable get() = AgriTheme.colors.iconBgLight

// ---------------------------------------------------------------------------
// CHART DATA (24h, kWh)
// ---------------------------------------------------------------------------
private val ConsumptionProfile = floatArrayOf(
    0.6f, 0.5f, 0.4f, 0.4f, 0.5f, 0.8f, 1.3f, 1.8f, 2.2f, 2.6f, 3.0f, 3.3f,
    3.5f, 3.7f, 3.9f, 4.2f, 4.6f, 5.1f, 5.6f, 5.9f, 5.2f, 4.3f, 3.0f, 1.8f
)
private val SolarProfile = floatArrayOf(
    0f, 0f, 0f, 0f, 0.1f, 0.5f, 1.2f, 2.0f, 2.9f, 3.6f, 4.2f, 4.6f,
    4.8f, 4.7f, 4.3f, 3.8f, 3.0f, 2.1f, 1.2f, 0.5f, 0.15f, 0.05f, 0f, 0f
)
private const val ChartMaxY = 6f
private const val SolarPeakFraction = 12f / 23f

private data class EnergyBreakdownItem(
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color,
    val title: String,
    val subtitle: String,
    val usage: Float,
    val status: String,
    val statusColor: Color,
    val pulse: Boolean = false
)

@Composable
private fun energyBreakdownItems(): List<EnergyBreakdownItem> = listOf(
    EnergyBreakdownItem(
        icon = Icons.Default.WaterDrop,
        iconBg = EnergyBlueBg,
        iconTint = EnergyBlue,
        title = "Irrigation Systems",
        subtitle = "Automatic • 4h 30m",
        usage = 12.4f,
        status = "Optimal",
        statusColor = EnergyGreenText
    ),
    EnergyBreakdownItem(
        icon = Icons.Default.Lightbulb,
        iconBg = EnergyPurpleBg,
        iconTint = EnergyPurple,
        title = "Grow Lights",
        subtitle = "LED Spectrum • 12h",
        usage = 18.2f,
        status = "High Usage",
        statusColor = EnergyOrange,
        pulse = true
    )
)

// ---------------------------------------------------------------------------
// MAIN SCREEN
// ---------------------------------------------------------------------------
@Composable
fun EnergyOverviewScreen(
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateAnalitik: () -> Unit = {},
    onNavigatePetani: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var selectedDateText by remember { mutableStateOf("Today, 24 Oct") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = EnergyBg,
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 1, // "Kontrol" tab — Energy Overview lives under Kontrol
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateHome()
                        1 -> { /* Already on Kontrol */ }
                        2 -> onNavigateAnalitik()
                        3 -> onNavigatePetani()
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            EnergyTopBar(
                selectedDateText = selectedDateText,
                onBackClick = onBackClick,
                onCalendarClick = { showDatePickerDialog = true }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item { ChartSection(modifier = Modifier.staggeredAppear(0)) }
                item { KeyMetricsSection(modifier = Modifier.staggeredAppear(1)) }
                item { BreakdownSection(modifier = Modifier.staggeredAppear(3)) }
                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }

        if (showDatePickerDialog) {
            EnergyDatePickerDialog(
                currentDateText = selectedDateText,
                onDismiss = { showDatePickerDialog = false },
                onDateSelected = { newDate ->
                    selectedDateText = newDate
                    showDatePickerDialog = false
                }
            )
        }
    }
}

// ---------------------------------------------------------------------------
// TOP APP BAR
// ---------------------------------------------------------------------------
@Composable
private fun EnergyTopBar(
    selectedDateText: String,
    onBackClick: () -> Unit,
    onCalendarClick: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(EnergyBg.copy(alpha = 0.96f))
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 13.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                onClick = onBackClick
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Laporan Energi",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = EnergyTextDark
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = selectedDateText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = EnergyTextMuted
                )
            }
            CircleIconButton(
                icon = Icons.Default.DateRange,
                contentDescription = "Filter date",
                onClick = onCalendarClick
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(EnergyBorder)
        )
    }
}

@Composable
private fun CircleIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(AgriTheme.colors.surface)
            .border(1.dp, EnergyGridLine, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = EnergyTextDark,
            modifier = Modifier.size(20.dp)
        )
    }
}

// ---------------------------------------------------------------------------
// DATE PICKER DIALOG
// ---------------------------------------------------------------------------
@Composable
private fun EnergyDatePickerDialog(
    currentDateText: String,
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    var selectedPreset by remember {
        mutableStateOf(
            when {
                currentDateText.contains("Today", ignoreCase = true) || currentDateText.contains("Hari Ini", ignoreCase = true) -> "Hari Ini"
                currentDateText.contains("Yesterday", ignoreCase = true) || currentDateText.contains("Kemarin", ignoreCase = true) -> "Kemarin"
                currentDateText.contains("7", ignoreCase = true) -> "7 Hari"
                currentDateText.contains("30", ignoreCase = true) -> "30 Hari"
                else -> "Custom"
            }
        )
    }

    var selectedDay by remember { androidx.compose.runtime.mutableIntStateOf(24) }
    val displayedMonth = "Oktober 2026"
    val presets = listOf("Hari Ini", "Kemarin", "7 Hari", "30 Hari")

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(EnergyGreen.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = null,
                                tint = EnergyGreenText,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Filter Tanggal",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = EnergyTextDark
                            )
                            Text(
                                text = "Pilih rentang laporan energi",
                                fontSize = 12.sp,
                                color = EnergyTextMuted
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(EnergySlateBg)
                            .clickable { onDismiss() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = EnergyTextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(EnergyBorder)
                )

                // Presets
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    presets.forEach { preset ->
                        val isSelected = selectedPreset == preset
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isSelected) EnergyGreen.copy(alpha = 0.15f) else EnergyBg)
                                .border(
                                    width = 1.dp,
                                    color = if (isSelected) EnergyGreenText else EnergyBorder,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable {
                                    selectedPreset = preset
                                    when (preset) {
                                        "Hari Ini" -> selectedDay = 24
                                        "Kemarin" -> selectedDay = 23
                                        "7 Hari" -> selectedDay = 24
                                        "30 Hari" -> selectedDay = 24
                                    }
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = preset,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) EnergyGreenText else EnergyTextSecondary
                            )
                        }
                    }
                }

                // Month Navigation Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(EnergyBg, RoundedCornerShape(14.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Bulan Sebelumnya",
                            tint = EnergyTextDark
                        )
                    }
                    Text(
                        text = displayedMonth,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = EnergyTextDark
                    )
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Bulan Selanjutnya",
                            tint = EnergyTextDark
                        )
                    }
                }

                // Days of week header
                val daysOfWeek = listOf("Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    daysOfWeek.forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = EnergyTextMuted
                        )
                    }
                }

                // Calendar Grid for October 2026 (Starts on Thursday = offset 4)
                val totalDaysInOct = 31
                val startOffset = 4

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    for (row in 0 until 5) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (col in 0 until 7) {
                                val cellIndex = row * 7 + col
                                val dayNum = cellIndex - startOffset + 1
                                val isValidDay = dayNum in 1..totalDaysInOct

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(36.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isValidDay) {
                                        val isDaySelected = (selectedPreset == "Custom" || selectedPreset == "Hari Ini" || selectedPreset == "Kemarin") && selectedDay == dayNum
                                        val isToday = dayNum == 24

                                        Box(
                                            modifier = Modifier
                                                .size(32.dp)
                                                .clip(CircleShape)
                                                .background(
                                                    when {
                                                        isDaySelected -> EnergyGreen
                                                        isToday -> EnergyGreen.copy(alpha = 0.2f)
                                                        else -> Color.Transparent
                                                    }
                                                )
                                                .clickable {
                                                    selectedDay = dayNum
                                                    selectedPreset = if (dayNum == 24) "Hari Ini" else if (dayNum == 23) "Kemarin" else "Custom"
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "$dayNum",
                                                fontSize = 13.sp,
                                                fontWeight = if (isDaySelected || isToday) FontWeight.Bold else FontWeight.Medium,
                                                color = when {
                                                    isDaySelected -> Color.White
                                                    isToday -> EnergyGreenText
                                                    else -> EnergyTextDark
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Selected Info Card
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(EnergySlateBg, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Terpilih:",
                            fontSize = 12.sp,
                            color = EnergyTextMuted
                        )
                        Text(
                            text = when (selectedPreset) {
                                "Hari Ini" -> "Hari Ini, 24 Oct 2026"
                                "Kemarin" -> "Kemarin, 23 Oct 2026"
                                "7 Hari" -> "18 - 24 Oct 2026 (7 Hari)"
                                "30 Hari" -> "1 - 24 Oct 2026 (30 Hari)"
                                else -> "$selectedDay Oktober 2026"
                            },
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = EnergyTextDark
                        )
                    }
                }

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onDismiss() },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = EnergySlateBg)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Batal",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = EnergyTextSecondary
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1.3f)
                            .clickable {
                                val resultText = when (selectedPreset) {
                                    "Hari Ini" -> "Today, 24 Oct"
                                    "Kemarin" -> "Yesterday, 23 Oct"
                                    "7 Hari" -> "18 - 24 Oct"
                                    "30 Hari" -> "1 - 24 Oct"
                                    else -> "$selectedDay Oct 2026"
                                }
                                onDateSelected(resultText)
                            },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = EnergyGreenText)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = "Terapkan",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// CHART SECTION
// ---------------------------------------------------------------------------
@Composable
private fun ChartSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Energy Overview",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = EnergyTextDark
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem(color = EnergyGreen, label = "Consumption")
                LegendItem(color = EnergyAmber, label = "Solar Gen")
            }
        }

        EnergyChartCard()
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = EnergyTextSecondary
        )
    }
}

@Composable
private fun EnergyChartCard(modifier: Modifier = Modifier) {
    val chartProgress = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        delay(450)
        chartProgress.animateTo(1f, tween(1700, easing = FastOutSlowInEasing))
    }
    val showTooltip by remember { derivedStateOf { chartProgress.value > 0.85f } }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        border = BorderStroke(1.dp, EnergyBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column {
                    Text(
                        text = "Total Consumption",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = EnergyTextMuted
                    )
                    Spacer(Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val consumption = rememberCountUp(32f, delayMillis = 500)
                        Text(
                            text = "${formatKwh(consumption.value)} kWh",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = EnergyTextDark,
                            letterSpacing = (-0.75).sp
                        )
                        ConsumptionDeltaBadge()
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Solar Production",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = EnergyTextMuted
                    )
                    val production = rememberCountUp(45f, delayMillis = 650)
                    Text(
                        text = "${formatKwh(production.value)} kWh",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = EnergyAmberText,
                        letterSpacing = (-0.5).sp
                    )
                }
            }

            // Animated chart with tooltip
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                EnergyChart(progress = chartProgress.value, modifier = Modifier.fillMaxSize())
                androidx.compose.animation.AnimatedVisibility(
                    visible = showTooltip,
                    enter = scaleIn(
                        initialScale = 0.5f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    ) + fadeIn(tween(200)),
                    exit = fadeOut(tween(120)),
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = maxWidth * SolarPeakFraction - 74.dp, y = 2.dp)
                ) {
                    TooltipPill()
                }
            }

            // X axis labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("00:00", "06:00", "12:00", "18:00", "23:59").forEachIndexed { index, label ->
                    Text(
                        text = label,
                        fontSize = 12.sp,
                        fontWeight = if (index == 2) FontWeight.Bold else FontWeight.Medium,
                        color = if (index == 2) EnergyTextMid else EnergyTextFaint
                    )
                }
            }
        }
    }
}

@Composable
private fun ConsumptionDeltaBadge() {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(EnergyGreen.copy(alpha = 0.14f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.TrendingDown,
            contentDescription = null,
            tint = EnergyGreenText,
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = "-12%",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = EnergyGreenText
        )
    }
}

@Composable
private fun TooltipPill() {
    Box(
        modifier = Modifier
            .shadow(6.dp, RoundedCornerShape(8.dp), clip = false)
            .clip(RoundedCornerShape(8.dp))
            // Tooltip stays dark in both themes so the white label remains legible.
            .background(Color(0xFF111827))
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = "12:00 PM • Peak Solar",
            fontSize = 10.sp,
            color = Color.White
        )
    }
}

@Composable
private fun EnergyChart(progress: Float, modifier: Modifier = Modifier) {
    // Resolve theme colors in composition — the Canvas draw lambda is non-composable.
    val gridLineColor = AgriTheme.colors.grayBorder
    val gridBaseColor = AgriTheme.colors.inputBorder
    val consumptionColor = AgriTheme.colors.accent

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val topPad = 14.dp.toPx()
        val bottomPad = 2.dp.toPx()
        val chartH = h - topPad - bottomPad
        val stepX = w / (ConsumptionProfile.size - 1)

        fun mapY(value: Float): Float = topPad + (1f - value / ChartMaxY) * chartH

        fun headPoint(values: FloatArray, fraction: Float): Offset {
            val dist = fraction * (values.size - 1)
            val index = dist.toInt().coerceIn(0, values.size - 2)
            val t = dist - index
            val x = stepX * (index + t)
            val y = mapY(values[index]) + (mapY(values[index + 1]) - mapY(values[index])) * t
            return Offset(x, y)
        }

        // Grid lines: dashed + solid baseline
        val dash = PathEffect.dashPathEffect(floatArrayOf(10f, 12f))
        repeat(5) { i ->
            val y = topPad + chartH * i / 4f
            val isBase = i == 4
            drawLine(
                color = if (isBase) gridBaseColor else gridLineColor,
                start = Offset(0f, y),
                end = Offset(w, y),
                strokeWidth = 1.dp.toPx(),
                pathEffect = if (isBase) null else dash
            )
        }

        listOf(
            ConsumptionProfile to consumptionColor,
            SolarProfile to EnergyAmber
        ).forEach { (values, color) ->
            val linePath = Path().apply {
                moveTo(0f, mapY(values[0]))
                for (i in 1 until values.size) {
                    val prevX = stepX * (i - 1)
                    val x = stepX * i
                    val midX = (prevX + x) / 2f
                    cubicTo(midX, mapY(values[i - 1]), midX, mapY(values[i]), x, mapY(values[i]))
                }
            }
            val measure = PathMeasure().apply { setPath(linePath, false) }

            // Gradient fill, revealed with a clip synced to the line
            val fillPath = Path().apply {
                addPath(linePath)
                lineTo(w, h - bottomPad)
                lineTo(0f, h - bottomPad)
                close()
            }
            clipRect(right = w * progress) {
                drawPath(
                    fillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(color.copy(alpha = 0.20f), Color.Transparent),
                        startY = topPad,
                        endY = h - bottomPad
                    )
                )
            }

            // Line reveal via PathMeasure
            if (progress > 0f) {
                val partial = Path()
                measure.getSegment(0f, measure.length * progress, partial, true)
                drawPath(
                    partial,
                    color = color,
                    style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
                )
            }

            // Traveling head dot while the line is drawing
            if (progress > 0.01f && progress < 1f) {
                val head = headPoint(values, progress)
                drawCircle(color.copy(alpha = 0.30f), radius = 8.dp.toPx(), center = head)
                drawCircle(Color.White, radius = 4.5.dp.toPx(), center = head)
                drawCircle(color, radius = 3.5.dp.toPx(), center = head)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// KEY METRICS
// ---------------------------------------------------------------------------
@Composable
private fun KeyMetricsSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Key Metrics",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = EnergyTextDark
        )
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            EnergyMetricCard(
                icon = Icons.Default.BatteryChargingFull,
                iconBg = EnergyGreen.copy(alpha = 0.15f),
                iconTint = EnergyGreenText,
                label = "Battery Level",
                value = 85f,
                valueColor = EnergyGreenText,
                ringColor = EnergyGreen,
                delayMillis = 250
            )
            EnergyMetricCard(
                icon = Icons.Default.Power,
                iconBg = EnergySlateBg,
                iconTint = EnergySlate,
                label = "Grid Used",
                value = 10f,
                valueColor = EnergyTextMid,
                ringColor = EnergySlate,
                delayMillis = 400
            )
            EnergyMetricCard(
                icon = Icons.Default.WbSunny,
                iconBg = EnergyAmber.copy(alpha = 0.15f),
                iconTint = EnergyAmberText,
                label = "Solar Used",
                value = 90f,
                valueColor = EnergyAmberText,
                ringColor = EnergyAmber,
                delayMillis = 550
            )
        }
    }
}

@Composable
private fun EnergyMetricCard(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    label: String,
    value: Float,
    valueColor: Color,
    ringColor: Color,
    modifier: Modifier = Modifier,
    delayMillis: Int = 0
) {
    val count = rememberCountUp(value, delayMillis = delayMillis + 250)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        border = BorderStroke(1.dp, EnergyBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = iconTint,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = EnergyTextSecondary
                    )
                    Text(
                        text = "${count.value.roundToInt()}%",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = valueColor
                    )
                }
            }
            MiniRadialProgress(
                progress = value / 100f,
                color = ringColor,
                delayMillis = delayMillis + 350
            )
        }
    }
}

@Composable
private fun MiniRadialProgress(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier,
    delayMillis: Int = 0
) {
    val sweep = remember { Animatable(0f) }
    LaunchedEffect(progress) {
        delay(delayMillis.toLong())
        sweep.animateTo(progress, tween(1400, easing = FastOutSlowInEasing))
    }
    val trackColor = AgriTheme.colors.grayBgAlt

    Canvas(modifier = modifier.size(44.dp)) {
        val stroke = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
        // Inset by half the stroke width so the round caps stay inside bounds
        val strokePad = stroke.width / 2f + 1.dp.toPx()
        val arcSize = Size(size.width - strokePad * 2f, size.height - strokePad * 2f)
        val arcTopLeft = Offset(strokePad, strokePad)
        drawArc(
            color = trackColor,
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = arcTopLeft,
            size = arcSize,
            style = stroke
        )
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360f * sweep.value,
            useCenter = false,
            topLeft = arcTopLeft,
            size = arcSize,
            style = stroke
        )
    }
}

// ---------------------------------------------------------------------------
// BREAKDOWN
// ---------------------------------------------------------------------------
@Composable
private fun BreakdownSection(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Breakdown",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = EnergyTextDark
            )
            Text(
                text = "View Full Report",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = EnergyGreenText,
                modifier = Modifier.clickable { }
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            energyBreakdownItems().forEachIndexed { index, item ->
                EnergyBreakdownCard(
                    item = item,
                    modifier = Modifier.staggeredAppear(4 + index),
                    delayMillis = index * 150
                )
            }
        }
    }
}

@Composable
private fun EnergyBreakdownCard(
    item: EnergyBreakdownItem,
    modifier: Modifier = Modifier,
    delayMillis: Int = 0
) {
    val count = rememberCountUp(item.usage, delayMillis = delayMillis + 300)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        border = BorderStroke(1.dp, EnergyBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(17.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(item.iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = item.iconTint,
                        modifier = Modifier.size(19.dp)
                    )
                }
                Column {
                    Text(
                        text = item.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = EnergyTextDark
                    )
                    Text(
                        text = item.subtitle,
                        fontSize = 12.sp,
                        color = EnergyTextMuted
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${formatKwh(count.value, decimals = 1)} kWh",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = EnergyTextDark
                )
                Spacer(Modifier.height(2.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if (item.pulse) PulseDot(color = item.statusColor)
                    Text(
                        text = item.status,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = item.statusColor
                    )
                }
            }
        }
    }
}

@Composable
private fun PulseDot(color: Color) {
    val infinite = rememberInfiniteTransition(label = "pulseDot")
    val glow by infinite.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseDotAlpha"
    )
    Box(
        modifier = Modifier
            .size(6.dp)
            .graphicsLayer { alpha = glow }
            .clip(CircleShape)
            .background(color)
    )
}

// ---------------------------------------------------------------------------
// HELPERS
// ---------------------------------------------------------------------------
@Composable
private fun rememberCountUp(
    target: Float,
    delayMillis: Int = 0,
    durationMillis: Int = 1300
) = remember(target) { Animatable(0f) }.also { animatable ->
    LaunchedEffect(target) {
        delay(delayMillis.toLong())
        animatable.animateTo(target, tween(durationMillis, easing = FastOutSlowInEasing))
    }
}

private fun formatKwh(value: Float, decimals: Int = 0): String =
    if (decimals == 0) value.roundToInt().toString()
    else String.format(java.util.Locale.US, "%.${decimals}f", value)

@Composable
private fun Modifier.staggeredAppear(index: Int): Modifier {
    var appeared by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { appeared = true }

    val alpha by animateFloatAsState(
        targetValue = if (appeared) 1f else 0f,
        animationSpec = tween(420, index * 80, FastOutSlowInEasing),
        label = "energyStaggerAlpha"
    )
    val translationY by animateFloatAsState(
        targetValue = if (appeared) 0f else 34f,
        animationSpec = tween(420, index * 80, FastOutSlowInEasing),
        label = "energyStaggerY"
    )

    return this.graphicsLayer(alpha = alpha, translationY = translationY)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun EnergyOverviewScreenPreview() {
    MyApplicationTheme {
        EnergyOverviewScreen()
    }
}
