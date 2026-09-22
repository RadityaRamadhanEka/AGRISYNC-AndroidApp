package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

enum class NotificationCategoryFilter { ALL, ADVISOR, SYSTEM }

data class AdvisorMessageItem(
    val sender: String,
    val text: String,
    val time: String,
    val isNew: Boolean = true,
    val avatarBgHex: Long = 0xFF2E7D32
)

data class SystemActivityItem(
    val title: String,
    val desc: String,
    val time: String,
    val iconType: String // "PRICE", "COMMUNITY", "TIPS"
)

@Composable
fun FarmerNotificationScreen(
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableIntStateOf(0) } // 0: Semua, 1: Pesan Penyuluh, 2: Sistem

    val advisorMessages = remember {
        listOf(
            AdvisorMessageItem(
                sender = "Bpk. Slamet H.",
                text = "Jangan lupa periksa tingkat pH tanah sebelum pemberian pupuk susulan...",
                time = "10 mnt yang lalu",
                avatarBgHex = 0xFF158340
            ),
            AdvisorMessageItem(
                sender = "Ibu Lilik Purwati",
                text = "Berikut link pendaftaran untuk pembagian bantuan benih jagung hibrida...",
                time = "45 mnt yang lalu",
                avatarBgHex = 0xFF1DAA55
            ),
            AdvisorMessageItem(
                sender = "Dr. Ir. Wahyudi",
                text = "Analisis drone menunjukkan adanya indikasi hama wereng di petak sawah...",
                time = "2 jam yang lalu",
                avatarBgHex = 0xFF0F5A2C
            )
        )
    }

    val systemActivities = remember {
        listOf(
            SystemActivityItem(
                title = "Harga Pasar Naik",
                desc = "Harga cabai merah naik 15% di wilayah Anda.",
                time = "4 jam yang lalu",
                iconType = "PRICE"
            ),
            SystemActivityItem(
                title = "Kelompok Tani Makmur",
                desc = "Budi mengunggah laporan panen baru.",
                time = "Kemarin",
                iconType = "COMMUNITY"
            ),
            SystemActivityItem(
                title = "Tips Budidaya",
                desc = "Waktunya melakukan pengecekan nutrisi hidroponik.",
                time = "2 hari yang lalu",
                iconType = "TIPS"
            )
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background,
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
            // Header
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = AgriTheme.colors.background,
                    shadowElevation = 1.dp
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
                            color = AgriTheme.colors.surface,
                            border = BorderStroke(1.dp, AgriTheme.colors.grayBorder),
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
                                    tint = AgriTheme.colors.textPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Text(
                            text = "Notifikasi Tani",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }
                }
            }

            // Tab Filter Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    NotificationTabPill(
                        label = "Semua",
                        isSelected = selectedFilter == 0,
                        onClick = { selectedFilter = 0 },
                        modifier = Modifier.weight(1f)
                    )
                    NotificationTabPill(
                        label = "Pesan Penyuluh",
                        isSelected = selectedFilter == 1,
                        onClick = { selectedFilter = 1 },
                        modifier = Modifier.weight(1.2f)
                    )
                    NotificationTabPill(
                        label = "Sistem",
                        isSelected = selectedFilter == 2,
                        onClick = { selectedFilter = 2 },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Section 1: PESAN PENYULUH
            if (selectedFilter == 0 || selectedFilter == 1) {
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "PESAN PENYULUH",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32).copy(alpha = 0.8f),
                            letterSpacing = 1.sp
                        )

                        for (msg in advisorMessages) {
                            AdvisorMessageCard(item = msg)
                        }
                    }
                }
            }

            // Section 2: AKTIVITAS SISTEM
            if (selectedFilter == 0 || selectedFilter == 2) {
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "AKTIVITAS SISTEM",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9CA3AF),
                            letterSpacing = 1.sp
                        )

                        for (act in systemActivities) {
                            SystemActivityCard(item = act)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationTabPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) AgriTheme.colors.surface else AgriTheme.colors.grayBgAlt,
        border = if (isSelected) BorderStroke(1.dp, AgriTheme.colors.grayBorder) else null,
        shadowElevation = if (isSelected) 1.dp else 0.dp
    ) {
        Box(
            modifier = Modifier.padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) AgriTheme.colors.primary else AgriTheme.colors.grayIcon
            )
        }
    }
}

@Composable
fun AdvisorMessageCard(item: AdvisorMessageItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        border = BorderStroke(1.dp, AgriTheme.colors.grayBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(item.avatarBgHex)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.sender,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTheme.colors.textPrimary
                    )

                    Surface(
                        shape = CircleShape,
                        color = AgriTheme.colors.mintBg,
                        border = BorderStroke(1.dp, AgriTheme.colors.primary.copy(alpha = 0.3f))
                    ) {
                        Text(
                            text = "NEW",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.primary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = item.text,
                    fontSize = 13.sp,
                    color = AgriTheme.colors.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = AgriTheme.colors.primary,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = item.time,
                        fontSize = 10.sp,
                        color = AgriTheme.colors.textMuted
                    )
                }
            }
        }
    }
}

@Composable
fun SystemActivityCard(item: SystemActivityItem) {
    val bgHex = when (item.iconType) {
        "PRICE" -> 0xFFECFDF5
        "COMMUNITY" -> 0xFFEFF6FF
        else -> 0xFFFAF5FF
    }
    val iconTintHex = when (item.iconType) {
        "PRICE" -> 0xFF059669
        "COMMUNITY" -> 0xFF2563EB
        else -> 0xFF9333EA
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        border = BorderStroke(1.dp, AgriTheme.colors.border),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(bgHex)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (item.iconType) {
                        "PRICE" -> Icons.AutoMirrored.Filled.TrendingUp
                        "COMMUNITY" -> Icons.Default.Groups
                        else -> Icons.Default.Spa
                    },
                    contentDescription = null,
                    tint = Color(iconTintHex),
                    modifier = Modifier.size(20.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary
                )
                Text(
                    text = item.desc,
                    fontSize = 11.sp,
                    color = AgriTheme.colors.textSecondary
                )
            }

            Text(
                text = item.time,
                fontSize = 10.sp,
                color = AgriTheme.colors.textMuted
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmerNotificationScreenPreview() {
    MyApplicationTheme {
        FarmerNotificationScreen()
    }
}
