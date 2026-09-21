package com.example.myapplication.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AgriCustomColors(
    val background: Color,
    val surface: Color,
    val primary: Color,
    val primaryDark: Color,
    val accent: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val border: Color,
    val inputBorder: Color,
    val iconBgLight: Color,
    // Extended semantic tokens
    val greenAction: Color,
    val greenEmphasis: Color,
    val greenDeep: Color,
    val mintBg: Color,
    val greenTintBg: Color,
    val redAlertBg: Color,
    val yellowWarnBg: Color,
    val blueInfoBg: Color,
    val purpleInfoBg: Color,
    val blueAccent: Color,
    val orangeAccent: Color,
    val grayIcon: Color,
    val grayBgAlt: Color,
    val grayBorder: Color
)

private val LightCustomColors = AgriCustomColors(
    background = AgriLightBg,
    surface = AgriLightSurface,
    primary = AgriLightPrimary,
    primaryDark = AgriLightPrimaryDark,
    accent = AgriLightAccent,
    textPrimary = AgriLightTextPrimary,
    textSecondary = AgriLightTextSecondary,
    textMuted = AgriLightTextMuted,
    border = AgriLightBorder,
    inputBorder = AgriLightInputBorder,
    iconBgLight = Color(0xFFF9FAFB),
    greenAction = AgriGreenAction,
    greenEmphasis = AgriGreenEmphasis,
    greenDeep = AgriGreenDeep,
    mintBg = AgriMintBg,
    greenTintBg = AgriGreenTintBg,
    redAlertBg = AgriRedAlertBg,
    yellowWarnBg = AgriYellowWarnBg,
    blueInfoBg = AgriBlueInfoBg,
    purpleInfoBg = AgriPurpleInfoBg,
    blueAccent = AgriBlueAccent,
    orangeAccent = AgriOrangeAccent,
    grayIcon = AgriGrayIcon,
    grayBgAlt = AgriGrayBgAlt,
    grayBorder = AgriGrayBorder
)

private val DarkCustomColors = AgriCustomColors(
    background = AgriDarkBg,
    surface = AgriDarkSurface,
    primary = AgriDarkPrimary,
    primaryDark = AgriDarkPrimaryDark,
    accent = AgriDarkAccent,
    textPrimary = AgriDarkTextPrimary,
    textSecondary = AgriDarkTextSecondary,
    textMuted = AgriDarkTextMuted,
    border = AgriDarkBorder,
    inputBorder = AgriDarkInputBorder,
    iconBgLight = Color(0xFF233226),
    greenAction = AgriGreenActionDark,
    greenEmphasis = AgriGreenEmphasisDark,
    greenDeep = AgriGreenDeepDark,
    mintBg = AgriGreenMintBgDark,
    greenTintBg = AgriGreenTintBgDark,
    redAlertBg = AgriRedAlertBgDark,
    yellowWarnBg = AgriYellowWarnBgDark,
    blueInfoBg = AgriBlueInfoBgDark,
    purpleInfoBg = AgriPurpleInfoBgDark,
    blueAccent = AgriBlueAccentDark,
    orangeAccent = AgriOrangeAccentDark,
    grayIcon = AgriGrayIconDark,
    grayBgAlt = AgriGrayBgAltDark,
    grayBorder = AgriGrayBorderDark
)

private val LocalAgriColors = staticCompositionLocalOf { LightCustomColors }

object AgriTheme {
    val colors: AgriCustomColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAgriColors.current
}

private val LightColorScheme = lightColorScheme(
    primary = AgriLightPrimary,
    secondary = AgriLightAccent,
    background = AgriLightBg,
    surface = AgriLightSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = AgriLightTextPrimary,
    onSurface = AgriLightTextPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = AgriDarkPrimary,
    secondary = AgriDarkAccent,
    background = AgriDarkBg,
    surface = AgriDarkSurface,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = AgriDarkTextPrimary,
    onSurface = AgriDarkTextPrimary
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val targetColors = if (darkTheme) DarkCustomColors else LightCustomColors

    // Smooth animated color transitions when toggling dark mode <-> light mode
    val animDuration = 400
    val animatedBackground = animateColorAsState(targetColors.background, tween(animDuration), label = "bg")
    val animatedSurface = animateColorAsState(targetColors.surface, tween(animDuration), label = "surface")
    val animatedPrimary = animateColorAsState(targetColors.primary, tween(animDuration), label = "primary")
    val animatedPrimaryDark = animateColorAsState(targetColors.primaryDark, tween(animDuration), label = "primaryDark")
    val animatedAccent = animateColorAsState(targetColors.accent, tween(animDuration), label = "accent")
    val animatedTextPrimary = animateColorAsState(targetColors.textPrimary, tween(animDuration), label = "textPrimary")
    val animatedTextSecondary = animateColorAsState(targetColors.textSecondary, tween(animDuration), label = "textSecondary")
    val animatedTextMuted = animateColorAsState(targetColors.textMuted, tween(animDuration), label = "textMuted")
    val animatedBorder = animateColorAsState(targetColors.border, tween(animDuration), label = "border")
    val animatedInputBorder = animateColorAsState(targetColors.inputBorder, tween(animDuration), label = "inputBorder")
    val animatedIconBgLight = animateColorAsState(targetColors.iconBgLight, tween(animDuration), label = "iconBgLight")
    val animatedGreenAction = animateColorAsState(targetColors.greenAction, tween(animDuration), label = "greenAction")
    val animatedGreenEmphasis = animateColorAsState(targetColors.greenEmphasis, tween(animDuration), label = "greenEmphasis")
    val animatedGreenDeep = animateColorAsState(targetColors.greenDeep, tween(animDuration), label = "greenDeep")
    val animatedMintBg = animateColorAsState(targetColors.mintBg, tween(animDuration), label = "mintBg")
    val animatedGreenTintBg = animateColorAsState(targetColors.greenTintBg, tween(animDuration), label = "greenTintBg")
    val animatedRedAlertBg = animateColorAsState(targetColors.redAlertBg, tween(animDuration), label = "redAlertBg")
    val animatedYellowWarnBg = animateColorAsState(targetColors.yellowWarnBg, tween(animDuration), label = "yellowWarnBg")
    val animatedBlueInfoBg = animateColorAsState(targetColors.blueInfoBg, tween(animDuration), label = "blueInfoBg")
    val animatedPurpleInfoBg = animateColorAsState(targetColors.purpleInfoBg, tween(animDuration), label = "purpleInfoBg")
    val animatedBlueAccent = animateColorAsState(targetColors.blueAccent, tween(animDuration), label = "blueAccent")
    val animatedOrangeAccent = animateColorAsState(targetColors.orangeAccent, tween(animDuration), label = "orangeAccent")
    val animatedGrayIcon = animateColorAsState(targetColors.grayIcon, tween(animDuration), label = "grayIcon")
    val animatedGrayBgAlt = animateColorAsState(targetColors.grayBgAlt, tween(animDuration), label = "grayBgAlt")
    val animatedGrayBorder = animateColorAsState(targetColors.grayBorder, tween(animDuration), label = "grayBorder")

    val animatedCustomColors = AgriCustomColors(
        background = animatedBackground.value,
        surface = animatedSurface.value,
        primary = animatedPrimary.value,
        primaryDark = animatedPrimaryDark.value,
        accent = animatedAccent.value,
        textPrimary = animatedTextPrimary.value,
        textSecondary = animatedTextSecondary.value,
        textMuted = animatedTextMuted.value,
        border = animatedBorder.value,
        inputBorder = animatedInputBorder.value,
        iconBgLight = animatedIconBgLight.value,
        greenAction = animatedGreenAction.value,
        greenEmphasis = animatedGreenEmphasis.value,
        greenDeep = animatedGreenDeep.value,
        mintBg = animatedMintBg.value,
        greenTintBg = animatedGreenTintBg.value,
        redAlertBg = animatedRedAlertBg.value,
        yellowWarnBg = animatedYellowWarnBg.value,
        blueInfoBg = animatedBlueInfoBg.value,
        purpleInfoBg = animatedPurpleInfoBg.value,
        blueAccent = animatedBlueAccent.value,
        orangeAccent = animatedOrangeAccent.value,
        grayIcon = animatedGrayIcon.value,
        grayBgAlt = animatedGrayBgAlt.value,
        grayBorder = animatedGrayBorder.value
    )

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalAgriColors provides animatedCustomColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
