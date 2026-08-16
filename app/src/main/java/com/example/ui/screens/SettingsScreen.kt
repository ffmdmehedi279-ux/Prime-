package com.example.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AimAssistsState
import com.example.model.SensitivityConfig
import com.example.model.TargetArea
import com.example.ui.components.HitboxVisualizer
import com.example.ui.components.SensiSlider
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiRedGlow
import com.example.ui.theme.SensiTheme

@Composable
fun SettingsScreen(
  config: SensitivityConfig,
  onConfigChange: (SensitivityConfig) -> Unit,
  aimState: AimAssistsState,
  onAimStateChange: (AimAssistsState) -> Unit,
  onOpenBotTab: () -> Unit
) {
  val context = LocalContext.current
  val colors = SensiTheme.colors
  val clipboardManager = LocalClipboardManager.current
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(colors.bg)
      .verticalScroll(scrollState)
      .padding(16.dp)
  ) {
    // Developer Credit Pill
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(colors.surfaceHighlight)
        .border(1.dp, colors.border, RoundedCornerShape(12.dp))
        .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Verified,
            contentDescription = null,
            tint = SensiRed,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "White FF Sensi • Dev: Prime Mehedi Moder",
            color = colors.textPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Text(
          text = "VIP MOD",
          color = SensiRed,
          fontSize = 10.sp,
          fontWeight = FontWeight.Black
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Top Section Badge: Auxílios de Mira
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .background(colors.surfaceElevated)
        .border(1.dp, colors.border, RoundedCornerShape(20.dp))
        .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "🎯", fontSize = 13.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Auxílios de Mira & Calibração",
          color = colors.textPrimary,
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Aim Assist Toggle Cards (As shown in Screenshot 1)
    AimFeatureToggleCard(
      title = "ESTABILIZADOR DE MIRA",
      subtitle = "Deixa sua mira completamente estabilizada para disparos precisos sem tremores.",
      icon = Icons.Default.Adjust,
      checked = aimState.aimStabilizer,
      onCheckedChange = {
        onAimStateChange(aimState.copy(aimStabilizer = it))
        Toast.makeText(context, "Estabilizador: ${if (it) "Ativado ✅" else "Desativado"}", Toast.LENGTH_SHORT).show()
      }
    )

    Spacer(modifier = Modifier.height(10.dp))

    AimFeatureToggleCard(
      title = "2X GRUDENTA",
      subtitle = "Calibra a sensibilidade da mira 2X para melhor fixação e transição suave no alvo.",
      icon = Icons.Default.Bolt,
      checked = aimState.sticky2x,
      onCheckedChange = {
        onAimStateChange(aimState.copy(sticky2x = it))
        Toast.makeText(context, "2X Grudenta: ${if (it) "Ativado ✅" else "Desativado"}", Toast.LENGTH_SHORT).show()
      }
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Mira BOT Card with circular Power Button
    MiraBotFeatureCard(
      active = aimState.miraBotActive,
      onToggle = {
        val newState = !aimState.miraBotActive
        onAimStateChange(aimState.copy(miraBotActive = newState))
        Toast.makeText(context, "Mira BOT: ${if (newState) "Ligado 🤖" else "Desligado"}", Toast.LENGTH_SHORT).show()
      }
    )

    Spacer(modifier = Modifier.height(14.dp))

    // HeadControl Hitbox Visualizer
    HitboxVisualizer(
      selectedArea = aimState.targetArea,
      onSelectArea = { onAimStateChange(aimState.copy(targetArea = it)) }
    )

    Spacer(modifier = Modifier.height(18.dp))

    // Sensitivity Sliders Card
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(colors.surfaceElevated)
        .border(1.dp, colors.border, RoundedCornerShape(18.dp))
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Tune,
            contentDescription = null,
            tint = SensiRed,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Configuração de Sensibilidade",
            color = colors.textPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
        }

        IconButton(
          onClick = {
            val sensiSummary = """
              🎯 WHITE FF SENSI BOT (Dev: Prime Mehedi Moder):
              - Geral: ${config.general}
              - Ponto Vermelho: ${config.redDot}
              - Mira 2X: ${config.scope2x}
              - Mira 4X: ${config.scope4x}
              - Mira AWM: ${config.sniper}
              - Olhadinha: ${config.freeLook}
              - DPI Sugerida: ${config.dpi}
              - Botão de Tiro: ${config.buttonSize}%
            """.trimIndent()
            clipboardManager.setText(AnnotatedString(sensiSummary))
            Toast.makeText(context, "Sensibilidade copiada com sucesso!", Toast.LENGTH_SHORT).show()
          },
          modifier = Modifier.size(34.dp)
        ) {
          Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = "Copiar Sensibilidade",
            tint = SensiRed,
            modifier = Modifier.size(18.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // 6 Scope Sliders
      SensiSlider(
        title = "Geral (General)",
        subtitle = "Movimentação da câmera e puxada de capa base",
        value = config.general,
        onValueChange = { onConfigChange(config.copy(general = it)) },
        iconEmoji = "🎯",
        tag = "slider_general"
      )

      SensiSlider(
        title = "Ponto Vermelho (Red Dot)",
        subtitle = "Mira aberta sem mira óptica / padrão",
        value = config.redDot,
        onValueChange = { onConfigChange(config.copy(redDot = it)) },
        iconEmoji = "🔴",
        tag = "slider_red_dot"
      )

      SensiSlider(
        title = "Mira 2X",
        subtitle = "Scope para média distância (SMG/Rifle)",
        value = config.scope2x,
        onValueChange = { onConfigChange(config.copy(scope2x = it)) },
        iconEmoji = "🔍",
        tag = "slider_2x"
      )

      SensiSlider(
        title = "Mira 4X",
        subtitle = "Scope de longo alcance para fuzis de precisão",
        value = config.scope4x,
        onValueChange = { onConfigChange(config.copy(scope4x = it)) },
        iconEmoji = "🔭",
        tag = "slider_4x"
      )

      SensiSlider(
        title = "Mira Sniper (AWM/Barrett)",
        subtitle = "Controle de mira de precisão pesada",
        value = config.sniper,
        onValueChange = { onConfigChange(config.copy(sniper = it)) },
        iconEmoji = "🎯",
        tag = "slider_sniper"
      )

      SensiSlider(
        title = "Olhadinha (Free Look)",
        subtitle = "Visão panorâmica livre enquanto corre",
        value = config.freeLook,
        onValueChange = { onConfigChange(config.copy(freeLook = it)) },
        iconEmoji = "👀",
        tag = "slider_free_look"
      )

      Spacer(modifier = Modifier.height(8.dp))

      // DPI & Refresh Rate Meter
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(12.dp))
          .background(colors.surfaceHighlight)
          .border(1.dp, colors.border, RoundedCornerShape(12.dp))
          .padding(horizontal = 14.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Speed,
            contentDescription = null,
            tint = SensiRed,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(text = "DPI Sugerida", color = colors.textPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Text(text = "Menor largura nas opções de desenvolvedor", color = colors.textMuted, fontSize = 10.sp)
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SensiRed)
            .padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
          Text(text = "${config.dpi} DPI", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Reset Button
      OutlinedButton(
        onClick = {
          onConfigChange(SensitivityConfig.DEFAULT)
          Toast.makeText(context, "Sensibilidade redefinida para os valores padrão.", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.borderLight)
      ) {
        Icon(
          imageVector = Icons.Default.Refresh,
          contentDescription = null,
          tint = colors.textMuted,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Redefinir Valores Padrão", color = colors.textMuted, fontSize = 13.sp)
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // Floating Sensi BOT Card (As shown in Screenshot 2)
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(colors.surfaceElevated)
        .border(1.dp, colors.border, RoundedCornerShape(16.dp))
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Layers,
            contentDescription = null,
            tint = SensiRed,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Flutuante Sensi BOT",
            color = colors.textPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Switch(
          checked = aimState.floatingBotEnabled,
          onCheckedChange = {
            onAimStateChange(aimState.copy(floatingBotEnabled = it))
            if (it) {
              Toast.makeText(context, "Painel flutuante ativado!", Toast.LENGTH_SHORT).show()
            }
          },
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = SensiRed,
            uncheckedThumbColor = colors.textMuted,
            uncheckedTrackColor = colors.surfaceHighlight
          ),
          modifier = Modifier.testTag("floating_bot_switch")
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Um painel flutuante de calibração rápida para ajustar as suas miras diretamente durante o treinamento.",
        color = colors.textMuted,
        fontSize = 12.sp,
        lineHeight = 16.sp
      )
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Game Quick Launchers
    Text(
      text = "Aplicar Configurações no Jogo",
      color = colors.textPrimary,
      fontSize = 15.sp,
      fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      Button(
        onClick = {
          launchFreeFire(context, "com.dts.freefireth")
        },
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("btn_launch_ff_normal"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = SensiRed,
          contentColor = Color.White
        )
      ) {
        Text(text = "FREE FIRE NORMAL", fontSize = 12.sp, fontWeight = FontWeight.Black)
      }

      Button(
        onClick = {
          launchFreeFire(context, "com.dts.freefiremax")
        },
        modifier = Modifier
          .weight(1f)
          .height(48.dp)
          .testTag("btn_launch_ff_max"),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = colors.surfaceHighlight,
          contentColor = colors.textPrimary
        )
      ) {
        Text(text = "FREE FIRE MAX", fontSize = 12.sp, fontWeight = FontWeight.Bold)
      }
    }

    Spacer(modifier = Modifier.height(30.dp))
  }
}

private fun launchFreeFire(context: Context, packageName: String) {
  val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
  if (launchIntent != null) {
    context.startActivity(launchIntent)
  } else {
    Toast.makeText(
      context,
      "Abra o Free Fire e insira os valores de sensibilidade nas configurações do jogo!",
      Toast.LENGTH_LONG
    ).show()
  }
}

@Composable
private fun AimFeatureToggleCard(
  title: String,
  subtitle: String,
  icon: ImageVector,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  val colors = SensiTheme.colors

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(colors.surfaceElevated)
      .border(1.dp, colors.border, RoundedCornerShape(14.dp))
      .clickable { onCheckedChange(!checked) }
      .padding(14.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      modifier = Modifier.weight(1f),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(colors.surfaceHighlight)
          .border(1.dp, if (checked) SensiRed else colors.border, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = if (checked) SensiRed else colors.textMuted,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column {
        Text(
          text = title,
          color = colors.textPrimary,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = subtitle,
          color = colors.textMuted,
          fontSize = 11.sp,
          lineHeight = 14.sp
        )
      }
    }

    Spacer(modifier = Modifier.width(8.dp))

    Switch(
      checked = checked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = Color.White,
        checkedTrackColor = SensiRed,
        uncheckedThumbColor = colors.textMuted,
        uncheckedTrackColor = colors.surfaceHighlight
      )
    )
  }
}

@Composable
private fun MiraBotFeatureCard(
  active: Boolean,
  onToggle: () -> Unit
) {
  val colors = SensiTheme.colors

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(colors.surfaceElevated)
      .border(1.dp, if (active) SensiRed.copy(alpha = 0.6f) else colors.border, RoundedCornerShape(14.dp))
      .clickable { onToggle() }
      .padding(14.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      modifier = Modifier.weight(1f),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(if (active) SensiRedGlow else colors.surfaceHighlight)
          .border(1.dp, if (active) SensiRed else colors.border, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Text(text = "🤖", fontSize = 18.sp)
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column {
        Text(
          text = "Mira BOT",
          color = colors.textPrimary,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "Otimiza sua mira branca, deixando sua sensibilidade mais leve e fluida para arrastes consistentes.",
          color = colors.textMuted,
          fontSize = 11.sp,
          lineHeight = 14.sp
        )
      }
    }

    Spacer(modifier = Modifier.width(8.dp))

    // Circular Power Button
    Box(
      modifier = Modifier
        .size(42.dp)
        .clip(CircleShape)
        .background(if (active) SensiRedGlow else colors.surfaceHighlight)
        .border(1.5.dp, if (active) SensiRed else colors.border, CircleShape)
        .clickable { onToggle() },
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.PowerSettingsNew,
        contentDescription = "Mira BOT Ativar",
        tint = if (active) SensiRed else colors.textMuted,
        modifier = Modifier.size(22.dp)
      )
    }
  }
}

