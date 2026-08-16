package com.example.ui

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.PreferencesManager
import com.example.model.AimAssistsState
import com.example.model.DeviceProfile
import com.example.model.OptimizationState
import com.example.model.SensitivityConfig
import com.example.ui.components.BottomNavBar
import com.example.ui.components.FloatingBotOverlay
import com.example.ui.components.HeaderBar
import com.example.ui.components.NavTab
import com.example.ui.components.PRESETS_LIST
import com.example.ui.components.PresetCard
import com.example.ui.screens.OptimizerScreen
import com.example.ui.screens.SensiBotScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SupportScreen
import com.example.ui.theme.SensiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
  isWhiteTheme: Boolean = true,
  onToggleTheme: () -> Unit = {}
) {
  val context = LocalContext.current
  val colors = SensiTheme.colors
  val prefs = remember { PreferencesManager(context) }

  var currentTab by remember { mutableStateOf(NavTab.SETTINGS) }
  var sensitivityConfig by remember { mutableStateOf(prefs.getSensitivity()) }
  var aimAssistsState by remember { mutableStateOf(prefs.getAimAssists()) }
  var deviceProfile by remember { mutableStateOf(prefs.getDeviceProfile()) }
  var optimizationState by remember { mutableStateOf(OptimizationState()) }
  var savedConfigs by remember { mutableStateOf(prefs.getSavedConfigs()) }

  var showPresetsSheet by remember { mutableStateOf(false) }
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      HeaderBar(
        isWhiteTheme = isWhiteTheme,
        onToggleTheme = onToggleTheme,
        onMenuClick = { showPresetsSheet = true },
        onQuickPresetClick = { showPresetsSheet = true }
      )
    },
    bottomBar = {
      BottomNavBar(
        selectedTab = currentTab,
        onTabSelected = { tab ->
          currentTab = tab
        }
      )
    },
    containerColor = colors.bg
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      when (currentTab) {
        NavTab.SETTINGS -> {
          SettingsScreen(
            config = sensitivityConfig,
            onConfigChange = {
              sensitivityConfig = it
              prefs.saveSensitivity(it)
            },
            aimState = aimAssistsState,
            onAimStateChange = {
              aimAssistsState = it
              prefs.saveAimAssists(it)
            },
            onOpenBotTab = { currentTab = NavTab.SENSI_BOT }
          )
        }
        NavTab.OPTIMIZER -> {
          OptimizerScreen(
            state = optimizationState,
            onStateChange = { optimizationState = it }
          )
        }
        NavTab.SENSI_BOT -> {
          SensiBotScreen(
            profile = deviceProfile,
            onProfileChange = {
              deviceProfile = it
              prefs.saveDeviceProfile(it)
            },
            onApplyConfig = { cfg ->
              sensitivityConfig = cfg
              prefs.saveSensitivity(cfg)
            },
            savedConfigs = savedConfigs,
            onSaveConfig = { name, cfg, prof ->
              prefs.addSavedConfig(name, cfg, prof)
              savedConfigs = prefs.getSavedConfigs()
            },
            onDeleteConfig = { id ->
              prefs.deleteSavedConfig(id)
              savedConfigs = prefs.getSavedConfigs()
            },
            onRenameConfig = { id, newName ->
              prefs.renameSavedConfig(id, newName)
              savedConfigs = prefs.getSavedConfigs()
            }
          )
        }
        NavTab.SUPPORT -> {
          SupportScreen()
        }
      }

      // Floating Tactical BOT HUD Overlay
      if (aimAssistsState.floatingBotEnabled) {
        FloatingBotOverlay(
          visible = true,
          aimState = aimAssistsState,
          onAimStateChange = {
            aimAssistsState = it
            prefs.saveAimAssists(it)
          },
          onDismiss = {
            aimAssistsState = aimAssistsState.copy(floatingBotEnabled = false)
            prefs.saveAimAssists(aimAssistsState)
          }
        )
      }
    }
  }

  // Presets Bottom Sheet
  if (showPresetsSheet) {
    ModalBottomSheet(
      onDismissRequest = { showPresetsSheet = false },
      sheetState = sheetState,
      containerColor = colors.surfaceElevated
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Text(
          text = "⚡ Presets Rápidos de Sensibilidade",
          color = colors.textPrimary,
          fontSize = 17.sp,
          fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
          modifier = Modifier.padding(bottom = 12.dp)
        )

        androidx.compose.foundation.lazy.LazyColumn(
          verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(10.dp)
        ) {
          items(PRESETS_LIST.size) { index ->
            val preset = PRESETS_LIST[index]
            PresetCard(
              preset = preset,
              isSelected = false,
              onApply = { cfg ->
                sensitivityConfig = cfg
                prefs.saveSensitivity(cfg)
                showPresetsSheet = false
                Toast.makeText(context, "Preset '${preset.name}' aplicado!", Toast.LENGTH_SHORT).show()
              },
              onPreview = {}
            )
          }
        }
      }
    }
  }
}

