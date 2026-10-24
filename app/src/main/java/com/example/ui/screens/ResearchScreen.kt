package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Company
import com.example.model.ResearchTech
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ResearchScreen(
  company: Company,
  techTree: List<ResearchTech>,
  onUnlockTech: (String) -> Unit
) {
  val currency = NumberFormat.getCurrencyInstance(Locale.US)

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
  ) {
    // Header Info Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = TechSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Column {
            Text(
              text = "AR-GE Laboratuvarı",
              fontSize = 20.sp,
              fontWeight = FontWeight.Black,
              color = TextPrimary
            )
            Text(
              text = "Mevcut Yıl: ${company.year} • Yıllara göre yeni teknolojileri açın",
              fontSize = 12.sp,
              color = CyanNeon
            )
          }

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = ElectricBlue.copy(alpha = 0.2f),
            border = androidx.compose.foundation.BorderStroke(1.dp, ElectricBlue)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Science, contentDescription = null, tint = ElectricBlue, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "${company.researchPoints} RP",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = ElectricBlue
              )
            }
          }
        }
      }
    }

    // Technology List
    items(techTree, key = { it.id }) { tech ->
      val isYearReady = company.year >= tech.requiredYear
      val canAfford = company.balance >= tech.cashCost && company.researchPoints >= tech.rpCost && isYearReady

      TechItemCard(
        tech = tech,
        currentYear = company.year,
        isYearReady = isYearReady,
        canAfford = canAfford,
        onUnlock = { onUnlockTech(tech.id) }
      )
    }
  }
}

@Composable
private fun TechItemCard(
  tech: ResearchTech,
  currentYear: Int,
  isYearReady: Boolean,
  canAfford: Boolean,
  onUnlock: () -> Unit
) {
  val currency = NumberFormat.getCurrencyInstance(Locale.US)

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (tech.isUnlocked) TechSurfaceCard else TechSurface
    ),
    border = androidx.compose.foundation.BorderStroke(
      width = 1.dp,
      color = if (tech.isUnlocked) SuccessGreen.copy(alpha = 0.4f) else OutlineDark
    )
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = when (tech.category) {
              "İşlemci" -> ElectricBlue.copy(alpha = 0.2f)
              "Ekran" -> CyanNeon.copy(alpha = 0.2f)
              "Kamera" -> PurpleCyber.copy(alpha = 0.2f)
              else -> AmberGold.copy(alpha = 0.2f)
            }
          ) {
            Text(
              text = tech.category,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = when (tech.category) {
                "İşlemci" -> ElectricBlue
                "Ekran" -> CyanNeon
                "Kamera" -> PurpleCyber
                else -> AmberGold
              }
            )
          }

          Spacer(modifier = Modifier.width(8.dp))

          Text(
            text = tech.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }

        if (tech.isUnlocked) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Açık", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
          }
        } else if (!isYearReady) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = CoralNeon.copy(alpha = 0.15f)
          ) {
            Text(
              text = "🔒 ${tech.requiredYear} Yılı",
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = CoralNeon
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = tech.description,
        fontSize = 12.sp,
        color = TextSecondary,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = "Kilit Açılan: ${tech.unlocksDescription} (Dönem: ${tech.requiredYear}+)",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = CyanNeon
      )

      if (!tech.isUnlocked) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "${currency.format(tech.cashCost).replace(".00", "")} + ${tech.rpCost} RP",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = AmberGold
            )
          }

          Button(
            onClick = onUnlock,
            enabled = canAfford,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = ElectricBlue,
              disabledContainerColor = TechSurfaceVariant
            )
          ) {
            Icon(Icons.Default.Science, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (!isYearReady) "${tech.requiredYear} Bekleniyor" else "Araştır & Aç",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }
    }
  }
}
