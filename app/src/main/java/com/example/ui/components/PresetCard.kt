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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SensitivityConfig
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiRedGlow
import com.example.ui.theme.SensiTheme

data class SensiPreset(
  val id: String,
  val name: String,
  val tag: String,
  val description: String,
  val iconEmoji: String,
  val config: SensitivityConfig
)

val PRESETS_LIST = listOf(
  SensiPreset(
    id = "fast_rush",
    name = "Rushador Veloz",
    tag = "POPULAR",
    description = "Sensibilidade ultra rápida para subida de capa instantânea em curta distância (MP40/UMP/M1014).",
    iconEmoji = "⚡",
    config = SensitivityConfig.FAST_RUSH
  ),
  SensiPreset(
    id = "precision_1tap",
    name = "Precisão 1-Tiro",
    tag = "1-TAP",
    description = "Mira equilibrada para armas de tiro único (Desert Eagle, SVD, Woodpecker, AC80).",
    iconEmoji = "🎯",
    config = SensitivityConfig.PRECISION_HEADSHOT
  ),
  SensiPreset(
    id = "balanced",
    name = "Equilibrado Geral",
    tag = "PADRÃO",
    description = "Configuração versátil balanceada para todas as distâncias e armas de assalto.",
    iconEmoji = "⚖️",
    config = SensitivityConfig.BALANCED
  ),
  SensiPreset(
    id = "low_end",
    name = "Dispositivo Básico",
    tag = "2GB-4GB",
    description = "Máxima sensibilidade (200) para compensar telas de menor taxa de atualização e atrito.",
    iconEmoji = "📱",
    config = SensitivityConfig.LOW_END_DEVICE
  ),
  SensiPreset(
    id = "high_refresh",
    name = "Alta Taxa 90/120Hz",
    tag = "PRO",
    description = "Calibrado com precisão para telas de alta fluidez sem deixar o tiro passar da cabeça.",
    iconEmoji = "🚀",
    config = SensitivityConfig.HIGH_REFRESH_RATE
  )
)

@Composable
fun PresetCard(
  preset: SensiPreset,
  isSelected: Boolean,
  onApply: (SensitivityConfig) -> Unit,
  onPreview: (SensiPreset) -> Unit
) {
  val colors = SensiTheme.colors

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(colors.surfaceElevated)
      .border(
        width = if (isSelected) 1.5.dp else 1.dp,
        color = if (isSelected) SensiRed else colors.border,
        shape = RoundedCornerShape(16.dp)
      )
      .padding(16.dp)
  ) {
    // Top Row: Emoji + Title + Tag
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(colors.surfaceHighlight)
            .border(1.dp, colors.border, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text(text = preset.iconEmoji, fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = preset.name,
          color = colors.textPrimary,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(if (isSelected) SensiRed else colors.surfaceHighlight)
          .border(1.dp, if (isSelected) SensiRed else colors.border, RoundedCornerShape(8.dp))
          .padding(horizontal = 8.dp, vertical = 3.dp)
      ) {
        Text(
          text = preset.tag,
          color = if (isSelected) Color.White else colors.textMuted,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = preset.description,
      color = colors.textMuted,
      fontSize = 12.sp,
      lineHeight = 16.sp
    )

    Spacer(modifier = Modifier.height(12.dp))

    // Scope Summary Badges
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(10.dp))
        .background(colors.bg)
        .padding(horizontal = 10.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      ScopeMiniPill(label = "Geral", value = preset.config.general)
      ScopeMiniPill(label = "Red Dot", value = preset.config.redDot)
      ScopeMiniPill(label = "2X", value = preset.config.scope2x)
      ScopeMiniPill(label = "4X", value = preset.config.scope4x)
      ScopeMiniPill(label = "Sniper", value = preset.config.sniper)
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Buttons
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Button(
        onClick = { onApply(preset.config) },
        modifier = Modifier
          .weight(1f)
          .height(40.dp)
          .testTag("apply_preset_${preset.id}"),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = SensiRed,
          contentColor = Color.White
        )
      ) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = "Aplicar", fontSize = 13.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
fun ScopeMiniPill(label: String, value: Int) {
  val colors = SensiTheme.colors
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(text = label, color = colors.textMuted, fontSize = 10.sp)
    Text(
      text = value.toString(),
      color = colors.textPrimary,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold
    )
  }
}

