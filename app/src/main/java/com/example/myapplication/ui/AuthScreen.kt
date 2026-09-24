package com.example.myapplication.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class AuthMode {
    LOGIN,
    REGISTER
}

@Composable
fun LoginScreen(
    onAuthSuccess: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    AuthScreen(
        initialMode = AuthMode.LOGIN,
        onAuthSuccess = onAuthSuccess,
        onNavigateToOtherMode = onNavigateToRegister,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun RegisterScreen(
    onAuthSuccess: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    AuthScreen(
        initialMode = AuthMode.REGISTER,
        onAuthSuccess = onAuthSuccess,
        onNavigateToOtherMode = onNavigateToLogin,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun AuthScreen(
    initialMode: AuthMode = AuthMode.LOGIN,
    onAuthSuccess: () -> Unit = {},
    onNavigateToOtherMode: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var currentMode by remember { mutableStateOf(initialMode) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Form inputs state
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Validation & Loading state
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    // Forgot password dialog
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    // Staggered Entrance Animation Trigger
    val isPreview = androidx.compose.ui.platform.LocalInspectionMode.current
    var isVisible by remember { mutableStateOf(isPreview) }
    LaunchedEffect(Unit) {
        if (!isPreview) {
            delay(80)
            isVisible = true
        }
    }

    // Floating background glowing animation
    val infiniteTransition = rememberInfiniteTransition(label = "BackgroundGlow")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    val logoFloatY by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logoFloatY"
    )

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Reset error messages when mode changes
    fun switchMode(newMode: AuthMode) {
        if (onNavigateToOtherMode != null) {
            onNavigateToOtherMode()
        } else {
            currentMode = newMode
            fullNameError = null
            emailError = null
            passwordError = null
        }
    }

    // Submit handler
    fun handleSubmit() {
        focusManager.clearFocus()
        keyboardController?.hide()

        var isValid = true

        if (currentMode == AuthMode.REGISTER && fullName.trim().isEmpty()) {
            fullNameError = "Nama lengkap tidak boleh kosong"
            isValid = false
        } else {
            fullNameError = null
        }

        if (email.trim().isEmpty()) {
            emailError = "Alamat email tidak boleh kosong"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            emailError = "Format email tidak valid"
            isValid = false
        } else {
            emailError = null
        }

        if (password.trim().isEmpty()) {
            passwordError = "Kata sandi tidak boleh kosong"
            isValid = false
        } else if (password.length < 6) {
            passwordError = "Kata sandi minimal 6 karakter"
            isValid = false
        } else {
            passwordError = null
        }

        if (isValid) {
            coroutineScope.launch {
                isLoading = true
                delay(1200) // Simulate network request
                isLoading = false
                val successMessage = if (currentMode == AuthMode.LOGIN) "Login Berhasil!" else "Pendaftaran Berhasil!"
                Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
                onAuthSuccess()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AgriTheme.colors.background)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { focusManager.clearFocus() }
    ) {
        // --- 1. AMBIENT BACKGROUND GLOWS & GRAPHICS ---
        Box(modifier = Modifier.fillMaxSize()) {
            // Background Farm Overlay Image with low opacity
            Image(
                painter = painterResource(id = R.drawable.img_digital_twin),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.04f)
            )

            // Top-Left Mint Green Orb Glow (#4ADE80)
            Box(
                modifier = Modifier
                    .size((320 * pulseScale).dp)
                    .offset(x = (-80).dp, y = (-120).dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4ADE80).copy(alpha = 0.35f))
                    .blur(60.dp)
            )

            // Bottom-Right Deep Green Orb Glow (#2E7D32)
            Box(
                modifier = Modifier
                    .size((340 * pulseScale).dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 90.dp, y = 110.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2E7D32).copy(alpha = 0.20f))
                    .blur(70.dp)
            )
        }

        // --- 2. MAIN SCROLLABLE CONTENT ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Navigation Back Button (Optional)
            if (onBackClick != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Surface(
                        onClick = onBackClick,
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.8f),
                        shadowElevation = 2.dp,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Kembali",
                                tint = Color(0xFF111811),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(12.dp))
            }

            // --- 3. TOP GLOWING LOGO BADGE ---
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(tween(500)) + slideInVertically(
                    initialOffsetY = { -40 },
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                )
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = logoFloatY.dp)
                        .padding(bottom = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Glowing Green Background Halo
                    Box(
                        modifier = Modifier
                            .size(92.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4ADE80).copy(alpha = 0.45f))
                            .blur(16.dp)
                    )

                    // Glassmorphic Logo Card Box
                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = Color.White.copy(alpha = 0.88f),
                        border = androidx.compose.foundation.BorderStroke(
                            2.dp,
                            Color(0xFF4ADE80).copy(alpha = 0.6f)
                        ),
                        shadowElevation = 10.dp,
                        modifier = Modifier.size(80.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Eco,
                                contentDescription = "Logo Agrisync",
                                tint = Color(0xFF15803D),
                                modifier = Modifier.size(38.dp)
                            )
                        }
                    }
                }
            }

            // --- 4. HEADER TEXT WITH MODE SWITCH ANIMATION ---
            AnimatedContent(
                targetState = currentMode,
                transitionSpec = {
                    if (targetState == AuthMode.REGISTER) {
                        (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> -width } + fadeOut()
                        )
                    } else {
                        (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                            slideOutHorizontally { width -> width } + fadeOut()
                        )
                    }
                },
                label = "HeaderTransition"
            ) { mode ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (mode == AuthMode.LOGIN) {
                        Text(
                            text = buildAnnotatedString {
                                append("Selamat datang\nkembali,\n")
                                withStyle(
                                    style = SpanStyle(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(Color(0xFF2E7D32), Color(0xFF4ADE80))
                                        ),
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                ) {
                                    append("Petani Modern!")
                                }
                            },
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111811),
                            textAlign = TextAlign.Center,
                            lineHeight = 36.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Membangun masa depan, satu login setiap saat.",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF6B7280),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    } else {
                        Text(
                            text = buildAnnotatedString {
                                append("Ayo Bergabung,\n")
                                withStyle(
                                    style = SpanStyle(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(Color(0xFF2E7D32), Color(0xFF4ADE80))
                                        ),
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                ) {
                                    append("Petani Masa Depan!")
                                }
                            },
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111811),
                            textAlign = TextAlign.Center,
                            lineHeight = 36.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Mulai perjalanan pertanian cerdas Anda sekarang.",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF6B7280),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- 5. GLASSMORPHIC FORM CARD CONTAINER ---
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(tween(600, delayMillis = 150)) + slideInVertically(
                    initialOffsetY = { 60 },
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                )
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color.White.copy(alpha = 0.85f),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        Color.White.copy(alpha = 0.7f)
                    ),
                    shadowElevation = 12.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(24.dp),
                            spotColor = Color(0xFF1F2687).copy(alpha = 0.12f),
                            ambientColor = Color(0xFF1F2687).copy(alpha = 0.08f)
                        )
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        // Top Specular Highlight Line
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0f),
                                            Color.White.copy(alpha = 0.8f),
                                            Color.White.copy(alpha = 0f)
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 28.dp)
                        ) {
                            AnimatedContent(
                                targetState = currentMode,
                                transitionSpec = {
                                    if (targetState == AuthMode.REGISTER) {
                                        (slideInHorizontally { width -> width } + fadeIn()).togetherWith(
                                            slideOutHorizontally { width -> -width } + fadeOut()
                                        )
                                    } else {
                                        (slideInHorizontally { width -> -width } + fadeIn()).togetherWith(
                                            slideOutHorizontally { width -> width } + fadeOut()
                                        )
                                    }
                                },
                                label = "FormTransition"
                            ) { mode ->
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    // Full Name Input (Register mode only)
                                    if (mode == AuthMode.REGISTER) {
                                        AgriInputField(
                                            label = "NAMA LENGKAP",
                                            value = fullName,
                                            onValueChange = {
                                                fullName = it
                                                if (fullNameError != null) fullNameError = null
                                            },
                                            placeholder = "Nama Lengkap Anda",
                                            leadingIcon = Icons.Outlined.Person,
                                            errorMessage = fullNameError,
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Text,
                                                imeAction = ImeAction.Next
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                            )
                                        )

                                        Spacer(modifier = Modifier.height(18.dp))
                                    }

                                    // Email Input
                                    AgriInputField(
                                        label = "ALAMAT EMAIL",
                                        value = email,
                                        onValueChange = {
                                            email = it
                                            if (emailError != null) emailError = null
                                        },
                                        placeholder = "nama@agrisync.com",
                                        leadingIcon = Icons.Outlined.Email,
                                        errorMessage = emailError,
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Email,
                                            imeAction = ImeAction.Next
                                        ),
                                        keyboardActions = KeyboardActions(
                                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(18.dp))

                                    // Password Input
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "KATA SANDI",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF4B5563),
                                                letterSpacing = 0.6.sp
                                            )

                                            if (mode == AuthMode.LOGIN) {
                                                Text(
                                                    text = "Lupa?",
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    color = Color(0xFF2E7D32),
                                                    modifier = Modifier.clickable {
                                                        showForgotPasswordDialog = true
                                                    }
                                                )
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(6.dp))

                                        AgriInputField(
                                            label = null, // Handled by custom header above
                                            value = password,
                                            onValueChange = {
                                                password = it
                                                if (passwordError != null) passwordError = null
                                            },
                                            placeholder = "••••••••",
                                            leadingIcon = Icons.Outlined.Lock,
                                            trailingIcon = {
                                                IconButton(
                                                    onClick = { isPasswordVisible = !isPasswordVisible },
                                                    modifier = Modifier.size(24.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                                        contentDescription = if (isPasswordVisible) "Sembunyikan sandi" else "Tampilkan sandi",
                                                        tint = if (isPasswordVisible) Color(0xFF2E7D32) else Color(0xFF9CA3AF)
                                                    )
                                                }
                                            },
                                            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                            errorMessage = passwordError,
                                            keyboardOptions = KeyboardOptions(
                                                keyboardType = KeyboardType.Password,
                                                imeAction = ImeAction.Done
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onDone = { handleSubmit() }
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(26.dp))

                                    // Primary Action Button ("Masuk dengan Aman" / "Daftar Sekarang")
                                    AgriGradientButton(
                                        text = if (mode == AuthMode.LOGIN) "Masuk dengan Aman" else "Daftar Sekarang",
                                        isLoading = isLoading,
                                        onClick = { handleSubmit() }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- 6. DIVIDER SECTION ---
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(tween(500, delayMillis = 250))
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color(0xFFD1D5DB).copy(alpha = 0.5f)
                    )

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF6F8F6),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Atau lanjutkan dengan",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF6B7280),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- 7. SOCIAL LOGIN BUTTONS ---
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(tween(500, delayMillis = 350)) + slideInVertically(
                    initialOffsetY = { 30 },
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Google Button
                    SocialLoginButton(
                        text = "Google",
                        iconRes = R.drawable.ic_google_logo,
                        onClick = {
                            Toast.makeText(context, "Lanjutkan dengan Google", Toast.LENGTH_SHORT).show()
                            coroutineScope.launch {
                                delay(600)
                                onAuthSuccess()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )

                    // Apple Button
                    SocialLoginButton(
                        text = "Apple",
                        iconRes = R.drawable.ic_apple_logo,
                        onClick = {
                            Toast.makeText(context, "Lanjutkan dengan Apple", Toast.LENGTH_SHORT).show()
                            coroutineScope.launch {
                                delay(600)
                                onAuthSuccess()
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 8. BOTTOM MODE SWITCH LINK ---
            AnimatedVisibility(
                visible = isVisible,
                enter = fadeIn(tween(500, delayMillis = 450))
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            switchMode(if (currentMode == AuthMode.LOGIN) AuthMode.REGISTER else AuthMode.LOGIN)
                        }
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (currentMode == AuthMode.LOGIN) "Baru di Agrisync? " else "Sudah punya akun? ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF4B5563)
                    )
                    Text(
                        text = if (currentMode == AuthMode.LOGIN) "Daftar Sekarang" else "Masuk",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32),
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }

        // --- 9. FORGOT PASSWORD DIALOG ---
        if (showForgotPasswordDialog) {
            ForgotPasswordDialog(
                onDismiss = { showForgotPasswordDialog = false },
                onSubmit = { resetEmail ->
                    Toast.makeText(
                        context,
                        "Link reset kata sandi dikirim ke $resetEmail",
                        Toast.LENGTH_LONG
                    ).show()
                    showForgotPasswordDialog = false
                }
            )
        }
    }
}

// ==========================================
// CUSTOM STYLED REUSABLE COMPONENTS
// ==========================================

@Composable
fun AgriInputField(
    label: String?,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    trailingIcon: (@Composable () -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var isFocused by remember { mutableStateOf(false) }

    val activeBorderColor = when {
        errorMessage != null -> Color(0xFFEF4444)
        isFocused -> Color(0xFF2E7D32)
        else -> Color(0xFFE5E7EB)
    }

    val activeIconTint = when {
        errorMessage != null -> Color(0xFFEF4444)
        isFocused -> Color(0xFF2E7D32)
        else -> Color(0xFF9CA3AF)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        label?.let {
            Text(
                text = it,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4B5563),
                letterSpacing = 0.6.sp,
                modifier = Modifier.padding(start = 2.dp, bottom = 6.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 15.sp,
                    color = Color(0xFF9CA3AF)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = activeIconTint,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = trailingIcon,
            visualTransformation = visualTransformation,
            singleLine = true,
            isError = errorMessage != null,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = AgriTheme.colors.surface,
                unfocusedContainerColor = AgriTheme.colors.surface,
                disabledContainerColor = AgriTheme.colors.surface,
                errorContainerColor = AgriTheme.colors.redAlertBg,
                focusedBorderColor = activeBorderColor,
                unfocusedBorderColor = activeBorderColor,
                errorBorderColor = Color(0xFFEF4444),
                focusedTextColor = Color(0xFF111811),
                unfocusedTextColor = Color(0xFF111811)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
        )

        AnimatedVisibility(
            visible = errorMessage != null,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            errorMessage?.let { msg ->
                Text(
                    text = msg,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFEF4444),
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun AgriGradientButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "ButtonScale"
    )

    Surface(
        onClick = onClick,
        enabled = !isLoading,
        shape = RoundedCornerShape(12.dp),
        interactionSource = interactionSource,
        color = Color.Transparent,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .scale(scale)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color(0xFF2E7D32).copy(alpha = 0.4f),
                ambientColor = Color(0xFF2E7D32).copy(alpha = 0.2f)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2E7D32), Color(0xFF47A858))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 2.5.dp,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "SocialBtnScale"
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        color = AgriTheme.colors.surface,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            AgriTheme.colors.grayBorder
        ),
        shadowElevation = 2.dp,
        interactionSource = interactionSource,
        modifier = modifier
            .height(50.dp)
            .scale(scale)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF374151)
            )
        }
    }
}

@Composable
fun ForgotPasswordDialog(
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var emailInput by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = AgriTheme.colors.surface,
            shadowElevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2E7D32).copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Lupa Kata Sandi?",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF111811)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Masukkan alamat email Anda untuk menerima instruksi pemulihan kata sandi.",
                    fontSize = 13.sp,
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                AgriInputField(
                    label = "EMAIL PEMULIHAN",
                    value = emailInput,
                    onValueChange = {
                        emailInput = it
                        if (inputError != null) inputError = null
                    },
                    placeholder = "nama@agrisync.com",
                    leadingIcon = Icons.Outlined.Email,
                    errorMessage = inputError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Batal",
                            color = Color(0xFF6B7280),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    AgriGradientButton(
                        text = "Kirim",
                        isLoading = false,
                        onClick = {
                            if (emailInput.trim().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailInput.trim()).matches()) {
                                inputError = "Masukkan email yang valid"
                            } else {
                                onSubmit(emailInput.trim())
                            }
                        },
                        modifier = Modifier.weight(1.2f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MyApplicationTheme {
        LoginScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    MyApplicationTheme {
        RegisterScreen()
    }
}
