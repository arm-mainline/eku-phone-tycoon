package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Company
import com.example.model.NewsItem
import com.example.model.PhoneModel
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DashboardScreen(
  company: Company,
  activePhones: List<PhoneModel>,
  newsFeed: List<NewsItem>,
  onAdvanceWeeks: (Int) -> Unit,
  onNavigateToStudio: () -> Unit,
  onDiscontinuePhone: (String) -> Unit,
  onUpgradeOffice: () -> Unit,
  onSaveGame: () -> Unit,
  onResetGame: () -> Unit
) {
  val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
  var showResetConfirm by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp)
  ) {
    // Top Hero Stats Card
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = TechSurface),
        border = CardDefaults.outlinedCardBorder().copy(
          brush = Brush.horizontalGradient(listOf(CyanNeon.copy(alpha = 0.6f), ElectricBlue.copy(alpha = 0.6f)))
        )
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = company.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = TextPrimary
              )
              Text(
                text = "${company.officeTitle} • Seviye ${company.officeLevel}",
                fontSize = 12.sp,
                color = CyanNeon,
                fontWeight = FontWeight.SemiBold
              )
            }

            // Year and Week Pill
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = TechSurfaceVariant,
              border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold)
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = AmberGold, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                  text = "${company.year} • Hafta ${company.week}/52",
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Bold,
                  color = AmberGold
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Year progress indicator
          LinearProgressIndicator(
            progress = { (company.week / 52.0f).coerceIn(0f, 1f) },
            modifier = Modifier
              .fillMaxWidth()
              .height(4.dp)
              .clip(CircleShape),
            color = AmberGold,
            trackColor = TechSurfaceVariant
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Balance & Research Points Banner
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            // Cash box
            Surface(
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(16.dp),
              color = TechSurfaceCard,
              border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.AccountBalanceWallet,
                    contentDescription = null,
                    tint = SuccessGreen,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(text = "Kasa", fontSize = 11.sp, color = TextMuted)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = currencyFormat.format(company.balance).replace(".00", ""),
                  fontSize = 17.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = SuccessGreen
                )
              }
            }

            // RP box
            Surface(
              modifier = Modifier.weight(1f),
              shape = RoundedCornerShape(16.dp),
              color = TechSurfaceCard,
              border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Science,
                    contentDescription = null,
                    tint = ElectricBlue,
                    modifier = Modifier.size(16.dp)
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(text = "AR-GE Puanı", fontSize = 11.sp, color = TextMuted)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "${company.researchPoints} RP",
                  fontSize = 17.sp,
                  fontWeight = FontWeight.ExtraBold,
                  color = ElectricBlue
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Reputation and Save Status Bar
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Star, contentDescription = null, tint = AmberGold, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "İtibar: ${company.reputation}/1000",
                fontSize = 12.sp,
                color = TextSecondary
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Save, contentDescription = null, tint = SuccessGreen, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Otomatik Kayıt Aktif",
                fontSize = 11.sp,
                color = SuccessGreen
              )
            }
          }
        }
      }
    }

    // Time Advance Controls
    item {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Button(
            onClick = { onAdvanceWeeks(1) },
            modifier = Modifier
              .weight(1f)
              .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = CyanNeon, contentColor = CyanNeonDark)
          ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "+1 Hafta", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }

          Button(
            onClick = { onAdvanceWeeks(4) },
            modifier = Modifier
              .weight(1.1f)
              .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = Color.White)
          ) {
            Icon(Icons.Default.FastForward, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "+4 Hafta (1 Ay)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }

          OutlinedButton(
            onClick = onNavigateToStudio,
            modifier = Modifier
              .weight(1.1f)
              .height(50.dp),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, AmberGold),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = AmberGold)
          ) {
            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Tasarla", fontWeight = FontWeight.Bold, fontSize = 13.sp)
          }
        }

        // Save & Reset Row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextButton(onClick = onSaveGame) {
            Icon(Icons.Default.Save, contentDescription = null, tint = CyanNeon, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Manuel Kaydet", color = CyanNeon, fontSize = 12.sp)
          }

          TextButton(onClick = { showResetConfirm = true }) {
            Icon(Icons.Default.Refresh, contentDescription = null, tint = TextMuted, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Sıfırla (2020)", color = TextMuted, fontSize = 11.sp)
          }
        }
      }
    }

    // Active Phones Section Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Aktif Satıştaki Telefonlar (${activePhones.size}/${company.maxActivePhones})",
          fontSize = 16.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        if (company.officeLevel < 5) {
          TextButton(onClick = onUpgradeOffice) {
            Icon(Icons.Default.Upgrade, contentDescription = null, tint = AmberGold, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "Ofisi Büyüt", color = AmberGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
          }
        }
      }
    }

    // Active Phones List
    if (activePhones.isEmpty()) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = TechSurfaceVariant.copy(alpha = 0.5f))
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.Smartphone,
              contentDescription = null,
              tint = TextMuted,
              modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "Şu anda satılan telefonunuz yok!",
              color = TextPrimary,
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "${company.year} yılı teknolojileriyle işlemci, ekran paneli ve çözünürlük seçerek telefon tasarlayın.",
              color = TextSecondary,
              fontSize = 12.sp,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = onNavigateToStudio,
              shape = RoundedCornerShape(14.dp),
              colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
            ) {
              Text("Stüdyoda Yeni Model Tasarla")
            }
          }
        }
      }
    } else {
      items(activePhones, key = { it.id }) { phone ->
        ActivePhoneCard(
          phone = phone,
          onDiscontinue = { onDiscontinuePhone(phone.id) }
        )
      }
    }

    // News Feed Section Header
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = "Sektör ve Basın Haberleri",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
    }

    items(newsFeed.take(4)) { news ->
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TechSurfaceCard),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.verticalGradient(listOf(OutlineDark, Color.Transparent)))
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.Top
        ) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(if (news.isPositive) CyanNeon.copy(alpha = 0.15f) else CoralNeon.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = if (news.isPositive) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
              contentDescription = null,
              tint = if (news.isPositive) CyanNeon else CoralNeon,
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column(modifier = Modifier.weight(1f)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = news.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
              )
              Text(
                text = "${news.year} H${news.week}",
                fontSize = 10.sp,
                color = TextMuted
              )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = news.body,
              fontSize = 12.sp,
              color = TextSecondary,
              lineHeight = 16.sp
            )
          }
        }
      }
    }
  }

  // Reset confirmation dialog
  if (showResetConfirm) {
    AlertDialog(
      onDismissRequest = { showResetConfirm = false },
      title = { Text("Oyunu Sıfırla?") },
      text = { Text("Tüm ilerlemeniz silinecek ve 2020 yılından sıfır bütçeyle yeniden başlanacaktır. Onaylıyor musunuz?") },
      confirmButton = {
        Button(
          onClick = {
            showResetConfirm = false
            onResetGame()
          },
          colors = ButtonDefaults.buttonColors(containerColor = CoralNeon)
        ) {
          Text("Sıfırla")
        }
      },
      dismissButton = {
        TextButton(onClick = { showResetConfirm = false }) {
          Text("Vazgeç")
        }
      }
    )
  }
}

@Composable
private fun ActivePhoneCard(
  phone: PhoneModel,
  onDiscontinue: () -> Unit
) {
  val currency = NumberFormat.getCurrencyInstance(Locale.US)

  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
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
              .size(16.dp)
              .clip(CircleShape)
              .background(phone.color.hexColor)
              .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = phone.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = AmberGold.copy(alpha = 0.2f),
          border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold)
        ) {
          Text(
            text = "★ ${phone.criticScore}/10",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = AmberGold
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Specs summary row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        ChipBadge(text = phone.processor.name, color = phone.processor.brand.badgeColor)
        ChipBadge(text = "${phone.displayPanel.name} (${phone.displayResolution.name})", color = CyanNeon)
        ChipBadge(text = "${phone.refreshRate.hz}Hz", color = ElectricBlue)
        ChipBadge(text = "${phone.retailPrice} $", color = AmberGold)
      }

      Spacer(modifier = Modifier.height(12.dp))
      HorizontalDivider(color = OutlineVariantDark, thickness = 0.8.dp)
      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text(text = "Haftalık Satış", fontSize = 11.sp, color = TextMuted)
          Text(
            text = "${NumberFormat.getNumberInstance().format(phone.weeklySoldUnits)} adet",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = CyanNeon
          )
        }

        Column {
          Text(text = "Toplam Satış", fontSize = 11.sp, color = TextMuted)
          Text(
            text = "${NumberFormat.getNumberInstance().format(phone.totalSoldUnits)} adet",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }

        Column {
          Text(text = "Toplam Kar", fontSize = 11.sp, color = TextMuted)
          Text(
            text = currency.format(phone.totalProfit).replace(".00", ""),
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = SuccessGreen
          )
        }

        IconButton(
          onClick = onDiscontinue,
          modifier = Modifier.size(32.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Cancel,
            contentDescription = "Satıştan Kaldır",
            tint = TextMuted,
            modifier = Modifier.size(20.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))
      val progress = (phone.weeksOnMarket / 18.0f).coerceIn(0f, 1f)
      LinearProgressIndicator(
        progress = { 1.0f - progress },
        modifier = Modifier
          .fillMaxWidth()
          .height(4.dp)
          .clip(CircleShape),
        color = if (progress < 0.7f) CyanNeon else AmberGold,
        trackColor = TechSurfaceVariant
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "Pazardaki Süre: ${phone.weeksOnMarket}/18 Hafta",
        fontSize = 10.sp,
        color = TextMuted
      )
    }
  }
}

@Composable
private fun ChipBadge(text: String, color: Color) {
  Surface(
    shape = RoundedCornerShape(6.dp),
    color = color.copy(alpha = 0.12f),
    border = androidx.compose.foundation.BorderStroke(0.6.dp, color.copy(alpha = 0.5f))
  ) {
    Text(
      text = text,
      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      color = color
    )
  }
}
