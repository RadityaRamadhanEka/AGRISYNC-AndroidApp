package com.example.myapplication.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// ---------------------------------------------------------------------------
// DATA MODELS & ENUMS
// ---------------------------------------------------------------------------

enum class ActivityTabFilter(val title: String) {
    ALL("All"),
    ALERTS("Alerts"),
    SYSTEMS("Systems")
}

enum class ActivitySeverity {
    CRITICAL,
    WARNING,
    INFO,
    SUCCESS
}

data class ActivityLogModel(
    val id: String,
    val title: String,
    val description: String,
    val timestamp: String,
    val isAlert: Boolean, // true = Alerts tab, false = Systems tab
    val severity: ActivitySeverity,
    val badgeBgColor: Color,
    val badgeRingColor: Color,
    val iconTint: Color,
    val iconVector: ImageVector,
    val zoneName: String = "Zone A - Hydroponics",
    val sensorId: String = "SN-9042",
    val recommendedAction: String? = null
)

// Sample Activity Logs matching the Figma design
val sampleActivityLogs = listOf(
    ActivityLogModel(
        id = "LOG-101",
        title = "pH Critical Warning",
        description = "Acidity levels dropped below 5.5 in Zone A. Check nutrient solution immediately.",
        timestamp = "2m ago",
        isAlert = true,
        severity = ActivitySeverity.CRITICAL,
        badgeBgColor = Color(0xFFFEF2F2),
        badgeRingColor = Color(0xFFFEE2E2),
        iconTint = Color(0xFFEF4444),
        iconVector = Icons.Default.Warning,
        zoneName = "Zone A - Main Hydroponic Tank",
        sensorId = "pH-SENS-01",
        recommendedAction = "Tambahkan larutan pH Up sebanyak 50ml dan lakukan resirkulasi air."
    ),
    ActivityLogModel(
        id = "LOG-102",
        title = "Nutrient level adjusted",
        description = "Auto-dosing completed for Formula B. Concentration stable.",
        timestamp = "1h ago",
        isAlert = false,
        severity = ActivitySeverity.SUCCESS,
        badgeBgColor = Color(0xFFEFF6FF),
        badgeRingColor = Color(0xFFDBEAFE),
        iconTint = Color(0xFF3B82F6),
        iconVector = Icons.Outlined.Science,
        zoneName = "Dosing Pump Station 2",
        sensorId = "EC-DOS-03",
        recommendedAction = "Tidak ada tindakan yang diperlukan, konsentrasi EC berada pada 1.8 mS/cm."
    ),
    ActivityLogModel(
        id = "LOG-103",
        title = "Scheduled Lighting On",
        description = "Main Grow Room switched to Day Mode intensity (80%).",
        timestamp = "2h ago",
        isAlert = false,
        severity = ActivitySeverity.INFO,
        badgeBgColor = Color(0xFFFFFBEB),
        badgeRingColor = Color(0xFFFEF3C7),
        iconTint = Color(0xFFF59E0B),
        iconVector = Icons.Default.WbSunny,
        zoneName = "Main Grow Room - Array 1",
        sensorId = "LIGHT-CTRL-08",
        recommendedAction = "Lampu otomatis mati dalam 12 jam sesuai jadwal siklus fotosintesis."
    ),
    ActivityLogModel(
        id = "LOG-104",
        title = "Temperature Spike Warning",
        description = "Temperature in Zone C reached 31.5°C. Auxiliary misting fans deployed.",
        timestamp = "3h ago",
        isAlert = true,
        severity = ActivitySeverity.WARNING,
        badgeBgColor = Color(0xFFFFF7ED),
        badgeRingColor = Color(0xFFFFEDD5),
        iconTint = Color(0xFFF97316),
        iconVector = Icons.Default.Thermostat,
        zoneName = "Zone C - Seedling Nursery",
        sensorId = "TEMP-HUM-04",
        recommendedAction = "Periksa ventilasi udara samping dan shading net greenhouse."
    ),
    ActivityLogModel(
        id = "LOG-105",
        title = "AI Airflow Optimization",
        description = "Fan speed adjusted to reduce humidity pockets based on sensor array 4.",
        timestamp = "4h ago",
        isAlert = false,
        severity = ActivitySeverity.SUCCESS,
        badgeBgColor = Color(0xFFF0FDF4),
        badgeRingColor = Color(0xFFBBF7D0),
        iconTint = Color(0xFF22C55E),
        iconVector = Icons.Default.AutoAwesome,
        zoneName = "Sensors Array 4 - Canopy Zone",
        sensorId = "AI-FLOW-99",
        recommendedAction = "Kecepatan kipas meningkat menjadi 1200 RPM untuk meratakan kelembapan."
    ),
    ActivityLogModel(
        id = "LOG-106",
        title = "Irrigation Cycle Complete",
        description = "Zone B watering completed successfully. Soil moisture at 65% target.",
        timestamp = "6h ago",
        isAlert = false,
        severity = ActivitySeverity.SUCCESS,
        badgeBgColor = Color(0xFFECFEFF),
        badgeRingColor = Color(0xFFCFFAFE),
        iconTint = Color(0xFF06B6D4),
        iconVector = Icons.Default.WaterDrop,
        zoneName = "Zone B - Romaine Cultivation",
        sensorId = "IRRIG-VALVE-02",
        recommendedAction = "Siklus pengairan berikutnya dijadwalkan pukul 16:00 WIB."
    ),
    ActivityLogModel(
        id = "LOG-107",
        title = "Low Water Reservoir Warning",
        description = "Main reservoir level dropped to 18%. Auto-refill valve engaged.",
        timestamp = "8h ago",
        isAlert = true,
        severity = ActivitySeverity.WARNING,
        badgeBgColor = Color(0xFFFEF2F2),
        badgeRingColor = Color(0xFFFEE2E2),
        iconTint = Color(0xFFEF4444),
        iconVector = Icons.Default.WaterDrop,
        zoneName = "Main Water Tank - 1000L",
        sensorId = "LEVEL-FLOAT-01",
        recommendedAction = "Katup otomatis mengisi ulang hingga batas 80% kapasitas."
    ),
    ActivityLogModel(
        id = "LOG-108",
        title = "Daily System Check",
        description = "All sensors calibrated. Network connection stable.",
        timestamp = "12h ago",
        isAlert = false,
        severity = ActivitySeverity.INFO,
        badgeBgColor = Color(0xFFF9FAFB),
        badgeRingColor = Color(0xFFF3F4F6),
        iconTint = Color(0xFF6B7280),
        iconVector = Icons.Default.Shield,
        zoneName = "System Gateway Hub v2",
        sensorId = "GATEWAY-01",
        recommendedAction = "Semua 24 sensor dalam kondisi online dan bekerja normal."
    )
)

// ---------------------------------------------------------------------------
// MAIN SCREEN COMPOSABLE
// ---------------------------------------------------------------------------

@Composable
fun ActivityLogScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
    onNavigateToAnalytics: () -> Unit = {},
    onNavigateToDigitalTwin: () -> Unit = {},
    onNavigateToControl: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(ActivityTabFilter.ALL) }
    var selectedBottomTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    var expandedLogId by remember { mutableStateOf<String?>(null) }
    var acknowledgedLogIds by remember { mutableStateOf(setOf<String>()) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Refresh simulation
    val triggerRefresh: () -> Unit = {
        scope.launch {
            isRefreshing = true
            delay(800)
            isRefreshing = false
            snackbarHostState.showSnackbar("Log aktivitas berhasil diperbarui")
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriBgColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = selectedBottomTab,
                onTabSelected = { tab ->
                    selectedBottomTab = tab
                    when (tab) {
                        0 -> onNavigateToHome()
                        1 -> onNavigateToControl()
                        2 -> onNavigateToAnalytics()
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
            // 1. Top Header Bar
            ActivityLogHeader(
                onBackClick = onBackClick,
                isSearchVisible = isSearchVisible,
                onToggleSearch = { isSearchVisible = !isSearchVisible },
                isRefreshing = isRefreshing,
                onRefreshClick = triggerRefresh
            )

            // 2. Collapsible Search Bar
            AnimatedVisibility(
                visible = isSearchVisible,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(
                                "Cari log (misal: pH, Airflow, Zone A)...",
                                fontSize = 13.sp,
                                color = AgriTextMuted
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = AgriGreenDark
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Clear",
                                        tint = AgriTextMuted
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = AgriGreenPrimary,
                            unfocusedBorderColor = Color(0xFFE5E7EB)
                        )
                    )
                }
            }

            // 3. Segmented Filter Tabs (All, Alerts, Systems)
            SegmentedTabsSection(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it },
                allCount = sampleActivityLogs.size,
                alertsCount = sampleActivityLogs.count { it.isAlert },
                systemsCount = sampleActivityLogs.count { !it.isAlert }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Activity Logs Timeline List
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    if (targetState.ordinal > initialState.ordinal) {
                        (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> -width } + fadeOut()
                        )
                    } else {
                        (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> width } + fadeOut()
                        )
                    }
                },
                modifier = Modifier.weight(1f),
                label = "TabContentTransition"
            ) { currentTab ->
                val logsForCurrentTab = sampleActivityLogs.filter { log ->
                    val matchesTab = when (currentTab) {
                        ActivityTabFilter.ALL -> true
                        ActivityTabFilter.ALERTS -> log.isAlert
                        ActivityTabFilter.SYSTEMS -> !log.isAlert
                    }
                    val matchesSearch = if (searchQuery.isBlank()) true else {
                        log.title.contains(searchQuery, ignoreCase = true) ||
                                log.description.contains(searchQuery, ignoreCase = true) ||
                                log.zoneName.contains(searchQuery, ignoreCase = true)
                    }
                    matchesTab && matchesSearch
                }

                if (logsForCurrentTab.isEmpty()) {
                    EmptyLogsView(
                        tabName = currentTab.title,
                        searchQuery = searchQuery,
                        onResetSearch = { searchQuery = "" }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            end = 20.dp,
                            top = 12.dp,
                            bottom = 24.dp
                        )
                    ) {
                        itemsIndexed(
                            items = logsForCurrentTab,
                            key = { _, item -> item.id }
                        ) { index, log ->
                            val isExpanded = expandedLogId == log.id
                            val isAcknowledged = acknowledgedLogIds.contains(log.id)
                            val isFirst = index == 0
                            val isLast = index == logsForCurrentTab.lastIndex

                            TimelineActivityItem(
                                log = log,
                                isFirst = isFirst,
                                isLast = isLast,
                                isExpanded = isExpanded,
                                isAcknowledged = isAcknowledged,
                                onItemClick = {
                                    expandedLogId = if (isExpanded) null else log.id
                                },
                                onAcknowledge = {
                                    acknowledgedLogIds = acknowledgedLogIds + log.id
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Peringatan '${log.title}' telah ditandai selesai")
                                    }
                                },
                                onActionClick = {
                                    if (log.title.contains("pH", ignoreCase = true) || log.title.contains("Nutrient", ignoreCase = true)) {
                                        onNavigateToDigitalTwin()
                                    } else {
                                        onNavigateToAnalytics()
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. HEADER COMPOSABLE
// ---------------------------------------------------------------------------

@Composable
fun ActivityLogHeader(
    onBackClick: () -> Unit,
    isSearchVisible: Boolean,
    onToggleSearch: () -> Unit,
    isRefreshing: Boolean,
    onRefreshClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = AgriBgColor,
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Circular White Back Button
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .shadow(elevation = 2.dp, shape = CircleShape)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, Color(0xFFE5E7EB), CircleShape)
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Color(0xFF111827),
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = "Activity Log",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827)
                )
            }

            // Action Buttons (Search & Refresh)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Search Toggle Button
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(if (isSearchVisible) AgriGreenLight else Color.Transparent)
                        .clickable { onToggleSearch() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Logs",
                        tint = if (isSearchVisible) AgriGreenDark else Color(0xFF6B7280),
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Refresh Button with Animation
                val infiniteTransition = rememberInfiniteTransition(label = "refreshSpin")
                val rotationAngle by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = if (isRefreshing) 360f else 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "spin"
                )

                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .clickable { onRefreshClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh Logs",
                        tint = Color(0xFF6B7280),
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(if (isRefreshing) rotationAngle else 0f)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 2. SEGMENTED TABS COMPOSABLE
// ---------------------------------------------------------------------------

@Composable
fun SegmentedTabsSection(
    selectedTab: ActivityTabFilter,
    onTabSelected: (ActivityTabFilter) -> Unit,
    allCount: Int,
    alertsCount: Int,
    systemsCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        // Outer Container Pill
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = Color(0x99E5E7EB) // rgba(229, 231, 235, 0.6)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActivityTabFilter.entries.forEach { tab ->
                    val isSelected = selectedTab == tab

                    val tabBgColor by animateColorAsState(
                        targetValue = if (isSelected) Color.White else Color.Transparent,
                        animationSpec = tween(durationMillis = 200),
                        label = "tabBgColor"
                    )

                    val textColor by animateColorAsState(
                        targetValue = if (isSelected) AgriGreenDark else Color(0xFF6B7280),
                        animationSpec = tween(durationMillis = 200),
                        label = "textColor"
                    )

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(38.dp)
                            .shadow(
                                elevation = if (isSelected) 2.dp else 0.dp,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .background(tabBgColor)
                            .clickable { onTabSelected(tab) },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = tab.title,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                color = textColor
                            )

                            // Alert indicator dot or count badge for Alerts
                            if (tab == ActivityTabFilter.ALERTS && alertsCount > 0) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFEF4444))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 3. TIMELINE ACTIVITY ITEM COMPOSABLE
// ---------------------------------------------------------------------------

@Composable
fun TimelineActivityItem(
    log: ActivityLogModel,
    isFirst: Boolean,
    isLast: Boolean,
    isExpanded: Boolean,
    isAcknowledged: Boolean,
    onItemClick: () -> Unit,
    onAcknowledge: () -> Unit,
    onActionClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(bottom = if (isLast) 0.dp else 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Node Icon Badge Column (With connected vertical timeline line)
        Box(
            modifier = Modifier
                .width(48.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.TopCenter
        ) {
            // Timeline Vertical Line (Seamlessly connects badges)
            val lineModifier = when {
                isFirst && isLast -> Modifier.height(0.dp)
                isFirst -> Modifier
                    .padding(top = 24.dp)
                    .fillMaxHeight()
                isLast -> Modifier.height(24.dp)
                else -> Modifier.fillMaxHeight()
            }

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .then(lineModifier)
                    .background(Color(0xFFE5E7EB))
            )

            // Pulsing Glow effect for CRITICAL alerts
            if (log.severity == ActivitySeverity.CRITICAL && !isAcknowledged) {
                val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                val pulseScale by infiniteTransition.animateFloat(
                    initialValue = 1.0f,
                    targetValue = 1.25f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "pulseScale"
                )
                val pulseAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.4f,
                    targetValue = 0.0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "pulseAlpha"
                )

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .scale(pulseScale)
                        .clip(CircleShape)
                        .background(Color(0xFFEF4444).copy(alpha = pulseAlpha))
                )
            }

            // Outer Badge Circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .shadow(elevation = 1.dp, shape = CircleShape)
                    .clip(CircleShape)
                    .background(log.badgeBgColor)
                    .border(2.dp, Color.White, CircleShape)
                    .border(1.dp, log.badgeRingColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = log.iconVector,
                    contentDescription = log.title,
                    tint = log.iconTint,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        // Content Card Column
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { onItemClick() }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = log.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111827),
                    modifier = Modifier.weight(1f)
                )

                // Time Badge Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9999.dp))
                        .background(
                            if (log.severity == ActivitySeverity.CRITICAL && !isAcknowledged)
                                Color(0xFFFEF2F2)
                            else
                                Color.Transparent
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = log.timestamp,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (log.severity == ActivitySeverity.CRITICAL && !isAcknowledged)
                            Color(0xFFEF4444)
                        else
                            Color(0xFF9CA3AF)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = log.description,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF6B7280),
                lineHeight = 22.sp
            )

            // Expanded Detail View
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(1.dp, Color(0xFFE5E7EB), RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = log.zoneName,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriGreenDark
                        )
                        Text(
                            text = "Sensor: ${log.sensorId}",
                            fontSize = 11.sp,
                            color = AgriTextMuted
                        )
                    }

                    if (log.recommendedAction != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalDivider(color = Color(0xFFF3F4F6))
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Rekomendasi",
                                tint = AgriGreenPrimary,
                                modifier = Modifier
                                    .size(16.dp)
                                    .offset(y = 2.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = log.recommendedAction,
                                fontSize = 12.sp,
                                color = Color(0xFF374151),
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (log.isAlert && !isAcknowledged) {
                            TextButton(
                                onClick = onAcknowledge,
                                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFEF4444))
                            ) {
                                Text("Tandai Selesai", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        Button(
                            onClick = onActionClick,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AgriGreenDark),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Buka Telemetri", fontSize = 12.sp, color = Color.White)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = "Buka",
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF3F4F6))
        }
    }
}

// ---------------------------------------------------------------------------
// 4. EMPTY LOGS STATE VIEW
// ---------------------------------------------------------------------------

@Composable
fun EmptyLogsView(
    tabName: String,
    searchQuery: String,
    onResetSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(AgriGreenMintCard),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Empty",
                tint = AgriGreenDark,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (searchQuery.isNotEmpty()) "Pencarian Tidak Ditemukan" else "Tidak Ada Log $tabName",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF111827)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (searchQuery.isNotEmpty())
                "Tidak ada hasil untuk '$searchQuery'. Coba kata kunci lain."
            else
                "Semua sistem bekerja optimal dan tidak ada riwayat $tabName saat ini.",
            fontSize = 13.sp,
            color = Color(0xFF6B7280),
            modifier = Modifier.padding(horizontal = 16.dp),
            lineHeight = 20.sp
        )

        if (searchQuery.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onResetSearch,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AgriGreenDark)
            ) {
                Text("Hapus Filter Pencarian", fontSize = 13.sp)
            }
        }
    }
}

// ---------------------------------------------------------------------------
// PREVIEW
// ---------------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ActivityLogScreenPreview() {
    ActivityLogScreen()
}
