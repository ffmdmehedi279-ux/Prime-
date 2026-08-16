package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiTheme

enum class NavTab(val title: String, val icon: ImageVector, val tag: String) {
  SETTINGS("Ajustes", Icons.Default.Settings, "tab_settings"),
  OPTIMIZER("Otimização PRO", Icons.Default.Bolt, "tab_optimizer"),
  SENSI_BOT("SensiBot", Icons.Default.SmartToy, "tab_sensi_bot"),
  SUPPORT("Suporte", Icons.Default.Chat, "tab_support")
}

@Composable
fun BottomNavBar(
  selectedTab: NavTab,
  onTabSelected: (NavTab) -> Unit
) {
  val colors = SensiTheme.colors

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(colors.surface)
  ) {
    // Top border line
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)
        .background(colors.border)
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 6.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      NavTab.values().forEach { tab ->
        val isSelected = selectedTab == tab

        val interactionSource = remember { MutableInteractionSource() }

        Box(
          modifier = Modifier
            .weight(1f)
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
              interactionSource = interactionSource,
              indication = ripple(bounded = true, color = SensiRed),
              onClick = { onTabSelected(tab) }
            )
            .testTag(tab.tag),
          contentAlignment = Alignment.Center
        ) {
          if (isSelected) {
            // Active Capsule Pill Design (as shown in reference screenshot)
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(12.dp))
                  .background(
                    Brush.verticalGradient(
                      colors = listOf(SensiRed, SensiRedDark)
                    )
                  )
                  .padding(horizontal = 14.dp, vertical = 5.dp),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = tab.icon,
                  contentDescription = tab.title,
                  tint = Color.White,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = tab.title,
                color = SensiRed,
                fontSize = 10.sp,
                fontWeight = FontWeight.ExtraBold
              )
            }
          } else {
            // Inactive Tab
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = tab.icon,
                contentDescription = tab.title,
                tint = colors.textMuted,
                modifier = Modifier.size(22.dp)
              )
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                text = tab.title,
                color = colors.textMuted,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium
              )
            }
          }
        }
      }
    }
  }
}

