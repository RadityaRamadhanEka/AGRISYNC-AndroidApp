package com.example.myapplication.ui

import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Science
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

// Digital Twin Theme Colors (theme-aware: follow AgriTheme light/dark)
val AgriDarkSystemHealth: Color
    @Composable get() = AgriTheme.colors.greenDeep
val AgriToggleActiveTrack: Color
    @Composable get() = AgriTheme.colors.accent
val AgriToggleActiveThumb = Color.White
val AgriToggleInactiveTrack: Color
    @Composable get() = AgriTheme.colors.grayBorder

@Composable
fun DigitalTwinScreen(
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = onBackClick,
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    onNavigatePetani: () -> Unit = {},
    onScanClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriBgColor,
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 1, // Tab "Kontrol"
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateHome()
                        1 -> onNavigateControl()
                        2 -> onNavigateAnalytics()
                        3 -> onNavigatePetani()
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onScanClick,
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
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Top Bar Navigation
            item {
                DigitalTwinTopHeader(onBackClick = onBackClick)
            }

            // 2. Live Feed Rendering 3D Card
            item {
                LiveFeedRenderingCard()
            }

            // 3. Kendali Ekosistem 2x2 Grid
            item {
                EcosystemControlSection()
            }

            // 4. System Health Dark Banner
            item {
                SystemHealthBannerCard()
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 1. TOP HEADER SECTION
// ---------------------------------------------------------------------------
@Composable
fun DigitalTwinTopHeader(onBackClick: () -> Unit) {
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
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                imageVector = Icons.Default.GridView,
                contentDescription = null,
                tint = AgriGreenDark,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Digital Twin",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTextDark
            )
        }

        // Profile Avatar Badge
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(AgriTheme.colors.purpleInfoBg),
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
// 2. LIVE FEED RENDERING 3D CARD WITH SENSOR OVERLAYS
// ---------------------------------------------------------------------------
@Composable
fun LiveFeedRenderingCard() {
    var isFullscreenOpen by remember { mutableStateOf(false) }

    if (isFullscreenOpen) {
        Interactive3DFullscreenDialog(onDismiss = { isFullscreenOpen = false })
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(380.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(28.dp))
        ) {
            // 3D Hydroponic Farm Render Image
            Image(
                painter = painterResource(id = R.drawable.img_digital_twin),
                contentDescription = "3D Digital Twin Hydroponic Render",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // --- Sensor Floating Tags Overlaid ---

            // Tag 1: Top Left - Suhu: 24°C
            SensorPillTag(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 24.dp, top = 40.dp),
                icon = Icons.Default.Thermostat,
                label = "Suhu: 24°C",
                iconTint = AgriGreenDark
            )

            // Tag 2: Middle Right - CO2 & Kelembaban Dual Pill
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 20.dp)
                    .offset(y = (20).dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.End
            ) {
                SensorPillTag(
                    icon = Icons.Outlined.Eco,
                    label = "CO2: 800ppm",
                    iconTint = AgriGreenDark
                )
                SensorPillTag(
                    icon = Icons.Default.WaterDrop,
                    label = "Kelembaban: 65%",
                    iconTint = AgriGreenDark
                )
            }

            // --- Bottom Bar inside Render Image ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Live Feed Indicator
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF22C55E))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "LIVE FEED RENDERING",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                }

                // Fullscreen Icon
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.4f))
                        .clickable { isFullscreenOpen = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = "Fullscreen",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// INTERACTIVE 3D FULLSCREEN DIALOG (REAL-TIME GLB 3D WEBGL MESH RENDERER)
// ---------------------------------------------------------------------------
@Composable
fun Interactive3DFullscreenDialog(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0B1320))
        ) {
            // Real-Time 3D GLB Model WebGL Viewer
            Glb3DWebViewer(
                glbAssetFileName = "hidroponik.glb",
                modifier = Modifier.fillMaxSize()
            )

            // Top Control Bar Overlay
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 44.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.White
                    )
                }

                // Live Badge
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.Black.copy(alpha = 0.6f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF22C55E))
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "REAL-TIME 3D GLB VIEW",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Empty Spacer for symmetry
                Spacer(modifier = Modifier.size(42.dp))
            }

            // Bottom Instruction Banner
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 36.dp),
                shape = RoundedCornerShape(50),
                color = Color.Black.copy(alpha = 0.7f)
            ) {
                Text(
                    text = "💡 Sentuh & Usap layar untuk memutar 3D Mesh 360° | Pinch untuk Zoom",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
        }
    }
}

@Composable
fun Glb3DWebViewer(
    glbAssetFileName: String = "hidroponik.glb",
    modifier: Modifier = Modifier
) {
    val htmlContent = remember(glbAssetFileName) {
        """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
            <style>
                body, html { margin: 0; padding: 0; width: 100%; height: 100%; overflow: hidden; background-color: #0b1320; }
                model-viewer { width: 100vw; height: 100vh; background-color: #0b1320; }
            </style>
            <script type="module" src="https://ajax.googleapis.com/ajax/libs/model-viewer/3.4.0/model-viewer.min.js"></script>
        </head>
        <body>
            <model-viewer 
                src="models/$glbAssetFileName" 
                alt="3D Hydroponic Farm Model"
                auto-rotate 
                camera-controls 
                shadow-intensity="1"
                exposure="1"
                bounds="tight">
            </model-viewer>
        </body>
        </html>
        """.trimIndent()
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.allowFileAccess = true
                settings.allowContentAccess = true
                settings.allowFileAccessFromFileURLs = true
                settings.allowUniversalAccessFromFileURLs = true
                settings.domStorageEnabled = true
                settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                webViewClient = WebViewClient()
                loadDataWithBaseURL("file:///android_asset/", htmlContent, "text/html", "UTF-8", null)
            }
        }
    )
}

@Composable
fun SensorPillTag(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    iconTint: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = Color.White.copy(alpha = 0.92f),
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTextDark
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 3. KENDALI EKOSISTEM 2X2 GRID
// ---------------------------------------------------------------------------
@Composable
fun EcosystemControlSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kendali Ekosistem",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTextDark
            )

            Surface(
                shape = RoundedCornerShape(50),
                color = AgriGreenMintCard
            ) {
                Text(
                    text = "4 DEVICES ACTIVE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriGreenDark,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 2x2 Controls Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Column 1
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Card 1: Lampu Grow
                ControlCardItem(
                    title = "LAMPU GROW",
                    value = "14:00 - 22:00",
                    initialState = true,
                    icon = Icons.Default.WbSunny,
                    iconBgActive = AgriGreenLight,
                    progressType = 1
                )

                // Card 3: Nutrisi A/B
                ControlCardItem(
                    title = "NUTRISI A/B",
                    value = "Dosing 2.4ml/s",
                    initialState = true,
                    icon = Icons.Default.Science,
                    iconBgActive = AgriGreenLight,
                    progressType = 2
                )
            }

            // Column 2
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Card 2: Pompa Air
                ControlCardItem(
                    title = "POMPA AIR",
                    value = "OFF",
                    subtext = "Next cycle: 18:00",
                    initialState = false,
                    icon = Icons.Default.WaterDrop,
                    iconBgActive = Color(0xFFE0F2FE),
                    progressType = 0
                )

                // Card 4: Kipas
                ControlCardItem(
                    title = "KIPAS",
                    value = "Standby",
                    subtext = "Trigger at >26°C",
                    initialState = false,
                    icon = Icons.Default.Air,
                    iconBgActive = Color(0xFFF1F5F9),
                    progressType = 0
                )
            }
        }
    }
}

@Composable
fun ControlCardItem(
    title: String,
    value: String,
    subtext: String? = null,
    initialState: Boolean,
    icon: ImageVector,
    iconBgActive: Color,
    progressType: Int // 0: None, 1: Full active bar, 2: Segmented bar
) {
    var isChecked by remember { mutableStateOf(initialState) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isChecked) iconBgActive else AgriTheme.colors.grayBgAlt),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = if (isChecked) AgriGreenDark else AgriTheme.colors.grayIcon,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Switch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AgriToggleActiveThumb,
                        checkedTrackColor = AgriToggleActiveTrack,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = AgriToggleInactiveTrack
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = AgriGreenDark
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTextDark
            )

            if (subtext != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtext,
                    fontSize = 11.sp,
                    color = AgriTextMuted
                )
            }

            // Progress bar / Indicator line at bottom
            if (progressType == 1) {
                Spacer(modifier = Modifier.height(10.dp))
                LinearProgressIndicator(
                    progress = { 0.65f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = AgriGreenPrimary,
                    trackColor = AgriTheme.colors.grayBorder
                )
            } else if (progressType == 2) {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(AgriGreenPrimary)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(AgriGreenPrimary)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(AgriTheme.colors.grayBorder)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 4. SYSTEM HEALTH DARK BANNER CARD
// ---------------------------------------------------------------------------
@Composable
fun SystemHealthBannerCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriDarkSystemHealth)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "SYSTEM HEALTH",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriGreenPrimary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "98.4",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "%",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "UPTIME",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriGreenPrimary,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "124 Hari",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 5. BOTTOM BAR FOR DIGITAL TWIN SCREEN
// ---------------------------------------------------------------------------
@Composable
fun DigitalTwinBottomBar(
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
                DigitalTwinNavItem(
                    label = "HOME",
                    icon = Icons.Default.GridView,
                    isSelected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )
                DigitalTwinNavItem(
                    label = "CONTROLS",
                    icon = Icons.Default.Settings,
                    isSelected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )
                Spacer(modifier = Modifier.width(52.dp))
                DigitalTwinNavItem(
                    label = "TANAM",
                    icon = Icons.Outlined.LocalFlorist,
                    isSelected = selectedTab == 2,
                    onClick = { onTabSelected(2) }
                )
                DigitalTwinNavItem(
                    label = "PETANI",
                    icon = Icons.Default.Person,
                    isSelected = selectedTab == 3,
                    onClick = { onTabSelected(3) }
                )
}

        }
    }
}

@Composable
fun DigitalTwinNavItem(
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
fun DigitalTwinScreenPreview() {
    MyApplicationTheme {
        DigitalTwinScreen()
    }
}
