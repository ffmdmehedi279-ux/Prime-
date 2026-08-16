package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FitScreen
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AimAssistsState
import com.example.model.TargetArea
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceHighlight
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiRedGlow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import kotlin.math.roundToInt

@Composable
fun FloatingBotOverlay(
  visible: Boolean,
  aimState: AimAssistsState,
  onAimStateChange: (AimAssistsState) -> Unit,
  onDismiss: () -> Unit
) {
  if (!visible) return

  var offsetX by remember { mutableFloatStateOf(0f) }
  var offsetY by remember { mutableFloatStateOf(0f) }
  var isMinimized by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
      .padding(16.dp),
    contentAlignment = Alignment.Center
  ) {
    if (isMinimized) {
      // Floating Bubble when minimized
      Box(
        modifier = Modifier
          .size(56.dp)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              colors = listOf(SensiRed, SensiRedDark)
            )
          )
          .border(2.dp, TextWhite, CircleShape)
          .shadow(12.dp, CircleShape)
          .clickable { isMinimized = false }
          .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
              change.consume()
              offsetX += dragAmount.x
              offsetY += dragAmount.y
            }
          },
        contentAlignment = Alignment.Center
      ) {
        Text(text = "🤖", fontSize = 24.sp)
      }
    } else {
      // Expanded Floating Tactical Panel
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(20.dp))
          .background(DarkSurfaceElevated)
          .border(1.5.dp, SensiRed, RoundedCornerShape(20.dp))
          .shadow(24.dp, RoundedCornerShape(20.dp))
          .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
              change.consume()
              offsetX += dragAmount.x
              offsetY += dragAmount.y
            }
          }
      ) {
        // Red Tactical Header
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.horizontalGradient(
                colors = listOf(SensiRed, SensiRedDark)
              )
            )
            .padding(horizontal = 14.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.GpsFixed,
              contentDescription = "Sensi BOT",
              tint = TextWhite,
              modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Sensi BOT HUD",
              color = TextWhite,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.width(8.dp))

            // User Profile Pill
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Black.copy(alpha = 0.35f))
                .padding(horizontal = 8.dp, vertical = 3.dp),
              contentAlignment = Alignment.Center
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Person,
                  contentDescription = null,
                  tint = TextWhite,
                  modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "PRO***PLAYER",
                  color = TextWhite,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Medium
                )
              }
            }
          }

          // Header Controls: Minimize & Close
          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
              onClick = { isMinimized = true },
              modifier = Modifier.size(28.dp)
            ) {
              Icon(
                imageVector = Icons.Default.FitScreen,
                contentDescription = "Minimizar",
                tint = TextWhite,
                modifier = Modifier.size(16.dp)
              )
            }
            Spacer(modifier = Modifier.width(4.dp))
            IconButton(
              onClick = onDismiss,
              modifier = Modifier.size(28.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Fechar",
                tint = TextWhite,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }

        // Body with Tactical Controls
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
        ) {
          // Target Area Switcher Pills
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(DarkBg)
              .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            TargetArea.values().forEach { area ->
              val isSelected = aimState.targetArea == area
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(8.dp))
                  .background(
                    if (isSelected) SensiRed else Color.Transparent
                  )
                  .clickable {
                    onAimStateChange(aimState.copy(targetArea = area))
                  }
                  .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = when (area) {
                    TargetArea.HEAD -> "CABEÇA"
                    TargetArea.NECK -> "PESCOÇO"
                    TargetArea.BODY -> "CORPO"
                  },
                  color = if (isSelected) TextWhite else TextMuted,
                  fontSize = 11.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Tactical Toggles
          FloatingToggleRow(
            title = "Mira Estável",
            checked = aimState.aimStabilizer,
            onCheckedChange = { onAimStateChange(aimState.copy(aimStabilizer = it)) }
          )
          FloatingToggleRow(
            title = "AimRed (Ponto Vermelho)",
            checked = aimState.aimRed,
            onCheckedChange = { onAimStateChange(aimState.copy(aimRed = it)) }
          )
          FloatingToggleRow(
            title = "Precisão Mira 2X",
            checked = aimState.precision2x,
            onCheckedChange = { onAimStateChange(aimState.copy(precision2x = it)) }
          )
          FloatingToggleRow(
            title = "Precisão Mira Branca",
            checked = aimState.precisionWhiteCrosshair,
            onCheckedChange = { onAimStateChange(aimState.copy(precisionWhiteCrosshair = it)) }
          )

          Spacer(modifier = Modifier.height(8.dp))

          Text(
            text = "HUD de calibração ativa. Ajuste suas configurações no jogo para obter máxima precisão.",
            color = TextMuted,
            fontSize = 11.sp,
            lineHeight = 14.sp
          )
        }
      }
    }
  }
}

@Composable
private fun FloatingToggleRow(
  title: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Box(
        modifier = Modifier
          .size(24.dp)
          .clip(CircleShape)
          .background(if (checked) SensiRedGlow else DarkSurfaceHighlight)
          .border(1.dp, if (checked) SensiRed else DarkBorder, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Settings,
          contentDescription = null,
          tint = if (checked) SensiRed else TextMuted,
          modifier = Modifier.size(14.dp)
        )
      }
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = title,
        color = TextWhite,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium
      )
    }

    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = TextWhite,
        checkedTrackColor = SensiRed,
        uncheckedThumbColor = TextMuted,
        uncheckedTrackColor = DarkSurfaceHighlight
      )
    )
  }
}
