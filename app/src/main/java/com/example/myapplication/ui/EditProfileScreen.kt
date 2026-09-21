package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
    onNavigateControl: () -> Unit = {},
    onNavigateAnalytics: () -> Unit = {},
    onNavigatePetani: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("Alex") }
    var email by remember { mutableStateOf("alex@agrisync.com") }
    var farmRole by remember { mutableStateOf("Farm Manager") }

    var isSaving by remember { mutableStateOf(false) }
    var showPhotoBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AgriTheme.colors.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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

                    Text(
                        text = "Edit Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = AgriTheme.colors.textPrimary,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 40.dp), // Balance the back button
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // Avatar Section
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .size(136.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer Gradient Ring
                    Box(
                        modifier = Modifier
                            .size(128.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF2F7F33),
                                        Color(0xFF4ADE80)
                                    )
                                ),
                                shape = CircleShape
                            )
                            .padding(3.dp)
                            .clip(CircleShape)
                            .background(AgriTheme.colors.surface)
                            .clickable { showPhotoBottomSheet = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE5E7EB)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Avatar",
                                modifier = Modifier.size(72.dp),
                                tint = Color(0xFF2F7F33)
                            )
                        }
                    }

                    // Edit Pencil Badge Button at Bottom Right
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .align(Alignment.BottomEnd)
                            .clip(CircleShape)
                            .background(Color(0xFF2F7F33))
                            .border(3.dp, Color.White, CircleShape)
                            .clickable { showPhotoBottomSheet = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Photo",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Form Fields
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Full Name Input
                    ProfileInputField(
                        label = "Full Name",
                        value = fullName,
                        onValueChange = { fullName = it },
                        icon = Icons.Outlined.Person,
                        keyboardType = KeyboardType.Text
                    )

                    // Email Input
                    ProfileInputField(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        icon = Icons.Outlined.Email,
                        keyboardType = KeyboardType.Email
                    )

                    // Farm Role Input
                    ProfileInputField(
                        label = "Farm Role",
                        value = farmRole,
                        onValueChange = { farmRole = it },
                        icon = Icons.Outlined.Badge,
                        keyboardType = KeyboardType.Text
                    )
                }
            }

            // Save Changes Button
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (!isSaving) {
                            isSaving = true
                            scope.launch {
                                delay(1200) // Simulate saving
                                isSaving = false
                                snackbarHostState.showSnackbar("Profil berhasil diperbarui!")
                                delay(500)
                                onBackClick()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2E7D32),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    if (isSaving) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Menyimpan...",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "Save Changes",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    // Photo Options Bottom Sheet
    if (showPhotoBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPhotoBottomSheet = false },
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
                    text = "Ubah Foto Profil",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = AgriTheme.colors.textPrimary
                )

                PhotoOptionItem(
                    icon = Icons.Outlined.PhotoCamera,
                    title = "Ambil Foto dengan Kamera",
                    onClick = {
                        showPhotoBottomSheet = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Fitur kamera aktif")
                        }
                    }
                )

                PhotoOptionItem(
                    icon = Icons.Outlined.Image,
                    title = "Pilih dari Galeri",
                    onClick = {
                        showPhotoBottomSheet = false
                        scope.launch {
                            snackbarHostState.showSnackbar("Galeri dibuka")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun ProfileInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    keyboardType: KeyboardType
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = AgriTheme.colors.textPrimary,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = AgriTheme.colors.surface,
                unfocusedContainerColor = AgriTheme.colors.surface,
                focusedBorderColor = Color(0xFF2F7F33),
                unfocusedBorderColor = AgriTheme.colors.inputBorder,
                focusedTextColor = AgriTheme.colors.textPrimary,
                unfocusedTextColor = AgriTheme.colors.textPrimary
            ),
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = label,
                    tint = AgriTheme.colors.textMuted,
                    modifier = Modifier.size(20.dp)
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            )
        )
    }
}

@Composable
private fun PhotoOptionItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(AgriTheme.colors.iconBgLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF2F7F33),
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = AgriTheme.colors.textPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditProfileScreenPreview() {
    MyApplicationTheme {
        EditProfileScreen()
    }
}
