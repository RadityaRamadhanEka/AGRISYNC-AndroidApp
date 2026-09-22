package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterCenterFocus
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PlantScanScreen(
    onBackClick: () -> Unit = {},
    onNavigateToRecommendation: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isFlashOn by remember { mutableStateOf(false) }
    var isScanning by remember { mutableStateOf(true) }
    var scanProgress by remember { mutableFloatStateOf(0.65f) }
    var showResultSheet by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Continuous Progress Animation (Simulating AI computer vision analysis)
    LaunchedEffect(isScanning) {
        if (isScanning) {
            scanProgress = 0.2f
            while (scanProgress < 0.95f) {
                delay(120)
                scanProgress += 0.03f
            }
        }
    }

    // Trigger Scan Complete
    fun triggerScanComplete() {
        coroutineScope.launch {
            scanProgress = 1.0f
            delay(300)
            isScanning = false
            showResultSheet = true
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0A120B))
    ) {
        // 1. Fullscreen Camera Viewfinder Preview (Green Leaf Background)
        Image(
            painter = painterResource(id = R.drawable.img_bayam),
            contentDescription = "Camera Preview Leaf",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Dark Semi-Transparent Vignette & Glassmorphism Mask
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.65f),
                            Color.Black.copy(alpha = 0.35f),
                            Color.Black.copy(alpha = 0.85f)
                        )
                    )
                )
        )

        // Flash Light Burst Effect Overlay
        AnimatedVisibility(
            visible = isFlashOn,
            enter = fadeIn(tween(150)),
            exit = fadeOut(tween(250))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = 0.22f))
            )
        }

        // 2. Main Scan Content Overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar Header Controls (Back & Flash Toggle)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Glassmorphism Back Button
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { onBackClick() },
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.35f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Screen Title Badge
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.Black.copy(alpha = 0.4f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.15f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color(0xFF4ADE80),
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "AI PLANT SCANNER",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }
                }

                // Glassmorphism Flash Toggle Button
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { isFlashOn = !isFlashOn },
                    shape = CircleShape,
                    color = if (isFlashOn) Color(0xFF4ADE80).copy(alpha = 0.3f) else Color.Black.copy(alpha = 0.35f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isFlashOn) Color(0xFF4ADE80) else Color.White.copy(alpha = 0.2f)
                    )
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isFlashOn) Icons.Default.FlashOn else Icons.Default.FlashOff,
                            contentDescription = "Toggle Flash",
                            tint = if (isFlashOn) Color(0xFF4ADE80) else Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // 3. Central Viewfinder Bounding Box (300.dp x 300.dp)
            Box(
                modifier = Modifier
                    .size(290.dp)
                    .offset(y = (-20).dp),
                contentAlignment = Alignment.Center
            ) {
                // Viewfinder Bounding Corner Brackets Frame Component
                ScanningViewfinderFrame(isScanning = isScanning)

                // Floating Sensor Tag (CHLOROPHYLL LEVEL - Optimal)
                FloatingSensorTag(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 24.dp, y = 30.dp)
                )

                // Bottom Analysis Progress Section
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 70.dp)
                        .fillMaxWidth(0.92f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isScanning) "ANALYZING PLANT..." else "SCAN COMPLETE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4ADE80),
                            letterSpacing = 1.sp,
                            modifier = Modifier.shadow(8.dp, spotColor = Color(0xFF4ADE80))
                        )
                        Text(
                            text = "${(scanProgress * 100).toInt()}%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            letterSpacing = 1.sp
                        )
                    }

                    // Progress Bar Fill with Glow Head
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        shape = RoundedCornerShape(50),
                        color = Color(0xFF374151).copy(alpha = 0.6f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.15f))
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(scanProgress)
                                    .clip(RoundedCornerShape(50))
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(Color(0xFF2E7D32), Color(0xFF4ADE80))
                                        )
                                    )
                            )
                        }
                    }
                }
            }

            // 4. Bottom Capture & Instruction Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(28.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                // Instruction Pill Chip
                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color.Black.copy(alpha = 0.45f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.15f))
                ) {
                    Text(
                        text = "Posisikan daun di dalam bingkai untuk menganalisis",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 9.dp)
                    )
                }

                // Concentric Ring Pulsing Shutter Button
                PulsingShutterButton(
                    onClick = { triggerScanComplete() }
                )
            }
        }

        // 5. Scan Results Bottom Sheet Modal
        AnimatedVisibility(
            visible = showResultSheet,
            enter = slideInVertically(tween(400)) { it } + fadeIn(tween(400)),
            exit = slideOutVertically(tween(300)) { it } + fadeOut(tween(300)),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            ScanResultSheet(
                onDismiss = {
                    showResultSheet = false
                    isScanning = true
                    scanProgress = 0.2f
                },
                onViewRecommendation = {
                    showResultSheet = false
                    onNavigateToRecommendation()
                }
            )
        }
    }
}

// ---------------------------------------------------------------------------
// VIEWFINDER SCANNING FRAME & ANIMATED LASER BAR
// ---------------------------------------------------------------------------
@Composable
fun ScanningViewfinderFrame(isScanning: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "ViewfinderLoop")

    // Corner bracket glow pulse
    val cornerGlowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowPulse"
    )

    // Vertical Laser Scanning Bar Movement (0f to 1f)
    val laserPosition by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "LaserBar"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // 4 Glowing Corner Brackets
        val cornerColor = Color(0xFF4ADE80).copy(alpha = cornerGlowAlpha)
        val cornerSize = 42.dp
        val strokeThickness = 4.dp

        // Top Left Corner
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(cornerSize)
                .drawCornerBracket(cornerColor, strokeThickness, isTop = true, isLeft = true)
        )

        // Top Right Corner
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(cornerSize)
                .drawCornerBracket(cornerColor, strokeThickness, isTop = true, isLeft = false)
        )

        // Bottom Left Corner
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(cornerSize)
                .drawCornerBracket(cornerColor, strokeThickness, isTop = false, isLeft = true)
        )

        // Bottom Right Corner
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(cornerSize)
                .drawCornerBracket(cornerColor, strokeThickness, isTop = false, isLeft = false)
        )

        // Center Reticle Focus Icon
        Icon(
            imageVector = Icons.Default.FilterCenterFocus,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.35f),
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.Center)
        )

        // ANIMATED LASER SCANNING BAR
        if (isScanning) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .offset(y = (280 * laserPosition).dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF4ADE80),
                                Color.Transparent
                            )
                        )
                    )
                    .shadow(12.dp, spotColor = Color(0xFF4ADE80))
            )
        }
    }
}

// Extension to draw bracket corners
private fun Modifier.drawCornerBracket(
    color: Color,
    thickness: androidx.compose.ui.unit.Dp,
    isTop: Boolean,
    isLeft: Boolean
): Modifier {
    return this.background(Color.Transparent)
        .border(
            width = thickness,
            color = color,
            shape = RoundedCornerShape(
                topStart = if (isTop && isLeft) 16.dp else 2.dp,
                topEnd = if (isTop && !isLeft) 16.dp else 2.dp,
                bottomStart = if (!isTop && isLeft) 16.dp else 2.dp,
                bottomEnd = if (!isTop && !isLeft) 16.dp else 2.dp
            )
        )
}

// ---------------------------------------------------------------------------
// FLOATING SENSOR TAG (CHLOROPHYLL LEVEL - Optimal)
// ---------------------------------------------------------------------------
@Composable
fun FloatingSensorTag(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = Color.White.copy(alpha = 0.95f),
        shadowElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E7D32).copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = "CHLOROPHYLL LEVEL",
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6B7280),
                letterSpacing = 0.5.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Optimal",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF2E7D32)
                )

                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF2E7D32),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// PULSING CONCENTRIC RINGS SHUTTER BUTTON
// ---------------------------------------------------------------------------
@Composable
fun PulsingShutterButton(onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "ShutterRings")

    val ringScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Ring1"
    )

    val ringAlpha by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RingAlpha1"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(110.dp)
    ) {
        // Outer Animated Pulsing Ring
        Box(
            modifier = Modifier
                .size((72 * ringScale).dp)
                .clip(CircleShape)
                .background(Color(0xFF2E7D32).copy(alpha = ringAlpha))
                .border(1.dp, Color(0xFF4ADE80).copy(alpha = ringAlpha), CircleShape)
        )

        // Outer Fixed Border Ring
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(2.dp, Color(0xFF2E7D32).copy(alpha = 0.6f), CircleShape)
        )

        // Main Trigger Button
        Surface(
            modifier = Modifier
                .size(66.dp)
                .clip(CircleShape)
                .clickable { onClick() },
            shape = CircleShape,
            color = Color(0xFF2E7D32),
            shadowElevation = 8.dp
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                // Inner White Capture Circle
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// SCAN RESULT BOTTOM SHEET (AI DIAGNOSIS & HEALTH REPORT)
// ---------------------------------------------------------------------------
@Composable
fun ScanResultSheet(
    onDismiss: () -> Unit,
    onViewRecommendation: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        color = AgriTheme.colors.surface,
        shadowElevation = 24.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Drag handle pill bar
            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(5.dp)
                    .clip(CircleShape)
                    .background(AgriTheme.colors.grayBorder)
                    .align(Alignment.CenterHorizontally)
            )

            // Header Row: Plant Identification Title + Close Button
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
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(AgriTheme.colors.mintBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Spa,
                            contentDescription = null,
                            tint = AgriTheme.colors.primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Bayam Hijau",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriTheme.colors.textPrimary
                        )
                        Text(
                            text = "Spinacia oleracea",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AgriTheme.colors.textSecondary
                        )
                    }
                }

                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Tutup",
                        tint = AgriTheme.colors.grayIcon
                    )
                }
            }

            Divider(color = AgriTheme.colors.border)

            // Health Status Banner Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.mintBg)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "SKOR KESEHATAN",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.primary,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "98% Sangat Sehat",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriTheme.colors.primary
                        )
                    }

                    Surface(
                        shape = CircleShape,
                        color = AgriTheme.colors.surface
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = AgriTheme.colors.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Optimal",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriTheme.colors.primary
                            )
                        }
                    }
                }
            }

            // 3 Key Metrics Row (Chlorophyll SPAD, Nitrogen, Temperature)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Metric 1: SPAD Klorofil
                ScanMetricTile(
                    modifier = Modifier.weight(1f),
                    label = "SPAD KLOROFIL",
                    value = "42.8",
                    status = "Optimal",
                    icon = Icons.Default.Spa,
                    iconBg = AgriTheme.colors.mintBg,
                    iconTint = AgriTheme.colors.primary
                )

                // Metric 2: Kadar Nitrogen
                ScanMetricTile(
                    modifier = Modifier.weight(1f),
                    label = "NITROGEN",
                    value = "Normal",
                    status = "Adequate",
                    icon = Icons.Default.WaterDrop,
                    iconBg = AgriTheme.colors.blueInfoBg,
                    iconTint = AgriTheme.colors.blueAccent
                )

                // Metric 3: Suhu Daun
                ScanMetricTile(
                    modifier = Modifier.weight(1f),
                    label = "SUHU DAUN",
                    value = "24.2°C",
                    status = "Ideal",
                    icon = Icons.Default.Thermostat,
                    iconBg = AgriTheme.colors.yellowWarnBg,
                    iconTint = AgriTheme.colors.orangeAccent
                )
            }

            // AI Diagnosis Recommendation Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.background),
                border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.border)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = AgriTheme.colors.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Rekomendasi AI AgriSync",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }

                    Text(
                        text = "Kandungan klorofil & aktivitas kloroplas berada dalam rentang ideal pertumbuhan vegetatif. Pertahankan sirkulasi nutrisi EC 1.8 - 2.2 mS/cm.",
                        fontSize = 12.sp,
                        color = AgriTheme.colors.textSecondary,
                        lineHeight = 18.sp
                    )
                }
            }

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, AgriTheme.colors.grayBorder),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AgriTheme.colors.textPrimary)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Scan Ulang",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Button(
                    onClick = onViewRecommendation,
                    modifier = Modifier
                        .weight(1.3f)
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AgriTheme.colors.primary,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Rekomendasi AI",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// SCAN METRIC TILE ITEM
// ---------------------------------------------------------------------------
@Composable
fun ScanMetricTile(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    status: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    iconTint: Color
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = AgriTheme.colors.background,
        border = androidx.compose.foundation.BorderStroke(1.dp, AgriTheme.colors.border)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier.size(14.dp)
                )
            }

            Text(
                text = label,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = AgriTheme.colors.textMuted,
                letterSpacing = 0.3.sp
            )

            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTheme.colors.textPrimary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlantScanScreenPreview() {
    MyApplicationTheme {
        PlantScanScreen()
    }
}
