package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiTheme

@Composable
fun HeaderBar(
  isWhiteTheme: Boolean,
  onToggleTheme: () -> Unit,
  onMenuClick: () -> Unit,
  onQuickPresetClick: () -> Unit = {}
) {
  val colors = SensiTheme.colors

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(colors.surface)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 10.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Left Target Logo + App Name + Developer Prime Mehedi Moder Credit
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.weight(1f)
      ) {
        Box(
          modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(
              Brush.radialGradient(
                colors = listOf(colors.surfaceHighlight, colors.surface)
              )
            )
            .border(1.5.dp, SensiRed, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text(text = "🎯", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = buildAnnotatedString {
                append("WHITE ")
                withStyle(SpanStyle(color = SensiRed, fontWeight = FontWeight.Black)) {
                  append("FF")
                }
                append(" SENSI")
              },
              color = colors.textPrimary,
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.width(6.dp))

            // VIP Badge
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(SensiRed)
                .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
              Text(
                text = "VIP",
                color = Color.White,
                fontSize = 9.sp,
                fontWeight = FontWeight.Black
              )
            }
          }

          // Developer Credit
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Verified,
              contentDescription = "Verified Developer",
              tint = SensiRed,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
              text = "Dev: Prime Mehedi Moder",
              color = colors.textMuted,
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }

      // Right Action Buttons: Theme Toggle + Presets + Menu
      Row(verticalAlignment = Alignment.CenterVertically) {
        // White / Dark Theme Switcher
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colors.surfaceHighlight)
            .border(1.dp, colors.border, RoundedCornerShape(10.dp)),
          contentAlignment = Alignment.Center
        ) {
          IconButton(
            onClick = onToggleTheme,
            modifier = Modifier
              .size(36.dp)
              .testTag("btn_toggle_theme")
          ) {
            Icon(
              imageVector = if (isWhiteTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
              contentDescription = "Trocar Tema",
              tint = SensiRed,
              modifier = Modifier.size(18.dp)
            )
          }
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Quick Presets
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colors.surfaceHighlight)
            .border(1.dp, colors.border, RoundedCornerShape(10.dp)),
          contentAlignment = Alignment.Center
        ) {
          IconButton(
            onClick = onQuickPresetClick,
            modifier = Modifier
              .size(36.dp)
              .testTag("btn_quick_presets")
          ) {
            Icon(
              imageVector = Icons.Default.Tune,
              contentDescription = "Presets Rápidos",
              tint = colors.textPrimary,
              modifier = Modifier.size(18.dp)
            )
          }
        }

        Spacer(modifier = Modifier.width(6.dp))

        // Menu
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colors.surfaceHighlight)
            .border(1.dp, colors.border, RoundedCornerShape(10.dp)),
          contentAlignment = Alignment.Center
        ) {
          IconButton(
            onClick = onMenuClick,
            modifier = Modifier
              .size(36.dp)
              .testTag("btn_menu")
          ) {
            Icon(
              imageVector = Icons.Default.Menu,
              contentDescription = "Menu",
              tint = colors.textPrimary,
              modifier = Modifier.size(19.dp)
            )
          }
        }
      }
    }

    // Red separator glow line
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.5.dp)
        .background(
          Brush.horizontalGradient(
            colors = listOf(
              Color.Transparent,
              SensiRed,
              SensiRedDark,
              Color.Transparent
            )
          )
        )
    )
  }
}

