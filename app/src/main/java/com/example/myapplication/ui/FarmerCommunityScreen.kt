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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

data class FarmerGroupItem(
    val id: String,
    val name: String,
    val role: String, // "Member" or "Admin"
    val memberCount: String,
    val location: String,
    val newMessagesCount: String,
    val avatarBgHex: Long = 0xFFDCFCE7,
    val extraMembersText: String
)

data class PopularDiscussionItem(
    val id: String,
    val tag: String, // "HOT TOPIC", "SHARING", "EVENT"
    val timeAgo: String,
    val title: String,
    val body: String,
    val likesCount: String,
    val commentsCount: String,
    val tagBgHex: Long = 0xFFFEE2E2,
    val tagTextHex: Long = 0xFFDC2626
)

@Composable
fun FarmerCommunityScreen(
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val myGroups = remember {
        listOf(
            FarmerGroupItem(
                id = "1",
                name = "Paguyuban Hidroponik Kota",
                role = "Member",
                memberCount = "245 Anggota",
                location = "Jakarta Selatan",
                newMessagesCount = "12 Pesan Baru",
                avatarBgHex = 0xFFDCFCE7,
                extraMembersText = "+4"
            ),
            FarmerGroupItem(
                id = "2",
                name = "Petani Milenial Sejahtera",
                role = "Admin",
                memberCount = "89 Anggota",
                location = "Online Community",
                newMessagesCount = "Tidak ada pesan baru",
                avatarBgHex = 0xFFFEF3C7,
                extraMembersText = "+12"
            )
        )
    }

    val discussions = remember {
        listOf(
            PopularDiscussionItem(
                id = "1",
                tag = "HOT TOPIC",
                timeAgo = "2 jam yang lalu",
                title = "Tips Mengatasi Hama Kutu Putih pada Cabai",
                body = "Saya sudah coba pakai pestisida nabati tapi belum hilang total. Ada saran campuran yang lebih efektif?",
                likesCount = "142",
                commentsCount = "48 Komentar",
                tagBgHex = 0xFFFEE2E2,
                tagTextHex = 0xFFDC2626
            ),
            PopularDiscussionItem(
                id = "2",
                tag = "SHARING",
                timeAgo = "5 jam yang lalu",
                title = "Update Harga Pupuk NPK Non Subsidi",
                body = "Di daerah Jawa Tengah harga mulai naik lagi per karung 50kg. Bagaimana di daerah lain?",
                likesCount = "89",
                commentsCount = "124 Komentar",
                tagBgHex = 0xFFDBEAFE,
                tagTextHex = 0xFF2563EB
            ),
            PopularDiscussionItem(
                id = "3",
                tag = "EVENT",
                timeAgo = "1 hari yang lalu",
                title = "Kopdar Akbar Petani Hidroponik Nasional",
                body = "Akan diadakan di Bandung bulan depan. Siapa saja yang rencana hadir?",
                likesCount = "256",
                commentsCount = "312 Komentar",
                tagBgHex = 0xFFF3E8FF,
                tagTextHex = 0xFF9333EA
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
            // Header Section
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
                        // Title + Bell
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

                                Text("Kelompok Tani", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                            }

                            Surface(
                                shape = CircleShape,
                                color = Color.White,
                                border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                                shadowElevation = 1.dp
                            ) {
                                Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                                    Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = "Notifications", tint = Color(0xFF111827), modifier = Modifier.size(20.dp))
                                }
                            }
                        }

                        // Search Bar Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Cari diskusi...", fontSize = 14.sp, color = Color(0xFF9CA3AF)) },
                                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color(0xFF9CA3AF)) },
                                singleLine = true,
                                shape = RoundedCornerShape(16.dp),
                                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF2E7D32), unfocusedBorderColor = Color(0xFFE5E7EB), unfocusedContainerColor = Color.White),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                                modifier = Modifier.weight(1f)
                            )

                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFF2E7D32),
                                shadowElevation = 3.dp,
                                modifier = Modifier.clickable { }
                            ) {
                                Box(modifier = Modifier.padding(12.dp)) {
                                    Icon(imageVector = Icons.Default.Add, contentDescription = "New Discussion", tint = Color.White, modifier = Modifier.size(20.dp))
                                }
                            }
                        }

                        // "Cari Kelompok Baru" Button
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { },
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White.copy(alpha = 0.9f),
                            border = BorderStroke(1.dp, Color(0xFF2E7D32).copy(alpha = 0.3f)),
                            shadowElevation = 1.dp
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(imageVector = Icons.Default.Groups, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Cari Kelompok Baru", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
                            }
                        }
                    }
                }
            }

            // Section: Kelompok Saya
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Kelompok Saya", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        Text("Lihat Semua", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), modifier = Modifier.clickable { })
                    }

                    for (group in myGroups) {
                        GroupCardItem(item = group)
                    }
                }
            }

            // Section: Forum Diskusi Terpopuler
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text("Forum Diskusi Terpopuler", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))

                    for (post in discussions) {
                        DiscussionPostCard(item = post)
                    }
                }
            }
        }
    }
}

@Composable
fun GroupCardItem(item: FarmerGroupItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(item.avatarBgHex)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Groups, contentDescription = null, tint = Color(0xFF15803D), modifier = Modifier.size(30.dp))
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFDCFCE7),
                            border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                        ) {
                            Text(item.role, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF15803D), modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                        }
                    }

                    Text("${item.memberCount} • ${item.location}", fontSize = 12.sp, color = Color(0xFF5E8760))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(if (item.newMessagesCount.contains("Tidak")) Color(0xFF9CA3AF) else Color(0xFF4ADE80))
                        )
                        Text(item.newMessagesCount, fontSize = 12.sp, color = if (item.newMessagesCount.contains("Tidak")) Color(0xFF6B7280) else Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                    }
                }
            }

            Divider(color = Color(0xFFF3F4F6))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatars stack
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(3) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE5E7EB))
                                .border(2.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color(0xFF6B7280), modifier = Modifier.size(16.dp))
                        }
                    }
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF3F4F6))
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(item.extraMembersText, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6B7280))
                    }
                }

                Button(
                    onClick = { },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text(if (item.role == "Admin") "Kelola Grup" else "Masuk Forum", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(14.dp))
                }
            }
        }
    }
}

@Composable
fun DiscussionPostCard(item: PopularDiscussionItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(item.tagBgHex)
                ) {
                    Text(item.tag, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(item.tagTextHex), letterSpacing = 0.5.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp))
                }
                Text("• ${item.timeAgo}", fontSize = 10.sp, color = Color(0xFF9CA3AF))
            }

            Text(item.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
            Text(item.body, fontSize = 12.sp, color = Color(0xFF6B7280), maxLines = 2, overflow = TextOverflow.Ellipsis)

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Default.ThumbUpOffAlt, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(14.dp))
                    Text(item.likesCount, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF9CA3AF))
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(imageVector = Icons.Default.ChatBubbleOutline, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(14.dp))
                    Text(item.commentsCount, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF9CA3AF))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmerCommunityScreenPreview() {
    MyApplicationTheme {
        FarmerCommunityScreen()
    }
}
