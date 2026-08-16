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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SensiCalculator
import com.example.data.SensiRecommendation
import com.example.model.DeviceProfile
import com.example.model.PlayStyle
import com.example.model.SavedConfig
import com.example.model.SensitivityConfig
import com.example.ui.components.PRESETS_LIST
import com.example.ui.components.PresetCard
import com.example.ui.components.SensiPreset
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiRedGlow
import com.example.ui.theme.SensiTheme
import androidx.compose.material.icons.filled.Verified
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SensiBotScreen(
  profile: DeviceProfile,
  onProfileChange: (DeviceProfile) -> Unit,
  onApplyConfig: (SensitivityConfig) -> Unit,
  savedConfigs: List<SavedConfig>,
  onSaveConfig: (String, SensitivityConfig, DeviceProfile) -> Unit,
  onDeleteConfig: (String) -> Unit,
  onRenameConfig: (String, String) -> Unit
) {
  val context = LocalContext.current
  val colors = SensiTheme.colors
  val coroutineScope = rememberCoroutineScope()
  val scrollState = rememberScrollState()

  var isGenerating by remember { mutableStateOf(false) }
  var recommendation by remember {
    mutableStateOf<SensiRecommendation?>(SensiCalculator.calculateRecommendation(profile))
  }

  // Dialog state for saving config
  var showSaveDialog by remember { mutableStateOf(false) }
  var saveConfigName by remember { mutableStateOf("") }

  // Dialog state for rename
  var showRenameDialog by remember { mutableStateOf(false) }
  var renameConfigId by remember { mutableStateOf("") }
  var renameConfigName by remember { mutableStateOf("") }

  fun generateSensi() {
    isGenerating = true
    coroutineScope.launch {
      delay(900)
      recommendation = SensiCalculator.calculateRecommendation(profile)
      isGenerating = false
      Toast.makeText(context, "Nova sensibilidade calculada com sucesso!", Toast.LENGTH_SHORT).show()
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
            text = "White FF Sensi Bot • Dev: Prime Mehedi Moder",
            color = colors.textPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Text(
          text = "IA BOT 3.0",
          color = SensiRed,
          fontSize = 10.sp,
          fontWeight = FontWeight.Black
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Hero Central Robot Radar Card
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(20.dp))
        .background(colors.surfaceElevated)
        .border(1.dp, colors.border, RoundedCornerShape(20.dp))
        .padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Big Glowing Robot Target Radar
      Box(
        modifier = Modifier
          .size(110.dp)
          .clip(CircleShape)
          .background(
            Brush.radialGradient(
              colors = listOf(SensiRedGlow, colors.surfaceHighlight)
            )
          )
          .border(2.dp, SensiRed, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Text(text = "🎯", fontSize = 28.sp)
          Spacer(modifier = Modifier.height(2.dp))
          Text(text = "🤖", fontSize = 24.sp)
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = buildAnnotatedString {
          append("Sensi ")
          withStyle(SpanStyle(color = SensiRed, fontWeight = FontWeight.Black)) {
            append("BOT")
          }
        },
        color = colors.textPrimary,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
      )

      Spacer(modifier = Modifier.height(6.dp))

      Text(
        text = "Experimente a criação de sensibilidade inteligente personalizada para o seu dispositivo e estilo de jogo.",
        color = colors.textMuted,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        textAlign = androidx.compose.ui.text.style.TextAlign.Center
      )

      Spacer(modifier = Modifier.height(20.dp))

      // Iniciar SensiBot / Generate Sensi Action Pill Button
      Button(
        onClick = { generateSensi() },
        enabled = !isGenerating,
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .shadow(8.dp, RoundedCornerShape(14.dp))
          .testTag("btn_generate_sensi"),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = colors.surfaceHighlight,
          contentColor = colors.textPrimary
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, SensiRed)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          if (isGenerating) {
            CircularProgressIndicator(
              modifier = Modifier.size(20.dp),
              color = SensiRed,
              strokeWidth = 2.dp
            )
            Text(
              text = "CALCULANDO SENSI...",
              color = colors.textPrimary,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(20.dp))
          } else {
            Text(
              text = "Iniciar SensiBot",
              color = colors.textPrimary,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.5.sp
            )

            // Red Circular Arrow Icon
            Box(
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(SensiRed),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Device Profile Form
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(colors.surfaceElevated)
        .border(1.dp, colors.border, RoundedCornerShape(18.dp))
        .padding(16.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.PhoneAndroid,
          contentDescription = null,
          tint = SensiRed,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Perfil do Dispositivo",
          color = colors.textPrimary,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Device Model Field
      OutlinedTextField(
        value = profile.deviceModel,
        onValueChange = { onProfileChange(profile.copy(deviceModel = it)) },
        label = { Text("Modelo do Celular / Marca") },
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("input_device_model"),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = SensiRed,
          unfocusedBorderColor = colors.border,
          focusedLabelColor = SensiRed,
          unfocusedLabelColor = colors.textMuted,
          focusedTextColor = colors.textPrimary,
          unfocusedTextColor = colors.textPrimary
        )
      )

      Spacer(modifier = Modifier.height(12.dp))

      // RAM Selector
      Text(text = "Memória RAM: ${profile.ramGb} GB", color = colors.textPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
      Spacer(modifier = Modifier.height(6.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        listOf(2, 3, 4, 6, 8, 12).forEach { ram ->
          val isSelected = profile.ramGb == ram
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSelected) SensiRed else colors.surfaceHighlight)
              .border(1.dp, if (isSelected) SensiRed else colors.border, RoundedCornerShape(8.dp))
              .clickable { onProfileChange(profile.copy(ramGb = ram)) }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${ram}G",
              color = if (isSelected) Color.White else colors.textMuted,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Refresh Rate Selector
      Text(text = "Taxa de Atualização da Tela", color = colors.textPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
      Spacer(modifier = Modifier.height(6.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        listOf(60, 90, 120, 144).forEach { hz ->
          val isSelected = profile.refreshRateHz == hz
          Box(
            modifier = Modifier
              .weight(1f)
              .clip(RoundedCornerShape(8.dp))
              .background(if (isSelected) SensiRed else colors.surfaceHighlight)
              .border(1.dp, if (isSelected) SensiRed else colors.border, RoundedCornerShape(8.dp))
              .clickable { onProfileChange(profile.copy(refreshRateHz = hz)) }
              .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "${hz}Hz",
              color = if (isSelected) Color.White else colors.textMuted,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Play Style Selector
      Text(text = "Estilo de Jogo", color = colors.textPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
      Spacer(modifier = Modifier.height(6.dp))
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        PlayStyle.values().forEach { style ->
          val isSelected = profile.playStyle == style
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(if (isSelected) SensiRedGlow else colors.surfaceHighlight)
              .border(1.dp, if (isSelected) SensiRed else colors.border, RoundedCornerShape(10.dp))
              .clickable { onProfileChange(profile.copy(playStyle = style)) }
              .padding(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column {
                Text(
                  text = style.label,
                  color = if (isSelected) SensiRed else colors.textPrimary,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = style.description,
                  color = colors.textMuted,
                  fontSize = 11.sp
                )
              }
              if (isSelected) {
                Text(text = "✓", color = SensiRed, fontSize = 14.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Sensi Results Cards & Range Recommendations
    recommendation?.let { rec ->
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(18.dp))
          .background(colors.surfaceElevated)
          .border(1.5.dp, SensiRed, RoundedCornerShape(18.dp))
          .padding(16.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.SmartToy,
              contentDescription = null,
              tint = SensiRed,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Recomendação Personalizada",
              color = colors.textPrimary,
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(SensiRed)
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(text = "CALCULADO", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = rec.explanation,
          color = colors.textMuted,
          fontSize = 12.sp,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 6 Calculated Scope Cards Grid
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            ScopeResultCard(
              modifier = Modifier.weight(1f),
              title = "Geral",
              value = rec.config.general,
              rangeText = "${rec.generalRange.min}–${rec.generalRange.max}"
            )
            ScopeResultCard(
              modifier = Modifier.weight(1f),
              title = "Ponto Vermelho",
              value = rec.config.redDot,
              rangeText = "${rec.redDotRange.min}–${rec.redDotRange.max}"
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            ScopeResultCard(
              modifier = Modifier.weight(1f),
              title = "Mira 2X",
              value = rec.config.scope2x,
              rangeText = "${rec.scope2xRange.min}–${rec.scope2xRange.max}"
            )
            ScopeResultCard(
              modifier = Modifier.weight(1f),
              title = "Mira 4X",
              value = rec.config.scope4x,
              rangeText = "${rec.scope4xRange.min}–${rec.scope4xRange.max}"
            )
          }

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            ScopeResultCard(
              modifier = Modifier.weight(1f),
              title = "Mira Sniper",
              value = rec.config.sniper,
              rangeText = "${rec.sniperRange.min}–${rec.sniperRange.max}"
            )
            ScopeResultCard(
              modifier = Modifier.weight(1f),
              title = "Olhadinha",
              value = rec.config.freeLook,
              rangeText = "${rec.freeLookRange.min}–${rec.freeLookRange.max}"
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Recommended DPI and Fire Button Size Banner
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(colors.bg)
            .padding(10.dp),
          horizontalArrangement = Arrangement.SpaceAround
        ) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "DPI Sugerida", color = colors.textMuted, fontSize = 11.sp)
            Text(text = "${rec.recommendedDpi}", color = SensiRed, fontSize = 14.sp, fontWeight = FontWeight.Bold)
          }
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Tamanho do Botão", color = colors.textMuted, fontSize = 11.sp)
            Text(text = "${rec.recommendedButtonSize}%", color = colors.textPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Action Buttons: Apply to Profile, Save Config, Regenerate
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = {
              onApplyConfig(rec.config)
              Toast.makeText(context, "Sensibilidade aplicada aos seus Ajustes!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
              .weight(1f)
              .height(44.dp)
              .testTag("btn_apply_recommendation"),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = SensiRed,
              contentColor = Color.White
            )
          ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Aplicar", fontSize = 13.sp, fontWeight = FontWeight.Bold)
          }

          Button(
            onClick = {
              saveConfigName = "${profile.deviceModel} (${profile.playStyle.label})"
              showSaveDialog = true
            },
            modifier = Modifier
              .weight(1f)
              .height(44.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = colors.surfaceHighlight,
              contentColor = colors.textPrimary
            )
          ) {
            Icon(imageVector = Icons.Default.Bookmark, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "Salvar", fontSize = 13.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Presets Section
    Text(
      text = "Presets Prontos de Sensibilidade",
      color = colors.textPrimary,
      fontSize = 15.sp,
      fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    PRESETS_LIST.forEach { preset ->
      PresetCard(
        preset = preset,
        isSelected = false,
        onApply = { cfg ->
          onApplyConfig(cfg)
          Toast.makeText(context, "Preset '${preset.name}' aplicado!", Toast.LENGTH_SHORT).show()
        },
        onPreview = {}
      )
      Spacer(modifier = Modifier.height(10.dp))
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Saved Configurations History
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
            imageVector = Icons.Default.Bookmark,
            contentDescription = null,
            tint = SensiRed,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Configurações Salvas",
            color = colors.textPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Text(
          text = "${savedConfigs.size} salvas",
          color = colors.textMuted,
          fontSize = 12.sp
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      if (savedConfigs.isEmpty()) {
        Text(
          text = "Nenhuma configuração salva ainda. Gere uma sensibilidade e clique em Salvar!",
          color = colors.textMuted,
          fontSize = 12.sp,
          modifier = Modifier.padding(vertical = 12.dp)
        )
      } else {
        savedConfigs.forEach { saved ->
          SavedConfigItem(
            saved = saved,
            onLoad = {
              onApplyConfig(saved.config)
              onProfileChange(saved.deviceProfile)
              Toast.makeText(context, "Configuração '${saved.name}' carregada!", Toast.LENGTH_SHORT).show()
            },
            onDelete = { onDeleteConfig(saved.id) },
            onRename = {
              renameConfigId = saved.id
              renameConfigName = saved.name
              showRenameDialog = true
            }
          )
          Spacer(modifier = Modifier.height(8.dp))
        }
      }
    }

    Spacer(modifier = Modifier.height(30.dp))
  }

  // Save Config Dialog
  if (showSaveDialog) {
    AlertDialog(
      onDismissRequest = { showSaveDialog = false },
      containerColor = colors.surfaceElevated,
      title = { Text(text = "Salvar Configuração", color = colors.textPrimary, fontWeight = FontWeight.Bold) },
      text = {
        OutlinedTextField(
          value = saveConfigName,
          onValueChange = { saveConfigName = it },
          label = { Text("Nome da Configuração") },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SensiRed,
            unfocusedBorderColor = colors.border,
            focusedTextColor = colors.textPrimary,
            unfocusedTextColor = colors.textPrimary
          )
        )
      },
      confirmButton = {
        Button(
          onClick = {
            recommendation?.let { rec ->
              onSaveConfig(saveConfigName, rec.config, profile)
              Toast.makeText(context, "Configuração salva com sucesso!", Toast.LENGTH_SHORT).show()
            }
            showSaveDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = SensiRed)
        ) {
          Text(text = "Salvar", color = Color.White)
        }
      },
      dismissButton = {
        TextButton(onClick = { showSaveDialog = false }) {
          Text(text = "Cancelar", color = colors.textMuted)
        }
      }
    )
  }

  // Rename Config Dialog
  if (showRenameDialog) {
    AlertDialog(
      onDismissRequest = { showRenameDialog = false },
      containerColor = colors.surfaceElevated,
      title = { Text(text = "Renomear Configuração", color = colors.textPrimary, fontWeight = FontWeight.Bold) },
      text = {
        OutlinedTextField(
          value = renameConfigName,
          onValueChange = { renameConfigName = it },
          label = { Text("Novo Nome") },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SensiRed,
            unfocusedBorderColor = colors.border,
            focusedTextColor = colors.textPrimary,
            unfocusedTextColor = colors.textPrimary
          )
        )
      },
      confirmButton = {
        Button(
          onClick = {
            onRenameConfig(renameConfigId, renameConfigName)
            showRenameDialog = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = SensiRed)
        ) {
          Text(text = "Renomear", color = Color.White)
        }
      },
      dismissButton = {
        TextButton(onClick = { showRenameDialog = false }) {
          Text(text = "Cancelar", color = colors.textMuted)
        }
      }
    )
  }
}

@Composable
private fun ScopeResultCard(
  modifier: Modifier = Modifier,
  title: String,
  value: Int,
  rangeText: String
) {
  val colors = SensiTheme.colors
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(colors.bg)
      .border(1.dp, colors.border, RoundedCornerShape(12.dp))
      .padding(12.dp)
  ) {
    Column {
      Text(text = title, color = colors.textMuted, fontSize = 11.sp)
      Spacer(modifier = Modifier.height(4.dp))
      Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(text = "$value", color = colors.textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Black)
        Text(text = "Faixa: $rangeText", color = SensiRed, fontSize = 10.sp, fontWeight = FontWeight.Bold)
      }
    }
  }
}

@Composable
private fun SavedConfigItem(
  saved: SavedConfig,
  onLoad: () -> Unit,
  onDelete: () -> Unit,
  onRename: () -> Unit
) {
  val colors = SensiTheme.colors
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(colors.surfaceHighlight)
      .border(1.dp, colors.border, RoundedCornerShape(12.dp))
      .padding(12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(text = saved.name, color = colors.textPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = "${saved.date} • Geral: ${saved.config.general} • 2X: ${saved.config.scope2x}", color = colors.textMuted, fontSize = 11.sp)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
      IconButton(onClick = onLoad, modifier = Modifier.size(32.dp)) {
        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Carregar", tint = SensiRed, modifier = Modifier.size(18.dp))
      }
      IconButton(onClick = onRename, modifier = Modifier.size(32.dp)) {
        Icon(imageVector = Icons.Default.Edit, contentDescription = "Renomear", tint = colors.textMuted, modifier = Modifier.size(16.dp))
      }
      IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
        Icon(imageVector = Icons.Default.Delete, contentDescription = "Deletar", tint = colors.textMuted, modifier = Modifier.size(16.dp))
      }
    }
  }
}
