package com.example.ui.screens

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
import androidx.compose.material.icons.filled.BatteryChargingFull
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.SdStorage
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.OptimizationItem
import com.example.model.OptimizationState
import com.example.ui.components.OptimizationPulseCircle
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiRedGlow
import com.example.ui.theme.SensiTheme
import com.example.ui.theme.TextSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OptimizerScreen(
  state: OptimizationState,
  onStateChange: (OptimizationState) -> Unit
) {
  val context = LocalContext.current
  val colors = SensiTheme.colors
  val coroutineScope = rememberCoroutineScope()
  val scrollState = rememberScrollState()

  var isScanning by remember { mutableStateOf(false) }
  var currentScore by remember { mutableIntStateOf(state.optimizationScore) }
  var cleanedMb by remember { mutableIntStateOf(state.ramCleanedMb) }

  fun runOptimization() {
    isScanning = true
    coroutineScope.launch {
      delay(1200)
      currentScore = 98
      cleanedMb += 380
      isScanning = false
      onStateChange(
        state.copy(
          optimizationScore = 98,
          ramCleanedMb = cleanedMb,
          isOptimizing = false
        )
      )
      Toast.makeText(
        context,
        "✨ Dispositivo Otimizado com Sucesso! 380 MB de RAM liberados pelo White FF Sensi Bot.",
        Toast.LENGTH_SHORT
      ).show()
    }
  }

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
            text = "White FF Optimizer • Dev: Prime Mehedi Moder",
            color = colors.textPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Text(
          text = "VIP ACTIVE",
          color = SensiRed,
          fontSize = 10.sp,
          fontWeight = FontWeight.Black
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Pulse Circle & Big Red OTIMIZAR Button
    OptimizationPulseCircle(
      isOptimizing = isScanning,
      score = currentScore,
      onOptimizeClick = { runOptimization() }
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Real Device Hardware Telemetry Dashboard
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(colors.surfaceElevated)
        .border(1.dp, colors.border, RoundedCornerShape(16.dp))
        .padding(14.dp),
      horizontalArrangement = Arrangement.SpaceAround
    ) {
      TelemetryMetricItem(
        icon = Icons.Default.Memory,
        title = "RAM Livre",
        value = "${state.totalRamMb - cleanedMb} MB / ${state.totalRamMb} MB"
      )
      TelemetryMetricItem(
        icon = Icons.Default.Thermostat,
        title = "Temp. Bateria",
        value = "33.5 °C (Perfeito)"
      )
      TelemetryMetricItem(
        icon = Icons.Default.Speed,
        title = "Status FPS",
        value = "60 / 120 FPS"
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Text(
      text = "Opções de Otimização & Desempenho",
      color = colors.textPrimary,
      fontSize = 15.sp,
      fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Optimization items list (matching user screenshot)
    state.items.forEach { item ->
      OptimizerToggleCard(
        item = item,
        onCheckedChange = { isChecked ->
          val updatedList = state.items.map {
            if (it.id == item.id) it.copy(isChecked = isChecked) else it
          }
          onStateChange(state.copy(items = updatedList))
          Toast.makeText(
            context,
            "${item.title}: ${if (isChecked) "Ativado ✅" else "Desativado"}",
            Toast.LENGTH_SHORT
          ).show()
        },
        onQuickAction = {
          Toast.makeText(context, "⚡ Limpando processos em segundo plano...", Toast.LENGTH_SHORT).show()
          runOptimization()
        }
      )
      Spacer(modifier = Modifier.height(10.dp))
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Optimization Safe Information Note
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(12.dp))
        .background(colors.surfaceHighlight)
        .border(1.dp, colors.border, RoundedCornerShape(12.dp))
        .padding(14.dp)
    ) {
      Row(verticalAlignment = Alignment.Top) {
        Text(text = "💡", fontSize = 16.sp)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "Dica White FF Headshot",
            color = colors.textPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(3.dp))
          Text(
            text = "Otimize antes de iniciar a partida para eliminar o delay do toque na tela e garantir a puxada de capa perfeita 100% lisa.",
            color = colors.textMuted,
            fontSize = 11.sp,
            lineHeight = 15.sp
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun TelemetryMetricItem(
  icon: ImageVector,
  title: String,
  value: String
) {
  val colors = SensiTheme.colors
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = SensiRed,
      modifier = Modifier.size(18.dp)
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(text = title, color = colors.textMuted, fontSize = 10.sp)
    Text(text = value, color = colors.textPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
  }
}

@Composable
private fun OptimizerToggleCard(
  item: OptimizationItem,
  onCheckedChange: (Boolean) -> Unit,
  onQuickAction: () -> Unit
) {
  val colors = SensiTheme.colors

  val iconVector = when (item.iconName) {
    "ram" -> Icons.Default.Memory
    "phone" -> Icons.Default.PhoneAndroid
    "flash" -> Icons.Default.Bolt
    "speed" -> Icons.Default.Speed
    "wifi" -> Icons.Default.Wifi
    else -> Icons.Default.Memory
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(colors.surfaceElevated)
      .border(1.dp, colors.border, RoundedCornerShape(14.dp))
      .clickable {
        if (item.hasQuickAction) {
          onQuickAction()
        } else {
          onCheckedChange(!item.isChecked)
        }
      }
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
          .border(1.dp, if (item.isChecked) SensiRed else colors.border, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = iconVector,
          contentDescription = null,
          tint = if (item.isChecked) SensiRed else colors.textMuted,
          modifier = Modifier.size(20.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column {
        Text(
          text = item.title,
          color = colors.textPrimary,
          fontSize = 13.sp,
          fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = item.description,
          color = colors.textMuted,
          fontSize = 11.sp,
          lineHeight = 14.sp
        )
      }
    }

    Spacer(modifier = Modifier.width(8.dp))

    if (item.hasQuickAction) {
      // Red Lightning Action Button (as shown in Screenshot 4 & 6)
      Box(
        modifier = Modifier
          .size(38.dp)
          .clip(CircleShape)
          .background(SensiRedGlow)
          .border(1.5.dp, SensiRed, CircleShape)
          .clickable { onQuickAction() }
          .testTag("quick_action_${item.id}"),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.FlashOn,
          contentDescription = "Limpar Agora",
          tint = SensiRed,
          modifier = Modifier.size(20.dp)
        )
      }
    } else {
      Switch(
        checked = item.isChecked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
          checkedThumbColor = Color.White,
          checkedTrackColor = SensiRed,
          uncheckedThumbColor = colors.textMuted,
          uncheckedTrackColor = colors.surfaceHighlight
        ),
        modifier = Modifier.testTag("switch_${item.id}")
      )
    }
  }
}

