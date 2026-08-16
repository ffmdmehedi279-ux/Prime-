package com.example.model

data class SavedConfig(
  val id: String,
  val name: String,
  val date: String,
  val config: SensitivityConfig,
  val deviceProfile: DeviceProfile,
  val notes: String = ""
)
