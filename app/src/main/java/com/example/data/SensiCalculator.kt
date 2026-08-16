package com.example.data

import com.example.model.DeviceProfile
import com.example.model.PlayStyle
import com.example.model.SensitivityConfig
import kotlin.math.roundToInt

data class SensiRange(
  val min: Int,
  val max: Int,
  val recommended: Int
)

data class SensiRecommendation(
  val config: SensitivityConfig,
  val generalRange: SensiRange,
  val redDotRange: SensiRange,
  val scope2xRange: SensiRange,
  val scope4xRange: SensiRange,
  val sniperRange: SensiRange,
  val freeLookRange: SensiRange,
  val recommendedDpi: Int,
  val recommendedButtonSize: Int,
  val explanation: String
)

object SensiCalculator {

  fun calculateRecommendation(profile: DeviceProfile): SensiRecommendation {
    var baseGeneral = 180.0
    var baseRedDot = 170.0
    var base2x = 168.0
    var base4x = 152.0
    var baseSniper = 105.0
    var baseFreeLook = 140.0

    // Adjust for RAM (Lower RAM needs higher sensitivity compensation for touch friction)
    when {
      profile.ramGb <= 3 -> {
        baseGeneral += 14.0
        baseRedDot += 12.0
        base2x += 10.0
        base4x += 8.0
      }
      profile.ramGb == 4 -> {
        baseGeneral += 8.0
        baseRedDot += 6.0
        base2x += 5.0
      }
      profile.ramGb >= 8 -> {
        baseGeneral -= 4.0
        baseRedDot -= 4.0
        base2x -= 3.0
      }
    }

    // Adjust for Screen Refresh Rate (90Hz / 120Hz provides smoother tracking, slightly lower sensi stops overshooting)
    when {
      profile.refreshRateHz >= 120 -> {
        baseGeneral -= 6.0
        baseRedDot -= 5.0
        base2x -= 4.0
      }
      profile.refreshRateHz == 90 -> {
        baseGeneral -= 2.0
        baseRedDot -= 2.0
      }
      else -> {
        baseGeneral += 5.0
        baseRedDot += 4.0
      }
    }

    // Adjust for Touch Sampling Rate
    if (profile.touchSamplingRateHz >= 360) {
      baseGeneral -= 4.0
      baseRedDot -= 3.0
    } else if (profile.touchSamplingRateHz <= 120) {
      baseGeneral += 8.0
      baseRedDot += 7.0
    }

    // Adjust for Play Style
    var styleExplanation = ""
    when (profile.playStyle) {
      PlayStyle.FAST_AIM -> {
        baseGeneral += 12.0
        baseRedDot += 14.0
        base2x += 10.0
        base4x += 6.0
        baseFreeLook += 20.0
        styleExplanation = "Ajustado para rush agressivo com puxada de capa rápida em curta distância."
      }
      PlayStyle.PRECISION -> {
        baseGeneral -= 10.0
        baseRedDot -= 8.0
        base2x -= 6.0
        base4x -= 10.0
        baseSniper -= 15.0
        baseFreeLook -= 10.0
        styleExplanation = "Ajustado para armas de um tiro (Desert Eagle, SVD, Woodpecker) com mira firme sem passar da cabeça."
      }
      PlayStyle.SMOOTH -> {
        baseGeneral -= 4.0
        baseRedDot -= 2.0
        base2x -= 3.0
        baseSniper += 5.0
        styleExplanation = "Ajustado para movimentação suave, rastreamento contínuo de alvos em movimento."
      }
      PlayStyle.BALANCED -> {
        styleExplanation = "Configuração equilibrada para todos os tipos de armas e distâncias."
      }
    }

    // Calculate DPI & Button Size recommendation
    val recommendedDpi = when {
      profile.dpi > 0 -> profile.dpi
      profile.ramGb <= 4 -> 540
      profile.refreshRateHz >= 120 -> 480
      else -> 500
    }

    val recommendedButtonSize = when (profile.playStyle) {
      PlayStyle.FAST_AIM -> 48
      PlayStyle.PRECISION -> 56
      PlayStyle.SMOOTH -> 52
      PlayStyle.BALANCED -> 50
    }

    fun clampSensi(v: Double): Int = v.roundToInt().coerceIn(0, 200)

    val genVal = clampSensi(baseGeneral)
    val redVal = clampSensi(baseRedDot)
    val s2xVal = clampSensi(base2x)
    val s4xVal = clampSensi(base4x)
    val snpVal = clampSensi(baseSniper)
    val freeVal = clampSensi(baseFreeLook)

    val config = SensitivityConfig(
      general = genVal,
      redDot = redVal,
      scope2x = s2xVal,
      scope4x = s4xVal,
      sniper = snpVal,
      freeLook = freeVal,
      dpi = recommendedDpi,
      buttonSize = recommendedButtonSize
    )

    fun makeRange(center: Int, spread: Int): SensiRange {
      val min = (center - spread).coerceAtLeast(0)
      val max = (center + spread).coerceAtMost(200)
      return SensiRange(min = min, max = max, recommended = center)
    }

    return SensiRecommendation(
      config = config,
      generalRange = makeRange(genVal, 10),
      redDotRange = makeRange(redVal, 10),
      scope2xRange = makeRange(s2xVal, 12),
      scope4xRange = makeRange(s4xVal, 12),
      sniperRange = makeRange(snpVal, 15),
      freeLookRange = makeRange(freeVal, 18),
      recommendedDpi = recommendedDpi,
      recommendedButtonSize = recommendedButtonSize,
      explanation = "$styleExplanation Calibrado para ${profile.deviceModel} (${profile.ramGb}GB RAM, ${profile.refreshRateHz}Hz)."
    )
  }
}
