package com.example.model

enum class TargetArea(val label: String, val badge: String) {
  HEAD("CABEÇA / HEAD", "🎯 Prioridade Capa"),
  NECK("PESCOÇO / NECK", "⚡ Transição Rápida"),
  BODY("CORPO / BODY", "🛡️ Dano Seguro")
}

data class AimAssistsState(
  val aimStabilizer: Boolean = true,
  val sticky2x: Boolean = false,
  val miraBotActive: Boolean = true,
  val targetArea: TargetArea = TargetArea.HEAD,
  val floatingBotEnabled: Boolean = false,
  val aimRed: Boolean = true,
  val precision2x: Boolean = true,
  val precisionWhiteCrosshair: Boolean = true,
)
