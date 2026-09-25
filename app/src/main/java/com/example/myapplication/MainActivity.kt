package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.ActivityLogScreen
import com.example.myapplication.ui.AgriSyncHomeScreen
import com.example.myapplication.ui.AiRecommendationScreen
import com.example.myapplication.ui.AnalyticsDetailScreen
import com.example.myapplication.ui.BreakEvenSimulatorScreen
import com.example.myapplication.ui.DeviceConnectivityScreen
import com.example.myapplication.ui.DigitalTwinScreen
import com.example.myapplication.ui.EditProfileScreen
import com.example.myapplication.ui.EnergyOverviewScreen
import com.example.myapplication.ui.FarmerFeatureScreen
import com.example.myapplication.ui.FeasibilityAnalysisScreen
import com.example.myapplication.ui.IotControlCenterScreen
import com.example.myapplication.ui.LoginScreen
import com.example.myapplication.ui.NotificationPreferencesScreen
import com.example.myapplication.ui.OnboardingScreen
import com.example.myapplication.ui.PlantScanScreen
import com.example.myapplication.ui.ProductionManagementScreen
import com.example.myapplication.ui.RegisterScreen
import com.example.myapplication.ui.SettingsScreen
import com.example.myapplication.ui.SplashScreen
import com.example.myapplication.ui.WelcomeScreen
import com.example.myapplication.ui.theme.AgriTheme
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }

            // Keep system bar icons legible when the in-app theme switches
            LaunchedEffect(isDarkMode) {
                val transparent = Color.Transparent.toArgb()
                val style = if (isDarkMode) SystemBarStyle.dark(transparent)
                else SystemBarStyle.light(transparent, transparent)
                enableEdgeToEdge(statusBarStyle = style, navigationBarStyle = style)
            }

            MyApplicationTheme(darkTheme = isDarkMode, dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AgriTheme.colors.background
                ) {
                    var currentScreen by remember { mutableIntStateOf(10) }

                    when (currentScreen) {
                        10 -> SplashScreen(
                            onSplashFinished = { currentScreen = 11 }
                        )
                        11 -> OnboardingScreen(
                            onOnboardingFinished = { currentScreen = 12 },
                            onSkip = { currentScreen = 12 }
                        )
                        12 -> WelcomeScreen(
                            onGetStarted = { currentScreen = 14 },
                            onLogin = { currentScreen = 13 },
                            onHelpClick = { currentScreen = 15 }
                        )
                        13 -> LoginScreen(
                            onAuthSuccess = { currentScreen = 0 },
                            onNavigateToRegister = { currentScreen = 14 },
                            onBackClick = { currentScreen = 12 }
                        )
                        14 -> RegisterScreen(
                            onAuthSuccess = { currentScreen = 0 },
                            onNavigateToLogin = { currentScreen = 13 },
                            onBackClick = { currentScreen = 12 }
                        )
                        0 -> AgriSyncHomeScreen(
                            onNavigateToAnalytics = { currentScreen = 1 },
                            onNavigateToDigitalTwin = { currentScreen = 2 },
                            onNavigateToRecommendation = { currentScreen = 3 },
                            onNavigateToProductionManagement = { currentScreen = 4 },
                            onNavigateToActivityLog = { currentScreen = 7 },
                            onNavigateToControl = { currentScreen = 8 },
                            onNavigateToPetani = { currentScreen = 15 },
                            onNavigateToSettings = { currentScreen = 16 },
                            onNavigateToScan = { currentScreen = 20 }
                        )
                        1 -> AnalyticsDetailScreen(
                            onBackClick = { currentScreen = 0 },
                            onNavigateToHome = { currentScreen = 0 },
                            onNavigateToProductionManagement = { currentScreen = 4 },
                            onNavigateToControl = { currentScreen = 8 },
                            onNavigateToPetani = { currentScreen = 15 },
                            onScanClick = { currentScreen = 20 }
                        )
                        2 -> DigitalTwinScreen(
                            onBackClick = { currentScreen = 0 },
                            onNavigateHome = { currentScreen = 0 },
                            onNavigateControl = { currentScreen = 8 },
                            onNavigateAnalytics = { currentScreen = 1 },
                            onNavigatePetani = { currentScreen = 15 },
                            onScanClick = { currentScreen = 20 }
                        )
                        3 -> AiRecommendationScreen(
                            onBackClick = { currentScreen = 0 },
                            onNavigateHome = { currentScreen = 0 },
                            onNavigateControl = { currentScreen = 8 },
                            onNavigateAnalytics = { currentScreen = 1 },
                            onNavigatePetani = { currentScreen = 15 },
                            onScanClick = { currentScreen = 20 }
                        )
                        4 -> ProductionManagementScreen(
                            onNavigateToHome = { currentScreen = 0 },
                            onNavigateToAnalytics = { currentScreen = 1 },
                            onNavigateToDigitalTwin = { currentScreen = 2 },
                            onNavigateToRecommendation = { currentScreen = 3 },
                            onNavigateToControl = { currentScreen = 8 },
                            onNavigateToPetani = { currentScreen = 15 },
                            onNavigateToSettings = { currentScreen = 16 },
                            onNavigateToFeasibility = { currentScreen = 5 },
                            onNavigateToBEPSimulator = { currentScreen = 6 },
                            onScanClick = { currentScreen = 20 }
                        )
                        5 -> FeasibilityAnalysisScreen(
                            onBackClick = { currentScreen = 4 },
                            onNavigateToHome = { currentScreen = 0 },
                            onNavigateToControl = { currentScreen = 8 },
                            onNavigateToProductionManagement = { currentScreen = 4 },
                            onNavigateToAnalytics = { currentScreen = 1 },
                            onNavigateToPetani = { currentScreen = 15 },
                            onNavigateToSettings = { currentScreen = 16 },
                            onScanClick = { currentScreen = 20 }
                        )
                        6 -> BreakEvenSimulatorScreen(
                            onBackClick = { currentScreen = 4 },
                            onNavigateToHome = { currentScreen = 0 },
                            onNavigateToControl = { currentScreen = 8 },
                            onNavigateToProductionManagement = { currentScreen = 4 },
                            onNavigateToAnalytics = { currentScreen = 1 },
                            onNavigateToPetani = { currentScreen = 15 },
                            onNavigateToSettings = { currentScreen = 16 },
                            onScanClick = { currentScreen = 20 }
                        )
                        7 -> ActivityLogScreen(
                            onBackClick = { currentScreen = 0 },
                            onNavigateToHome = { currentScreen = 0 },
                            onNavigateToAnalytics = { currentScreen = 1 },
                            onNavigateToDigitalTwin = { currentScreen = 2 },
                            onNavigateToControl = { currentScreen = 8 },
                            onNavigateToPetani = { currentScreen = 15 }
                        )
                        8 -> IotControlCenterScreen(
                            onNavigateHome = { currentScreen = 0 },
                            onNavigateAnalitik = { currentScreen = 1 },
                            onNavigateEnergy = { currentScreen = 9 },
                            onNavigatePetani = { currentScreen = 15 }
                        )
                        9 -> EnergyOverviewScreen(
                            onBackClick = { currentScreen = 8 },
                            onNavigateHome = { currentScreen = 0 },
                            onNavigateAnalitik = { currentScreen = 1 },
                            onNavigatePetani = { currentScreen = 15 }
                        )
                        15 -> FarmerFeatureScreen(
                            onNavigateToHome = { currentScreen = 0 },
                            onNavigateToControl = { currentScreen = 8 },
                            onNavigateToAnalytics = { currentScreen = 1 }
                        )
                        16 -> SettingsScreen(
                            isDarkMode = isDarkMode,
                            onToggleDarkMode = { isDarkMode = it },
                            onNavigateToEditProfile = { currentScreen = 17 },
                            onNavigateToDeviceConnectivity = { currentScreen = 18 },
                            onNavigateToNotificationPreferences = { currentScreen = 19 },
                            onNavigateHome = { currentScreen = 0 },
                            onNavigateControl = { currentScreen = 8 },
                            onNavigateAnalytics = { currentScreen = 1 },
                            onNavigatePetani = { currentScreen = 15 },
                            onSignOut = { currentScreen = 12 }
                        )
                        17 -> EditProfileScreen(
                            onBackClick = { currentScreen = 16 },
                            onNavigateHome = { currentScreen = 0 },
                            onNavigateControl = { currentScreen = 8 },
                            onNavigateAnalytics = { currentScreen = 1 },
                            onNavigatePetani = { currentScreen = 15 }
                        )
                        18 -> DeviceConnectivityScreen(
                            onBackClick = { currentScreen = 16 },
                            onNavigateHome = { currentScreen = 0 },
                            onNavigateControl = { currentScreen = 8 },
                            onNavigateAnalytics = { currentScreen = 1 },
                            onNavigatePetani = { currentScreen = 15 }
                        )
                        19 -> NotificationPreferencesScreen(
                            onBackClick = { currentScreen = 16 },
                            onNavigateHome = { currentScreen = 0 },
                            onNavigateControl = { currentScreen = 8 },
                            onNavigateAnalytics = { currentScreen = 1 },
                            onNavigatePetani = { currentScreen = 15 }
                        )
                        20 -> PlantScanScreen(
                            onBackClick = { currentScreen = 0 },
                            onNavigateToRecommendation = { currentScreen = 3 }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AgriSyncHomeScreenMainPreview() {
    MyApplicationTheme {
        AgriSyncHomeScreen()
    }
}
