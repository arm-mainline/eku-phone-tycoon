package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.TrendingUp
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
import com.example.model.Competitor
import com.example.model.PhoneModel
import com.example.ui.theme.*

@Composable
fun MarketScreen(
  company: Company,
  competitors: List<Competitor>,
  activePhones: List<PhoneModel>
) {
  val totalSales = company.totalUnitsSold
  val ekuShare = (totalSales / 15_000f).coerceIn(2.0f, 40.0f)

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
  ) {
    // Header
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = TechSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Küresel Pazar Analizi",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary
              )
              Text(
                text = "Dünya akıllı telefon pazarındaki rekabet ve paylar",
                fontSize = 12.sp,
                color = TextSecondary
              )
            }

            Surface(
              shape = RoundedCornerShape(12.dp),
              color = CyanNeon.copy(alpha = 0.2f),
              border = androidx.compose.foundation.BorderStroke(1.dp, CyanNeon)
            ) {
              Text(
                text = "Payınız: %${String.format("%.1f", ekuShare)}",
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = CyanNeon
              )
            }
          }
        }
      }
    }

    // Market Share Visual Bars
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = TechSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "Pazar Payı Dağılımı",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(14.dp))

          // Eku Mobile Row
          MarketShareRow(
            brandName = "${company.name} (Siz)",
            country = "Türkiye",
            share = ekuShare,
            color = CyanNeon,
            isUser = true
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Competitors
          competitors.forEach { comp ->
            MarketShareRow(
              brandName = comp.name,
              country = comp.country,
              share = comp.marketSharePercent,
              color = comp.brandColor,
              isUser = false
            )
            Spacer(modifier = Modifier.height(10.dp))
          }
        }
      }
    }

    // Competitor Flagships
    item {
      Text(
        text = "Piyasadaki Rakip Amiral Gemileri",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
    }

    items(competitors.take(4)) { comp ->
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TechSurfaceCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(comp.brandColor)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = comp.currentFlagship,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = "${comp.name} • ${comp.country}",
                fontSize = 11.sp,
                color = TextSecondary
              )
            }
          }

          Column(horizontalAlignment = Alignment.End) {
            Text(
              text = "${comp.flagshipPrice} $",
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              color = AmberGold
            )
            Text(
              text = "Güç: ${comp.flagshipScore}/100",
              fontSize = 11.sp,
              color = TextMuted
            )
          }
        }
      }
    }
  }
}

@Composable
private fun MarketShareRow(
  brandName: String,
  country: String,
  share: Float,
  color: Color,
  isUser: Boolean
) {
  Column {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = brandName,
          fontSize = 13.sp,
          fontWeight = if (isUser) FontWeight.ExtraBold else FontWeight.SemiBold,
          color = if (isUser) CyanNeon else TextPrimary
        )
        Text(
          text = " ($country)",
          fontSize = 10.sp,
          color = TextMuted
        )
      }
      Text(
        text = "%${String.format("%.1f", share)}",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = if (isUser) CyanNeon else TextSecondary
      )
    }
    Spacer(modifier = Modifier.height(4.dp))
    LinearProgressIndicator(
      progress = { (share / 100.0f).coerceIn(0f, 1f) },
      modifier = Modifier
        .fillMaxWidth()
        .height(6.dp)
        .clip(CircleShape),
      color = color,
      trackColor = TechSurfaceVariant
    )
  }
}
