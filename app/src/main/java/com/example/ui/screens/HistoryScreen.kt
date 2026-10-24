package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
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
import com.example.model.PhoneModel
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HistoryScreen(
  company: Company,
  activePhones: List<PhoneModel>,
  archivedPhones: List<PhoneModel>
) {
  val allPhones = (activePhones + archivedPhones).sortedByDescending { it.totalSoldUnits }
  val currency = NumberFormat.getCurrencyInstance(Locale.US)

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
  ) {
    // Header Overview
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = TechSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Text(
            text = "Şirket Mirası ve Üretim Arşivi",
            fontSize = 20.sp,
            fontWeight = FontWeight.Black,
            color = TextPrimary
          )
          Spacer(modifier = Modifier.height(14.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Column {
              Text(text = "Üretilen Model", fontSize = 11.sp, color = TextMuted)
              Text(
                text = "${company.totalPhonesLaunched} Adet",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = ElectricBlue
              )
            }
            Column {
              Text(text = "Toplam Satış", fontSize = 11.sp, color = TextMuted)
              Text(
                text = NumberFormat.getNumberInstance().format(company.totalUnitsSold),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = CyanNeon
              )
            }
            Column {
              Text(text = "Toplam Ciro", fontSize = 11.sp, color = TextMuted)
              Text(
                text = currency.format(company.totalRevenue).replace(".00", ""),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = SuccessGreen
              )
            }
          }
        }
      }
    }

    if (allPhones.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = TechSurfaceCard)
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(Icons.Default.History, contentDescription = null, tint = TextMuted, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Henüz üretilmiş bir telefon bulunmuyor.",
              color = TextSecondary,
              fontSize = 13.sp
            )
          }
        }
      }
    } else {
      item {
        Text(
          text = "Üretilen Tüm Modeller (Satış Sıralı)",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
      }

      items(allPhones) { phone ->
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = TechSurfaceCard),
          border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(phone.color.hexColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = phone.name,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  color = TextPrimary
                )
                if (phone.isDiscontinued) {
                  Spacer(modifier = Modifier.width(6.dp))
                  Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = TechSurfaceVariant
                  ) {
                    Text(
                      text = "Arşiv",
                      modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                      fontSize = 9.sp,
                      color = TextMuted
                    )
                  }
                }
              }

              Surface(
                shape = RoundedCornerShape(8.dp),
                color = AmberGold.copy(alpha = 0.2f)
              ) {
                Text(
                  text = "★ ${phone.criticScore}/10",
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = AmberGold
                )
              }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "${phone.processor.name} • ${phone.ram.sizeGb}GB • ${phone.displayPanel.name} (${phone.displayResolution.name} ${phone.refreshRate.hz}Hz) • ${phone.camera.name}",
              fontSize = 11.sp,
              color = TextSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = "Çıkış: ${phone.releaseYear} H${phone.releaseWeek} • Satılan: ${NumberFormat.getNumberInstance().format(phone.totalSoldUnits)} adet",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = CyanNeon
              )
              Text(
                text = "Toplam Gelir: ${currency.format(phone.totalRevenue).replace(".00", "")}",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = SuccessGreen
              )
            }
          }
        }
      }
    }
  }
}
