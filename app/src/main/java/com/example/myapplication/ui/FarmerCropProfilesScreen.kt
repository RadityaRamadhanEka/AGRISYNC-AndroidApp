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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

data class CropProfileItem(
    val id: String,
    val name: String,
    val variety: String,
    val emoji: String,
    val badgeText: String, // "IoT Aktif", "Siap Panen", "Perawatan"
    val badgeBgHex: Long,
    val badgeTextHex: Long,
    val quantity: String,
    val landArea: String,
    val progressDays: Int,
    val totalDays: Int,
    val progressColorHex: Long
)

enum class CropProfileScreenView { LIST, ADD_FORM, DETAIL }

@Composable
fun FarmerCropProfilesScreen(
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var currentView by remember { mutableStateOf(CropProfileScreenView.LIST) }
    var selectedCropId by remember { mutableStateOf("1") }
    var searchQuery by remember { mutableStateOf("") }

    val crops = remember {
        listOf(
            CropProfileItem(
                id = "1",
                name = "Cabai Merah",
                variety = "Varietas Hiyung",
                emoji = "🌶️",
                badgeText = "IoT Aktif",
                badgeBgHex = 0xFFDCFCE7,
                badgeTextHex = 0xFF15803D,
                quantity = "500 Batang",
                landArea = "20 m²",
                progressDays = 45,
                totalDays = 90,
                progressColorHex = 0xFF2E7D32
            ),
            CropProfileItem(
                id = "2",
                name = "Selada Romaine",
                variety = "Hidroponik",
                emoji = "🥬",
                badgeText = "Siap Panen",
                badgeBgHex = 0xFFEFF6FF,
                badgeTextHex = 0xFF2563EB,
                quantity = "2.000 Lubang",
                landArea = "50 m²",
                progressDays = 28,
                totalDays = 30,
                progressColorHex = 0xFF2563EB
            ),
            CropProfileItem(
                id = "3",
                name = "Tomat Ceri",
                variety = "Tropical Ruby",
                emoji = "🍅",
                badgeText = "Perawatan",
                badgeBgHex = 0xFFFFF7ED,
                badgeTextHex = 0xFFEA580C,
                quantity = "150 Batang",
                landArea = "15 m²",
                progressDays = 12,
                totalDays = 65,
                progressColorHex = 0xFFEA580C
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentView) {
                CropProfileScreenView.LIST -> {
                    CropProfileListView(
                        crops = crops,
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        onAddClick = { currentView = CropProfileScreenView.ADD_FORM },
                        onDetailClick = { cropId ->
                            selectedCropId = cropId
                            currentView = CropProfileScreenView.DETAIL
                        },
                        onBackClick = onBackClick
                    )
                }
                CropProfileScreenView.ADD_FORM -> {
                    AddCropProfileFormView(
                        onBackClick = { currentView = CropProfileScreenView.LIST },
                        onSavedSuccess = { currentView = CropProfileScreenView.LIST }
                    )
                }
                CropProfileScreenView.DETAIL -> {
                    CropDetailOverviewView(
                        cropId = selectedCropId,
                        onBackClick = { currentView = CropProfileScreenView.LIST },
                        onNavigateAnalytics = onNavigateAnalytics
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// VIEW 1: PROFIL TANAMAN SAYA LIST (Figma Node 90:2306)
// ---------------------------------------------------------------------------
@Composable
fun CropProfileListView(
    crops: List<CropProfileItem>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onDetailClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header Bar
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFE8F5E9).copy(alpha = 0.8f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White,
                                border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                                shadowElevation = 1.dp,
                                modifier = Modifier.clickable { onBackClick() }
                            ) {
                                Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF111827), modifier = Modifier.size(20.dp))
                                }
                            }

                            Text("Profil Tanaman Saya", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        }

                        Surface(
                            shape = CircleShape,
                            color = Color(0xFF2E7D32),
                            shadowElevation = 3.dp,
                            modifier = Modifier.clickable { onAddClick() }
                        ) {
                            Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Tanaman", tint = Color.White, modifier = Modifier.size(22.dp))
                            }
                        }
                    }

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchChange,
                        placeholder = { Text("Cari nama tanaman...", fontSize = 14.sp, color = Color(0xFF9CA3AF)) },
                        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color(0xFF9CA3AF)) },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF2E7D32), unfocusedBorderColor = Color(0xFFE5E7EB), unfocusedContainerColor = Color.White),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Crop Cards
        items(crops) { crop ->
            CropProfileCard(item = crop, onDetailClick = { onDetailClick(crop.id) })
        }
    }
}

@Composable
fun CropProfileCard(item: CropProfileItem, onDetailClick: () -> Unit) {
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
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFF9FAFB),
                        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                    ) {
                        Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) {
                            Text(item.emoji, fontSize = 24.sp)
                        }
                    }

                    Column {
                        Text(item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        Text(item.variety, fontSize = 12.sp, color = Color(0xFF5E8760))
                    }
                }

                Surface(
                    shape = CircleShape,
                    color = Color(item.badgeBgHex),
                    border = BorderStroke(1.dp, Color(item.badgeBgHex))
                ) {
                    Text(item.badgeText, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(item.badgeTextHex), modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                }
            }

            // Stats Sub-boxes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF9FAFB),
                    border = BorderStroke(1.dp, Color(0xFFF3F4F6))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("Jumlah", fontSize = 10.sp, color = Color(0xFF6B7280))
                        Text(item.quantity, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFF9FAFB),
                    border = BorderStroke(1.dp, Color(0xFFF3F4F6))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("Luas Lahan", fontSize = 10.sp, color = Color(0xFF6B7280))
                        Text(item.landArea, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F2937))
                    }
                }
            }

            // Progress Bar
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Progress Tanam", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF4B5563))
                    Text("${item.progressDays} / ${item.totalDays} Hari", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(item.progressColorHex))
                }

                val progress = item.progressDays.toFloat() / item.totalDays.toFloat()
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = Color(item.progressColorHex),
                    trackColor = Color(0xFFE5E7EB)
                )
            }

            // Button Lihat Detail
            OutlinedButton(
                onClick = onDetailClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFF2E7D32))
            ) {
                Text("Lihat Detail", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
            }
        }
    }
}

// ---------------------------------------------------------------------------
// VIEW 2: TAMBAH PROFIL TANAMAN FORM (Figma Node 90:2159)
// ---------------------------------------------------------------------------
@Composable
fun AddCropProfileFormView(
    onBackClick: () -> Unit,
    onSavedSuccess: () -> Unit
) {
    var selectedCrop by remember { mutableStateOf("Cabai Merah") }
    var seedCount by remember { mutableStateOf("500") }
    var landArea by remember { mutableStateOf("20") }
    var plantDate by remember { mutableStateOf("10/21/2026") }
    var estDuration by remember { mutableStateOf("90") }
    var mediaType by remember { mutableStateOf("Tanah (Konvensional)") }
    var isIotEnabled by remember { mutableStateOf(true) }

    var cropDropdownExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
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
                        Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF111827), modifier = Modifier.size(20.dp))
                        }
                    }

                    Text("Tambah Profil Tanaman", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                }
            }
        }

        item {
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
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Komoditas / Tanaman
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Komoditas / Tanaman", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                        Box {
                            OutlinedTextField(
                                value = selectedCrop,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = {
                                    IconButton(onClick = { cropDropdownExpanded = true }) {
                                        Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.fillMaxWidth()
                            )
                            DropdownMenu(
                                expanded = cropDropdownExpanded,
                                onDismissRequest = { cropDropdownExpanded = false }
                            ) {
                                listOf("Cabai Merah", "Selada Romaine", "Tomat Ceri", "Bayam Hijau").forEach { item ->
                                    DropdownMenuItem(text = { Text(item) }, onClick = { selectedCrop = item; cropDropdownExpanded = false })
                                }
                            }
                        }
                    }

                    // Jumlah Bibit
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Jumlah Bibit", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                        OutlinedTextField(
                            value = seedCount,
                            onValueChange = { seedCount = it },
                            trailingIcon = { Text("batang", fontSize = 12.sp, color = Color(0xFF9CA3AF), modifier = Modifier.padding(end = 12.dp)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Luas Lahan
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Luas Lahan", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                        OutlinedTextField(
                            value = landArea,
                            onValueChange = { landArea = it },
                            trailingIcon = { Text("m²", fontSize = 12.sp, color = Color(0xFF9CA3AF), modifier = Modifier.padding(end = 12.dp)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Tanggal Tanam
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Tanggal Tanam", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                        OutlinedTextField(
                            value = plantDate,
                            onValueChange = { plantDate = it },
                            leadingIcon = { Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, tint = Color(0xFF2E7D32)) },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Perkiraan Panen
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("Perkiraan Panen", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                        OutlinedTextField(
                            value = estDuration,
                            onValueChange = { estDuration = it },
                            trailingIcon = { Text("hari", fontSize = 12.sp, color = Color(0xFF9CA3AF), modifier = Modifier.padding(end = 12.dp)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // IoT Standard Card Toggle
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                border = BorderStroke(1.dp, Color(0xFF4ADE80).copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("Gunakan Standar IoT", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                            Icon(imageVector = Icons.Default.ElectricBolt, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                        }
                        Text("Aktifkan monitoring otomatis untuk suhu, kelembaban, dan nutrisi via sensor.", fontSize = 11.sp, color = Color(0xFF5E8760))
                    }
                    Switch(
                        checked = isIotEnabled,
                        onCheckedChange = { isIotEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF2E7D32))
                    )
                }
            }
        }

        // Save Profile Button
        item {
            Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                Button(
                    onClick = onSavedSuccess,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Text("Simpan Profil", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// VIEW 3: DETAIL CROP OVERVIEW (Figma Node 90:2471)
// ---------------------------------------------------------------------------
@Composable
fun CropDetailOverviewView(
    cropId: String,
    onBackClick: () -> Unit,
    onNavigateAnalytics: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
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
                        Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF111827), modifier = Modifier.size(20.dp))
                        }
                    }

                    Text("Detail Cabai Merah", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                }
            }
        }

        // Hero Card with Crop Image & IoT Badge & Health Score Gauge
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawCircle(color = Color(0xFF4ADE80).copy(alpha = 0.15f), radius = 180.dp.toPx(), center = Offset(size.width * 0.8f, size.height * 0.3f))
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Top Badges Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.Black.copy(alpha = 0.4f),
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF4ADE80))
                                    )
                                    Text("IoT Aktif", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }

                            Text("🌶️", fontSize = 32.sp)
                        }

                        // Bottom Radial Health Badge
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            shadowElevation = 4.dp,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier.size(44.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularGaugeIndicator(progress = 0.96f)
                                    Text("96%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                }

                                Column {
                                    Text("KESEHATAN", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280), letterSpacing = 0.5.sp)
                                    Text("Sangat Baik", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Progress Tanam Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(imageVector = Icons.Default.Spa, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(18.dp))
                            Text("Progress Tanam", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        }

                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFDCFCE7),
                            border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                        ) {
                            Text("Vegetative", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D), modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                    }

                    LinearProgressIndicator(
                        progress = { 45f / 90f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(CircleShape),
                        color = Color(0xFF2E7D32),
                        trackColor = Color(0xFFE5E7EB)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Day 45", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                        Text("Target 90 Hari", fontSize = 13.sp, color = Color(0xFF9CA3AF))
                    }
                }
            }
        }

        // 2x2 Sensor Metrics Row
        item {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricDetailSquare(
                        title = "Temp",
                        value = "28°C",
                        icon = Icons.Default.Thermostat,
                        modifier = Modifier.weight(1f)
                    )
                    MetricDetailSquare(
                        title = "Humidity",
                        value = "75%",
                        icon = Icons.Default.WaterDrop,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MetricDetailSquare(
                        title = "pH Tanah",
                        value = "6.2",
                        icon = Icons.Default.Science,
                        modifier = Modifier.weight(1f)
                    )
                    MetricDetailSquare(
                        title = "EC",
                        value = "1.8 mS/cm",
                        icon = Icons.Default.ElectricBolt,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Link View Analytics
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateAnalytics() },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("View Analytics", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                }
            }
        }

        // Bottom Action Buttons
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Icon(imageVector = Icons.Default.Settings, contentDescription = null, tint = Color(0xFF374151), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Kontrol Manual", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF374151))
                }

                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Icon(imageVector = Icons.Default.CalendarToday, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Atur Nutrisi", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun MetricDetailSquare(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF6B7280), modifier = Modifier.size(16.dp))
                Text(title, fontSize = 12.sp, color = Color(0xFF6B7280))
            }
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
        }
    }
}

@Composable
fun CircularGaugeIndicator(progress: Float) {
    Canvas(modifier = Modifier.size(44.dp)) {
        drawArc(
            color = Color(0xFFE5E7EB),
            startAngle = 135f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
        )
        drawArc(
            color = Color(0xFF2E7D32),
            startAngle = 135f,
            sweepAngle = 270f * progress,
            useCenter = false,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FarmerCropProfilesScreenPreview() {
    MyApplicationTheme {
        FarmerCropProfilesScreen()
    }
}
