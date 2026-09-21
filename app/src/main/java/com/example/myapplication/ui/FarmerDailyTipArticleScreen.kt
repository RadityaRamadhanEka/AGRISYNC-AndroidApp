package com.example.myapplication.ui

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun FarmerDailyTipArticleScreen(
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isBookmarked by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xFFF9FAFB),
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFE8F5E9).copy(alpha = 0.8f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF111827))
                    }

                    Text("Tips Harian", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))

                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = Color(0xFF111827))
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = { isBookmarked = !isBookmarked },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (isBookmarked) Color(0xFF15803D) else Color(0xFF2E7D32))
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (isBookmarked) "Tersimpan di Bookmark" else "Simpan ke Bookmark",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                containerColor = Color.White,
                contentColor = Color(0xFF2E7D32),
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share", modifier = Modifier.size(20.dp))
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Hero Card Image with "ORGANIK" badge
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(color = Color(0xFF4ADE80).copy(alpha = 0.2f), radius = 160.dp.toPx(), center = Offset(size.width * 0.7f, size.height * 0.4f))
                        }

                        // Top Left Badge
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(16.dp),
                            shape = CircleShape,
                            color = Color.White.copy(alpha = 0.25f),
                            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Spa, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                Text("ORGANIK", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White, letterSpacing = 0.5.sp)
                            }
                        }

                        Text(
                            text = "🥬",
                            fontSize = 72.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            // Title & Author Meta Row
            item {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = "Tingkatkan Hasil Panen dengan Teknik Pemupukan Organik Terbaru",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827),
                        lineHeight = 30.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2E7D32)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                            }

                            Column {
                                Text("Dr. Green", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                                Text("Ahli Agronomi", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF2E7D32))
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("WAKTU BACA", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9CA3AF), letterSpacing = 0.5.sp)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Schedule, contentDescription = null, tint = Color(0xFF4B5563), modifier = Modifier.size(14.dp))
                                Text("5 Menit", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF4B5563))
                            }
                        }
                    }
                }
            }

            // Article Introduction
            item {
                Text(
                    text = "Dalam pertanian modern, efisiensi nutrisi adalah kunci keberhasilan panen yang melimpah. Metode hidroponik yang kita gunakan di AGRISYNC memungkinkan kontrol penuh, namun seringkali detail kecil terlewatkan. Berikut adalah langkah teknis untuk mengoptimalkan sistem Anda secara organik.",
                    fontSize = 14.sp,
                    color = Color(0xFF4B5563),
                    lineHeight = 24.sp
                )
            }

            // Section 1: Optimasi pH Air
            item {
                ArticleStepCard(
                    title = "Optimasi pH Air",
                    desc = "Keseimbangan pH sangat mempengaruhi penyerapan nutrisi akar. Tanaman selada membutuhkan kondisi sedikit asam.",
                    items = listOf(
                        "Jaga rentang pH ideal antara 5.5 - 6.5 untuk penyerapan maksimal.",
                        "Lakukan pengecekan setiap pagi sebelum matahari terik."
                    ),
                    icon = Icons.Default.WaterDrop,
                    iconBgHex = 0xFFEFF6FF,
                    iconTintHex = 0xFF2563EB
                )
            }

            // Section 2: Pemilihan Nutrisi Cair
            item {
                ArticleStepCard(
                    title = "Pemilihan Nutrisi Cair",
                    desc = "Tidak semua pupuk cair diciptakan sama. Fokus pada kandungan mikro-nutrien yang lengkap dan berbasis organik.",
                    items = listOf(
                        "Gunakan rasio N-P-K 3:1:4 khusus untuk fase pembuahan vegetatif.",
                        "Tambahkan larutan kalsium nitrat untuk memperkuat dinding sel daun."
                    ),
                    icon = Icons.Default.Lightbulb,
                    iconBgHex = 0xFFFFFBEB,
                    iconTintHex = 0xFFD97706
                )
            }

            // Section 3: Jadwal Pencahayaan
            item {
                ArticleStepCard(
                    title = "Jadwal Pencahayaan",
                    desc = "Cahaya adalah sumber energi utama. Durasi penyinaran harus disesuaikan dengan fase pertumbuhan agar tidak stress.",
                    items = listOf(
                        "Berikan 14-16 jam cahaya untuk sayuran daun hijau."
                    ),
                    icon = Icons.Default.WbSunny,
                    iconBgHex = 0xFFFAF5FF,
                    iconTintHex = 0xFF9333EA
                )
            }
        }
    }
}

@Composable
fun ArticleStepCard(
    title: String,
    desc: String,
    items: List<String>,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBgHex: Long,
    iconTintHex: Long
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(iconBgHex)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = icon, contentDescription = null, tint = Color(iconTintHex), modifier = Modifier.size(20.dp))
                }

                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
            }

            Text(desc, fontSize = 13.sp, color = Color(0xFF4B5563), lineHeight = 20.sp)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (itemText in items) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF9FAFB),
                        border = BorderStroke(1.dp, Color(0xFFF3F4F6))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                            Text(itemText, fontSize = 12.sp, color = Color(0xFF374151), fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmerDailyTipArticleScreenPreview() {
    MyApplicationTheme {
        FarmerDailyTipArticleScreen()
    }
}
