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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

data class ChatMessage(
    val id: String,
    val text: String,
    val time: String,
    val isOutgoing: Boolean,
    val hasAttachment: Boolean = false,
    val isRecommendation: Boolean = false
)

@Composable
fun FarmerConsultationChatScreen(
    onBackClick: () -> Unit = {},
    onMoreClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }

    val chatMessages = remember {
        mutableStateListOf(
            ChatMessage("1", "Halo Pak Budi, bagaimana perkembangan tanaman melon di Greenhouse A? Apakah sudah ada tanda-tanda pembungaan?", "08:15", isOutgoing = false),
            ChatMessage("2", "Sudah Pak, beberapa sudah mulai muncul bunga. Tapi saya khawatir dengan warna daunnya yang agak kekuningan di bagian pinggir.", "08:17", isOutgoing = true),
            ChatMessage("3", "Ini hasil scan tadi pagi Pak.", "08:18", isOutgoing = true, hasAttachment = true),
            ChatMessage("4", "Berdasarkan data sensor, tingkat Nitrogen (N) sedikit rendah. Saya sarankan tingkatkan dosis nutrisi AB Mix khusus fase generatif mulai besok.", "08:22", isOutgoing = false),
            ChatMessage("5", "Atur pH air di kisaran 6.0 - 6.5 untuk penyerapan nutrisi yang optimal.", "08:23", isOutgoing = false, isRecommendation = true)
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background,
        topBar = {
            // Header: Advisor Profile Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White.copy(alpha = 0.95f),
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        IconButton(onClick = onBackClick) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF111827))
                        }

                        Box(modifier = Modifier.size(42.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF2E7D32)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
                            }
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF4ADE80))
                                    .border(2.dp, Color.White, CircleShape)
                                    .align(Alignment.BottomEnd)
                            )
                        }

                        Column {
                            Text("Bpk. Slamet H.", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = AgriTheme.colors.textPrimary)
                            Text("ONLINE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4ADE80), letterSpacing = 0.5.sp)
                        }
                    }

                    IconButton(onClick = onMoreClick) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More", tint = Color(0xFF6B7280))
                    }
                }
            }
        },
        bottomBar = {
            // Chat Input Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF3F4F6),
                        modifier = Modifier.clickable { }
                    ) {
                        Box(modifier = Modifier.padding(10.dp)) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Attach", tint = Color(0xFF4B5563), modifier = Modifier.size(20.dp))
                        }
                    }

                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = { Text("Tulis pesan...", fontSize = 14.sp, color = Color(0xFF9CA3AF)) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF2E7D32),
                            unfocusedBorderColor = Color(0xFFE5E7EB),
                            unfocusedContainerColor = AgriTheme.colors.background,
                            focusedContainerColor = AgriTheme.colors.surface
                        ),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (inputText.isNotEmpty()) {
                                    chatMessages.add(ChatMessage("${chatMessages.size + 1}", inputText, "08:25", isOutgoing = true))
                                    inputText = ""
                                }
                            }
                        )
                    )

                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF2E7D32),
                        shadowElevation = 4.dp,
                        modifier = Modifier.clickable {
                            if (inputText.isNotEmpty()) {
                                chatMessages.add(ChatMessage("${chatMessages.size + 1}", inputText, "08:25", isOutgoing = true))
                                inputText = ""
                            }
                        }
                    ) {
                        Box(modifier = Modifier.padding(10.dp)) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Date Divider Pill ("HARI INI")
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF3F4F6)
                    ) {
                        Text(
                            text = "HARI INI",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF9CA3AF),
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Message Bubbles
            items(chatMessages) { msg ->
                ChatBubbleRow(msg = msg)
            }
        }
    }
}

@Composable
fun ChatBubbleRow(msg: ChatMessage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (msg.isOutgoing) Alignment.End else Alignment.Start
    ) {
        if (msg.isRecommendation) {
            // Special REKOMENDASI card bubble
            Card(
                modifier = Modifier.fillMaxWidth(0.85f),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomEnd = 24.dp, bottomStart = 4.dp),
                colors = CardDefaults.cardColors(containerColor = AgriTheme.colors.surface),
                border = BorderStroke(1.dp, Color(0xFF4ADE80)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
                        Text("REKOMENDASI", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32), letterSpacing = 0.5.sp)
                    }
                    Text(msg.text, fontSize = 13.sp, color = Color(0xFF374151), fontWeight = FontWeight.Medium)
                }
            }
        } else if (msg.isOutgoing) {
            // Outgoing green bubble
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp, bottomStart = 24.dp, bottomEnd = 4.dp),
                    color = Color(0xFF2E7D32),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth(0.82f)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (msg.hasAttachment) {
                            // Attachment card
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color.White.copy(alpha = 0.2f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(imageVector = Icons.Default.Sensors, contentDescription = null, tint = Color.White)
                                    }
                                    Column {
                                        Text("Plant Health Report", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                        Text("Analisis: Klorosis Ringan", fontSize = 10.sp, color = Color.White.copy(alpha = 0.8f))
                                        Text("Sensor Data Attached", fontSize = 9.sp, color = Color.White.copy(alpha = 0.9f), fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                        Text(msg.text, fontSize = 13.sp, color = Color.White)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(msg.time, fontSize = 10.sp, color = Color(0xFF9CA3AF))
                    Icon(imageVector = Icons.Default.DoneAll, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(14.dp))
                }
            }
        } else {
            // Incoming white bubble
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(topStart = 4.dp, topEnd = 24.dp, bottomStart = 24.dp, bottomEnd = 24.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                    shadowElevation = 1.dp,
                    modifier = Modifier.fillMaxWidth(0.85f)
                ) {
                    Text(
                        text = msg.text,
                        fontSize = 13.sp,
                        color = Color(0xFF374151),
                        modifier = Modifier.padding(14.dp)
                    )
                }

                Text(msg.time, fontSize = 10.sp, color = Color(0xFF9CA3AF), modifier = Modifier.padding(start = 4.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmerConsultationChatScreenPreview() {
    MyApplicationTheme {
        FarmerConsultationChatScreen()
    }
}
