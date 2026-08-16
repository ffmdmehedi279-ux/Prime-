package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiTheme
import com.example.ui.theme.TextSuccess

@Composable
fun OptimizationPulseCircle(
  isOptimizing: Boolean,
  score: Int,
  onOptimizeClick: () -> Unit
) {
  val colors = SensiTheme.colors

  val infiniteTransition = rememberInfiniteTransition(label = "rotate_pulse")
  val rotation by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 360f,
    animationSpec = infiniteRepeatable(
      animation = tween(2000, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "rotation"
  )

  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Top Badge: Limpeza Rápida
    Box(
      modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .background(colors.surfaceHighlight)
        .border(1.dp, colors.border, RoundedCornerShape(20.dp))
        .padding(horizontal = 16.dp, vertical = 6.dp),
      contentAlignment = Alignment.Center
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.FlashOn,
          contentDescription = null,
          tint = SensiRed,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "Limpeza Rápida & Otimização",
          color = colors.textPrimary,
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Central Pulsing Booster Circle
    Box(
      modifier = Modifier.size(160.dp),
      contentAlignment = Alignment.Center
    ) {
      // Background Outer Ring with glow
      Canvas(modifier = Modifier.size(160.dp)) {
        drawCircle(
          color = SensiRed.copy(alpha = if (isOptimizing) 0.35f else 0.12f),
          radius = size.minDimension / 2f
        )
      }

      // Spinning radar ring when optimizing
      if (isOptimizing) {
        CircularProgressIndicator(
          modifier = Modifier.size(150.dp),
          color = SensiRed,
          strokeWidth = 3.dp,
          trackColor = colors.surfaceHighlight
        )
      } else {
        Canvas(modifier = Modifier.size(150.dp)) {
          drawCircle(
            color = colors.borderLight,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
          )
        }
      }

      // Inner Core Circle with Broom Icon
      Box(
        modifier = Modifier
          .size(120.dp)
          .clip(CircleShape)
          .background(
            Brush.verticalGradient(
              colors = listOf(colors.surfaceHighlight, colors.surfaceElevated)
            )
          )
          .border(
            width = 2.dp,
            color = if (isOptimizing) SensiRed else colors.border,
            shape = CircleShape
          )
          .clickable(enabled = !isOptimizing) { onOptimizeClick() },
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Default.CleaningServices,
            contentDescription = "Otimizar",
            tint = if (isOptimizing) SensiRed else colors.textPrimary,
            modifier = Modifier.size(42.dp)
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "$score%",
            color = if (score >= 90) TextSuccess else SensiRed,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // Big Bold Red Button: OTIMIZAR / OPTIMIZE
    Button(
      onClick = onOptimizeClick,
      enabled = !isOptimizing,
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .shadow(8.dp, RoundedCornerShape(14.dp))
        .testTag("optimize_main_button"),
      shape = RoundedCornerShape(14.dp),
      colors = ButtonDefaults.buttonColors(
        containerColor = SensiRed,
        contentColor = Color.White,
        disabledContainerColor = colors.surfaceHighlight,
        disabledContentColor = colors.textMuted
      )
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        if (isOptimizing) {
          CircularProgressIndicator(
            modifier = Modifier.size(20.dp),
            color = Color.White,
            strokeWidth = 2.5.dp
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "OTIMIZANDO SISTEMA...",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
        } else {
          Icon(
            imageVector = Icons.Default.AutoFixHigh,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "OTIMIZAR AGORA",
            fontSize = 16.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.2.sp
          )
        }
      }
    }
  }
}

