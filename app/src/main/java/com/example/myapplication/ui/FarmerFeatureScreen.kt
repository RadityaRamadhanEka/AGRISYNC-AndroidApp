package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

// ---------------------------------------------------------------------------
// ENUM & DATA MODELS FOR SERVICES
// ---------------------------------------------------------------------------
enum class FarmerSubScreen {
    MAIN,
    TIPS_ARTICLE,
    HARVEST_RECORD,
    MARKET_PRICES,
    COLLECTORS_MAP,
    CONSULTATION_CHAT,
    COMMUNITY,
    CROP_PROFILES,
    NOTIFICATIONS
}

enum class FarmerServiceType(
    val title: String,
    val subtitle: String,
    val bgHex: Long,
    val iconTintHex: Long
) {
    PANEN("Panen", "Catat hasil & jadwal", 0xFFFEF3C7, 0xFFD97706),
    HARGA_PASAR("Harga Pasar", "Update harga komoditas", 0xFFD1FAE5, 0xFF059669),
    PENGEPUL("Pengepul", "Jual hasil panen", 0xFFDBEAFE, 0xFF2563EB),
    PENYULUH("Penyuluh", "Konsultasi ahli tani", 0xFFF3E8FF, 0xFF7C3AED),
    KELOMPOK_TANI("Kelompok Tani", "Forum komunitas", 0xFFFFEDD5, 0xFFEA580C),
    PROFIL_TANAMAN("Profil Tanaman", "Database botani", 0xFFECFCCB, 0xFF65A30D)
}

// ---------------------------------------------------------------------------
// MAIN SCREEN COMPOSABLE: FarmerFeatureScreen
// ---------------------------------------------------------------------------
@Composable
fun FarmerFeatureScreen(
    onNavigateToHome: () -> Unit = {},
    onNavigateToControl: () -> Unit = {},
    onNavigateToAnalytics: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var activeSubScreen by remember { mutableStateOf(FarmerSubScreen.MAIN) }

    when (activeSubScreen) {
        FarmerSubScreen.MAIN -> {
            FarmerFeatureMainContent(
                onNavigateToHome = onNavigateToHome,
                onNavigateToControl = onNavigateToControl,
                onNavigateToAnalytics = onNavigateToAnalytics,
                onOpenTipsArticle = { activeSubScreen = FarmerSubScreen.TIPS_ARTICLE },
                onOpenHarvestRecord = { activeSubScreen = FarmerSubScreen.HARVEST_RECORD },
                onOpenMarketPrices = { activeSubScreen = FarmerSubScreen.MARKET_PRICES },
                onOpenCollectorsMap = { activeSubScreen = FarmerSubScreen.COLLECTORS_MAP },
                onOpenConsultationChat = { activeSubScreen = FarmerSubScreen.CONSULTATION_CHAT },
                onOpenCommunity = { activeSubScreen = FarmerSubScreen.COMMUNITY },
                onOpenCropProfiles = { activeSubScreen = FarmerSubScreen.CROP_PROFILES },
                onOpenNotifications = { activeSubScreen = FarmerSubScreen.NOTIFICATIONS },
                modifier = modifier
            )
        }
        FarmerSubScreen.TIPS_ARTICLE -> {
            FarmerDailyTipArticleScreen(
                onBackClick = { activeSubScreen = FarmerSubScreen.MAIN },
                modifier = modifier
            )
        }
        FarmerSubScreen.HARVEST_RECORD -> {
            FarmerHarvestRecordScreen(
                onBackClick = { activeSubScreen = FarmerSubScreen.MAIN },
                onNavigateHome = onNavigateToHome,
                onNavigateControl = onNavigateToControl,
                onNavigateAnalytics = onNavigateToAnalytics,
                modifier = modifier
            )
        }
        FarmerSubScreen.MARKET_PRICES -> {
            FarmerMarketPricesScreen(
                onBackClick = { activeSubScreen = FarmerSubScreen.MAIN },
                onNavigateHome = onNavigateToHome,
                onNavigateControl = onNavigateToControl,
                onNavigateAnalytics = onNavigateToAnalytics,
                modifier = modifier
            )
        }
        FarmerSubScreen.COLLECTORS_MAP -> {
            FarmerCollectorsMapScreen(
                onBackClick = { activeSubScreen = FarmerSubScreen.MAIN },
                onNavigateHome = onNavigateToHome,
                onNavigateControl = onNavigateToControl,
                onNavigateAnalytics = onNavigateToAnalytics,
                modifier = modifier
            )
        }
        FarmerSubScreen.CONSULTATION_CHAT -> {
            FarmerConsultationChatScreen(
                onBackClick = { activeSubScreen = FarmerSubScreen.MAIN },
                modifier = modifier
            )
        }
        FarmerSubScreen.COMMUNITY -> {
            FarmerCommunityScreen(
                onBackClick = { activeSubScreen = FarmerSubScreen.MAIN },
                onNavigateHome = onNavigateToHome,
                onNavigateControl = onNavigateToControl,
                onNavigateAnalytics = onNavigateToAnalytics,
                modifier = modifier
            )
        }
        FarmerSubScreen.CROP_PROFILES -> {
            FarmerCropProfilesScreen(
                onBackClick = { activeSubScreen = FarmerSubScreen.MAIN },
                onNavigateHome = onNavigateToHome,
                onNavigateControl = onNavigateToControl,
                onNavigateAnalytics = onNavigateToAnalytics,
                modifier = modifier
            )
        }
        FarmerSubScreen.NOTIFICATIONS -> {
            FarmerNotificationScreen(
                onBackClick = { activeSubScreen = FarmerSubScreen.MAIN },
                onNavigateHome = onNavigateToHome,
                onNavigateControl = onNavigateToControl,
                onNavigateAnalytics = onNavigateToAnalytics,
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerFeatureMainContent(
    onNavigateToHome: () -> Unit = {},
    onNavigateToControl: () -> Unit = {},
    onNavigateToAnalytics: () -> Unit = {},
    onOpenTipsArticle: () -> Unit = {},
    onOpenHarvestRecord: () -> Unit = {},
    onOpenMarketPrices: () -> Unit = {},
    onOpenCollectorsMap: () -> Unit = {},
    onOpenConsultationChat: () -> Unit = {},
    onOpenCommunity: () -> Unit = {},
    onOpenCropProfiles: () -> Unit = {},
    onOpenNotifications: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedServiceModal by remember { mutableStateOf<FarmerServiceType?>(null) }
    var isTipsModalOpen by remember { mutableStateOf(false) }
    var isNotificationsModalOpen by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    // Filter services based on query
    val filteredServices = remember(searchQuery) {
        FarmerServiceType.values().filter { service ->
            searchQuery.isBlank() ||
                    service.title.contains(searchQuery, ignoreCase = true) ||
                    service.subtitle.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background,
        bottomBar = {
            AgriSyncBottomBar(
                selectedTab = 3, // "Petani" tab is active
                onTabSelected = { tab ->
                    when (tab) {
                        0 -> onNavigateToHome()
                        1 -> onNavigateToControl()
                        2 -> onNavigateToAnalytics()
                        3 -> { /* Already on Petani */ }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
        ) {
            // Ambient Decorative Glow Backgrounds (Figma node 90:310)
            AmbientBackgroundGlows()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // 1. Header Section (Title, User Avatar, Search Bar)
                item {
                    FarmerHeaderSection(
                        searchQuery = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onClearSearch = { searchQuery = "" }
                    )
                }

                // 2. Daily Tips Banner Card ("TIPS HARIAN")
                item {
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                        DailyTipsCard(
                            onReadNowClick = { onOpenTipsArticle() }
                        )
                    }
                }

                // 3. Layanan Utama Grid Section
                item {
                    Column(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Layanan Utama",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = AgriTheme.colors.textPrimary
                            )

                            if (searchQuery.isNotEmpty()) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFFE8F5E9),
                                    modifier = Modifier.clickable { searchQuery = "" }
                                ) {
                                    Text(
                                        text = "${filteredServices.size} Ditemukan • Hapus",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF2E7D32),
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                    )
                                }
                            }
                        }

                        if (filteredServices.isEmpty()) {
                            // Empty state when search yields no result
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        tint = Color(0xFF9CA3AF),
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Fitur atau layanan tidak ditemukan",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AgriTheme.colors.textPrimary
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Coba kata kunci lain seperti 'Panen', 'Harga', atau 'Penyuluh'",
                                        fontSize = 12.sp,
                                        color = Color(0xFF6B7280),
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Button(
                                        onClick = { searchQuery = "" },
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                                    ) {
                                        Text("Tampilkan Semua Layanan", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        } else {
                            // 2-Column Grid rendered seamlessly
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                val chunked = filteredServices.chunked(2)
                                for (rowItems in chunked) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        for (service in rowItems) {
                                            ServiceCardItem(
                                                modifier = Modifier.weight(1f),
                                                service = service,
                                                onClick = {
                                                    when (service) {
                                                        FarmerServiceType.PANEN -> onOpenHarvestRecord()
                                                        FarmerServiceType.HARGA_PASAR -> onOpenMarketPrices()
                                                        FarmerServiceType.PENGEPUL -> onOpenCollectorsMap()
                                                        FarmerServiceType.PENYULUH -> onOpenConsultationChat()
                                                        FarmerServiceType.KELOMPOK_TANI -> onOpenCommunity()
                                                        FarmerServiceType.PROFIL_TANAMAN -> onOpenCropProfiles()
                                                    }
                                                }
                                            )
                                        }
                                        if (rowItems.size == 1) {
                                            Spacer(modifier = Modifier.weight(1f))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 4. Notifikasi Tani Banner Section
                item {
                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                        NotifikasiTaniBanner(
                            onClick = { onOpenNotifications() }
                        )
                    }
                }
            }
        }
    }

    // ---------------------------------------------------------------------------
    // INTERACTIVE SUB-MODALS / BOTTOM SHEETS
    // ---------------------------------------------------------------------------

    // 1. Daily Tips Modal
    if (isTipsModalOpen) {
        DailyTipsDetailModal(onDismiss = { isTipsModalOpen = false })
    }

    // 2. Notifications Modal
    if (isNotificationsModalOpen) {
        NotifikasiTaniDetailModal(onDismiss = { isNotificationsModalOpen = false })
    }

    // 3. Service Detail Modals based on selected service card
    when (selectedServiceModal) {
        FarmerServiceType.PANEN -> PanenModal(onDismiss = { selectedServiceModal = null })
        FarmerServiceType.HARGA_PASAR -> HargaPasarModal(onDismiss = { selectedServiceModal = null })
        FarmerServiceType.PENGEPUL -> PengepulModal(onDismiss = { selectedServiceModal = null })
        FarmerServiceType.PENYULUH -> PenyuluhModal(onDismiss = { selectedServiceModal = null })
        FarmerServiceType.KELOMPOK_TANI -> KelompokTaniModal(onDismiss = { selectedServiceModal = null })
        FarmerServiceType.PROFIL_TANAMAN -> ProfilTanamanModal(onDismiss = { selectedServiceModal = null })
        null -> {}
    }
}

// ---------------------------------------------------------------------------
// 1. HEADER SECTION (Title, Avatar, Search Box)
// ---------------------------------------------------------------------------
@Composable
fun FarmerHeaderSection(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = AgriTheme.colors.mintBg.copy(alpha = 0.85f),
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Row: Title + User Profile Ring Avatar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Fitur Petani",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTheme.colors.textPrimary
                    )
                    Text(
                        text = "Pusat Layanan & Komunitas Tani AgriSync",
                        fontSize = 12.sp,
                        color = AgriTheme.colors.textSecondary
                    )
                }

                // Profile Avatar with Ring
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(AgriTheme.colors.surface)
                        .border(2.dp, Color(0xFF2E7D32).copy(alpha = 0.25f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF2E7D32), Color(0xFF1B5E20))
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profil Petani",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            // Search Bar Input Field (Matching Figma design)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = AgriTheme.colors.surface,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = AgriTheme.colors.textMuted,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onQueryChange,
                        placeholder = {
                            Text(
                                text = "Cari fitur atau layanan...",
                                fontSize = 14.sp,
                                color = AgriTheme.colors.textMuted
                            )
                        },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = AgriTheme.colors.textPrimary,
                            unfocusedTextColor = AgriTheme.colors.textPrimary,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            disabledBorderColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        modifier = Modifier.weight(1f)
                    )

                    if (searchQuery.isNotEmpty()) {
                        IconButton(
                            onClick = onClearSearch,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search",
                                tint = AgriTheme.colors.grayIcon,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 2. DAILY TIPS BANNER CARD ("TIPS HARIAN")
// ---------------------------------------------------------------------------
@Composable
fun DailyTipsCard(
    onReadNowClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1.0f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "tipsScale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onReadNowClick
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2E7D32), Color(0xFF1B5E20))
                    )
                )
                .padding(20.dp)
        ) {
            // Decorative background blurs
            Canvas(modifier = Modifier.matchParentSize()) {
                drawCircle(
                    color = Color.White.copy(alpha = 0.06f),
                    radius = 120.dp.toPx(),
                    center = Offset(size.width + 20.dp.toPx(), -20.dp.toPx())
                )
                drawCircle(
                    color = Color(0xFF4ADE80).copy(alpha = 0.12f),
                    radius = 80.dp.toPx(),
                    center = Offset(-20.dp.toPx(), size.height + 20.dp.toPx())
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Badge "TIPS HARIAN"
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.12f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "TIPS HARIAN",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }

                    // Headline Title
                    Text(
                        text = "Tingkatkan Hasil Panen",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Description text
                    Text(
                        text = "Pelajari teknik pemupukan organik terbaru untuk efisiensi maksimal.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFF3F4F6).copy(alpha = 0.85f),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // "Baca Sekarang" Button
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White,
                        shadowElevation = 2.dp,
                        modifier = Modifier.clickable { onReadNowClick() }
                    ) {
                        Text(
                            text = "Baca Sekarang",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Right Translucent Leaf Circle Graphic
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.12f))
                        .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Eco,
                        contentDescription = "Eco Plant",
                        tint = Color(0xFF86EFAC),
                        modifier = Modifier.size(38.dp)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// 3. SERVICE CARD ITEM (Layanan Utama Grid Cell)
// ---------------------------------------------------------------------------
@Composable
fun ServiceCardItem(
    modifier: Modifier = Modifier,
    service: FarmerServiceType,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1.0f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "cardScale"
    )

    Card(
        modifier = modifier
            .height(154.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        border = BorderStroke(1.dp, AgriTheme.colors.grayBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Icon Badge Box (48x48 rounded 16dp)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(service.bgHex)),
                contentAlignment = Alignment.Center
            ) {
                ServiceIconGraphic(
                    service = service,
                    tint = Color(service.iconTintHex)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = service.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary
                )
                Text(
                    text = service.subtitle,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = AgriTheme.colors.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Custom Icon Dispatcher for Service Cards
@Composable
fun ServiceIconGraphic(
    service: FarmerServiceType,
    tint: Color
) {
    when (service) {
        FarmerServiceType.PANEN -> {
            // Tractor / Harvest Icon
            Canvas(modifier = Modifier.size(26.dp)) {
                // Main tractor body
                drawRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.2f, size.height * 0.35f),
                    size = Size(size.width * 0.5f, size.height * 0.35f)
                )
                // Cabin
                drawRect(
                    color = tint,
                    topLeft = Offset(size.width * 0.45f, size.height * 0.15f),
                    size = Size(size.width * 0.25f, size.height * 0.25f)
                )
                // Rear Wheel (Large)
                drawCircle(
                    color = tint,
                    radius = size.width * 0.18f,
                    center = Offset(size.width * 0.35f, size.height * 0.75f)
                )
                // Front Wheel (Small)
                drawCircle(
                    color = tint,
                    radius = size.width * 0.12f,
                    center = Offset(size.width * 0.78f, size.height * 0.8f)
                )
            }
        }
        FarmerServiceType.HARGA_PASAR -> {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }
        FarmerServiceType.PENGEPUL -> {
            Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }
        FarmerServiceType.PENYULUH -> {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }
        FarmerServiceType.KELOMPOK_TANI -> {
            Icon(
                imageVector = Icons.Default.Groups,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }
        FarmerServiceType.PROFIL_TANAMAN -> {
            Icon(
                imageVector = Icons.Default.Spa,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// 4. NOTIFIKASI TANI BANNER
// ---------------------------------------------------------------------------
@Composable
fun NotifikasiTaniBanner(
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
        border = BorderStroke(1.dp, AgriTheme.colors.border),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Bell Icon Circle
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF3F4F6)),
                    contentAlignment = Alignment.Center
                ) {
                    Box {
                        Icon(
                            imageVector = Icons.Default.NotificationsNone,
                            contentDescription = "Notifikasi",
                            tint = Color(0xFF374151),
                            modifier = Modifier.size(22.dp)
                        )
                        // Active badge dot
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFEF4444))
                                .align(Alignment.TopEnd)
                        )
                    }
                }

                Column {
                    Text(
                        text = "Notifikasi Tani",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTheme.colors.textPrimary
                    )
                    Text(
                        text = "3 pesan baru dari penyuluh",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280)
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Buka Notifikasi",
                tint = Color(0xFF9CA3AF),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Ambient Background Blur Circles Component
@Composable
fun AmbientBackgroundGlows() {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Top Left Soft Glow
            drawCircle(
                color = Color(0xFF4ADE80).copy(alpha = 0.05f),
                radius = 180.dp.toPx(),
                center = Offset(20.dp.toPx(), -20.dp.toPx())
            )
            // Bottom Right Soft Glow
            drawCircle(
                color = Color(0xFF2E7D32).copy(alpha = 0.06f),
                radius = 220.dp.toPx(),
                center = Offset(size.width + 40.dp.toPx(), size.height - 40.dp.toPx())
            )
        }
    }
}

// ---------------------------------------------------------------------------
// DETAILED MODALS / BOTTOM SHEETS IMPLEMENTATION
// ---------------------------------------------------------------------------

// 1. Daily Tips Modal Dialog
@Composable
fun DailyTipsDetailModal(onDismiss: () -> Unit) {
    var isBookmarked by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = AgriTheme.colors.surface,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Header Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = AgriTheme.colors.mintBg
                    ) {
                        Text(
                            text = "PUPUK & NUTRISI • 3 MIN BACA",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Row {
                        IconButton(onClick = { isBookmarked = !isBookmarked }) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) Color(0xFF2E7D32) else Color(0xFF6B7280)
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "Teknik Pemupukan Organik Terbaru untuk Hasil Panen Maksimal",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }

                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.mintBg)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = Color(0xFF2E7D32),
                                    modifier = Modifier.size(28.dp)
                                )
                                Column {
                                    Text(
                                        text = "Ringkasan Eksekutif",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E7D32)
                                    )
                                    Text(
                                        text = "Penggunaan Pupuk Organik Cair (POC) dikombinasikan dengan bio-charcoal terbukti meningkatkan bobot panen hingga 24%.",
                                        fontSize = 12.sp,
                                        color = Color(0xFF1B5E20)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Langkah-Langkah Aplikasi:",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }

                    item {
                        TipStepItem(
                            stepNumber = "1",
                            title = "Fermentasi Limbah Hijau",
                            description = "Campurkan sisa sayuran segar dengan molase dan bioaktivator EM4. Diamkan selama 14 hari dalam wadah kedap udara."
                        )
                    }

                    item {
                        TipStepItem(
                            stepNumber = "2",
                            title = "Penyaringan & Pengenceran",
                            description = "Saring cairan POC dengan rasio 1:100 (10 ml POC untuk setiap 1 Liter air bersih murni)."
                        )
                    }

                    item {
                        TipStepItem(
                            stepNumber = "3",
                            title = "Penyemprotan Pagi Hari",
                            description = "Semprotkan pada stomata daun bawah pukul 06.00-08.00 WIB saat penyerapan nutrisi optimal."
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom Action Button
                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("Pahami & Terapkan Hari Ini", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun TipStepItem(stepNumber: String, title: String, description: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Color(0xFF2E7D32)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = stepNumber, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Column {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
            Text(text = description, fontSize = 12.sp, color = Color(0xFF4B5563))
        }
    }
}

// 2. Panen Modal (Catat Hasil & Jadwal Panen)
@Composable
fun PanenModal(onDismiss: () -> Unit) {
    var weightInput by remember { mutableStateOf("") }
    var selectedCrop by remember { mutableStateOf("Selada Romaine") }
    var showSuccessToast by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = AgriTheme.colors.surface,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Modal Header
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
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFFEF3C7)),
                            contentAlignment = Alignment.Center
                        ) {
                            ServiceIconGraphic(
                                service = FarmerServiceType.PANEN,
                                tint = Color(0xFFD97706)
                            )
                        }
                        Column {
                            Text("Manajemen Panen", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                            Text("Catat hasil panen & jadwal mendatang", fontSize = 11.sp, color = Color(0xFF6B7280))
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Divider(color = Color(0xFFF3F4F6))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Quick Stats Cards
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("PANEN BULAN INI", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB45309))
                                    Text("1,250 kg", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF78350F))
                                }
                            }
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.mintBg)
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("PANEN BERIKUTNYA", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                                    Text("12 Okt (H-3)", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                }
                            }
                        }
                    }

                    // Record New Harvest Form
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.background),
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text("Catat Hasil Panen Baru", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)

                                OutlinedTextField(
                                    value = selectedCrop,
                                    onValueChange = { selectedCrop = it },
                                    label = { Text("Jenis Komoditas") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )

                                OutlinedTextField(
                                    value = weightInput,
                                    onValueChange = { weightInput = it },
                                    label = { Text("Jumlah Hasil Panen (kg)") },
                                    placeholder = { Text("Contoh: 150") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    shape = RoundedCornerShape(12.dp)
                                )

                                Button(
                                    onClick = {
                                        if (weightInput.isNotEmpty()) {
                                            showSuccessToast = true
                                            weightInput = ""
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                                ) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Simpan Record Panen", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }

                                if (showSuccessToast) {
                                    Surface(
                                        color = Color(0xFFD1FAE5),
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(10.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF059669))
                                            Text("Data panen berhasil dicatat!", fontSize = 12.sp, color = Color(0xFF065F46), fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Harvest Timeline Schedule
                    item {
                        Text("Jadwal & Riwayat Panen", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                    }

                    item {
                        HarvestHistoryRow("Selada Romaine Batch 4", "150 kg • Grade A", "21 Sep 2026", true)
                    }
                    item {
                        HarvestHistoryRow("Cabai Merah Keriting", "Estimasi 80 kg", "25 Sep 2026", false)
                    }
                    item {
                        HarvestHistoryRow("Tomat Cherry Hydroponic", "Estimasi 200 kg", "02 Okt 2026", false)
                    }
                }
            }
        }
    }
}

@Composable
fun HarvestHistoryRow(title: String, detail: String, date: String, isDone: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFFF9FAFB))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.Spa,
                contentDescription = null,
                tint = if (isDone) Color(0xFF059669) else Color(0xFFD97706),
                modifier = Modifier.size(20.dp)
            )
            Column {
                Text(title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                Text(detail, fontSize = 11.sp, color = Color(0xFF6B7280))
            }
        }
        Surface(
            shape = CircleShape,
            color = if (isDone) Color(0xFFD1FAE5) else Color(0xFFFEF3C7)
        ) {
            Text(
                text = date,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (isDone) Color(0xFF065F46) else Color(0xFFB45309),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }
}

// 3. Harga Pasar Modal (Update Harga Komoditas)
@Composable
fun HargaPasarModal(onDismiss: () -> Unit) {
    var isAlertEnabled by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = AgriTheme.colors.surface,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
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
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFD1FAE5)),
                            contentAlignment = Alignment.Center
                        ) {
                            ServiceIconGraphic(
                                service = FarmerServiceType.HARGA_PASAR,
                                tint = Color(0xFF059669)
                            )
                        }
                        Column {
                            Text("Harga Pasar Komoditas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                            Text("Update Real-Time Pasar Induk", fontSize = 11.sp, color = Color(0xFF6B7280))
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // Location selector pill
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF3F4F6)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(16.dp))
                            Text("Lokasi: Pasar Induk Kramat Jati, Jakarta", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                        }
                        Text("Ubah", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF059669))
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        MarketPriceCardItem("Cabai Merah Keriting", "Rp 45.000 / kg", "+5.2%", true)
                    }
                    item {
                        MarketPriceCardItem("Selada Romaine Hydroponic", "Rp 28.000 / kg", "+2.1%", true)
                    }
                    item {
                        MarketPriceCardItem("Tomat Cherry Red", "Rp 22.000 / kg", "-1.5%", false)
                    }
                    item {
                        MarketPriceCardItem("Bawang Merah Brebes", "Rp 38.000 / kg", "0.0%", true)
                    }
                    item {
                        MarketPriceCardItem("Padi IR64 Super", "Rp 12.500 / kg", "+0.8%", true)
                    }
                }

                // Bottom Toggle Alert
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.mintBg)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Notifikasi Perubahan Harga", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
                            Text("Dapatkan alert jika harga naik/turun > 5%", fontSize = 10.sp, color = Color(0xFF2E7D32))
                        }
                        Switch(
                            checked = isAlertEnabled,
                            onCheckedChange = { isAlertEnabled = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF2E7D32))
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MarketPriceCardItem(name: String, price: String, change: String, isUp: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.background),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                Text("Terakhir diperbarui: 10 menit lalu", fontSize = 10.sp, color = Color(0xFF9CA3AF))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(price, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                Surface(
                    shape = CircleShape,
                    color = if (isUp) Color(0xFFD1FAE5) else Color(0xFFFEE2E2)
                ) {
                    Text(
                        text = change,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isUp) Color(0xFF065F46) else Color(0xFF991B1B),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

// 4. Pengepul Modal (Jual Hasil Panen)
@Composable
fun PengepulModal(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = AgriTheme.colors.surface,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
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
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFDBEAFE)),
                            contentAlignment = Alignment.Center
                        ) {
                            ServiceIconGraphic(
                                service = FarmerServiceType.PENGEPUL,
                                tint = Color(0xFF2563EB)
                            )
                        }
                        Column {
                            Text("Mitra Pengepul & Pembeli", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                            Text("Jual hasil panen langsung ke pembeli", fontSize = 11.sp, color = Color(0xFF6B7280))
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Divider(color = Color(0xFFF3F4F6))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        PengepulItemCard(
                            name = "PT Agrobisnis Jaya Utama",
                            rating = "4.9 (128 Transaksi)",
                            distance = "2.4 km dari lokasi Anda",
                            need = "Cari 500 kg Selada Romaine",
                            offerPrice = "Rp 29.000 / kg"
                        )
                    }
                    item {
                        PengepulItemCard(
                            name = "Pengepul Pak Mulyono",
                            rating = "4.8 (85 Transaksi)",
                            distance = "4.1 km dari lokasi Anda",
                            need = "Cari 200 kg Cabai Merah",
                            offerPrice = "Rp 46.000 / kg"
                        )
                    }
                    item {
                        PengepulItemCard(
                            name = "Koperasi Tani Makmur",
                            rating = "4.9 (210 Transaksi)",
                            distance = "5.8 km dari lokasi Anda",
                            need = "Cari 1 Ton Tomat Cherry",
                            offerPrice = "Rp 23.000 / kg"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PengepulItemCard(
    name: String,
    rating: String,
    distance: String,
    need: String,
    offerPrice: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.background),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(14.dp))
                        Text(rating, fontSize = 11.sp, color = Color(0xFF6B7280))
                    }
                }
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFDBEAFE)
                ) {
                    Text(offerPrice, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E40AF), modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                }
            }

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Kebutuhan: $need", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151))
                    Text(distance, fontSize = 10.sp, color = Color(0xFF6B7280))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Hubungi Direct", fontSize = 11.sp)
                }
            }
        }
    }
}

// 5. Penyuluh Modal (Konsultasi Ahli Tani)
@Composable
fun PenyuluhModal(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = AgriTheme.colors.surface,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
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
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFF3E8FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            ServiceIconGraphic(
                                service = FarmerServiceType.PENYULUH,
                                tint = Color(0xFF7C3AED)
                            )
                        }
                        Column {
                            Text("Penyuluh Pertanian", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                            Text("Konsultasi Ahli Tani Terverifikasi", fontSize = 11.sp, color = Color(0xFF6B7280))
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        PenyuluhItemCard(
                            name = "Dr. Ir. Bambang Subagyo, M.Si",
                            specialty = "Spesialis Hama & Penyakit Tanaman",
                            status = "Online • Siap Konsultasi",
                            isOnline = true
                        )
                    }
                    item {
                        PenyuluhItemCard(
                            name = "Siti Aminah, S.P.",
                            specialty = "Ahli Nutrisi Hydroponik & Pemupukan",
                            status = "Online • Siap Konsultasi",
                            isOnline = true
                        )
                    }
                    item {
                        PenyuluhItemCard(
                            name = "Ahmad Hidayat, M.Sc.",
                            specialty = "Teknologi Irigasi & Digital Farming",
                            status = "Offline • Balas dlm 1 jam",
                            isOnline = false
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PenyuluhItemCard(
    name: String,
    specialty: String,
    status: String,
    isOnline: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.background),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF3E8FF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.School, contentDescription = null, tint = Color(0xFF7C3AED), modifier = Modifier.size(24.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                Text(specialty, fontSize = 11.sp, color = Color(0xFF6B7280))
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(if (isOnline) Color(0xFF10B981) else Color(0xFF9CA3AF))
                    )
                    Text(status, fontSize = 10.sp, color = if (isOnline) Color(0xFF047857) else Color(0xFF6B7280), fontWeight = FontWeight.SemiBold)
                }
            }

            Button(
                onClick = { },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("Chat", fontSize = 11.sp)
            }
        }
    }
}

// 6. Kelompok Tani Modal (Forum Komunitas)
@Composable
fun KelompokTaniModal(onDismiss: () -> Unit) {
    var newPostText by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = AgriTheme.colors.surface,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
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
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFFFEDD5)),
                            contentAlignment = Alignment.Center
                        ) {
                            ServiceIconGraphic(
                                service = FarmerServiceType.KELOMPOK_TANI,
                                tint = Color(0xFFEA580C)
                            )
                        }
                        Column {
                            Text("Forum Kelompok Tani", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                            Text("Poktan Suka Maju (48 Anggota)", fontSize = 11.sp, color = Color(0xFF6B7280))
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                // New Post Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newPostText,
                        onValueChange = { newPostText = it },
                        placeholder = { Text("Tulis pertanyaan atau informasi...", fontSize = 12.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    Button(
                        onClick = { if (newPostText.isNotEmpty()) newPostText = "" },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEA580C))
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Kirim", modifier = Modifier.size(16.dp))
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        CommunityPostCard(
                            author = "Pak Supri (Petani Selada)",
                            time = "10m yang lalu",
                            content = "Izin tanya rekan-rekan, ada yang punya rekomendasi pupuk organik cair untuk selada saat musim hujan?",
                            repliesCount = "5 Balasan"
                        )
                    }
                    item {
                        CommunityPostCard(
                            author = "Bu Hani (Bendahara Poktan)",
                            time = "2j yang lalu",
                            content = "Pengumuman: Jadwal pembersihan saluran irigasi bersama akan diadakan Sabtu jam 07.00 WIB.",
                            repliesCount = "12 Menyukai"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommunityPostCard(author: String, time: String, content: String, repliesCount: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.background),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(author, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                Text(time, fontSize = 10.sp, color = Color(0xFF9CA3AF))
            }
            Text(content, fontSize = 12.sp, color = Color(0xFF374151))
            Text(repliesCount, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFEA580C))
        }
    }
}

// 7. Profil Tanaman Modal (Database Botani)
@Composable
fun ProfilTanamanModal(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = AgriTheme.colors.surface,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
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
                                .size(38.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFECFCCB)),
                            contentAlignment = Alignment.Center
                        ) {
                            ServiceIconGraphic(
                                service = FarmerServiceType.PROFIL_TANAMAN,
                                tint = Color(0xFF65A30D)
                            )
                        }
                        Column {
                            Text("Database Botani Tanaman", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                            Text("Katalog & Panduan Budidaya", fontSize = 11.sp, color = Color(0xFF6B7280))
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        PlantProfileCard(
                            name = "Selada Romaine (Lactuca sativa)",
                            ph = "6.0 - 6.8",
                            temp = "18 - 24°C",
                            harvestTime = "30 Hari",
                            water = "Tinggi"
                        )
                    }
                    item {
                        PlantProfileCard(
                            name = "Cabai Merah Keriting (Capsicum annuum)",
                            ph = "5.5 - 6.5",
                            temp = "24 - 28°C",
                            harvestTime = "75 Hari",
                            water = "Sedang"
                        )
                    }
                    item {
                        PlantProfileCard(
                            name = "Tomat Cherry Red (Solanum lycopersicum)",
                            ph = "6.0 - 6.8",
                            temp = "20 - 25°C",
                            harvestTime = "60 Hari",
                            water = "Tinggi"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlantProfileCard(name: String, ph: String, temp: String, harvestTime: String, water: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.background),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PlantParamChip("pH: $ph")
                PlantParamChip("Suhu: $temp")
                PlantParamChip("Panen: $harvestTime")
            }
        }
    }
}

@Composable
fun PlantParamChip(text: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xFFECFCCB)
    ) {
        Text(text, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3F6212), modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
    }
}

// 8. Notifikasi Tani Detail Modal
@Composable
fun NotifikasiTaniDetailModal(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f),
            shape = RoundedCornerShape(28.dp),
            color = AgriTheme.colors.surface,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Pesan & Notifikasi Tani", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        NotificationDetailItem(
                            sender = "Penyuluh Dr. Ir. Bambang",
                            time = "10m yang lalu",
                            message = "Rekomendasi pemupukan selada minggu ke-3 sudah diperbarui di dashboard Anda. Silakan cek detailnya."
                        )
                    }
                    item {
                        NotificationDetailItem(
                            sender = "Sistem Peringatan Dini",
                            time = "2j yang lalu",
                            message = "Kelembapan tanah lahan B terdeteksi turun ke 45%. Sistem irigasi otomatis disarankan aktif."
                        )
                    }
                    item {
                        NotificationDetailItem(
                            sender = "PT Agrobisnis Jaya",
                            time = "1 hari lalu",
                            message = "Penawaran harga panen selada Romaine Rp 29.000/kg telah dikirimkan. Silakan konfirmasi ketersediaan."
                        )
                    }
                }

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("Tandai Semua Dibaca", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun NotificationDetailItem(sender: String, time: String, message: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.background),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(sender, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                Text(time, fontSize = 10.sp, color = Color(0xFF9CA3AF))
            }
            Text(message, fontSize = 12.sp, color = Color(0xFF4B5563))
        }
    }
}

// ---------------------------------------------------------------------------
// PREVIEW
// ---------------------------------------------------------------------------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FarmerFeatureScreenPreview() {
    MyApplicationTheme {
        FarmerFeatureScreen()
    }
}
