package com.example.myapplication.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Sensors
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AgriRedAlert
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean = false,
    onToggleDarkMode: (Boolean) -> Unit = {},
    onNavigateToEditProfile: () -> Unit = {},
    onNavigateToDeviceConnectivity: () -> Unit = {},
    onNavigateToNotificationPreferences: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    onNavigatePetani: () -> Unit = {},
    onSignOut: () -> Unit = {}
) {
    var showSignOutDialog by remember { mutableStateOf(false) }
    var showSecuritySheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background,
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = -1, // Settings is opened from dashboard header
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Settings",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.textPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Raditya - Farm Manager",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = AgriTheme.colors.textSecondary
                        )
                    }

                    // Avatar Circle with Gradient Border Ring
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF2F7F33),
                                        Color(0xFF4ADE80)
                                    )
                                ),
                                shape = CircleShape
                            )
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(AgriTheme.colors.surface)
                            .clickable { onNavigateToEditProfile() },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE5E7EB)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile",
                                modifier = Modifier.size(28.dp),
                                tint = Color(0xFF2F7F33)
                            )
                        }
                    }
                }
            }

            // Menu Item 1: Profile Settings
            item {
                SettingsMenuItemCard(
                    icon = Icons.Outlined.Person,
                    title = "Profile Settings",
                    subtitle = null,
                    onClick = onNavigateToEditProfile
                )
            }

            // Menu Item 2: Device Connectivity
            item {
                SettingsMenuItemCard(
                    icon = Icons.Outlined.Sensors,
                    title = "Device Connectivity",
                    subtitle = "IoT & Sensors",
                    onClick = onNavigateToDeviceConnectivity
                )
            }

            // Menu Item 3: Notification Preferences
            item {
                SettingsMenuItemCard(
                    icon = Icons.Outlined.Notifications,
                    title = "Notification Preferences",
                    subtitle = null,
                    onClick = onNavigateToNotificationPreferences
                )
            }

            // Menu Item 4: Security & Privacy
            item {
                SettingsMenuItemCard(
                    icon = Icons.Outlined.Shield,
                    title = "Security & Privacy",
                    subtitle = null,
                    onClick = { showSecuritySheet = true }
                )
            }

            // Menu Item 5: App Theme (Dark Mode Switch with Animation)
            item {
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
                            .padding(horizontal = 18.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            // Icon container with rotating Sun/Moon animation
                            val rotationAngle by animateFloatAsState(
                                targetValue = if (isDarkMode) 360f else 0f,
                                animationSpec = tween(durationMillis = 500),
                                label = "themeIconRotation"
                            )
                            val iconScale by animateFloatAsState(
                                targetValue = if (isDarkMode) 1.1f else 1.0f,
                                animationSpec = tween(durationMillis = 300),
                                label = "themeIconScale"
                            )

                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(AgriTheme.colors.iconBgLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                                    contentDescription = "Theme Icon",
                                    tint = if (isDarkMode) Color(0xFF818CF8) else Color(0xFFF59E0B),
                                    modifier = Modifier
                                        .size(20.dp)
                                        .rotate(rotationAngle)
                                        .scale(iconScale)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = "App Theme",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AgriTheme.colors.textPrimary
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = if (isDarkMode) "Dark Mode Enabled" else "Light Mode",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AgriTheme.colors.textSecondary
                                )
                            }
                        }

                        // Switch with custom colors
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { onToggleDarkMode(it) },
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

            // Footer Section: App Version & Sign Out
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "AGRISYNC App v1.4.2",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = AgriTheme.colors.textMuted
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { showSignOutDialog = true }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Sign Out",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AgriRedAlert
                        )
                    }
                }
            }
        }
    }

    // Sign Out Confirmation Dialog
    if (showSignOutDialog) {
        AlertDialog(
            onDismissRequest = { showSignOutDialog = false },
            title = {
                Text(
                    text = "Konfirmasi Keluar",
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary
                )
            },
            text = {
                Text(
                    text = "Apakah Anda yakin ingin keluar dari akun AGRISYNC?",
                    color = AgriTheme.colors.textSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showSignOutDialog = false
                        onSignOut()
                    }
                ) {
                    Text("Keluar", color = AgriRedAlert, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSignOutDialog = false }) {
                    Text("Batal", color = AgriTheme.colors.textMuted)
                }
            },
            containerColor = AgriTheme.colors.surface
        )
    }

    // Security & Privacy Bottom Sheet
    if (showSecuritySheet) {
        ModalBottomSheet(
            onDismissRequest = { showSecuritySheet = false },
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
                    text = "Security & Privacy",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary
                )

                SecurityOptionRow(
                    icon = Icons.Outlined.Fingerprint,
                    title = "Biometrics Lock",
                    subtitle = "Face ID / Fingerprint authentication"
                )

                SecurityOptionRow(
                    icon = Icons.Outlined.Lock,
                    title = "Ubah Kata Sandi",
                    subtitle = "Perbarui password akun Anda"
                )

                SecurityOptionRow(
                    icon = Icons.Outlined.Security,
                    title = "Two-Factor Authentication",
                    subtitle = "Autentikasi dua langkah (SMS / OTP)"
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun SettingsMenuItemCard(
    icon: ImageVector,
    title: String,
    subtitle: String?,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
                        .background(AgriTheme.colors.iconBgLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = AgriTheme.colors.textPrimary,
                        modifier = Modifier.size(20.dp)
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
                    if (subtitle != null) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AgriTheme.colors.textSecondary
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = AgriTheme.colors.textMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun SecurityOptionRow(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { }
            .padding(vertical = 10.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(AgriTheme.colors.iconBgLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = AgriTheme.colors.primary,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = AgriTheme.colors.textPrimary
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = AgriTheme.colors.textSecondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    MyApplicationTheme {
        SettingsScreen()
    }
}
