package com.example.model

enum class PlayStyle(val label: String, val description: String) {
  BALANCED("Balanced", "Versatile setting for all combat ranges"),
  FAST_AIM("Fast Aim / Rush", "Ultra-fast response for close-range drag shots"),
  SMOOTH("Smooth", "Gliding and stable tracking with low jitter"),
  PRECISION("Precision / 1-Tap", "Consistent one-tap headshots for Deagle, Woodpecker & SVD")
}

data class DeviceProfile(
  val deviceModel: String = "Xiaomi Poco X3 Pro",
  val ramGb: Int = 8,
  val refreshRateHz: Int = 120,
  val touchSamplingRateHz: Int = 240,
  val dpi: Int = 440,
  val preferredFps: Int = 60,
  val playStyle: PlayStyle = PlayStyle.FAST_AIM
)
