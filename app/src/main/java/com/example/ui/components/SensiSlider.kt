package com.example.ui.components

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensiSlider(
  title: String,
  subtitle: String? = null,
  value: Int,
  range: ClosedFloatingPointRange<Float> = 0f..200f,
  onValueChange: (Int) -> Unit,
  iconEmoji: String = "🎯",
  tag: String = "sensi_slider"
) {
  val colors = SensiTheme.colors

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp)
  ) {
    // Header Row: Icon + Title + Value Badge
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(30.dp)
            .clip(CircleShape)
            .background(colors.surfaceHighlight)
            .border(1.dp, colors.border, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text(text = iconEmoji, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column {
          Text(
            text = title,
            color = colors.textPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
          )
          if (subtitle != null) {
            Text(
              text = subtitle,
              color = colors.textMuted,
              fontSize = 11.sp
            )
          }
        }
      }

      // Red Gaming Value Badge
      Box(
        modifier = Modifier
          .clip(RoundedCornerShape(12.dp))
          .background(
            Brush.horizontalGradient(
              colors = listOf(SensiRed, SensiRedDark)
            )
          )
          .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = value.toString(),
          color = Color.White,
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    // Slider Row with - and + Step Buttons
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Minus button
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(colors.surfaceHighlight)
          .border(1.dp, colors.border, RoundedCornerShape(8.dp))
          .clickable {
            if (value > range.start.toInt()) {
              onValueChange(value - 1)
            }
          },
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Remove,
          contentDescription = "Diminuir $title",
          tint = colors.textPrimary,
          modifier = Modifier.size(16.dp)
        )
      }

      // Compose Slider
      Slider(
        value = value.toFloat(),
        onValueChange = { onValueChange(it.toInt()) },
        valueRange = range,
        modifier = Modifier
          .weight(1f)
          .padding(horizontal = 8.dp)
          .testTag(tag),
        colors = SliderDefaults.colors(
          thumbColor = if (colors.isWhite) SensiRed else Color.White,
          activeTrackColor = SensiRed,
          inactiveTrackColor = colors.surfaceHighlight,
          activeTickColor = Color.Transparent,
          inactiveTickColor = Color.Transparent
        )
      )

      // Plus button
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(colors.surfaceHighlight)
          .border(1.dp, colors.border, RoundedCornerShape(8.dp))
          .clickable {
            if (value < range.endInclusive.toInt()) {
              onValueChange(value + 1)
            }
          },
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Add,
          contentDescription = "Aumentar $title",
          tint = colors.textPrimary,
          modifier = Modifier.size(16.dp)
        )
      }
    }
  }
}

