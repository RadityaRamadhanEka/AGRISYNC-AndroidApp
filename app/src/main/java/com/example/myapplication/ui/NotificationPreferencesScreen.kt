package com.example.myapplication.ui

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.SystemUpdate
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun NotificationPreferencesScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    onNavigatePetani: () -> Unit = {}
) {
    var systemAlerts by remember { mutableStateOf(true) }
    var cropReminders by remember { mutableStateOf(true) }
    var weeklyReports by remember { mutableStateOf(true) }
    var appUpdates by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background,
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = -1,
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateHome()
                        1 -> onNavigateControl()
                        2 -> onNavigateAnalytics()
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
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                        text = "Notifikasi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTheme.colors.textPrimary
                    )
                }
            }

            // Notification Card 1: Lansiran Sistem
            item {
                NotificationPreferenceCard(
                    title = "Lansiran Sistem",
                    subtitle = "Peringatan pH & suhu kritis",
                    icon = Icons.Outlined.Warning,
                    iconBg = Color(0xFFFEF2F2),
                    iconTint = Color(0xFFEF4444),
                    isChecked = systemAlerts,
                    onCheckedChange = { systemAlerts = it }
                )
            }

            // Notification Card 2: Pengingat Tanaman
            item {
                NotificationPreferenceCard(
                    title = "Pengingat Tanaman",
                    subtitle = "Jadwal penyiraman & panen",
                    icon = Icons.Outlined.Eco,
                    iconBg = Color(0xFFE8F5E9),
                    iconTint = Color(0xFF2F7F33),
                    isChecked = cropReminders,
                    onCheckedChange = { cropReminders = it }
                )
            }

            // Notification Card 3: Laporan Mingguan
            item {
                NotificationPreferenceCard(
                    title = "Laporan Mingguan",
                    subtitle = "Ringkasan energi & keberlanjutan",
                    icon = Icons.Outlined.BarChart,
                    iconBg = Color(0xFFFFFBEB),
                    iconTint = Color(0xFFD97706),
                    isChecked = weeklyReports,
                    onCheckedChange = { weeklyReports = it }
                )
            }

            // Notification Card 4: Pembaruan Aplikasi
            item {
                NotificationPreferenceCard(
                    title = "Pembaruan Aplikasi",
                    subtitle = "Fitur baru & perbaikan bug",
                    icon = Icons.Outlined.SystemUpdate,
                    iconBg = Color(0xFFEFF6FF),
                    iconTint = Color(0xFF2563EB),
                    isChecked = appUpdates,
                    onCheckedChange = { appUpdates = it }
                )
            }

            // Footnote Text
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Anda dapat mengubah preferensi ini kapan saja. Notifikasi mendesak terkait keamanan akun tidak dapat dinonaktifkan.",
                    fontSize = 12.sp,
                    color = AgriTheme.colors.textMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun NotificationPreferenceCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = AgriTheme.colors.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconTint,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AgriTheme.colors.textPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF5E8760)
                    )
                }
            }

            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF4ADE80),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0xFFE5E7EB)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationPreferencesScreenPreview() {
    MyApplicationTheme {
        NotificationPreferencesScreen()
    }
}
