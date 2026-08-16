package com.example.model

data class SensitivityConfig(
  val general: Int = 185,
  val redDot: Int = 175,
  val scope2x: Int = 180,
  val scope4x: Int = 165,
  val sniper: Int = 110,
  val freeLook: Int = 145,
  val dpi: Int = 480,
  val buttonSize: Int = 55,
) {
  companion object {
    val DEFAULT = SensitivityConfig()

    val BALANCED = SensitivityConfig(
      general = 175,
      redDot = 165,
      scope2x = 170,
      scope4x = 155,
      sniper = 100,
      freeLook = 135,
      dpi = 450,
      buttonSize = 52
    )

    val FAST_RUSH = SensitivityConfig(
      general = 198,
      redDot = 192,
      scope2x = 195,
      scope4x = 185,
      sniper = 125,
      freeLook = 180,
      dpi = 580,
      buttonSize = 48
    )

    val PRECISION_HEADSHOT = SensitivityConfig(
      general = 165,
      redDot = 155,
      scope2x = 160,
      scope4x = 145,
      sniper = 85,
      freeLook = 120,
      dpi = 411,
      buttonSize = 58
    )

    val LOW_END_DEVICE = SensitivityConfig(
      general = 200,
      redDot = 195,
      scope2x = 190,
      scope4x = 180,
      sniper = 120,
      freeLook = 175,
      dpi = 520,
      buttonSize = 50
    )

    val HIGH_REFRESH_RATE = SensitivityConfig(
      general = 170,
      redDot = 160,
      scope2x = 165,
      scope4x = 150,
      sniper = 95,
      freeLook = 130,
      dpi = 460,
      buttonSize = 54
    )
  }
}
