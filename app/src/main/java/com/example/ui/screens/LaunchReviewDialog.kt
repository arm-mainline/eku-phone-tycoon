package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.PhoneModel
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun LaunchReviewDialog(
  phone: PhoneModel,
  onDismiss: () -> Unit
) {
  val currency = NumberFormat.getCurrencyInstance(Locale.US)

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .fillMaxHeight(0.85f),
      shape = RoundedCornerShape(28.dp),
      color = TechDarkBg,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, CyanNeon)
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp)
      ) {
        // Header
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "🎉 TEBRİKLER!",
            color = AmberGold,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "${phone.name} Küresel Pazara Çıktı!",
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
          )
          Spacer(modifier = Modifier.height(10.dp))

          // Big Score Badge
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = TechSurfaceCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, AmberGold)
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Star, contentDescription = null, tint = AmberGold, modifier = Modifier.size(24.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "${phone.criticScore} / 10",
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                color = AmberGold
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Medya Puanı",
                fontSize = 12.sp,
                color = TextSecondary
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // First Week Burst Sales Box
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = TechSurfaceVariant)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceAround
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "İlk Hafta Satış", fontSize = 11.sp, color = TextMuted)
              Text(
                text = "${NumberFormat.getNumberInstance().format(phone.weeklySoldUnits)} adet",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = CyanNeon
              )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "İlk Hafta Gelir", fontSize = 11.sp, color = TextMuted)
              Text(
                text = currency.format(phone.totalRevenue).replace(".00", ""),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = SuccessGreen
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))
        Text(
          text = "Teknoloji Medyası İncelemeleri",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = TextPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Reviews List
        LazyColumn(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(phone.reviews) { review ->
            Card(
              modifier = Modifier.fillMaxWidth(),
              shape = RoundedCornerShape(14.dp),
              colors = CardDefaults.cardColors(containerColor = TechSurfaceCard),
              border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
            ) {
              Column(modifier = Modifier.padding(12.dp)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                      text = review.mediaOutlet,
                      fontWeight = FontWeight.Bold,
                      fontSize = 13.sp,
                      color = ElectricBlue
                    )
                    Text(
                      text = " (${review.reviewerName})",
                      fontSize = 10.sp,
                      color = TextMuted
                    )
                  }

                  Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = AmberGold.copy(alpha = 0.2f)
                  ) {
                    Text(
                      text = "★ ${review.score}/10",
                      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                      fontSize = 11.sp,
                      fontWeight = FontWeight.Bold,
                      color = AmberGold
                    )
                  }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "\"${review.headline}\"",
                  fontWeight = FontWeight.SemiBold,
                  fontSize = 12.sp,
                  color = TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = review.comment,
                  fontSize = 11.sp,
                  color = TextSecondary,
                  lineHeight = 15.sp
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
          onClick = onDismiss,
          modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
          shape = RoundedCornerShape(14.dp),
          colors = ButtonDefaults.buttonColors(containerColor = CyanNeon, contentColor = CyanNeonDark)
        ) {
          Text(text = "Ofise Dön ve Satışları Yönet", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
      }
    }
  }
}
