package com.example.myapplication.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CenterFocusWeak
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun OnboardingScreen(
    onOnboardingFinished: () -> Unit = {},
    onSkip: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var currentPage by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Ambient Green Glow Background Elements
            Box(
                modifier = Modifier
                    .size(380.dp)
                    .offset(x = (-80).dp, y = (-80).dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF13EC1E).copy(alpha = 0.12f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 100.dp, y = 100.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF13EC1E).copy(alpha = 0.08f),
                                Color.Transparent
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Navigation Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF13EC1E))
                        )
                        Text(
                            text = "AGRISYNC",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F291E),
                            letterSpacing = 1.5.sp
                        )
                    }

                    Text(
                        text = if (currentPage == 2) "Masuk" else "Lewati",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF13EC1E),
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onSkip() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Middle Dynamic Content Area with Slide & Fade Animations
                AnimatedContent(
                    targetState = currentPage,
                    transitionSpec = {
                        if (targetState > initialState) {
                            (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                                slideOutHorizontally { width -> -width } + fadeOut()
                            )
                        } else {
                            (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                                slideOutHorizontally { width -> width } + fadeOut()
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { page ->
                    when (page) {
                        0 -> OnboardingPage1Content()
                        1 -> OnboardingPage2Content()
                        2 -> OnboardingPage3Content()
                    }
                }

                // Bottom Pagination Dots & Primary Action Button
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Custom Animated Pagination Indicators
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(3) { index ->
                            val isSelected = index == currentPage
                            Box(
                                modifier = Modifier
                                    .height(8.dp)
                                    .width(if (isSelected) 36.dp else 8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (isSelected) Color(0xFF13EC1E)
                                        else Color(0xFFD1D5DB)
                                    )
                            )
                        }
                    }

                    // Main Action Button
                    Button(
                        onClick = {
                            if (currentPage < 2) {
                                currentPage++
                            } else {
                                onOnboardingFinished()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF13EC1E),
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 6.dp,
                            pressedElevation = 2.dp
                        )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (currentPage == 2) "Mulai Sekarang" else "Lanjut",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ===========================================================================
// PAGE 1: SMART REAL-TIME MONITORING (MAXIMIZED UI & ANIMATIONS)
// ===========================================================================
@Composable
fun OnboardingPage1Content() {
    val infiniteTransition = rememberInfiniteTransition(label = "Page1Infinite")

    // Pulse animation for concentric rings
    val pulseScale1 by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.14f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale1"
    )

    val pulseAlpha1 by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseAlpha1"
    )

    val centerIconPulse by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "centerIconPulse"
    )

    // Staggered floating bobbing animations for sensor pills
    val floatY1 by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatY1"
    )

    val floatY2 by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = -8f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatY2"
    )

    val floatY3 by infiniteTransition.animateFloat(
        initialValue = -6f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatY3"
    )

    // Live blinking status dot
    val liveDotAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "liveDotAlpha"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Hero Graphic Area
        Box(
            modifier = Modifier
                .size(310.dp),
            contentAlignment = Alignment.Center
        ) {
            // Animated Pulse Outer Ring
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .graphicsLayer {
                        scaleX = pulseScale1
                        scaleY = pulseScale1
                        alpha = pulseAlpha1
                    }
                    .clip(CircleShape)
                    .background(Color(0xFF13EC1E))
            )

            // Inner Ring Frame
            Box(
                modifier = Modifier
                    .size(230.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.5.dp,
                        color = Color(0xFF13EC1E).copy(alpha = 0.3f),
                        shape = CircleShape
                    )
            )

            // Center Plant Icon Container with Radial Glow Aura
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .graphicsLayer {
                        scaleX = centerIconPulse
                        scaleY = centerIconPulse
                    }
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF13EC1E).copy(alpha = 0.35f),
                                Color(0xFFE8F5E9)
                            )
                        )
                    )
                    .border(
                        width = 3.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFF13EC1E), Color(0xFF81C784))
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Eco,
                    contentDescription = null,
                    tint = Color(0xFF1B5E20),
                    modifier = Modifier.size(80.dp)
                )
            }

            // FLOATING SENSOR PILL 1: Temperature (Top Right)
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 10.dp, y = (20 + floatY1).dp),
                shape = RoundedCornerShape(16.dp),
                color = AgriTheme.colors.surface,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDCFCE7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Thermostat,
                            contentDescription = null,
                            tint = Color(0xFF16A34A),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "SUHU",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6B7280)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF16A34A).copy(alpha = liveDotAlpha))
                            )
                        }
                        Text(
                            text = "24.5°C",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }
                }
            }

            // FLOATING SENSOR PILL 2: Acidity / pH (Bottom Left)
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-10).dp, y = (-15 + floatY2).dp),
                shape = RoundedCornerShape(16.dp),
                color = AgriTheme.colors.surface,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDBEAFE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WaterDrop,
                            contentDescription = null,
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "ASAM (pH)",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF6B7280)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(5.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2563EB).copy(alpha = liveDotAlpha))
                            )
                        }
                        Text(
                            text = "6.5 pH",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }
                }
            }

            // FLOATING SENSOR PILL 3: Sunlight (Bottom Right / Center)
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 20.dp, y = (40 + floatY3).dp),
                shape = RoundedCornerShape(16.dp),
                color = AgriTheme.colors.surface,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFEF3C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.WbSunny,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Column {
                        Text(
                            text = "CAHAYA",
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B7280)
                        )
                        Text(
                            text = "88% Optimal",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title & Description
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Pantau Tanaman",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Secara ",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AgriTheme.colors.textPrimary
                )
                Text(
                    text = "Real-Time",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF13EC1E)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Dapatkan data akurat dari sensor IoT langsung di ponsel Anda untuk kondisi tanah, suhu, dan kelembapan secara presisi.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

// ===========================================================================
// PAGE 2: AI ASSET SCANNING (CUSTOM HIGH-TECH SCANNER ANIMATION)
// ===========================================================================
@Composable
fun OnboardingPage2Content() {
    val infiniteTransition = rememberInfiniteTransition(label = "Page2Infinite")

    // Vertical Laser Scan Progress (0.05f to 0.95f)
    val scanProgress by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scanProgress"
    )

    // Pulsing grid opacity
    val gridAlpha by infiniteTransition.animateFloat(
        initialValue = 0.15f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "gridAlpha"
    )

    // Corner Bracket Glow Pulse
    val bracketGlow by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "bracketGlow"
    )

    // Blinking Radar Dot
    val radarDotAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "radarDotAlpha"
    )

    // Subtle scale breathing for leaf target
    val leafScale by infiniteTransition.animateFloat(
        initialValue = 0.97f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "leafScale"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // High-Tech Scanning Viewfinder Frame
        Box(
            modifier = Modifier
                .width(270.dp)
                .height(310.dp),
            contentAlignment = Alignment.Center
        ) {
            // Viewfinder Outer Container
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(28.dp),
                color = Color(0xFF0D1B13),
                border = androidx.compose.foundation.BorderStroke(
                    width = 2.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF13EC1E).copy(alpha = 0.8f),
                            Color(0xFF0F291E)
                        )
                    )
                ),
                shadowElevation = 12.dp
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    // Custom Canvas Layer: Draw Grid, Laser Trail, Beam & Corner Brackets
                    Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                        val width = size.width
                        val height = size.height

                        // 1. Draw Grid Lines
                        val gridStep = 28.dp.toPx()
                        var x = gridStep
                        while (x < width) {
                            drawLine(
                                color = Color(0xFF13EC1E).copy(alpha = gridAlpha * 0.4f),
                                start = Offset(x, 0f),
                                end = Offset(x, height),
                                strokeWidth = 1.dp.toPx()
                            )
                            x += gridStep
                        }
                        var y = gridStep
                        while (y < height) {
                            drawLine(
                                color = Color(0xFF13EC1E).copy(alpha = gridAlpha * 0.4f),
                                start = Offset(0f, y),
                                end = Offset(width, y),
                                strokeWidth = 1.dp.toPx()
                            )
                            y += gridStep
                        }

                        // 2. Draw Scanning Laser Line & Glow Trail
                        val currentY = height * scanProgress

                        // Laser Gradient Trail (behind scan line)
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF13EC1E).copy(alpha = 0.35f),
                                    Color.Transparent
                                ),
                                startY = currentY,
                                endY = currentY - 45.dp.toPx()
                            ),
                            topLeft = Offset(0f, currentY - 45.dp.toPx()),
                            size = Size(width, 45.dp.toPx())
                        )

                        // Main Laser Beam Line
                        drawLine(
                            color = Color(0xFF13EC1E),
                            start = Offset(0f, currentY),
                            end = Offset(width, currentY),
                            strokeWidth = 3.dp.toPx()
                        )

                        // Laser Core Glow Accent
                        drawLine(
                            color = Color.White,
                            start = Offset(width * 0.15f, currentY),
                            end = Offset(width * 0.85f, currentY),
                            strokeWidth = 1.5.dp.toPx()
                        )

                        // 3. Draw Viewfinder Corner Brackets [ ]
                        val bLen = 26.dp.toPx()
                        val bStroke = 3.5.dp.toPx()
                        val bColor = Color(0xFF13EC1E).copy(alpha = bracketGlow)

                        // Top-Left
                        drawLine(bColor, Offset(0f, 0f), Offset(bLen, 0f), bStroke)
                        drawLine(bColor, Offset(0f, 0f), Offset(0f, bLen), bStroke)

                        // Top-Right
                        drawLine(bColor, Offset(width, 0f), Offset(width - bLen, 0f), bStroke)
                        drawLine(bColor, Offset(width, 0f), Offset(width, bLen), bStroke)

                        // Bottom-Left
                        drawLine(bColor, Offset(0f, height), Offset(bLen, height), bStroke)
                        drawLine(bColor, Offset(0f, height), Offset(0f, height - bLen), bStroke)

                        // Bottom-Right
                        drawLine(bColor, Offset(width, height), Offset(width - bLen, height), bStroke)
                        drawLine(bColor, Offset(width, height), Offset(width, height - bLen), bStroke)
                    }

                    // Central Target Asset: Eco Leaf Icon with Target Reticle
                    Box(
                        modifier = Modifier
                            .graphicsLayer {
                                scaleX = leafScale
                                scaleY = leafScale
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CenterFocusWeak,
                            contentDescription = null,
                            tint = Color(0xFF13EC1E).copy(alpha = 0.3f),
                            modifier = Modifier.size(180.dp)
                        )
                        Icon(
                            imageVector = Icons.Outlined.Eco,
                            contentDescription = "Scanning Asset",
                            tint = Color(0xFF13EC1E),
                            modifier = Modifier.size(100.dp)
                        )
                    }

                    // Floating Bounding Box Frame on Target Leaf
                    Box(
                        modifier = Modifier
                            .size(130.dp)
                            .border(
                                width = 1.dp,
                                color = Color(0xFF13EC1E).copy(alpha = 0.6f),
                                shape = RoundedCornerShape(12.dp)
                            )
                    )

                    // TOP HUD TAG: Live Scanning Status
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp),
                        shape = RoundedCornerShape(50),
                        color = Color.Black.copy(alpha = 0.75f),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            Color(0xFF13EC1E).copy(alpha = 0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF13EC1E).copy(alpha = radarDotAlpha))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Scanning AI...",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }

                    // BOTTOM HUD TAG: Diagnosis Result
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        shape = RoundedCornerShape(50),
                        color = Color(0xFF13EC1E)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF0D1B13),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Kondisi: 98.8% Sehat",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0D1B13)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title & Description
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Pindai & Deteksi Asset",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )
            Text(
                text = "dengan AI Presisi",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF13EC1E),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Cukup arahkan kamera ke tanaman atau aset pertanian Anda. AI akan menganalisis penyakit, nutrisi, dan memberikan tindakan perbaikan instan.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

// ===========================================================================
// PAGE 3: ECOSYSTEM & MARKET (INTERACTIVE CONNECTED GRAPH ANIMATION)
// ===========================================================================
@Composable
fun OnboardingPage3Content() {
    val infiniteTransition = rememberInfiniteTransition(label = "Page3Infinite")

    // Expanding Wave Ripple from Central Hub
    val waveProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "waveProgress"
    )

    // Data pulse traveling along connection lines
    val pulseTravel by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulseTravel"
    )

    // Floating animation for node badges
    val float1 by infiniteTransition.animateFloat(
        initialValue = -7f,
        targetValue = 7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float1"
    )

    val float2 by infiniteTransition.animateFloat(
        initialValue = 7f,
        targetValue = -7f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float2"
    )

    val float3 by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float3"
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Network Ecosystem Hub Illustration
        Box(
            modifier = Modifier
                .size(310.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background Canvas: Connected energy lines & traveling pulse dots + expanding ripples
            Canvas(modifier = Modifier.fillMaxSize()) {
                val center = Offset(size.width / 2f, size.height / 2f)

                // 1. Expanding Wave Ripple from center
                val maxRippleRadius = size.width * 0.42f
                val rippleRadius = maxRippleRadius * waveProgress
                val rippleAlpha = (1f - waveProgress) * 0.4f
                drawCircle(
                    color = Color(0xFF13EC1E).copy(alpha = rippleAlpha),
                    radius = rippleRadius,
                    center = center,
                    style = Stroke(width = 2.dp.toPx())
                )

                // Node Positions relative to center
                val node1Offset = Offset(center.x + 85.dp.toPx(), center.y - 80.dp.toPx()) // Top Right: Market
                val node2Offset = Offset(center.x - 90.dp.toPx(), center.y + 70.dp.toPx()) // Bottom Left: Ahli
                val node3Offset = Offset(center.x + 85.dp.toPx(), center.y + 80.dp.toPx()) // Bottom Right: Price

                val nodes = listOf(node1Offset, node2Offset, node3Offset)

                // 2. Draw Connection Lines and Energy Pulse Particles
                nodes.forEach { nodePos ->
                    // Dashed connection line
                    drawLine(
                        color = Color(0xFF13EC1E).copy(alpha = 0.4f),
                        start = center,
                        end = nodePos,
                        strokeWidth = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )

                    // Traveling Pulse Dot
                    val particleX = center.x + ((nodePos.x - center.x) * pulseTravel)
                    val particleY = center.y + ((nodePos.y - center.y) * pulseTravel)
                    drawCircle(
                        color = Color(0xFF13EC1E),
                        radius = 4.5.dp.toPx(),
                        center = Offset(particleX, particleY)
                    )
                    drawCircle(
                        color = Color.White,
                        radius = 2.dp.toPx(),
                        center = Offset(particleX, particleY)
                    )
                }
            }

            // Central Node: Main Agriculture Core
            Surface(
                modifier = Modifier.size(92.dp),
                shape = RoundedCornerShape(26.dp),
                color = Color.White,
                shadowElevation = 10.dp,
                border = androidx.compose.foundation.BorderStroke(2.5.dp, Color(0xFF13EC1E))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Color(0xFF13EC1E), Color(0xFF0FAD16))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Eco,
                        contentDescription = "Farm Core",
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            // NODE 1: Digital Market (Top Right)
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 10.dp, y = (20 + float1).dp),
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDCFCE7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = "Market",
                            tint = Color(0xFF16A34A),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "PASAR DIGITAL",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B7280)
                        )
                        Text(
                            text = "Jual Panen Direct",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }
                }
            }

            // NODE 2: Expert Consultation (Bottom Left)
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = (-10).dp, y = (-20 + float2).dp),
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDBEAFE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SupportAgent,
                            contentDescription = "Ahli",
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "KONSULTASI",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B7280)
                        )
                        Text(
                            text = "Dokter Tanaman",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }
                }
            }

            // NODE 3: Real-Time Market Prices (Bottom Right)
            Surface(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = (-10 + float3).dp),
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFEF3C7)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = "Harga Real-Time",
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "HARGA PASAR",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6B7280)
                        )
                        Text(
                            text = "Update Harian",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Title & Description
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Terhubung ke",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AgriTheme.colors.textPrimary,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Ekosistem Pertanian",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF13EC1E),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Akses pembeli langsung, pantau harga pasar komoditas harian, dan berkonsultasi dengan agronomis profesional dalam satu platform terpadu.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF6B7280),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    MyApplicationTheme {
        OnboardingScreen()
    }
}
