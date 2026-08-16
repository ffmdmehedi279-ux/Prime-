package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.SensiRed
import com.example.ui.theme.SensiRedDark
import com.example.ui.theme.SensiRedGlow
import com.example.ui.theme.SensiTheme
import com.example.ui.theme.TextSuccess

data class FaqItem(
  val question: String,
  val answer: String
)

val FAQ_LIST = listOf(
  FaqItem(
    question = "Como aplicar a sensibilidade no Free Fire?",
    answer = "Abra o Free Fire, vá nas configurações no canto superior direito (ícone de engrenagem), selecione a aba 'Sensibilidade' e insira exatamente os valores recomendados aqui (Geral, Ponto Vermelho, 2X, 4X, Sniper, Olhadinha)."
  ),
  FaqItem(
    question = "O White FF Sensi Bot dá ban?",
    answer = "Não! 100% Anti-Ban e seguro. O aplicativo não altera arquivos APK, não injeta memória e não interage ilegalmente com o jogo. Ele fornece parâmetros ideais de cálculo para inserção manual."
  ),
  FaqItem(
    question = "O que é DPI e como melhora a puxada de capa?",
    answer = "DPI (Densidade de Pixels) ajustada nas Opções do Desenvolvedor do Android reduz o atraso de toque e proporciona uma rolagem ultra veloz na tela para facilitar subidas verticais instantâneas."
  ),
  FaqItem(
    question = "Por que a mira às vezes passa da cabeça?",
    answer = "Se a mira estiver passando para o ar, diminua o valor da sensibilidade Geral e Red Dot em 3 a 5 pontos, ou aumente levemente o tamanho do botão de tiro para 52% a 56%."
  ),
  FaqItem(
    question = "Qual o tamanho ideal do botão de disparo?",
    answer = "O tamanho recomendado varia entre 45% e 55%, posicionado na parte inferior para garantir espaço livre vertical na puxada."
  )
)

@Composable
fun SupportScreen() {
  val context = LocalContext.current
  val colors = SensiTheme.colors
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(colors.bg)
      .verticalScroll(scrollState)
      .padding(16.dp)
  ) {
    // Developer Credit Card
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(colors.surfaceElevated)
        .border(1.5.dp, SensiRed.copy(alpha = 0.7f), RoundedCornerShape(18.dp))
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(CircleShape)
              .background(SensiRedGlow)
              .border(1.5.dp, SensiRed, CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = SensiRed,
              modifier = Modifier.size(24.dp)
            )
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "Prime Mehedi Moder",
                color = colors.textPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black
              )
              Spacer(modifier = Modifier.width(4.dp))
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = "Verified",
                tint = SensiRed,
                modifier = Modifier.size(16.dp)
              )
            }
            Text(
              text = "Official Lead Developer & Modder",
              color = colors.textMuted,
              fontSize = 11.sp
            )
          }
        }

        Box(
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SensiRed)
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text(
            text = "PRO DEV",
            color = Color.White,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = "Aplicativo White FF Sensi Bot desenvolvido com máxima precisão por Prime Mehedi Moder para calibrar a sensibilidade dos jogadores no Free Fire.",
        color = colors.textMuted,
        fontSize = 11.sp,
        lineHeight = 15.sp
      )
    }

    Spacer(modifier = Modifier.height(14.dp))

    // Top Safe Security Banner
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(colors.surfaceElevated)
        .border(1.5.dp, TextSuccess.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
        .padding(16.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(TextSuccess.copy(alpha = 0.15f))
            .border(1.dp, TextSuccess, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.VerifiedUser,
            contentDescription = null,
            tint = TextSuccess,
            modifier = Modifier.size(20.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "100% SEGURO & ANTI-BAN",
            color = TextSuccess,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "Conformidade total com as diretrizes do jogo",
            color = colors.textMuted,
            fontSize = 11.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Este aplicativo é um assistente de cálculo e calibração de sensibilidade manual. Não modifica arquivos do jogo, não injeta códigos, não utiliza hacks e não altera o comportamento do Free Fire.",
        color = colors.textPrimary,
        fontSize = 12.sp,
        lineHeight = 16.sp
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Best Practice Shooting & HUD Tips
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(colors.surfaceElevated)
        .border(1.dp, colors.border, RoundedCornerShape(18.dp))
        .padding(16.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.TouchApp,
          contentDescription = null,
          tint = SensiRed,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Dicas de HUD & Puxada de Capa",
          color = colors.textPrimary,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      HudTipRow(
        number = "1",
        title = "Posicionamento do Botão",
        desc = "Deixe o botão de disparo na parte inferior direita com espaço livre acima para arrastar."
      )
      Spacer(modifier = Modifier.height(8.dp))
      HudTipRow(
        number = "2",
        title = "Puxada em Meia-Lua (Jota)",
        desc = "Para inimigos correndo para o lado, arraste o botão de tiro fazendo uma curva suave."
      )
      Spacer(modifier = Modifier.height(8.dp))
      HudTipRow(
        number = "3",
        title = "Ajuste Gradual",
        desc = "Se o tiro grudar muito no peito, aumente a Geral de 3 em 3 pontos até encontrar o encaixe perfeito."
      )
    }

    Spacer(modifier = Modifier.height(20.dp))

    // FAQ Section
    Text(
      text = "Perguntas Frequentes (FAQ)",
      color = colors.textPrimary,
      fontSize = 15.sp,
      fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(10.dp))

    FAQ_LIST.forEach { faq ->
      FaqExpandableCard(faq = faq)
      Spacer(modifier = Modifier.height(8.dp))
    }

    Spacer(modifier = Modifier.height(20.dp))

    // About & Feedback
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(18.dp))
        .background(colors.surfaceElevated)
        .border(1.dp, colors.border, RoundedCornerShape(18.dp))
        .padding(16.dp)
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Info,
          contentDescription = null,
          tint = SensiRed,
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Sobre o White FF Sensi Bot",
          color = colors.textPrimary,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Versão: 3.0.0 (VIP White Edition)\nCriado por Prime Mehedi Moder • Otimizado para todos os modelos de celulares Android.",
        color = colors.textMuted,
        fontSize = 12.sp,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(14.dp))

      Button(
        onClick = {
          Toast.makeText(context, "🌟 Obrigado por usar o White FF Sensi Bot por Prime Mehedi Moder!", Toast.LENGTH_SHORT).show()
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = SensiRed,
          contentColor = Color.White
        )
      ) {
        Icon(imageVector = Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Enviar Feedback / Apoiar Dev", fontSize = 13.sp, fontWeight = FontWeight.Bold)
      }
    }

    Spacer(modifier = Modifier.height(30.dp))
  }
}

@Composable
private fun HudTipRow(number: String, title: String, desc: String) {
  val colors = SensiTheme.colors
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(10.dp))
      .background(colors.surfaceHighlight)
      .padding(10.dp),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
        .background(SensiRedGlow)
        .border(1.dp, SensiRed, CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Text(text = number, color = SensiRed, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.width(10.dp))

    Column {
      Text(text = title, color = colors.textPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = desc, color = colors.textMuted, fontSize = 11.sp, lineHeight = 14.sp)
    }
  }
}

@Composable
private fun FaqExpandableCard(faq: FaqItem) {
  val colors = SensiTheme.colors
  var expanded by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(12.dp))
      .background(colors.surfaceElevated)
      .border(1.dp, colors.border, RoundedCornerShape(12.dp))
      .clickable { expanded = !expanded }
      .padding(14.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = faq.question,
        color = colors.textPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.weight(1f)
      )
      Icon(
        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
        contentDescription = null,
        tint = SensiRed,
        modifier = Modifier.size(20.dp)
      )
    }

    AnimatedVisibility(visible = expanded) {
      Column {
        Spacer(modifier = Modifier.height(8.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colors.borderLight)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = faq.answer,
          color = colors.textMuted,
          fontSize = 12.sp,
          lineHeight = 16.sp
        )
      }
    }
  }
}

