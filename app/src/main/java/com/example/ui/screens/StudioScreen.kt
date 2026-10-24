package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.*
import com.example.ui.components.PhonePreview
import com.example.ui.theme.*
import com.example.viewmodel.StudioDraft
import kotlin.math.roundToInt

enum class StudioCategory(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
  PROCESSOR("İşlemci", Icons.Default.Memory),
  MEMORY("RAM & ROM", Icons.Default.Storage),
  DISPLAY("Ekran", Icons.Default.Screenshot),
  CAMERA_BATTERY("Kamera & Pil", Icons.Default.CameraAlt),
  BUILD_OS("Kasa & OS", Icons.Default.Palette),
  PRICING("Lansman", Icons.Default.RocketLaunch)
}

@Composable
fun StudioScreen(
  draft: StudioDraft,
  currentYear: Int,
  isComponentAvailable: (Int, String?) -> Boolean,
  onNameChange: (String) -> Unit,
  onSelectProcessor: (Processor) -> Unit,
  onSelectRam: (RamOption) -> Unit,
  onSelectStorage: (StorageOption) -> Unit,
  onSelectDisplayPanel: (DisplayPanel) -> Unit,
  onSelectDisplayResolution: (DisplayResolution) -> Unit,
  onSelectRefreshRate: (RefreshRateOption) -> Unit,
  onSelectCamera: (CameraSetup) -> Unit,
  onSelectBattery: (BatterySetup) -> Unit,
  onSelectMaterial: (MaterialOption) -> Unit,
  onSelectColor: (PhoneColor) -> Unit,
  onToggleSoftware: (String) -> Unit,
  onUpdatePrice: (Int) -> Unit,
  onSelectMarketing: (MarketingTier) -> Unit,
  onLaunchPhone: () -> Unit
) {
  var activeCategory by remember { mutableStateOf(StudioCategory.PROCESSOR) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(TechDarkBg)
  ) {
    // Top Bar: Model Name editing & Live cost
    Surface(
      color = TechSurface,
      border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 10.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          OutlinedTextField(
            value = draft.name,
            onValueChange = onNameChange,
            label = { Text("Model Adı", fontSize = 11.sp) },
            singleLine = true,
            modifier = Modifier.weight(1f),
            colors = OutlinedTextFieldDefaults.colors(
              focusedTextColor = TextPrimary,
              unfocusedTextColor = TextPrimary,
              focusedBorderColor = CyanNeon,
              unfocusedBorderColor = OutlineDark
            ),
            shape = RoundedCornerShape(12.dp)
          )

          Spacer(modifier = Modifier.width(12.dp))

          Column(horizontalAlignment = Alignment.End) {
            Text(text = "Üretim Maliyeti", fontSize = 10.sp, color = TextMuted)
            Text(
              text = "${draft.unitCost} $",
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = CoralNeon
            )
            Text(
              text = "Puan: ${draft.benchmark.overall}/100",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = AmberGold
            )
          }
        }
      }
    }

    // Category Tabs (Horizontal Scroll)
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .background(TechSurfaceCard)
        .horizontalScroll(rememberScrollState())
        .padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      StudioCategory.values().forEach { cat ->
        val isSelected = activeCategory == cat
        FilterChip(
          selected = isSelected,
          onClick = { activeCategory = cat },
          label = {
            Text(
              text = cat.title,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              fontSize = 12.sp
            )
          },
          leadingIcon = {
            Icon(
              imageVector = cat.icon,
              contentDescription = null,
              modifier = Modifier.size(16.dp)
            )
          },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = CyanNeon,
            selectedLabelColor = CyanNeonDark,
            selectedLeadingIconColor = CyanNeonDark,
            containerColor = TechSurfaceVariant,
            labelColor = TextSecondary,
            iconColor = TextSecondary
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = if (isSelected) CyanNeon else OutlineDark
          )
        )
      }
    }

    // Main Content Area
    LazyColumn(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(top = 16.dp, bottom = 96.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Dynamic Visual Mockup Center
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = TechSurfaceCard),
          border = androidx.compose.foundation.BorderStroke(1.dp, OutlineDark)
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
          ) {
            PhonePreview(draft = draft)
          }
        }
      }

      // Bottleneck Warning banner if exists
      draft.benchmark.bottleneckWarning?.let { warning ->
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CoralNeon.copy(alpha = 0.15f)),
            border = androidx.compose.foundation.BorderStroke(1.dp, CoralNeon)
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              Icon(Icons.Default.Warning, contentDescription = null, tint = CoralNeon, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = warning,
                color = CoralNeon,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }

      // Category Specific Selection Cards
      when (activeCategory) {
        StudioCategory.PROCESSOR -> {
          item {
            Text(
              text = "İşlemci (SoC) Seçimi",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "MediaTek, Snapdragon, Exynos ve Google Tensor yongaları",
              fontSize = 12.sp,
              color = TextSecondary
            )
          }

          items(HardwareCatalog.processors) { proc ->
            val available = isComponentAvailable(proc.releaseYear, proc.techRequired)
            val isSelected = draft.processor.id == proc.id

            ProcessorSelectionCard(
              processor = proc,
              currentYear = currentYear,
              isSelected = isSelected,
              isAvailable = available,
              onSelect = { if (available) onSelectProcessor(proc) }
            )
          }
        }

        StudioCategory.MEMORY -> {
          item {
            Text(
              text = "RAM (Bellek) Kapasitesi",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          items(HardwareCatalog.ramOptions) { ram ->
            val available = isComponentAvailable(ram.releaseYear, ram.techRequired)
            val isSelected = draft.ram.id == ram.id
            ComponentOptionCard(
              title = ram.typeName,
              subtitle = "Çıkış: ${ram.releaseYear} • Çoklu Görev: ${ram.speedScore}/100",
              cost = ram.cost,
              releaseYear = ram.releaseYear,
              currentYear = currentYear,
              isSelected = isSelected,
              isAvailable = available,
              badge = "${ram.sizeGb} GB",
              badgeColor = ElectricBlue,
              onSelect = { if (available) onSelectRam(ram) }
            )
          }

          item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Depolama (ROM) Mimarisi",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          items(HardwareCatalog.storageOptions) { rom ->
            val available = isComponentAvailable(rom.releaseYear, rom.techRequired)
            val isSelected = draft.storage.id == rom.id
            ComponentOptionCard(
              title = "${rom.displaySize} (${rom.typeName})",
              subtitle = "Çıkış: ${rom.releaseYear} • Okuma/Yazma: ${rom.speedScore}/100",
              cost = rom.cost,
              releaseYear = rom.releaseYear,
              currentYear = currentYear,
              isSelected = isSelected,
              isAvailable = available,
              badge = rom.displaySize,
              badgeColor = AmberGold,
              onSelect = { if (available) onSelectStorage(rom) }
            )
          }
        }

        StudioCategory.DISPLAY -> {
          // SEPARATE 1: DISPLAY PANEL TECHNOLOGY
          item {
            Text(
              text = "1. Ekran Paneli Teknolojisi",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "Panel tipi renk doygunluğunu, parlaklığı ve kontrastı belirler",
              fontSize = 12.sp,
              color = TextSecondary
            )
          }

          items(HardwareCatalog.displayPanels) { panel ->
            val available = isComponentAvailable(panel.releaseYear, panel.techRequired)
            val isSelected = draft.displayPanel.id == panel.id
            ComponentOptionCard(
              title = panel.name,
              subtitle = "${panel.description} (${panel.brightnessNits} Nits)",
              cost = panel.cost,
              releaseYear = panel.releaseYear,
              currentYear = currentYear,
              isSelected = isSelected,
              isAvailable = available,
              badge = "${panel.brightnessNits} Nits",
              badgeColor = CyanNeon,
              onSelect = { if (available) onSelectDisplayPanel(panel) }
            )
          }

          // SEPARATE 2: DISPLAY RESOLUTION
          item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "2. Ekran Çözünürlüğü",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Text(
              text = "Piksel yoğunluğu keskinliği artırır, ancak pili daha çok tüketir",
              fontSize = 12.sp,
              color = TextSecondary
            )
          }

          items(HardwareCatalog.displayResolutions) { res ->
            val available = isComponentAvailable(res.releaseYear, res.techRequired)
            val isSelected = draft.displayResolution.id == res.id
            ComponentOptionCard(
              title = "${res.name} (${res.resolutionText})",
              subtitle = "Keskinlik: ${res.sharpnessScore}/100 • Güç Tüketimi: ${res.powerConsumptionCost}W",
              cost = res.cost,
              releaseYear = res.releaseYear,
              currentYear = currentYear,
              isSelected = isSelected,
              isAvailable = available,
              badge = res.name,
              badgeColor = ElectricBlue,
              onSelect = { if (available) onSelectDisplayResolution(res) }
            )
          }

          // SEPARATE 3: REFRESH RATE
          item {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "3. Ekran Yenileme Hızı",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          items(HardwareCatalog.refreshRates) { hzOpt ->
            val available = hzOpt.releaseYear <= currentYear
            val isSelected = draft.refreshRate.id == hzOpt.id
            ComponentOptionCard(
              title = "${hzOpt.hz} Hz Ekran Hızı",
              subtitle = "Akıcılık Puanı: ${hzOpt.smoothnessScore}/100",
              cost = hzOpt.cost,
              releaseYear = hzOpt.releaseYear,
              currentYear = currentYear,
              isSelected = isSelected,
              isAvailable = available,
              badge = "${hzOpt.hz}Hz",
              badgeColor = AmberGold,
              onSelect = { if (available) onSelectRefreshRate(hzOpt) }
            )
          }
        }

        StudioCategory.CAMERA_BATTERY -> {
          item {
            Text(
              text = "Kamera Kurulumu",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          items(HardwareCatalog.cameraSetups) { cam ->
            val available = isComponentAvailable(cam.releaseYear, cam.techRequired)
            val isSelected = draft.camera.id == cam.id
            ComponentOptionCard(
              title = cam.name,
              subtitle = "${cam.description} (Fotoğraf Puanı: ${cam.photoScore}/100)",
              cost = cam.cost,
              releaseYear = cam.releaseYear,
              currentYear = currentYear,
              isSelected = isSelected,
              isAvailable = available,
              badge = "${cam.lensCount} Lens",
              badgeColor = PurpleCyber,
              onSelect = { if (available) onSelectCamera(cam) }
            )
          }

          item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Batarya ve Şarj Hızı",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          items(HardwareCatalog.batterySetups) { bat ->
            val available = isComponentAvailable(bat.releaseYear, bat.techRequired)
            val isSelected = draft.battery.id == bat.id
            ComponentOptionCard(
              title = "${bat.capacityMah} mAh Batarya",
              subtitle = "${bat.chargingWatt}W Hızlı Şarj ${if (bat.hasWireless) "+ Kablosuz Şarj" else ""}",
              cost = bat.cost,
              releaseYear = bat.releaseYear,
              currentYear = currentYear,
              isSelected = isSelected,
              isAvailable = available,
              badge = "${bat.chargingWatt}W",
              badgeColor = SuccessGreen,
              onSelect = { if (available) onSelectBattery(bat) }
            )
          }
        }

        StudioCategory.BUILD_OS -> {
          item {
            Text(
              text = "Kasa Malzemesi",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          items(HardwareCatalog.materialOptions) { mat ->
            val available = isComponentAvailable(mat.releaseYear, mat.techRequired)
            val isSelected = draft.material.id == mat.id
            ComponentOptionCard(
              title = mat.name,
              subtitle = "${mat.description} (Prestij: ${mat.prestigeScore}/100)",
              cost = mat.cost,
              releaseYear = mat.releaseYear,
              currentYear = currentYear,
              isSelected = isSelected,
              isAvailable = available,
              badge = "Gövde",
              badgeColor = ElectricBlue,
              onSelect = { if (available) onSelectMaterial(mat) }
            )
          }

          item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Telefon Gövde Rengi",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              HardwareCatalog.colors.forEach { colorOpt ->
                val isSelected = draft.color.id == colorOpt.id
                Column(
                  horizontalAlignment = Alignment.CenterHorizontally,
                  modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onSelectColor(colorOpt) }
                    .padding(6.dp)
                ) {
                  Box(
                    modifier = Modifier
                      .size(42.dp)
                      .clip(CircleShape)
                      .background(colorOpt.hexColor)
                      .border(
                        width = if (isSelected) 3.dp else 1.dp,
                        color = if (isSelected) CyanNeon else Color.White.copy(alpha = 0.3f),
                        shape = CircleShape
                      )
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = colorOpt.name,
                    fontSize = 10.sp,
                    color = if (isSelected) CyanNeon else TextSecondary,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                  )
                }
              }
            }
          }

          item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Yazılım ve Yapay Zeka Özellikleri",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextPrimary
            )
          }

          items(HardwareCatalog.softwareFeatures) { sw ->
            val available = isComponentAvailable(2020, sw.techRequired)
            val isSelected = draft.selectedSoftwareIds.contains(sw.id)

            Card(
              modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = available) { onToggleSoftware(sw.id) },
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(
                containerColor = if (isSelected) ElectricBlue.copy(alpha = 0.15f) else TechSurfaceCard
              ),
              border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (isSelected) ElectricBlue else OutlineDark
              )
            ) {
              Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Checkbox(
                  checked = isSelected,
                  onCheckedChange = { if (available) onToggleSoftware(sw.id) },
                  colors = CheckboxDefaults.colors(checkedColor = ElectricBlue)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                  Text(text = sw.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                  Text(text = sw.description, fontSize = 11.sp, color = TextSecondary)
                  Text(
                    text = "Geliştirme Maliyeti: $${sw.developmentCost} • +${sw.scoreBonus} Puan",
                    fontSize = 11.sp,
                    color = AmberGold,
                    fontWeight = FontWeight.SemiBold
                  )
                }
                if (!available) {
                  Icon(Icons.Default.Lock, contentDescription = "Kilitli", tint = TextMuted)
                }
              }
            }
          }
        }

        StudioCategory.PRICING -> {
          item {
            PricingAndLaunchSection(
              draft = draft,
              onUpdatePrice = onUpdatePrice,
              onSelectMarketing = onSelectMarketing,
              onLaunch = onLaunchPhone
            )
          }
        }
      }
    }
  }
}

@Composable
private fun ProcessorSelectionCard(
  processor: Processor,
  currentYear: Int,
  isSelected: Boolean,
  isAvailable: Boolean,
  onSelect: () -> Unit
) {
  val isFutureTech = processor.releaseYear > currentYear

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(enabled = isAvailable, onClick = onSelect),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) CyanNeon.copy(alpha = 0.15f) else TechSurfaceCard
    ),
    border = androidx.compose.foundation.BorderStroke(
      width = if (isSelected) 1.8.dp else 1.dp,
      color = if (isSelected) CyanNeon else OutlineDark
    )
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = processor.brand.badgeColor.copy(alpha = 0.2f),
            border = androidx.compose.foundation.BorderStroke(1.dp, processor.brand.badgeColor)
          ) {
            Text(
              text = processor.brand.displayName,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = processor.brand.badgeColor
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = processor.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }

        if (isFutureTech) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = CoralNeon.copy(alpha = 0.15f)
          ) {
            Text(
              text = "🔒 ${processor.releaseYear} Yılı",
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = CoralNeon
            )
          }
        } else if (!isAvailable) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lock, contentDescription = null, tint = AmberGold, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "AR-GE Gerekli", fontSize = 11.sp, color = AmberGold, fontWeight = FontWeight.Bold)
          }
        } else {
          Text(
            text = "+${processor.cost} $",
            fontSize = 14.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (isSelected) CyanNeon else TextSecondary
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = processor.description,
        fontSize = 12.sp,
        color = TextSecondary,
        lineHeight = 16.sp
      )

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        MeterStat(label = "CPU", value = processor.cpuScore, color = CyanNeon)
        MeterStat(label = "GPU", value = processor.gpuScore, color = ElectricBlue)
        MeterStat(label = "Verimlilik", value = processor.efficiency, color = SuccessGreen)
      }
    }
  }
}

@Composable
private fun ComponentOptionCard(
  title: String,
  subtitle: String,
  cost: Int,
  releaseYear: Int,
  currentYear: Int,
  isSelected: Boolean,
  isAvailable: Boolean,
  badge: String,
  badgeColor: Color,
  onSelect: () -> Unit
) {
  val isFutureTech = releaseYear > currentYear

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(enabled = isAvailable, onClick = onSelect),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) CyanNeon.copy(alpha = 0.12f) else TechSurfaceCard
    ),
    border = androidx.compose.foundation.BorderStroke(
      width = if (isSelected) 1.6.dp else 1.dp,
      color = if (isSelected) CyanNeon else OutlineDark
    )
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = badgeColor.copy(alpha = 0.2f)
          ) {
            Text(
              text = badge,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = badgeColor
            )
          }
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = subtitle,
          fontSize = 11.sp,
          color = TextSecondary
        )
      }

      if (isFutureTech) {
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = CoralNeon.copy(alpha = 0.15f)
        ) {
          Text(
            text = "🔒 $releaseYear",
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = CoralNeon
          )
        }
      } else if (!isAvailable) {
        Icon(Icons.Default.Lock, contentDescription = "Kilitli", tint = TextMuted)
      } else {
        Text(
          text = "+$cost $",
          fontSize = 14.sp,
          fontWeight = FontWeight.Bold,
          color = if (isSelected) CyanNeon else TextSecondary
        )
      }
    }
  }
}

@Composable
private fun MeterStat(label: String, value: Int, color: Color) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(text = "$label: ", fontSize = 11.sp, color = TextMuted)
    Text(text = "$value", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
  }
}

@Composable
private fun PricingAndLaunchSection(
  draft: StudioDraft,
  onUpdatePrice: (Int) -> Unit,
  onSelectMarketing: (MarketingTier) -> Unit,
  onLaunch: () -> Unit
) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = TechSurface),
    border = androidx.compose.foundation.BorderStroke(1.dp, CyanNeon.copy(alpha = 0.5f))
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Text(
        text = "Fiyatlandırma & Kar Marjı",
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text(text = "Birim Maliyet", fontSize = 11.sp, color = TextMuted)
          Text(text = "${draft.unitCost} $", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = CoralNeon)
        }
        Column {
          Text(text = "Önerilen Fiyat", fontSize = 11.sp, color = TextMuted)
          Text(text = "${draft.recommendedPrice} $", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
        }
        Column {
          Text(text = "Birim Kar", fontSize = 11.sp, color = TextMuted)
          Text(
            text = "${draft.profitPerUnit} $",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = if (draft.profitPerUnit >= 0) SuccessGreen else CoralNeon
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "Satış Fiyatı: ${draft.retailPrice} $",
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = CyanNeon
      )

      Slider(
        value = draft.retailPrice.toFloat(),
        onValueChange = { onUpdatePrice(it.roundToInt()) },
        valueRange = (draft.unitCost * 0.8f)..(draft.unitCost * 3.5f),
        colors = SliderDefaults.colors(
          thumbColor = CyanNeon,
          activeTrackColor = CyanNeon,
          inactiveTrackColor = TechSurfaceVariant
        )
      )

      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = "Lansman Pazarlama Kampanyası",
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        color = TextPrimary
      )
      Spacer(modifier = Modifier.height(8.dp))

      MarketingTier.values().forEach { tier ->
        val isSelected = draft.marketingTier == tier
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onSelectMarketing(tier) },
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(
            containerColor = if (isSelected) AmberGold.copy(alpha = 0.15f) else TechSurfaceCard
          ),
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) AmberGold else OutlineDark
          )
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            RadioButton(
              selected = isSelected,
              onClick = { onSelectMarketing(tier) },
              colors = RadioButtonDefaults.colors(selectedColor = AmberGold)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(text = tier.displayName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
              Text(text = tier.description, fontSize = 11.sp, color = TextSecondary)
              Text(
                text = "Maliyet: $${tier.cost} • Hype Çarpanı: ${tier.hypeMultiplier}x",
                fontSize = 11.sp,
                color = AmberGold,
                fontWeight = FontWeight.SemiBold
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      Button(
        onClick = onLaunch,
        modifier = Modifier
          .fillMaxWidth()
          .height(54.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = CyanNeon, contentColor = CyanNeonDark)
      ) {
        Icon(Icons.Default.RocketLaunch, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "${draft.name} Telefonunu Pazara Sür!",
          fontWeight = FontWeight.Black,
          fontSize = 15.sp
        )
      }
    }
  }
}
