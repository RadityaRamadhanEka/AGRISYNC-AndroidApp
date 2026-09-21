package com.example.myapplication.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

// ---------------------------------------------------------------------------
// IoT CONTROL CENTER PALETTE
// ---------------------------------------------------------------------------
private val IotAmber = Color(0xFFF59E0B)
private val IotAmberDark = Color(0xFFB45309)
private val IotAmberBrown = Color(0xFF7C5615)
private val IotAmberBg = Color(0xFFFFF8E7)
private val IotAmberBorder = Color(0x33F59E0B)
private val IotActiveGreen = Color(0xFF4ADE80)
private val IotDotInactive = Color(0xFFD1D5DB)
private val IotSegmentBg = Color(0xFFEAF0EA)
private val IotCardBorder = Color(0xFFE5E7EB)
private val IotInactiveIconBg = Color(0xFFF1F5F9)
private val IotAlertRed = Color(0xFFEF4444)
private val IotInfoBlue = Color(0xFF3B82F6)
private val IotTextGray = Color(0xFF9CA3AF)
private val IotSegmentText = Color(0xFF6B7280)

// ---------------------------------------------------------------------------
// DATA MODELS
// ---------------------------------------------------------------------------
enum class IotFilter(val title: String) {
    ALL("All"),
    ALERTS("Alerts"),
    SYSTEM("System")
}

enum class IotSeverity { CRITICAL, WARNING, INFO }

data class IotDevice(
    val name: String,
    val icon: ImageVector,
    val isActive: Boolean,
    val statusLabel: String,
    val detail: String,
    val accent: Color,
    val softBg: Color,
)

data class IotAlert(
    val title: String,
    val message: String,
    val time: String,
    val severity: IotSeverity,
    val icon: ImageVector
)

private fun defaultIotDevices(): List<IotDevice> = listOf(
    IotDevice(
        name = "Grow Light",
        icon = Icons.Default.Lightbulb,
        isActive = false,
        statusLabel = "Off",
        detail = "Schedule 14:00 – 22:00",
        accent = IotAmber,
        softBg = Color(0xFFFEF3C7)
    ),
    IotDevice(
        name = "Water Pump",
        icon = Icons.Default.WaterDrop,
        isActive = true,
        statusLabel = "Active",
        detail = "Next cycle 18:00",
        accent = AgriBlueWater,
        softBg = Color(0xFFE0F2FE)
    ),
    IotDevice(
        name = "Nutrients",
        icon = Icons.Default.Science,
        isActive = false,
        statusLabel = "Off",
        detail = "Dosing 2.4 ml/s",
        accent = Color(0xFF8B5CF6),
        softBg = Color(0xFFEDE9FE)
    ),
    IotDevice(
        name = "Ventilation",
        icon = Icons.Default.Air,
        isActive = false,
        statusLabel = "Off",
        detail = "Trigger above 26°C",
        accent = Color(0xFF64748B),
        softBg = Color(0xFFF1F5F9)
    )
)

private val sampleIotAlerts = listOf(
    IotAlert(
        title = "Water Pump Overpressure",
        message = "Pressure reached 2.8 bar. Auto-shutoff engaged to protect the line.",
        time = "2 min ago",
        severity = IotSeverity.CRITICAL,
        icon = Icons.Default.Warning
    ),
    IotAlert(
        title = "pH Level Out of Range",
        message = "Reservoir pH is 5.1 against a target band of 5.8 – 6.2.",
        time = "18 min ago",
        severity = IotSeverity.WARNING,
        icon = Icons.Default.Opacity
    ),
    IotAlert(
        title = "Nutrient Reservoir Low",
        message = "32% remaining. Refill recommended before the next dosing cycle.",
        time = "1 hr ago",
        severity = IotSeverity.INFO,
        icon = Icons.Default.Science
    )
)

// ---------------------------------------------------------------------------
// MAIN SCREEN
// ---------------------------------------------------------------------------
@Composable
fun IotControlCenterScreen(
    onNavigateHome: () -> Unit = {},
    onNavigateAnalitik: () -> Unit = {},
    onNavigateEnergy: () -> Unit = {},
    onNavigatePetani: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isAutoMode by remember { mutableStateOf(false) }
    var filter by remember { mutableStateOf(IotFilter.ALL) }
    var devices by remember { mutableStateOf(defaultIotDevices()) }

    val counts = mapOf(
        IotFilter.ALL to devices.size,
        IotFilter.ALERTS to sampleIotAlerts.size,
        IotFilter.SYSTEM to 4
    )

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriBgColor,
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 1, // Tab "Kontrol" is active on this screen
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateHome()
                        2 -> onNavigateAnalitik()
                        3 -> onNavigatePetani()
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { IotHeader(Modifier.staggeredAppear(0)) }

            item { SystemModeSection(isAutoMode, { isAutoMode = it }, Modifier.staggeredAppear(1)) }

            item { IotFilterTabs(filter, counts, { filter = it }, Modifier.staggeredAppear(2)) }

            item {
                AnimatedContent(
                    targetState = filter,
                    transitionSpec = {
                        (fadeIn(tween(280)) + slideInHorizontally { it / 10 }) togetherWith fadeOut(tween(160))
                    },
                    label = "iotFilterContent"
                ) { selected ->
                    when (selected) {
                        IotFilter.ALL -> DevicesContent(
                            devices = devices,
                            onToggle = { name ->
                                devices = devices.map { device ->
                                    if (device.name == name) {
                                        val nowActive = !device.isActive
                                        device.copy(isActive = nowActive, statusLabel = if (nowActive) "Active" else "Off")
                                    } else {
                                        device
                                    }
                                }
                            },
                            onOpenEnergyMonitor = onNavigateEnergy
                        )

                        IotFilter.ALERTS -> AlertsContent()
                        IotFilter.SYSTEM -> SystemContent(onOpenEnergyMonitor = onNavigateEnergy)
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// HEADER
// ---------------------------------------------------------------------------
@Composable
private fun IotHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "AGRISYNC",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AgriGreenDark,
                letterSpacing = 1.2.sp
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "IoT Control Center",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTextDark,
                letterSpacing = (-0.6).sp
            )
        }
        NotificationBellButton()
    }
}

@Composable
private fun NotificationBellButton() {
    var hasUnread by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .size(46.dp)
            .clip(CircleShape)
            .background(AgriGreenLight)
            .clickable { hasUnread = false },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.NotificationsNone,
            contentDescription = "Notifikasi",
            tint = AgriGreenDark,
            modifier = Modifier.size(22.dp)
        )
        if (hasUnread) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 10.dp)
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(IotAlertRed)
                    .border(1.5.dp, Color.White, CircleShape)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// SYSTEM MODE (Auto / Manual sliding segmented control)
// ---------------------------------------------------------------------------
@Composable
private fun SystemModeSection(
    isAuto: Boolean,
    onModeChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SYSTEM MODE",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = AgriTextMuted,
                letterSpacing = 0.35.sp
            )
            AiOptimizedBadge()
        }

        Spacer(Modifier.height(12.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            color = IotSegmentBg
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                val half = maxWidth / 2
                val indicatorX by animateDpAsState(
                    targetValue = if (isAuto) 0.dp else half,
                    animationSpec = tween(300, easing = FastOutSlowInEasing),
                    label = "modeIndicator"
                )

                Box(
                    modifier = Modifier
                        .offset(x = indicatorX)
                        .width(half)
                        .fillMaxHeight()
                        .shadow(2.dp, RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                )

                Row(modifier = Modifier.fillMaxSize()) {
                    ModeSegment(
                        label = "Auto",
                        icon = Icons.Default.AutoAwesome,
                        selected = isAuto,
                        onClick = { onModeChange(true) },
                        modifier = Modifier.weight(1f)
                    )

                    ModeSegment(
                        label = "Manual",
                        icon = Icons.Default.TouchApp,
                        selected = !isAuto,
                        onClick = { onModeChange(false) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun AiOptimizedBadge() {
    val infinite = rememberInfiniteTransition(label = "aiBadge")
    val dotAlpha by infinite.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "aiDotAlpha"
    )
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = AgriGreenDark.copy(alpha = 0.10f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .graphicsLayer { alpha = dotAlpha }
                    .clip(CircleShape)
                    .background(AgriGreenDark)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = "AI Optimized",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = AgriGreenDark
            )
        }
    }
}

@Composable
private fun ModeSegment(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tint by animateColorAsState(
        targetValue = if (selected) AgriGreenDark else IotSegmentText,
        animationSpec = tween(250),
        label = "modeSegmentTint"
    )
    Row(
        modifier = modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(17.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = tint
        )
    }
}

// ---------------------------------------------------------------------------
// FILTER TABS (All / Alerts / System) with sliding pill
// ---------------------------------------------------------------------------
@Composable
private fun IotFilterTabs(
    selected: IotFilter,
    counts: Map<IotFilter, Int>,
    onSelect: (IotFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color(0x99E5E7EB)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            val segWidth = maxWidth / IotFilter.entries.size
            val indicatorX by animateDpAsState(
                targetValue = segWidth * selected.ordinal,
                animationSpec = tween(280, easing = FastOutSlowInEasing),
                label = "filterIndicator"
            )

            Box(
                modifier = Modifier
                    .offset(x = indicatorX)
                    .width(segWidth)
                    .height(38.dp)
                    .shadow(2.dp, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                IotFilter.entries.forEach { tab ->
                    FilterTabItem(
                        title = tab.title,
                        count = counts[tab] ?: 0,
                        selected = selected == tab,
                        onClick = { onSelect(tab) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterTabItem(
    title: String,
    count: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor by animateColorAsState(
        targetValue = if (selected) AgriGreenDark else IotSegmentText,
        animationSpec = tween(220),
        label = "filterTabColor"
    )
    val showBadge = count > 0 && title != "All"
    Row(
        modifier = modifier
            .height(38.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.SemiBold,
            color = textColor
        )
        if (showBadge) {
            Spacer(Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (selected) AgriGreenDark.copy(alpha = 0.12f) else Color(0x1A6B7280))
                    .padding(horizontal = 6.dp, vertical = 1.dp)
            ) {
                Text(
                    text = count.toString(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// TAB CONTENT: ALL DEVICES
// ---------------------------------------------------------------------------
@Composable
private fun DevicesContent(
    devices: List<IotDevice>,
    onToggle: (String) -> Unit,
    onOpenEnergyMonitor: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = "Device Controls",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextDark
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "${devices.count { it.isActive }} of ${devices.size} devices active",
                fontSize = 13.sp,
                color = AgriTextMuted
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            devices.chunked(2).forEach { rowDevices ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    rowDevices.forEach { device ->
                        IoTDeviceCard(
                            device = device,
                            onClick = { onToggle(device.name) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (rowDevices.size == 1) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }

        SolarArrayStatusCard(onOpenEnergyMonitor = onOpenEnergyMonitor)
    }
}

@Composable
private fun IoTDeviceCard(
    device: IotDevice,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.96f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium),
        label = "deviceCardScale"
    )
    val borderColor by animateColorAsState(
        targetValue = if (device.isActive) IotActiveGreen else IotCardBorder,
        animationSpec = tween(300),
        label = "deviceCardBorder"
    )
    val container by animateColorAsState(
        targetValue = if (device.isActive) IotActiveGreen.copy(alpha = 0.07f) else Color.White,
        animationSpec = tween(300),
        label = "deviceCardBg"
    )
    val iconBg by animateColorAsState(
        targetValue = if (device.isActive) device.softBg else IotInactiveIconBg,
        animationSpec = tween(300),
        label = "deviceIconBg"
    )
    val iconTint by animateColorAsState(
        targetValue = if (device.isActive) device.accent else IotTextGray,
        animationSpec = tween(300),
        label = "deviceIconTint"
    )
    val statusColor by animateColorAsState(
        targetValue = if (device.isActive) AgriGreenDark else IotTextGray,
        animationSpec = tween(300),
        label = "deviceStatusColor"
    )
    val statusPillBg by animateColorAsState(
        targetValue = if (device.isActive) IotActiveGreen.copy(alpha = 0.16f) else IotDotInactive.copy(alpha = 0.3f),
        animationSpec = tween(300),
        label = "deviceStatusPillBg"
    )

    Card(
        modifier = modifier
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clickable(interactionSource = interaction, indication = null) { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = container),
        border = BorderStroke(1.5.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = device.icon,
                        contentDescription = device.name,
                        tint = iconTint,
                        modifier = Modifier.size(21.dp)
                    )
                }
                Switch(
                    checked = device.isActive,
                    onCheckedChange = { onClick() },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = IotActiveGreen,
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = IotDotInactive.copy(alpha = 0.55f),
                        uncheckedBorderColor = IotDotInactive
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = device.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))

            // Status pill — makes the ON/OFF state unmistakable
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(statusPillBg)
                    .padding(horizontal = 9.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(statusColor)
                )
                Text(
                    text = device.statusLabel,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = statusColor
                )
            }
            Spacer(Modifier.height(3.dp))
            Text(
                text = device.detail,
                fontSize = 11.sp,
                color = AgriTextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// ---------------------------------------------------------------------------
// SOLAR / ENERGY MONITOR CARD
// ---------------------------------------------------------------------------
@Composable
private fun SolarArrayStatusCard(
    onOpenEnergyMonitor: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = IotAmberBg),
        border = BorderStroke(1.dp, IotAmberBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            // Decorative amber glow, top-right
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 44.dp, y = (-44).dp)
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(IotAmber.copy(alpha = 0.24f), Color.Transparent)
                        )
                    )
            )

            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Solar Array Status",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = IotAmberBrown
                    )
                    HarvestingBadge()
                }

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PanelBadge(label = "Panel A", icon = Icons.Default.WbSunny, iconTint = IotAmber)

                    EnergyFlowLine(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp)
                    )

                    PanelBadge(
                        label = "78%",
                        icon = Icons.Default.BatteryChargingFull,
                        iconTint = IotAmber,
                        ring = true
                    )
                }

                Spacer(Modifier.height(16.dp))

                EnergyMonitorButton(onClick = onOpenEnergyMonitor)
            }
        }
    }
}

@Composable
private fun EnergyMonitorButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium),
        label = "energyMonitorScale"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer { scaleX = scale; scaleY = scale }
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .border(1.dp, IotAmberBorder, RoundedCornerShape(14.dp))
            .clickable(interactionSource = interaction, indication = null) { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(IotAmber.copy(alpha = 0.16f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = IotAmberDark,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Energy Monitor",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = IotAmberBrown
                )
                Text(
                    text = "Consumption, generation & breakdown",
                    fontSize = 10.sp,
                    color = IotTextGray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Open Energy Monitor",
                tint = IotAmberDark,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun HarvestingBadge() {
    val infinite = rememberInfiniteTransition(label = "harvesting")
    val glow by infinite.animateFloat(
        initialValue = 0.35f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "harvestingGlow"
    )
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = IotAmber.copy(alpha = 0.2f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .graphicsLayer { alpha = glow }
                    .clip(CircleShape)
                    .background(IotAmberDark)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = "HARVESTING",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = IotAmberDark,
                letterSpacing = 0.5.sp
            )
        }
    }
}

@Composable
private fun PanelBadge(
    label: String,
    icon: ImageVector,
    iconTint: Color,
    ring: Boolean = false
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(46.dp), contentAlignment = Alignment.Center) {
            if (ring) {
                ChargingRing(modifier = Modifier.fillMaxSize())
            }
            Surface(
                modifier = Modifier.size(if (ring) 36.dp else 44.dp),
                shape = CircleShape,
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = label,
                        tint = iconTint,
                        modifier = Modifier.size(if (ring) 18.dp else 22.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = AgriTextMuted
        )
    }
}

@Composable
private fun ChargingRing(modifier: Modifier = Modifier) {
    val infinite = rememberInfiniteTransition(label = "chargingRing")
    val sweep by infinite.animateFloat(
        initialValue = 40f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1900, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "chargingSweep"
    )
    Canvas(modifier = modifier) {
        val stroke = 2.5.dp.toPx()
        // Inset by half the stroke width so the round caps never get clipped
        val strokePad = stroke / 2f + 1.dp.toPx()
        val arcSize = Size(size.width - strokePad * 2f, size.height - strokePad * 2f)
        val arcTopLeft = Offset(strokePad, strokePad)
        drawArc(
            color = IotAmber.copy(alpha = 0.22f),
            startAngle = -90f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = arcTopLeft,
            size = arcSize,
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
        drawArc(
            color = IotAmber,
            startAngle = -90f,
            sweepAngle = sweep,
            useCenter = false,
            topLeft = arcTopLeft,
            size = arcSize,
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun EnergyFlowLine(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val width = maxWidth
            val infinite = rememberInfiniteTransition(label = "energyFlow")
            val travel by infinite.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(1500, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "energyTravel"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(IotAmber.copy(alpha = 0.18f))
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    IotAmber.copy(alpha = 0.35f),
                                    IotAmber.copy(alpha = 0.75f),
                                    IotAmber.copy(alpha = 0.35f)
                                )
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .offset(x = width * travel - 22.dp)
                        .width(44.dp)
                        .fillMaxHeight()
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color.Transparent, Color.White.copy(alpha = 0.9f), Color.Transparent)
                            )
                        )
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(
            text = "450W",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = IotAmberDark
        )
    }
}

// ---------------------------------------------------------------------------
// TAB CONTENT: ALERTS
// ---------------------------------------------------------------------------
@Composable
private fun AlertsContent() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Active Alerts",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextDark
            )
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = IotAlertRed.copy(alpha = 0.12f)
            ) {
                Text(
                    text = "${sampleIotAlerts.size} NEW",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = IotAlertRed,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        sampleIotAlerts.forEach { alert ->
            IotAlertCard(alert)
        }
    }
}

@Composable
private fun IotAlertCard(alert: IotAlert) {
    val accent = when (alert.severity) {
        IotSeverity.CRITICAL -> IotAlertRed
        IotSeverity.WARNING -> IotAmber
        IotSeverity.INFO -> IotInfoBlue
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, IotCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = alert.icon,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (alert.severity == IotSeverity.CRITICAL) {
                            CriticalPulseDot(accent)
                            Spacer(Modifier.width(6.dp))
                        }
                        Text(
                            text = alert.title,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTextDark,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = alert.time,
                        fontSize = 11.sp,
                        color = AgriTextMuted
                    )
                }

                Spacer(Modifier.height(4.dp))
                Text(
                    text = alert.message,
                    fontSize = 13.sp,
                    color = AgriTextMuted
                )

                Spacer(Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = accent.copy(alpha = 0.12f),
                    modifier = Modifier.clickable { }
                ) {
                    Text(
                        text = "Acknowledge",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = accent,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CriticalPulseDot(color: Color) {
    val infinite = rememberInfiniteTransition(label = "criticalPulse")
    val scale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "criticalScale"
    )
    val alpha by infinite.animateFloat(
        initialValue = 0.6f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "criticalAlpha"
    )
    Box(modifier = Modifier.size(10.dp), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .graphicsLayer { scaleX = scale; scaleY = scale; this.alpha = alpha }
                .clip(CircleShape)
                .background(color)
        )
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
    }
}

// ---------------------------------------------------------------------------
// TAB CONTENT: SYSTEM
// ---------------------------------------------------------------------------
@Composable
private fun SystemContent(onOpenEnergyMonitor: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SystemStatusBanner()

        SolarArrayStatusCard(onOpenEnergyMonitor = onOpenEnergyMonitor)

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                TelemetryTile(
                    label = "Uptime",
                    value = "124 Hari",
                    icon = Icons.Default.Shield,
                    accent = IotActiveGreen,
                    modifier = Modifier.weight(1f)
                )
                TelemetryTile(
                    label = "Health",
                    value = "98.4%",
                    icon = Icons.Default.CheckCircle,
                    accent = AgriGreenPrimary,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                TelemetryTile(
                    label = "Network",
                    value = "Online",
                    icon = Icons.Default.Wifi,
                    accent = IotInfoBlue,
                    modifier = Modifier.weight(1f)
                )
                TelemetryTile(
                    label = "Battery",
                    value = "78%",
                    icon = Icons.Default.BatteryChargingFull,
                    accent = IotAmber,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun SystemStatusBanner() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AgriDarkSystemHealth),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(AgriGreenPrimary.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = AgriGreenPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Column {
                Text(
                    text = "All Systems Operational",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "24 sensors online • synced 2 min ago",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun TelemetryTile(
    label: String,
    value: String,
    icon: ImageVector,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, IotCardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(accent.copy(alpha = 0.14f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = accent,
                    modifier = Modifier.size(19.dp)
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTextDark
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                color = AgriTextMuted
            )
        }
    }
}

// ---------------------------------------------------------------------------
// STAGGERED ENTRANCE ANIMATION
// ---------------------------------------------------------------------------
@Composable
private fun Modifier.staggeredAppear(index: Int): Modifier {
    var appeared by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { appeared = true }

    val alpha by animateFloatAsState(
        targetValue = if (appeared) 1f else 0f,
        animationSpec = tween(
            durationMillis = 420,
            delayMillis = index * 80,
            easing = FastOutSlowInEasing
        ),
        label = "staggerAlpha"
    )
    val translationY by animateFloatAsState(
        targetValue = if (appeared) 0f else 34f,
        animationSpec = tween(
            durationMillis = 420,
            delayMillis = index * 80,
            easing = FastOutSlowInEasing
        ),
        label = "staggerTranslation"
    )

    return this.graphicsLayer(alpha = alpha, translationY = translationY)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun IotControlCenterScreenPreview() {
    MyApplicationTheme {
        IotControlCenterScreen()
    }
}