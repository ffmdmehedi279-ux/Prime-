package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.AimAssistsState
import com.example.model.DeviceProfile
import com.example.model.PlayStyle
import com.example.model.SavedConfig
import com.example.model.SensitivityConfig
import com.example.model.TargetArea
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PreferencesManager(context: Context) {
  private val prefs: SharedPreferences =
    context.getSharedPreferences("ff_sensi_bot_prefs", Context.MODE_PRIVATE)

  // Current Sensitivity
  fun saveSensitivity(config: SensitivityConfig) {
    prefs.edit()
      .putInt("sensi_general", config.general)
      .putInt("sensi_red_dot", config.redDot)
      .putInt("sensi_2x", config.scope2x)
      .putInt("sensi_4x", config.scope4x)
      .putInt("sensi_sniper", config.sniper)
      .putInt("sensi_free_look", config.freeLook)
      .putInt("sensi_dpi", config.dpi)
      .putInt("sensi_button_size", config.buttonSize)
      .apply()
  }

  fun getSensitivity(): SensitivityConfig {
    return SensitivityConfig(
      general = prefs.getInt("sensi_general", 185),
      redDot = prefs.getInt("sensi_red_dot", 175),
      scope2x = prefs.getInt("sensi_2x", 180),
      scope4x = prefs.getInt("sensi_4x", 165),
      sniper = prefs.getInt("sensi_sniper", 110),
      freeLook = prefs.getInt("sensi_free_look", 145),
      dpi = prefs.getInt("sensi_dpi", 480),
      buttonSize = prefs.getInt("sensi_button_size", 55)
    )
  }

  // Device Profile
  fun saveDeviceProfile(profile: DeviceProfile) {
    prefs.edit()
      .putString("device_model", profile.deviceModel)
      .putInt("device_ram", profile.ramGb)
      .putInt("device_refresh_rate", profile.refreshRateHz)
      .putInt("device_touch_sampling", profile.touchSamplingRateHz)
      .putInt("device_dpi", profile.dpi)
      .putInt("device_fps", profile.preferredFps)
      .putString("device_play_style", profile.playStyle.name)
      .apply()
  }

  fun getDeviceProfile(): DeviceProfile {
    val styleName = prefs.getString("device_play_style", PlayStyle.FAST_AIM.name) ?: PlayStyle.FAST_AIM.name
    val style = try {
      PlayStyle.valueOf(styleName)
    } catch (e: Exception) {
      PlayStyle.FAST_AIM
    }

    return DeviceProfile(
      deviceModel = prefs.getString("device_model", "Xiaomi Poco X3 Pro") ?: "Xiaomi Poco X3 Pro",
      ramGb = prefs.getInt("device_ram", 8),
      refreshRateHz = prefs.getInt("device_refresh_rate", 120),
      touchSamplingRateHz = prefs.getInt("device_touch_sampling", 240),
      dpi = prefs.getInt("device_dpi", 440),
      preferredFps = prefs.getInt("device_fps", 60),
      playStyle = style
    )
  }

  // Aim Assists State
  fun saveAimAssists(state: AimAssistsState) {
    prefs.edit()
      .putBoolean("aim_stabilizer", state.aimStabilizer)
      .putBoolean("aim_sticky_2x", state.sticky2x)
      .putBoolean("aim_mira_bot", state.miraBotActive)
      .putString("aim_target_area", state.targetArea.name)
      .putBoolean("aim_floating_bot", state.floatingBotEnabled)
      .putBoolean("aim_red", state.aimRed)
      .putBoolean("aim_precision_2x", state.precision2x)
      .putBoolean("aim_white_crosshair", state.precisionWhiteCrosshair)
      .apply()
  }

  fun getAimAssists(): AimAssistsState {
    val targetName = prefs.getString("aim_target_area", TargetArea.HEAD.name) ?: TargetArea.HEAD.name
    val targetArea = try {
      TargetArea.valueOf(targetName)
    } catch (e: Exception) {
      TargetArea.HEAD
    }

    return AimAssistsState(
      aimStabilizer = prefs.getBoolean("aim_stabilizer", true),
      sticky2x = prefs.getBoolean("aim_sticky_2x", false),
      miraBotActive = prefs.getBoolean("aim_mira_bot", true),
      targetArea = targetArea,
      floatingBotEnabled = prefs.getBoolean("aim_floating_bot", false),
      aimRed = prefs.getBoolean("aim_red", true),
      precision2x = prefs.getBoolean("aim_precision_2x", true),
      precisionWhiteCrosshair = prefs.getBoolean("aim_white_crosshair", true)
    )
  }

  // Saved Configurations History
  fun getSavedConfigs(): List<SavedConfig> {
    val rawJson = prefs.getString("saved_configs_json", null)
    if (rawJson.isNullOrEmpty()) {
      return getDefaultConfigs()
    }
    return try {
      val list = mutableListOf<SavedConfig>()
      val array = JSONArray(rawJson)
      for (i in 0 until array.length()) {
        val obj = array.getJSONObject(i)
        val cfgObj = obj.getJSONObject("config")
        val devObj = obj.getJSONObject("device")
        val style = try {
          PlayStyle.valueOf(devObj.optString("playStyle", PlayStyle.FAST_AIM.name))
        } catch (e: Exception) {
          PlayStyle.FAST_AIM
        }

        list.add(
          SavedConfig(
            id = obj.getString("id"),
            name = obj.getString("name"),
            date = obj.getString("date"),
            notes = obj.optString("notes", ""),
            config = SensitivityConfig(
              general = cfgObj.getInt("general"),
              redDot = cfgObj.getInt("redDot"),
              scope2x = cfgObj.getInt("scope2x"),
              scope4x = cfgObj.getInt("scope4x"),
              sniper = cfgObj.getInt("sniper"),
              freeLook = cfgObj.getInt("freeLook"),
              dpi = cfgObj.optInt("dpi", 480),
              buttonSize = cfgObj.optInt("buttonSize", 55)
            ),
            deviceProfile = DeviceProfile(
              deviceModel = devObj.getString("deviceModel"),
              ramGb = devObj.getInt("ramGb"),
              refreshRateHz = devObj.getInt("refreshRateHz"),
              touchSamplingRateHz = devObj.getInt("touchSamplingRateHz"),
              dpi = devObj.getInt("dpi"),
              preferredFps = devObj.optInt("preferredFps", 60),
              playStyle = style
            )
          )
        )
      }
      list
    } catch (e: Exception) {
      getDefaultConfigs()
    }
  }

  fun saveConfigsList(list: List<SavedConfig>) {
    val array = JSONArray()
    for (item in list) {
      val obj = JSONObject().apply {
        put("id", item.id)
        put("name", item.name)
        put("date", item.date)
        put("notes", item.notes)
        put("config", JSONObject().apply {
          put("general", item.config.general)
          put("redDot", item.config.redDot)
          put("scope2x", item.config.scope2x)
          put("scope4x", item.config.scope4x)
          put("sniper", item.config.sniper)
          put("freeLook", item.config.freeLook)
          put("dpi", item.config.dpi)
          put("buttonSize", item.config.buttonSize)
        })
        put("device", JSONObject().apply {
          put("deviceModel", item.deviceProfile.deviceModel)
          put("ramGb", item.deviceProfile.ramGb)
          put("refreshRateHz", item.deviceProfile.refreshRateHz)
          put("touchSamplingRateHz", item.deviceProfile.touchSamplingRateHz)
          put("dpi", item.deviceProfile.dpi)
          put("preferredFps", item.deviceProfile.preferredFps)
          put("playStyle", item.deviceProfile.playStyle.name)
        })
      }
      array.put(obj)
    }
    prefs.edit().putString("saved_configs_json", array.toString()).apply()
  }

  fun addSavedConfig(name: String, config: SensitivityConfig, profile: DeviceProfile, notes: String = "") {
    val currentList = getSavedConfigs().toMutableList()
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val newEntry = SavedConfig(
      id = "cfg_${System.currentTimeMillis()}",
      name = name.ifBlank { "Configuração #${currentList.size + 1}" },
      date = dateFormat.format(Date()),
      config = config,
      deviceProfile = profile,
      notes = notes
    )
    currentList.add(0, newEntry)
    saveConfigsList(currentList)
  }

  fun deleteSavedConfig(id: String) {
    val currentList = getSavedConfigs().filter { it.id != id }
    saveConfigsList(currentList)
  }

  fun renameSavedConfig(id: String, newName: String) {
    val currentList = getSavedConfigs().map {
      if (it.id == id) it.copy(name = newName) else it
    }
    saveConfigsList(currentList)
  }

  private fun getDefaultConfigs(): List<SavedConfig> {
    return listOf(
      SavedConfig(
        id = "preset_rush_pro",
        name = "Rushador Pro (Curta Distância)",
        date = "16/08/2026 15:30",
        config = SensitivityConfig.FAST_RUSH,
        deviceProfile = DeviceProfile("Xiaomi / Poco", 8, 120, 240, 580, 60, PlayStyle.FAST_AIM),
        notes = "Puxada de capa super veloz com MP40 e UMP."
      ),
      SavedConfig(
        id = "preset_precision",
        name = "Precisão 1-Tiro (Desert & SVD)",
        date = "15/08/2026 19:10",
        config = SensitivityConfig.PRECISION_HEADSHOT,
        deviceProfile = DeviceProfile("Samsung Galaxy", 6, 90, 180, 411, 60, PlayStyle.PRECISION),
        notes = "Mira travada na cabeça para tiro único."
      )
    )
  }
}
