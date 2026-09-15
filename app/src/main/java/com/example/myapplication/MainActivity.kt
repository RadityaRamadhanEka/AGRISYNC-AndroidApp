package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.AgriBgColor
import com.example.myapplication.ui.AgriSyncHomeScreen
import com.example.myapplication.ui.AiRecommendationScreen
import com.example.myapplication.ui.AnalyticsDetailScreen
import com.example.myapplication.ui.DigitalTwinScreen
import com.example.myapplication.ui.LoginScreen
import com.example.myapplication.ui.OnboardingScreen
import com.example.myapplication.ui.RegisterScreen
import com.example.myapplication.ui.SplashScreen
import com.example.myapplication.ui.WelcomeScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(darkTheme = false, dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AgriBgColor
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
                            onLogin = { currentScreen = 13 }
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
                            onNavigateToRecommendation = { currentScreen = 3 }
                        )
                        1 -> AnalyticsDetailScreen(
                            onBackClick = { currentScreen = 0 }
                        )
                        2 -> DigitalTwinScreen(
                            onBackClick = { currentScreen = 0 }
                        )
                        3 -> AiRecommendationScreen(
                            onBackClick = { currentScreen = 0 }
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