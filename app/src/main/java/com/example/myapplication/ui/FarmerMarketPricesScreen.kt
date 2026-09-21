package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

data class MarketCommodityItem(
    val name: String,
    val unit: String,
    val farmerPrice: String,
    val collectorPrice: String,
    val marketPrice: String,
    val farmerTrend: String,
    val collectorTrend: String,
    val marketTrend: String,
    val isFarmerUp: Boolean = true,
    val isCollectorUp: Boolean = true,
    val isMarketUp: Boolean = true,
    val iconBgHex: Long = 0xFFD1FAE5,
    val iconTintHex: Long = 0xFF059669
)

@Composable
fun FarmerMarketPricesScreen(
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Harga Petani, 1: Harga Pengepul, 2: Pasar Utama

    val commodities = remember {
        listOf(
            MarketCommodityItem(
                name = "Selada Keriting",
                unit = "Per Kilogram",
                farmerPrice = "Rp 15.000",
                collectorPrice = "Rp 18.000",
                marketPrice = "Rp 22.000",
                farmerTrend = "+2.5%",
                collectorTrend = "+3.2%",
                marketTrend = "+5.1%",
                iconBgHex = 0xFFD1FAE5,
                iconTintHex = 0xFF059669
            ),
            MarketCommodityItem(
                name = "Bayam Hijau",
                unit = "Per Ikat (250g)",
                farmerPrice = "Rp 4.500",
                collectorPrice = "Rp 5.500",
                marketPrice = "Rp 7.000",
                farmerTrend = "-1.2%",
                collectorTrend = "+1.8%",
                marketTrend = "+2.4%",
                isFarmerUp = false,
                iconBgHex = 0xFFECFCCB,
                iconTintHex = 0xFF65A30D
            ),
            MarketCommodityItem(
                name = "Cabai Rawit",
                unit = "Per Kilogram",
                farmerPrice = "Rp 65.000",
                collectorPrice = "Rp 72.000",
                marketPrice = "Rp 85.000",
                farmerTrend = "+5.8%",
                collectorTrend = "+4.2%",
                marketTrend = "+8.5%",
                iconBgHex = 0xFFFEE2E2,
                iconTintHex = 0xFFDC2626
            ),
            MarketCommodityItem(
                name = "Tomat Merah",
                unit = "Per Kilogram",
                farmerPrice = "Rp 12.000",
                collectorPrice = "Rp 14.500",
                marketPrice = "Rp 18.000",
                farmerTrend = "0.0%",
                collectorTrend = "+1.5%",
                marketTrend = "+1.9%",
                iconBgHex = 0xFFFFEDD5,
                iconTintHex = 0xFFEA580C
            )
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF9FAFB),
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 3,
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateHome()
                        1 -> onNavigateControl()
                        2 -> onNavigateAnalytics()
                        3 -> { /* Current screen */ }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Bar
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFE8F5E9).copy(alpha = 0.8f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                            shadowElevation = 1.dp,
                            modifier = Modifier.clickable { onBackClick() }
                        ) {
                            Box(
                                modifier = Modifier.size(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Kembali",
                                    tint = Color(0xFF111827),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Text(
                            text = "Harga Pasar",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                    }
                }
            }

            // Market Level Filter Pills (Harga Petani, Harga Pengepul, Pasar Utama)
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        MarketLevelTabPill(
                            label = "Harga Petani",
                            isSelected = selectedTab == 0,
                            onClick = { selectedTab = 0 }
                        )
                    }
                    item {
                        MarketLevelTabPill(
                            label = "Harga Pengepul",
                            isSelected = selectedTab == 1,
                            onClick = { selectedTab = 1 }
                        )
                    }
                    item {
                        MarketLevelTabPill(
                            label = "Pasar Utama",
                            isSelected = selectedTab == 2,
                            onClick = { selectedTab = 2 }
                        )
                    }
                }
            }

            // Commodities Sparkline Cards
            items(commodities) { comm ->
                val displayPrice = when (selectedTab) {
                    0 -> comm.farmerPrice
                    1 -> comm.collectorPrice
                    else -> comm.marketPrice
                }
                val displayTrend = when (selectedTab) {
                    0 -> comm.farmerTrend
                    1 -> comm.collectorTrend
                    else -> comm.marketTrend
                }
                val isUp = when (selectedTab) {
                    0 -> comm.isFarmerUp
                    1 -> comm.isCollectorUp
                    else -> comm.isMarketUp
                }

                CommoditySparklineCard(
                    item = comm,
                    displayPrice = displayPrice,
                    displayTrend = displayTrend,
                    isUp = isUp
                )
            }
        }
    }
}

@Composable
fun MarketLevelTabPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = CircleShape,
        color = if (isSelected) Color(0xFF2E7D32) else Color.White,
        border = if (isSelected) null else BorderStroke(1.dp, Color(0xFFE5E7EB)),
        shadowElevation = if (isSelected) 3.dp else 1.dp
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else Color(0xFF4B5563),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 11.dp)
        )
    }
}

@Composable
fun CommoditySparklineCard(
    item: MarketCommodityItem,
    displayPrice: String,
    displayTrend: String,
    isUp: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(item.iconBgHex)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Spa,
                            contentDescription = null,
                            tint = Color(item.iconTintHex),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        Text(item.unit, fontSize = 12.sp, color = Color(0xFF5E8760))
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(displayPrice, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                    Surface(
                        shape = CircleShape,
                        color = if (isUp) Color(0xFF2E7D32).copy(alpha = 0.1f) else Color(0xFFFEE2E2)
                    ) {
                        Text(
                            text = displayTrend,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isUp) Color(0xFF2E7D32) else Color(0xFFDC2626),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Divider(color = Color(0xFFF3F4F6))

            // 7-Day Trend Chart Sparkline Curve
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "TREN 7 HARI",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF9CA3AF),
                    letterSpacing = 0.5.sp
                )

                SparklineChartCanvas(isUp = isUp)
            }
        }
    }
}

@Composable
fun SparklineChartCanvas(isUp: Boolean) {
    val strokeColor = if (isUp) Color(0xFF2E7D32) else Color(0xFFDC2626)
    val gradientColor = if (isUp) Color(0xFF4ADE80).copy(alpha = 0.2f) else Color(0xFFFCA5A5).copy(alpha = 0.2f)

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
    ) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(0f, height * 0.7f)
            cubicTo(
                width * 0.25f, if (isUp) height * 0.8f else height * 0.2f,
                width * 0.5f, if (isUp) height * 0.3f else height * 0.6f,
                width * 0.75f, if (isUp) height * 0.4f else height * 0.8f
            )
            lineTo(width, if (isUp) height * 0.1f else height * 0.9f)
        }

        val fillPath = Path().apply {
            addPath(path)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(gradientColor, Color.Transparent)
            )
        )

        drawPath(
            path = path,
            color = strokeColor,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FarmerMarketPricesScreenPreview() {
    MyApplicationTheme {
        FarmerMarketPricesScreen()
    }
}
