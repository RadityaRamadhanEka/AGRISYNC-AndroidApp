package com.example.myapplication.ui

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun FarmerHarvestRecordScreen(
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var farmerName by remember { mutableStateOf("Raditya") }
    var location by remember { mutableStateOf("Greenhouse A - Hidroponik") }
    var commodity by remember { mutableStateOf("Selada Romaine") }
    var weightKg by remember { mutableStateOf("150") }
    var areaM2 by remember { mutableStateOf("50") }
    var ageDays by remember { mutableStateOf("30") }
    var harvestDate by remember { mutableStateOf("10/21/2026") }

    var isDatePickerOpen by remember { mutableStateOf(false) }
    var isSavedSuccess by remember { mutableStateOf(false) }

    var locationDropdownExpanded by remember { mutableStateOf(false) }
    var commodityDropdownExpanded by remember { mutableStateOf(false) }

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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Top Header Bar
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
                            text = "Catat Panen",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = AgriTheme.colors.textPrimary
                        )
                    }
                }
            }

            // Form Section Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // NAMA PETANI
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "NAMA PETANI",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5E8760),
                                letterSpacing = 0.5.sp
                            )
                            OutlinedTextField(
                                value = farmerName,
                                onValueChange = { farmerName = it },
                                leadingIcon = {
                                    Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color(0xFF5E8760))
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF2E7D32),
                                    unfocusedBorderColor = Color(0xFFE5E7EB)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        // LOKASI LAHAN Dropdown
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "LOKASI LAHAN",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5E8760),
                                letterSpacing = 0.5.sp
                            )
                            Box {
                                OutlinedTextField(
                                    value = location,
                                    onValueChange = {},
                                    readOnly = true,
                                    leadingIcon = {
                                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF5E8760))
                                    },
                                    trailingIcon = {
                                        IconButton(onClick = { locationDropdownExpanded = true }) {
                                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFF5E8760))
                                        }
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF2E7D32),
                                        unfocusedBorderColor = Color(0xFFE5E7EB)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { locationDropdownExpanded = true }
                                )
                                DropdownMenu(
                                    expanded = locationDropdownExpanded,
                                    onDismissRequest = { locationDropdownExpanded = false }
                                ) {
                                    listOf("Greenhouse A - Hidroponik", "Lahan B - Lembang", "Sawah Sektor 3", "Greenhouse B").forEach { loc ->
                                        DropdownMenuItem(
                                            text = { Text(loc) },
                                            onClick = {
                                                location = loc
                                                locationDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // KOMODITAS / TANAMAN Dropdown
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "KOMODITAS / TANAMAN",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF5E8760),
                                letterSpacing = 0.5.sp
                            )
                            Box {
                                OutlinedTextField(
                                    value = commodity,
                                    onValueChange = {},
                                    readOnly = true,
                                    leadingIcon = {
                                        Icon(imageVector = Icons.Default.Spa, contentDescription = null, tint = Color(0xFF5E8760))
                                    },
                                    trailingIcon = {
                                        IconButton(onClick = { commodityDropdownExpanded = true }) {
                                            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Color(0xFF5E8760))
                                        }
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF2E7D32),
                                        unfocusedBorderColor = Color(0xFFE5E7EB)
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { commodityDropdownExpanded = true }
                                )
                                DropdownMenu(
                                    expanded = commodityDropdownExpanded,
                                    onDismissRequest = { commodityDropdownExpanded = false }
                                ) {
                                    listOf("Selada Romaine", "Cabai Merah Keriting", "Tomat Cherry", "Bayam Hijau", "Bawang Merah").forEach { item ->
                                        DropdownMenuItem(
                                            text = { Text(item) },
                                            onClick = {
                                                commodity = item
                                                commodityDropdownExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // JUMLAH (KG) & LUAS (M²)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("JUMLAH (KG)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5E8760), letterSpacing = 0.5.sp)
                                OutlinedTextField(
                                    value = weightKg,
                                    onValueChange = { weightKg = it },
                                    trailingIcon = { Text("KG", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9CA3AF), modifier = Modifier.padding(end = 12.dp)) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    shape = RoundedCornerShape(16.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF2E7D32), unfocusedBorderColor = Color(0xFFE5E7EB)),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text("LUAS (M²)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5E8760), letterSpacing = 0.5.sp)
                                OutlinedTextField(
                                    value = areaM2,
                                    onValueChange = { areaM2 = it },
                                    trailingIcon = { Text("m²", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9CA3AF), modifier = Modifier.padding(end = 12.dp)) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    shape = RoundedCornerShape(16.dp),
                                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF2E7D32), unfocusedBorderColor = Color(0xFFE5E7EB)),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        // USIA TANAMAN
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("USIA TANAMAN", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5E8760), letterSpacing = 0.5.sp)
                            OutlinedTextField(
                                value = ageDays,
                                onValueChange = { ageDays = it },
                                leadingIcon = { Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF5E8760)) },
                                trailingIcon = { Text("Hari", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9CA3AF), modifier = Modifier.padding(end = 12.dp)) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF2E7D32), unfocusedBorderColor = Color(0xFFE5E7EB)),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        // TANGGAL PANEN
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("TANGGAL PANEN", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5E8760), letterSpacing = 0.5.sp)
                            OutlinedTextField(
                                value = harvestDate,
                                onValueChange = {},
                                readOnly = true,
                                leadingIcon = {
                                    IconButton(onClick = { isDatePickerOpen = true }) {
                                        Icon(imageVector = Icons.Default.DateRange, contentDescription = null, tint = Color(0xFF2E7D32))
                                    }
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF2E7D32), unfocusedBorderColor = Color(0xFFE5E7EB)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { isDatePickerOpen = true }
                            )
                        }

                        // FOTO HASIL PANEN UPLOAD BOX
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("FOTO HASIL PANEN", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5E8760), letterSpacing = 0.5.sp)
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { },
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFF2E7D32).copy(alpha = 0.05f),
                                border = BorderStroke(1.5.dp, Brush.linearGradient(listOf(Color(0xFF2E7D32).copy(alpha = 0.4f), Color(0xFF2E7D32).copy(alpha = 0.4f))))
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Surface(
                                        shape = CircleShape,
                                        color = Color.White,
                                        shadowElevation = 1.dp
                                    ) {
                                        Box(modifier = Modifier.padding(10.dp)) {
                                            Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(24.dp))
                                        }
                                    }
                                    Text("Tap untuk upload foto", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                                    Text("Format: JPG, PNG (Max 5MB)", fontSize = 10.sp, color = Color(0xFF9CA3AF))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Button Simpan Data Panen
                        Button(
                            onClick = { isSavedSuccess = true },
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
                                Text("Simpan Data Panen", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        if (isSavedSuccess) {
                            Surface(
                                color = Color(0xFFD1FAE5),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF059669))
                                    Text("Data panen berhasil disimpan!", fontSize = 12.sp, color = Color(0xFF065F46), fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // Statistik Panen Card Section
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
                    border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Statistik Panen", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                                Text("Volume bulan ini", fontSize = 12.sp, color = Color(0xFF5E8760))
                            }
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFF2E7D32).copy(alpha = 0.1f)
                            ) {
                                Text("Detail", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
                            }
                        }

                        // Bar Chart Weekly Preview
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            WeeklyBarColumn("Mg 1", 0.4f, Color(0xFF9CA3AF))
                            WeeklyBarColumn("Mg 2", 0.65f, Color(0xFF9CA3AF))
                            WeeklyBarColumn("Mg 3", 0.85f, Color(0xFF9CA3AF))
                            WeeklyBarColumn("Mg 4", 1.0f, Color(0xFF2E7D32))
                        }
                    }
                }
            }
        }
    }

    // Date Picker Calendar Modal (Figma node 90:639)
    if (isDatePickerOpen) {
        HarvestDatePickerModal(
            onDismiss = { isDatePickerOpen = false },
            onDateSelected = { date ->
                harvestDate = date
                isDatePickerOpen = false
            }
        )
    }
}

@Composable
fun WeeklyBarColumn(label: String, fillRatio: Float, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .width(28.dp)
                .height((70 * fillRatio).dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(color)
        )
        Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color(0xFF6B7280))
    }
}

// ---------------------------------------------------------------------------
// HARVEST DATE PICKER MODAL (Figma Node 90:639)
// ---------------------------------------------------------------------------
@Composable
fun HarvestDatePickerModal(
    onDismiss: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    var selectedDay by remember { mutableStateOf(8) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .clip(RoundedCornerShape(28.dp)),
            color = Color.White,
            shadowElevation = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Calendar Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {}) { Icon(Icons.Default.ChevronLeft, contentDescription = null) }
                    Text("Oktober 2023", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                    IconButton(onClick = {}) { Icon(Icons.Default.ChevronRight, contentDescription = null) }
                }

                // Days of week header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    listOf("SEN", "SEL", "RAB", "KAM", "JUM", "SAB", "MIN").forEach { day ->
                        Text(day, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF5E8760))
                    }
                }

                // Days grid 1 - 31
                val daysList = (1..31).toList()
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    val rows = daysList.chunked(7)
                    for (row in rows) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            for (dayNum in row) {
                                val isSelected = dayNum == selectedDay
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) Color(0xFF2E7D32) else Color.Transparent)
                                        .border(if (dayNum == 12) BorderStroke(1.dp, Color(0xFF4ADE80)) else BorderStroke(0.dp, Color.Transparent), CircleShape)
                                        .clickable { selectedDay = dayNum },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$dayNum",
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else Color(0xFF374151)
                                    )
                                }
                            }
                            if (row.size < 7) {
                                repeat(7 - row.size) {
                                    Spacer(modifier = Modifier.size(34.dp))
                                }
                            }
                        }
                    }
                }

                // Modal Bottom Action Buttons ("Batal" & "Pilih")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3F4F6))
                    ) {
                        Text("Batal", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280))
                    }

                    Button(
                        onClick = { onDateSelected("10/$selectedDay/2023") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                    ) {
                        Text("Pilih", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmerHarvestRecordScreenPreview() {
    MyApplicationTheme {
        FarmerHarvestRecordScreen()
    }
}
