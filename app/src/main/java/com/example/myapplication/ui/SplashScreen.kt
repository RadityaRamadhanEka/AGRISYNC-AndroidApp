package com.example.myapplication.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // --- Entry Animations State ---
    val logoScale = remember { Animatable(0.2f) }
    val logoAlpha = remember { Animatable(0f) }
    val titleOffsetY = remember { Animatable(40f) }
    val titleAlpha = remember { Animatable(0f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val progressValue = remember { Animatable(0f) }

    // --- Infinite Loop Animations (Pulsing Glow & Rotation) ---
    val infiniteTransition = rememberInfiniteTransition(label = "SplashLoop")
    
    val pulseGlowScale by infiniteTransition.animateFloat(
        initialValue = 0.88f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowScale"
    )

    val pulseGlowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.12f,
        targetValue = 0.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowAlpha"
    )

    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RingRotation"
    )

    // --- Orchestrated Animation Sequence ---
    LaunchedEffect(key1 = true) {
        // Phase 1: Logo Bouncy Scale & Fade In
        launch {
            logoAlpha.animateTo(1f, tween(400))
        }
        launch {
            logoScale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }

        delay(300)

        // Phase 2: Title & Subtitle Slide + Fade In
        launch {
            titleAlpha.animateTo(1f, tween(500))
        }
        launch {
            titleOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(500, easing = FastOutSlowInEasing)
            )
        }

        delay(200)

        launch {
            subtitleAlpha.animateTo(1f, tween(400))
        }

        // Phase 3: Loading Progress Bar Filling
        launch {
            progressValue.animateTo(
                targetValue = 1f,
                animationSpec = tween(1400, easing = FastOutSlowInEasing)
            )
        }

        delay(1800)
        onSplashFinished()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFF0C1B11)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF163E24),
                            Color(0xFF0C1B11),
                            Color(0xFF07100A)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // --- 1. Pulsing Ambient Radial Glow Background ---
            Box(
                modifier = Modifier
                    .size(340.dp)
                    .scale(pulseGlowScale)
                    .alpha(pulseGlowAlpha)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF45E377),
                                Color(0xFF1DAA55).copy(alpha = 0.5f),
                                Color.Transparent
                            )
                        )
                    )
            )

            // --- 2. Main Logo & Title Column ---
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Logo Container with Animated Rings
                Box(
                    modifier = Modifier
                        .scale(logoScale.value)
                        .alpha(logoAlpha.value)
                        .size(170.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer Dashed Rotating Tech Ring
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .rotate(ringRotation)
                    ) {
                        drawCircle(
                            color = Color(0xFF45E377).copy(alpha = 0.35f),
                            style = Stroke(
                                width = 2.dp.toPx(),
                                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                                    floatArrayOf(20f, 15f), 0f
                                )
                            )
                        )
                    }

                    // Inner Soft Glass Glow Container
                    Box(
                        modifier = Modifier
                            .size(136.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1B4D2B).copy(alpha = 0.9f))
                            .border(1.dp, Color(0xFF45E377).copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Eco,
                            contentDescription = "AGRISYNC Logo",
                            tint = Color(0xFF45E377),
                            modifier = Modifier.size(76.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                // Brand Name AGRISYNC (Sliding & Fading)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .offset(y = titleOffsetY.value.dp)
                        .alpha(titleAlpha.value)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "AGRI",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            letterSpacing = 2.sp
                        )
                        Text(
                            text = "SYNC",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF45E377),
                            letterSpacing = 2.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "SMART INDOOR FARMING",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF45E377).copy(alpha = subtitleAlpha.value),
                        letterSpacing = 3.sp
                    )
                }
            }

            // --- 3. Bottom Progress Bar & Version Info ---
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 52.dp)
                    .alpha(subtitleAlpha.value),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Dynamic Progress Bar Track
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                ) {
                    // Active Filling Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progressValue.value)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF1DAA55),
                                        Color(0xFF45E377)
                                    )
                                )
                            )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "v 1.0.2",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.45f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MyApplicationTheme {
        SplashScreen()
    }
}
