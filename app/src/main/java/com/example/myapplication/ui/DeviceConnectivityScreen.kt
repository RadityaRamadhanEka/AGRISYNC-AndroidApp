package com.example.myapplication.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BatteryFull
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.SignalCellularAlt
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceConnectivityScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    onNavigatePetani: () -> Unit = {}
) {
    var showAddDeviceModal by remember { mutableStateOf(false) }
    var selectedDeviceForOptions by remember { mutableStateOf<String?>(null) }
    val sheetState = rememberModalBottomSheetState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 4,
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateHome()
                        1 -> onNavigateControl()
                        2 -> onNavigateAnalytics()
                        3 -> onNavigatePetani()
                        4 -> {}
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Header Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AgriTheme.colors.surface)
                            .border(1.dp, AgriTheme.colors.border, CircleShape)
                            .clickable { onBackClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AgriTheme.colors.textPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Konektivitas Perangkat",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTheme.colors.textPrimary
                    )
                }
            }

            // Section 1: PERANGKAT TERHUBUNG
            item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "PERANGKAT TERHUBUNG",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = Color(0xFF5E8760)
                    )

                    // Device Card 1: Smart Grow Light v2
                    DeviceCardItem(
                        title = "Smart Grow Light v2",
                        statusText = "Online",
                        batteryText = "92%",
                        signalText = "Excellent",
                        zoneText = "Zone A",
                        icon = Icons.Outlined.Lightbulb,
                        iconBg = Color(0xFFE8F5E9),
                        iconTint = Color(0xFF2F7F33),
                        onOptionsClick = { selectedDeviceForOptions = "Smart Grow Light v2" }
                    )

                    // Device Card 2: PH Sensor Kit
                    DeviceCardItem(
                        title = "PH Sensor Kit",
                        statusText = "Online",
                        batteryText = "68%",
                        signalText = "Good",
                        zoneText = "Zone B",
                        icon = Icons.Outlined.WaterDrop,
                        iconBg = Color(0xFFEFF6FF),
                        iconTint = Color(0xFF2563EB),
                        onOptionsClick = { selectedDeviceForOptions = "PH Sensor Kit" }
                    )
                }
            }

            // Section 2: TAMBAH PERANGKAT BARU
            item {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(
                        text = "TAMBAH PERANGKAT BARU",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp,
                        color = Color(0xFF5E8760)
                    )

                    // Dashed Card Button for Adding New Device
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showAddDeviceModal = true },
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = AgriTheme.colors.surface.copy(alpha = 0.7f)
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            width = 2.dp,
                            color = Color(0xFF2F7F33).copy(alpha = 0.4f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp, horizontal = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Elevated Green Center Plus FAB
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2F7F33)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "Scan QR / Tambah Manual",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2F7F33)
                            )
                        }
                    }
                }
            }
        }
    }

    // Device Options Bottom Sheet
    selectedDeviceForOptions?.let { deviceName ->
        ModalBottomSheet(
            onDismissRequest = { selectedDeviceForOptions = null },
            sheetState = sheetState,
            containerColor = AgriTheme.colors.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = deviceName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary
                )

                DeviceOptionRow(
                    icon = Icons.Outlined.RestartAlt,
                    title = "Restart Perangkat",
                    onClick = {
                        selectedDeviceForOptions = null
                        scope.launch {
                            snackbarHostState.showSnackbar("Memulai ulang $deviceName...")
                        }
                    }
                )

                DeviceOptionRow(
                    icon = Icons.Outlined.Memory,
                    title = "Kalibrasi Sensor",
                    onClick = {
                        selectedDeviceForOptions = null
                        scope.launch {
                            snackbarHostState.showSnackbar("Proses kalibrasi $deviceName dimulai")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }

    // Add New Device Dialog (QR Scan or Manual)
    if (showAddDeviceModal) {
        var manualSerial by remember { mutableStateOf("") }
        var isScanning by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showAddDeviceModal = false },
            title = {
                Text(
                    text = "Tambah Perangkat IoT",
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Pindai kode QR pada fisik perangkat atau masukkan nomor seri secara manual.",
                        fontSize = 13.sp,
                        color = AgriTheme.colors.textSecondary
                    )

                    // Simulated QR Scanner Trigger Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFE8F5E9))
                            .clickable {
                                isScanning = true
                                scope.launch {
                                    delay(1000)
                                    isScanning = false
                                    showAddDeviceModal = false
                                    snackbarHostState.showSnackbar("Perangkat baru berhasil terhubung!")
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.QrCodeScanner,
                                contentDescription = "Scan",
                                tint = Color(0xFF2F7F33),
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isScanning) "Menghubungkan..." else "Buka Kamera Scan QR",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2F7F33)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = manualSerial,
                        onValueChange = { manualSerial = it },
                        label = { Text("Nomor Seri Perangkat") },
                        placeholder = { Text("Contoh: AGRI-9082-S3") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2F7F33),
                            unfocusedBorderColor = AgriTheme.colors.inputBorder
                        ),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (manualSerial.isNotBlank()) {
                            showAddDeviceModal = false
                            scope.launch {
                                snackbarHostState.showSnackbar("Perangkat $manualSerial ditambahkan!")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2F7F33))
                ) {
                    Text("Hubungkan", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDeviceModal = false }) {
                    Text("Batal", color = AgriTheme.colors.textMuted)
                }
            },
            containerColor = AgriTheme.colors.surface
        )
    }
}

@Composable
private fun DeviceCardItem(
    title: String,
    statusText: String,
    batteryText: String,
    signalText: String,
    zoneText: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    onOptionsClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = AgriTheme.colors.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Row: Icon, Title, Status & Options
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(iconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = title,
                            tint = iconTint,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = title,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.textPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Pulse glowing status indicator
                            val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                            val pulseScale by infiniteTransition.animateFloat(
                                initialValue = 0.8f,
                                targetValue = 1.2f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(800, easing = FastOutSlowInEasing),
                                    repeatMode = RepeatMode.Reverse
                                ),
                                label = "scale"
                            )

                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .scale(pulseScale)
                                    .background(Color(0xFF4ADE80), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = statusText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF2F7F33)
                            )
                        }
                    }
                }

                IconButton(onClick = onOptionsClick) {
                    Icon(
                        imageVector = Icons.Outlined.MoreHoriz,
                        contentDescription = "Options",
                        tint = AgriTheme.colors.textMuted
                    )
                }
            }

            HorizontalDivider(color = AgriTheme.colors.border, thickness = 1.dp)

            // Bottom Row: Battery, Signal, Zone
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.BatteryFull,
                        contentDescription = "Battery",
                        tint = AgriTheme.colors.textMuted,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = batteryText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = AgriTheme.colors.textSecondary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.SignalCellularAlt,
                        contentDescription = "Signal",
                        tint = AgriTheme.colors.textMuted,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = signalText,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = AgriTheme.colors.textSecondary
                    )
                }

                Text(
                    text = zoneText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF5E8760)
                )
            }
        }
    }
}

@Composable
private fun DeviceOptionRow(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(AgriTheme.colors.iconBgLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF2F7F33),
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = AgriTheme.colors.textPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeviceConnectivityScreenPreview() {
    MyApplicationTheme {
        DeviceConnectivityScreen()
    }
}
