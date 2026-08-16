package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.TargetArea
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkBorder
import com.example.ui.theme.DarkBorderLight
import com.example.ui.theme.DarkSurfaceElevated
import com.example.ui.theme.DarkSurfaceHighlight
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiRedGlow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun HitboxVisualizer(
  selectedArea: TargetArea,
  onSelectArea: (TargetArea) -> Unit
) {
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.5f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(1000, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "alpha"
  )

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(16.dp))
      .background(DarkSurfaceElevated)
      .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
      .padding(16.dp)
  ) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.CenterFocusStrong,
          contentDescription = "HeadControl",
          tint = SensiRed,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "HeadControl 3D Hitbox",
          color = TextWhite,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
      }

      // Selected Badge
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(8.dp))
          .background(SensiRedGlow)
          .border(1.dp, SensiRed, RoundedCornerShape(8.dp))
          .padding(horizontal = 8.dp, vertical = 3.dp)
      ) {
        Text(
          text = selectedArea.badge,
          color = SensiRed,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Interactive Body Canvas + Area Selection Buttons
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(170.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Character Hitbox Canvas
      Box(
        modifier = Modifier
          .weight(1f)
          .height(170.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(DarkBg)
          .border(1.dp, DarkBorderLight, RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
      ) {
        Canvas(modifier = Modifier.size(150.dp, 160.dp)) {
          drawHitboxCharacter(
            selectedArea = selectedArea,
            pulseAlpha = pulseAlpha
          )
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      // Priority Target Buttons
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        TargetArea.values().forEach { area ->
          val isSelected = selectedArea == area
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(
                if (isSelected) {
                  Brush.horizontalGradient(
                    colors = listOf(SensiRed, SensiRedDark)
                  )
                } else {
                  Brush.horizontalGradient(
                    colors = listOf(DarkSurfaceHighlight, DarkSurfaceHighlight)
                  )
                }
              )
              .border(
                1.dp,
                if (isSelected) SensiRed else DarkBorder,
                RoundedCornerShape(10.dp)
              )
              .clickable { onSelectArea(area) }
              .padding(horizontal = 10.dp, vertical = 10.dp)
              .testTag("target_area_${area.name.lowercase()}"),
            contentAlignment = Alignment.CenterStart
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = area.label,
                color = if (isSelected) TextWhite else TextMuted,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
              )
              if (isSelected) {
                Text(text = "✓", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }
    }
  }
}

private fun DrawScope.drawHitboxCharacter(
  selectedArea: TargetArea,
  pulseAlpha: Float
) {
  val centerX = size.width / 2f
  val headCenterY = size.height * 0.22f
  val neckCenterY = size.height * 0.38f
  val bodyCenterY = size.height * 0.65f

  // Grid lines background
  val gridColor = Color(0xFF1E1E28)
  for (i in 1..4) {
    val y = size.height * (i / 5f)
    drawLine(
      color = gridColor,
      start = Offset(0f, y),
      end = Offset(size.width, y),
      strokeWidth = 1f
    )
  }

  // Draw stylized body silhouette in dark titanium tone
  val bodyColor = Color(0xFF38384C)

  // Head Circle
  drawCircle(
    color = bodyColor,
    radius = 18.dp.toPx(),
    center = Offset(centerX, headCenterY)
  )

  // Neck / Collar
  val neckPath = Path().apply {
    moveTo(centerX - 8.dp.toPx(), headCenterY + 16.dp.toPx())
    lineTo(centerX + 8.dp.toPx(), headCenterY + 16.dp.toPx())
    lineTo(centerX + 12.dp.toPx(), neckCenterY + 8.dp.toPx())
    lineTo(centerX - 12.dp.toPx(), neckCenterY + 8.dp.toPx())
    close()
  }
  drawPath(neckPath, color = bodyColor)

  // Torso / Shoulders
  val torsoPath = Path().apply {
    moveTo(centerX - 36.dp.toPx(), neckCenterY + 8.dp.toPx())
    lineTo(centerX + 36.dp.toPx(), neckCenterY + 8.dp.toPx())
    lineTo(centerX + 28.dp.toPx(), bodyCenterY + 30.dp.toPx())
    lineTo(centerX - 28.dp.toPx(), bodyCenterY + 30.dp.toPx())
    close()
  }
  drawPath(torsoPath, color = bodyColor)

  // Tactical chest stripes
  drawLine(
    color = Color(0xFF52526E),
    start = Offset(centerX - 24.dp.toPx(), bodyCenterY - 10.dp.toPx()),
    end = Offset(centerX + 24.dp.toPx(), bodyCenterY - 10.dp.toPx()),
    strokeWidth = 3f
  )
  drawLine(
    color = Color(0xFF52526E),
    start = Offset(centerX - 18.dp.toPx(), bodyCenterY + 6.dp.toPx()),
    end = Offset(centerX + 18.dp.toPx(), bodyCenterY + 6.dp.toPx()),
    strokeWidth = 2.5f
  )

  // Highlight Box / Brackets according to Selected Hitbox Area
  val targetY = when (selectedArea) {
    TargetArea.HEAD -> headCenterY
    TargetArea.NECK -> neckCenterY
    TargetArea.BODY -> bodyCenterY
  }

  val targetBoxWidth = when (selectedArea) {
    TargetArea.HEAD -> 54.dp.toPx()
    TargetArea.NECK -> 58.dp.toPx()
    TargetArea.BODY -> 84.dp.toPx()
  }
  val targetBoxHeight = when (selectedArea) {
    TargetArea.HEAD -> 48.dp.toPx()
    TargetArea.NECK -> 36.dp.toPx()
    TargetArea.BODY -> 68.dp.toPx()
  }

  val boxLeft = centerX - targetBoxWidth / 2f
  val boxTop = targetY - targetBoxHeight / 2f
  val cornerLen = 10.dp.toPx()
  val bracketColor = Color(0xFFFF2A36).copy(alpha = pulseAlpha)

  // Glowing fill
  drawRect(
    color = Color(0xFFFF2A36).copy(alpha = 0.15f * pulseAlpha),
    topLeft = Offset(boxLeft, boxTop),
    size = Size(targetBoxWidth, targetBoxHeight)
  )

  // 4 Targeting Corner Brackets
  // Top-Left
  drawLine(bracketColor, Offset(boxLeft, boxTop), Offset(boxLeft + cornerLen, boxTop), strokeWidth = 3f)
  drawLine(bracketColor, Offset(boxLeft, boxTop), Offset(boxLeft, boxTop + cornerLen), strokeWidth = 3f)

  // Top-Right
  val boxRight = boxLeft + targetBoxWidth
  drawLine(bracketColor, Offset(boxRight, boxTop), Offset(boxRight - cornerLen, boxTop), strokeWidth = 3f)
  drawLine(bracketColor, Offset(boxRight, boxTop), Offset(boxRight, boxTop + cornerLen), strokeWidth = 3f)

  // Bottom-Left
  val boxBottom = boxTop + targetBoxHeight
  drawLine(bracketColor, Offset(boxLeft, boxBottom), Offset(boxLeft + cornerLen, boxBottom), strokeWidth = 3f)
  drawLine(bracketColor, Offset(boxLeft, boxBottom), Offset(boxLeft, boxBottom - cornerLen), strokeWidth = 3f)

  // Bottom-Right
  drawLine(bracketColor, Offset(boxRight, boxBottom), Offset(boxRight - cornerLen, boxBottom), strokeWidth = 3f)
  drawLine(bracketColor, Offset(boxRight, boxBottom), Offset(boxRight, boxBottom - cornerLen), strokeWidth = 3f)

  // Small center crosshair mark
  drawLine(bracketColor, Offset(centerX - 6.dp.toPx(), targetY), Offset(centerX + 6.dp.toPx(), targetY), strokeWidth = 2f)
  drawLine(bracketColor, Offset(centerX, targetY - 6.dp.toPx()), Offset(centerX, targetY + 6.dp.toPx()), strokeWidth = 2f)
}
