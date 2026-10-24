package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberGold
import com.example.ui.theme.CyanNeon
import com.example.viewmodel.StudioDraft

@Composable
fun PhonePreview(
  draft: StudioDraft,
  modifier: Modifier = Modifier
) {
  var showBackSide by remember { mutableStateOf(true) }

  val animatedColor by animateColorAsState(
    targetValue = draft.color.hexColor,
    label = "phoneColor"
  )

  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Phone container (19.5:9 proportion mock-up)
    Box(
      modifier = Modifier
        .width(180.dp)
        .height(350.dp)
        .shadow(16.dp, RoundedCornerShape(32.dp))
        .clip(RoundedCornerShape(32.dp))
        .background(
          brush = Brush.verticalGradient(
            colors = listOf(
              animatedColor,
              draft.color.secondaryColor
            )
          )
        )
        .border(
          width = 3.dp,
          brush = Brush.linearGradient(
            listOf(
              Color.White.copy(alpha = 0.45f),
              Color.Black.copy(alpha = 0.6f),
              Color.White.copy(alpha = 0.2f)
            )
          ),
          shape = RoundedCornerShape(32.dp)
        )
        .clickable { showBackSide = !showBackSide }
    ) {
      if (showBackSide) {
        // Back Chassis Design
        PhoneBackView(draft = draft)
      } else {
        // Front Display Design
        PhoneFrontView(draft = draft)
      }

      // Small toggle overlay button in bottom right
      Surface(
        modifier = Modifier
          .align(Alignment.BottomEnd)
          .padding(8.dp)
          .size(28.dp),
        shape = CircleShape,
        color = Color.Black.copy(alpha = 0.7f)
      ) {
        Icon(
          imageVector = Icons.Default.Cameraswitch,
          contentDescription = "Ön/Arka Görünüm",
          tint = CyanNeon,
          modifier = Modifier.padding(5.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Interactive switch indicator & SoC pill
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = draft.processor.brand.badgeColor.copy(alpha = 0.2f),
        border = androidx.compose.foundation.BorderStroke(1.dp, draft.processor.brand.badgeColor)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Memory,
            contentDescription = null,
            tint = draft.processor.brand.badgeColor,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = draft.processor.name,
            color = draft.processor.brand.badgeColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }

      Text(
        text = if (showBackSide) "• Arka Kapak (Dokun: Ekran)" else "• Ön Ekran (Dokun: Arka)",
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 11.sp
      )
    }
  }
}

@Composable
private fun BoxScope.PhoneBackView(draft: StudioDraft) {
  val materialOverlayAlpha = when (draft.material.id) {
    "mat_glass" -> 0.15f
    "mat_vegan_leather" -> 0.25f
    "mat_titanium" -> 0.18f
    else -> 0.05f
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White.copy(alpha = materialOverlayAlpha))
  )

  // Camera Island at Top Left
  Surface(
    modifier = Modifier
      .padding(top = 18.dp, start = 16.dp)
      .align(Alignment.TopStart),
    shape = RoundedCornerShape(18.dp),
    color = Color(0xFF10141E).copy(alpha = 0.85f),
    border = androidx.compose.foundation.BorderStroke(
      1.5.dp,
      draft.color.hexColor.copy(alpha = 0.5f)
    ),
    shadowElevation = 6.dp
  ) {
    Column(
      modifier = Modifier.padding(7.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
      CameraLens(size = 28.dp, ringColor = draft.color.hexColor)

      if (draft.camera.lensCount >= 2) {
        CameraLens(size = 26.dp, ringColor = Color.DarkGray)
      }

      if (draft.camera.lensCount >= 3) {
        if (draft.camera.hasPeriscope) {
          Box(
            modifier = Modifier
              .size(24.dp)
              .clip(RoundedCornerShape(6.dp))
              .background(Color(0xFF070B14))
              .border(1.dp, AmberGold, RoundedCornerShape(6.dp)),
            contentAlignment = Alignment.Center
          ) {
            Box(
              modifier = Modifier
                .size(14.dp, 10.dp)
                .background(Color(0xFF1E293B))
            )
          }
        } else {
          CameraLens(size = 22.dp, ringColor = Color.Gray)
        }
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Icon(
          imageVector = Icons.Default.FlashOn,
          contentDescription = null,
          tint = AmberGold,
          modifier = Modifier.size(10.dp)
        )
        Box(
          modifier = Modifier
            .size(4.dp)
            .clip(CircleShape)
            .background(Color.Red.copy(alpha = 0.7f))
        )
      }
    }
  }

  // Company Brand Logo on Back
  Column(
    modifier = Modifier
      .align(Alignment.BottomCenter)
      .padding(bottom = 24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "EKU",
      color = Color.White.copy(alpha = 0.85f),
      fontWeight = FontWeight.Black,
      letterSpacing = 4.sp,
      fontSize = 14.sp
    )
    Text(
      text = draft.material.name,
      color = Color.White.copy(alpha = 0.45f),
      fontSize = 8.sp,
      fontWeight = FontWeight.Medium
    )
  }
}

@Composable
private fun BoxScope.PhoneFrontView(draft: StudioDraft) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(5.dp)
      .clip(RoundedCornerShape(26.dp))
      .background(
        brush = Brush.linearGradient(
          colors = listOf(
            Color(0xFF0F172A),
            Color(0xFF1E1B4B),
            Color(0xFF0284C7).copy(alpha = 0.6f)
          )
        )
      )
  ) {
    Box(
      modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(top = 8.dp)
        .size(9.dp)
        .clip(CircleShape)
        .background(Color.Black)
        .border(0.5.dp, Color.DarkGray, CircleShape)
    )

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(top = 28.dp, start = 10.dp, end = 10.dp, bottom = 12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
          text = "10:42",
          color = Color.White,
          fontSize = 24.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "EkuOS 2.0",
          color = Color.White.copy(alpha = 0.7f),
          fontSize = 9.sp
        )
      }

      Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.Black.copy(alpha = 0.45f)
      ) {
        Column(
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "${draft.displayPanel.name} • ${draft.displayResolution.name}",
            color = CyanNeon,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "${draft.refreshRate.hz}Hz • ${draft.ram.sizeGb}GB RAM",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 8.sp
          )
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
      ) {
        repeat(4) {
          Box(
            modifier = Modifier
              .size(18.dp)
              .clip(RoundedCornerShape(5.dp))
              .background(Color.White.copy(alpha = 0.25f))
          )
        }
      }
    }
  }
}

@Composable
private fun CameraLens(size: androidx.compose.ui.unit.Dp, ringColor: Color) {
  Box(
    modifier = Modifier
      .size(size)
      .clip(CircleShape)
      .background(Color(0xFF05070D))
      .border(1.5.dp, ringColor.copy(alpha = 0.6f), CircleShape),
    contentAlignment = Alignment.Center
  ) {
    Box(
      modifier = Modifier
        .size(size * 0.55f)
        .clip(CircleShape)
        .background(Color(0xFF0F2338))
        .border(0.5.dp, Color(0xFF00B4D8).copy(alpha = 0.5f), CircleShape)
    )
  }
}
