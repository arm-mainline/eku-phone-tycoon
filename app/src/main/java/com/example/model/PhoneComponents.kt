package com.example.model

import androidx.compose.ui.graphics.Color

enum class ProcessorBrand(val displayName: String, val badgeColor: Color) {
  MEDIATEK("MediaTek", Color(0xFFF77F00)),
  SNAPDRAGON("Snapdragon", Color(0xFFE63946)),
  EXYNOS("Samsung Exynos", Color(0xFF3A86FF)),
  GOOGLE("Google Tensor", Color(0xFF457B9D))
}

data class Processor(
  val id: String,
  val name: String,
  val brand: ProcessorBrand,
  val releaseYear: Int,
  val cpuScore: Int,      // 1-100
  val gpuScore: Int,      // 1-100
  val efficiency: Int,    // 1-100
  val cost: Int,          // USD per unit
  val techRequired: String? = null,
  val description: String
)

data class RamOption(
  val id: String,
  val sizeGb: Int,
  val typeName: String,
  val releaseYear: Int,
  val speedScore: Int,
  val cost: Int,
  val techRequired: String? = null
)

data class StorageOption(
  val id: String,
  val sizeGb: Int,
  val displaySize: String,
  val typeName: String,
  val releaseYear: Int,
  val speedScore: Int,
  val cost: Int,
  val techRequired: String? = null
)

// Separated: Display Panel Technology
data class DisplayPanel(
  val id: String,
  val name: String,
  val releaseYear: Int,
  val brightnessNits: Int,
  val contrastScore: Int,
  val cost: Int,
  val techRequired: String? = null,
  val description: String
)

// Separated: Display Resolution
data class DisplayResolution(
  val id: String,
  val name: String,
  val resolutionText: String, // e.g. 1080 x 2400
  val releaseYear: Int,
  val sharpnessScore: Int,
  val powerConsumptionCost: Int, // battery drain factor
  val cost: Int,
  val techRequired: String? = null
)

// Display Refresh Rate
data class RefreshRateOption(
  val id: String,
  val hz: Int,
  val releaseYear: Int,
  val smoothnessScore: Int,
  val cost: Int
)

data class CameraSetup(
  val id: String,
  val name: String,
  val megapixels: String,
  val lensCount: Int,
  val releaseYear: Int,
  val hasOis: Boolean,
  val hasPeriscope: Boolean,
  val photoScore: Int,
  val cost: Int,
  val techRequired: String? = null,
  val description: String
)

data class BatterySetup(
  val id: String,
  val capacityMah: Int,
  val chargingWatt: Int,
  val releaseYear: Int,
  val hasWireless: Boolean,
  val batteryScore: Int,
  val cost: Int,
  val techRequired: String? = null
)

data class MaterialOption(
  val id: String,
  val name: String,
  val releaseYear: Int,
  val prestigeScore: Int,
  val durabilityScore: Int,
  val cost: Int,
  val techRequired: String? = null,
  val description: String
)

data class PhoneColor(
  val id: String,
  val name: String,
  val hexColor: Color,
  val secondaryColor: Color = hexColor
)

data class SoftwareOption(
  val id: String,
  val name: String,
  val scoreBonus: Int,
  val developmentCost: Int,
  val description: String,
  val techRequired: String? = null
)

object HardwareCatalog {
  // Real world historical evolution: MediaTek, Snapdragon, Exynos, Google Tensor
  val processors = listOf(
    // 2020 ERA
    Processor(
      id = "mtk_g90t",
      name = "Helio G90T",
      brand = ProcessorBrand.MEDIATEK,
      releaseYear = 2020,
      cpuScore = 40,
      gpuScore = 38,
      efficiency = 60,
      cost = 16,
      description = "2020: 12nm ekonomik oyun işlemcisi, fiyat/performans telefonlar için ideal."
    ),
    Processor(
      id = "sd_720g",
      name = "Snapdragon 720G",
      brand = ProcessorBrand.SNAPDRAGON,
      releaseYear = 2020,
      cpuScore = 45,
      gpuScore = 44,
      efficiency = 70,
      cost = 22,
      description = "2020: 8nm verimli orta segment klasiği, kararlı batarya ömrü."
    ),
    Processor(
      id = "sd_865",
      name = "Snapdragon 865 5G",
      brand = ProcessorBrand.SNAPDRAGON,
      releaseYear = 2020,
      cpuScore = 68,
      gpuScore = 67,
      efficiency = 74,
      cost = 52,
      description = "2020: Efsanevi ilk 5G amiral gemisi platformu, yüksek stabilite."
    ),
    Processor(
      id = "exynos_990",
      name = "Exynos 990",
      brand = ProcessorBrand.EXYNOS,
      releaseYear = 2020,
      cpuScore = 66,
      gpuScore = 64,
      efficiency = 62,
      cost = 45,
      description = "2020: 7nm EUV mimarisi, güçlü yapay zeka NPU birimi."
    ),

    // 2022 ERA
    Processor(
      id = "mtk_dim_8100",
      name = "Dimensity 8100",
      brand = ProcessorBrand.MEDIATEK,
      releaseYear = 2022,
      cpuScore = 75,
      gpuScore = 76,
      efficiency = 84,
      cost = 38,
      techRequired = "tech_5g_advanced",
      description = "2022: 5nm TSMC mimarili aşırı verimli amiral gemisi katili."
    ),
    Processor(
      id = "sd_8_plus_gen1",
      name = "Snapdragon 8+ Gen 1",
      brand = ProcessorBrand.SNAPDRAGON,
      releaseYear = 2022,
      cpuScore = 85,
      gpuScore = 86,
      efficiency = 82,
      cost = 68,
      techRequired = "tech_flagship_soc",
      description = "2022: 4nm TSMC üretimi soğuk çalışan tepe segment işlemcisi."
    ),
    Processor(
      id = "tensor_g2",
      name = "Google Tensor G2",
      brand = ProcessorBrand.GOOGLE,
      releaseYear = 2022,
      cpuScore = 72,
      gpuScore = 70,
      efficiency = 76,
      cost = 48,
      techRequired = "tech_ai_npu",
      description = "2022: Google yapay zeka ses ve fotoğraf işleme uzmanı çip."
    ),

    // 2024 ERA
    Processor(
      id = "sd_7plus_gen3",
      name = "Snapdragon 7+ Gen 3",
      brand = ProcessorBrand.SNAPDRAGON,
      releaseYear = 2024,
      cpuScore = 86,
      gpuScore = 87,
      efficiency = 85,
      cost = 58,
      techRequired = "tech_5g_advanced",
      description = "2024: Cortex-X4 çekirdekli fiyat/performans canavarı."
    ),
    Processor(
      id = "mtk_dim_9300",
      name = "Dimensity 9300+",
      brand = ProcessorBrand.MEDIATEK,
      releaseYear = 2024,
      cpuScore = 94,
      gpuScore = 95,
      efficiency = 88,
      cost = 90,
      techRequired = "tech_flagship_soc",
      description = "2024: Tüm büyük çekirdekli rekor kıran grafik ve CPU canavarı."
    ),
    Processor(
      id = "sd_8gen3",
      name = "Snapdragon 8 Gen 3",
      brand = ProcessorBrand.SNAPDRAGON,
      releaseYear = 2024,
      cpuScore = 93,
      gpuScore = 94,
      efficiency = 87,
      cost = 98,
      techRequired = "tech_flagship_soc",
      description = "2024: Cihaz içi üretken yapay zeka ve ışın izleme destekli çip."
    ),
    Processor(
      id = "exynos_2400",
      name = "Exynos 2400",
      brand = ProcessorBrand.EXYNOS,
      releaseYear = 2024,
      cpuScore = 88,
      gpuScore = 87,
      efficiency = 79,
      cost = 74,
      techRequired = "tech_flagship_soc",
      description = "2024: 10 çekirdekli Xclipse 940 grafik motorlu tepe çip."
    ),

    // 2026+ NEXT GEN ERA
    Processor(
      id = "mtk_dim_9400",
      name = "Dimensity 9400",
      brand = ProcessorBrand.MEDIATEK,
      releaseYear = 2026,
      cpuScore = 98,
      gpuScore = 99,
      efficiency = 94,
      cost = 125,
      techRequired = "tech_elite_arch",
      description = "2026: 3nm ikinci nesil mimari, PC seviyesinde ışın izleme ve yapay zeka."
    ),
    Processor(
      id = "sd_8_elite",
      name = "Snapdragon 8 Elite",
      brand = ProcessorBrand.SNAPDRAGON,
      releaseYear = 2026,
      cpuScore = 100,
      gpuScore = 100,
      efficiency = 96,
      cost = 140,
      techRequired = "tech_elite_arch",
      description = "2026: Özel Oryon mimarili 4.32GHz rekor kırıcı canavar işlemci."
    ),
    Processor(
      id = "tensor_g4",
      name = "Google Tensor G4",
      brand = ProcessorBrand.GOOGLE,
      releaseYear = 2026,
      cpuScore = 84,
      gpuScore = 80,
      efficiency = 88,
      cost = 80,
      techRequired = "tech_ai_npu",
      description = "2026: Entegre Gemini Nano v2 yapay zekasıyla donatılmış çip."
    )
  )

  // RAM
  val ramOptions = listOf(
    RamOption("ram_4gb", 4, "4GB LPDDR4X", releaseYear = 2020, speedScore = 40, cost = 12),
    RamOption("ram_6gb", 6, "6GB LPDDR4X", releaseYear = 2020, speedScore = 55, cost = 18),
    RamOption("ram_8gb", 8, "8GB LPDDR5", releaseYear = 2021, speedScore = 72, cost = 28),
    RamOption("ram_12gb", 12, "12GB LPDDR5X", releaseYear = 2023, speedScore = 88, cost = 45, techRequired = "tech_high_ram"),
    RamOption("ram_16gb", 16, "16GB LPDDR5X", releaseYear = 2024, speedScore = 96, cost = 62, techRequired = "tech_high_ram"),
    RamOption("ram_24gb", 24, "24GB LPDDR5X", releaseYear = 2026, speedScore = 100, cost = 88, techRequired = "tech_high_ram")
  )

  // Storage
  val storageOptions = listOf(
    StorageOption("rom_64gb", 64, "64 GB", "eMMC 5.1", releaseYear = 2020, speedScore = 35, cost = 8),
    StorageOption("rom_128gb", 128, "128 GB", "UFS 2.2", releaseYear = 2020, speedScore = 55, cost = 16),
    StorageOption("rom_256gb", 256, "256 GB", "UFS 3.1", releaseYear = 2022, speedScore = 75, cost = 28),
    StorageOption("rom_512gb", 512, "512 GB", "UFS 4.0", releaseYear = 2024, speedScore = 92, cost = 50, techRequired = "tech_ufs4"),
    StorageOption("rom_1tb", 1024, "1 TB", "UFS 4.0 Pro", releaseYear = 2025, speedScore = 100, cost = 85, techRequired = "tech_ufs4")
  )

  // Separate: Display Panels
  val displayPanels = listOf(
    DisplayPanel(
      id = "panel_ips_lcd",
      name = "IPS LCD",
      releaseYear = 2020,
      brightnessNits = 650,
      contrastScore = 48,
      cost = 10,
      description = "Ekonomik, doğal renkli ve dayanıklı standart sıvı kristal panel."
    ),
    DisplayPanel(
      id = "panel_amoled",
      name = "AMOLED",
      releaseYear = 2020,
      brightnessNits = 1200,
      contrastScore = 75,
      cost = 24,
      description = "Sonsuz kontrast, gerçek siyahlar ve canlı renk doygunluğu."
    ),
    DisplayPanel(
      id = "panel_super_amoled",
      name = "Super AMOLED Plus",
      releaseYear = 2022,
      brightnessNits = 1800,
      contrastScore = 84,
      cost = 36,
      description = "Daha ince dokunmatik katman ve güneş altında net görüş."
    ),
    DisplayPanel(
      id = "panel_ltpo_amoled",
      name = "LTPO Dinamik AMOLED",
      releaseYear = 2023,
      brightnessNits = 3000,
      contrastScore = 92,
      cost = 56,
      techRequired = "tech_ltpo_screen",
      description = "1Hz ile 120Hz arasında dinamik değişerek pil tasarrufu sağlayan panel."
    ),
    DisplayPanel(
      id = "panel_dynamic_oled_4500",
      name = "Ultra LTPO OLED (4500 Nits)",
      releaseYear = 2025,
      brightnessNits = 4500,
      contrastScore = 98,
      cost = 80,
      techRequired = "tech_ltpo_screen",
      description = "Göz kamaştıran 4500 nits tepe parlaklık ve 2160Hz PWM göz koruması."
    ),
    DisplayPanel(
      id = "panel_foldable_oled",
      name = "Katlanabilir Esnek OLED",
      releaseYear = 2024,
      brightnessNits = 2600,
      contrastScore = 95,
      cost = 115,
      techRequired = "tech_foldable_screen",
      description = "Ultra ince esnek cam (UTG) ile katlanabilen fütüristik ekran."
    )
  )

  // Separate: Display Resolution
  val displayResolutions = listOf(
    DisplayResolution(
      id = "res_hd_plus",
      name = "HD+",
      resolutionText = "720 x 1600 px",
      releaseYear = 2020,
      sharpnessScore = 45,
      powerConsumptionCost = 2,
      cost = 6
    ),
    DisplayResolution(
      id = "res_fhd_plus",
      name = "FHD+",
      resolutionText = "1080 x 2400 px",
      releaseYear = 2020,
      sharpnessScore = 72,
      powerConsumptionCost = 6,
      cost = 14
    ),
    DisplayResolution(
      id = "res_15k",
      name = "1.5K CrystalRes",
      resolutionText = "1220 x 2712 px",
      releaseYear = 2023,
      sharpnessScore = 88,
      powerConsumptionCost = 10,
      cost = 24,
      techRequired = "tech_oled_advanced"
    ),
    DisplayResolution(
      id = "res_2k_qhd",
      name = "2K QHD+",
      resolutionText = "1440 x 3200 px",
      releaseYear = 2021,
      sharpnessScore = 97,
      powerConsumptionCost = 16,
      cost = 36,
      techRequired = "tech_ltpo_screen"
    ),
    DisplayResolution(
      id = "res_4k_uhd",
      name = "4K Ultra HD",
      resolutionText = "2160 x 3840 px",
      releaseYear = 2024,
      sharpnessScore = 100,
      powerConsumptionCost = 26,
      cost = 58,
      techRequired = "tech_curved_screen"
    )
  )

  // Refresh Rates
  val refreshRates = listOf(
    RefreshRateOption("hz_60", 60, releaseYear = 2020, smoothnessScore = 50, cost = 0),
    RefreshRateOption("hz_90", 90, releaseYear = 2020, smoothnessScore = 70, cost = 6),
    RefreshRateOption("hz_120", 120, releaseYear = 2021, smoothnessScore = 88, cost = 14),
    RefreshRateOption("hz_144", 144, releaseYear = 2023, smoothnessScore = 96, cost = 24),
    RefreshRateOption("hz_165", 165, releaseYear = 2025, smoothnessScore = 100, cost = 36)
  )

  // Cameras
  val cameraSetups = listOf(
    CameraSetup(
      id = "cam_basic",
      name = "13MP Temel Tekli Lens",
      megapixels = "13 MP",
      lensCount = 1,
      releaseYear = 2020,
      hasOis = false,
      hasPeriscope = false,
      photoScore = 38,
      cost = 8,
      description = "Gündüz anlık fotoğraflar için yeterli ekonomik tekli sensör."
    ),
    CameraSetup(
      id = "cam_dual_50",
      name = "50MP OIS Ana + 8MP Geniş Açı",
      megapixels = "50 MP + 8 MP",
      lensCount = 2,
      releaseYear = 2021,
      hasOis = true,
      hasPeriscope = false,
      photoScore = 68,
      cost = 24,
      description = "Optik İmaj Sabitleyici (OIS) ile net gece fotoğrafları."
    ),
    CameraSetup(
      id = "cam_triple_sony",
      name = "50MP Sony IMX + 50MP Geniş + 12MP Telefoto",
      megapixels = "50 MP + 50 MP + 12 MP",
      lensCount = 3,
      releaseYear = 2023,
      hasOis = true,
      hasPeriscope = false,
      photoScore = 86,
      cost = 62,
      techRequired = "tech_camera_pro",
      description = "3x optik zoomlu dengeli profesyonel üçlü kamera adası."
    ),
    CameraSetup(
      id = "cam_200mp_periscope",
      name = "200MP HP2 + 50MP Periskop 5x Zoom",
      megapixels = "200 MP + 50 MP + 50 MP",
      lensCount = 3,
      releaseYear = 2024,
      hasOis = true,
      hasPeriscope = true,
      photoScore = 95,
      cost = 110,
      techRequired = "tech_periscope_zoom",
      description = "Ay fotoğrafları çekebilen 100x dijital / 5x kayıpsız optik zoom."
    ),
    CameraSetup(
      id = "cam_cinematic_quad",
      name = "1 İnç Devasa Sensör + Zeiss Dörtlü Lens + 8K Log",
      megapixels = "50 MP (1\") + 50 MP + 50 MP + 64 MP",
      lensCount = 4,
      releaseYear = 2025,
      hasOis = true,
      hasPeriscope = true,
      photoScore = 100,
      cost = 155,
      techRequired = "tech_1inch_sensor",
      description = "DSLR kalitesinde doğal bokeh ve sinematik video motoru."
    )
  )

  // Batteries
  val batterySetups = listOf(
    BatterySetup("bat_4000_18w", capacityMah = 4000, chargingWatt = 18, releaseYear = 2020, hasWireless = false, batteryScore = 45, cost = 10),
    BatterySetup("bat_5000_33w", capacityMah = 5000, chargingWatt = 33, releaseYear = 2020, hasWireless = false, batteryScore = 65, cost = 18),
    BatterySetup("bat_5000_67w", capacityMah = 5000, chargingWatt = 67, releaseYear = 2022, hasWireless = false, batteryScore = 78, cost = 28),
    BatterySetup("bat_5500_120w", capacityMah = 5500, chargingWatt = 120, releaseYear = 2024, hasWireless = true, batteryScore = 90, cost = 48, techRequired = "tech_fast_charge"),
    BatterySetup("bat_6500_sic", capacityMah = 6500, chargingWatt = 150, releaseYear = 2026, hasWireless = true, batteryScore = 100, cost = 82, techRequired = "tech_silicon_battery")
  )

  // Materials
  val materialOptions = listOf(
    MaterialOption(
      id = "mat_plastic",
      name = "Mat Polikarbonat",
      releaseYear = 2020,
      prestigeScore = 40,
      durabilityScore = 80,
      cost = 8,
      description = "Hafif, çizilmeye ve düşmelere dayanıklı ekonomik gövde."
    ),
    MaterialOption(
      id = "mat_aluminum",
      name = "Havacılık Sınıfı Alüminyum",
      releaseYear = 2020,
      prestigeScore = 68,
      durabilityScore = 88,
      cost = 22,
      description = "Sağlam metal çerçeve ve soğuk metal dokunuşu."
    ),
    MaterialOption(
      id = "mat_glass",
      name = "Gorilla Glass Victus Cam",
      releaseYear = 2021,
      prestigeScore = 85,
      durabilityScore = 72,
      cost = 38,
      techRequired = "tech_premium_build",
      description = "Ayna gibi parlayan ultra şık çift taraflı cam gövde."
    ),
    MaterialOption(
      id = "mat_vegan_leather",
      name = "Eko Lüks Vegan Deri",
      releaseYear = 2023,
      prestigeScore = 90,
      durabilityScore = 82,
      cost = 46,
      techRequired = "tech_premium_build",
      description = "Parmak izi tutmayan, sıcak ve lüks dokulu el işçiliği."
    ),
    MaterialOption(
      id = "mat_titanium",
      name = "Uzay Sınıfı Titanyum Çerçeve",
      releaseYear = 2024,
      prestigeScore = 98,
      durabilityScore = 96,
      cost = 70,
      techRequired = "tech_titanium",
      description = "Olağanüstü hafiflik ve kurşun geçirmez sağlamlık hissi."
    )
  )

  val colors = listOf(
    PhoneColor("col_black", "Uzay Siyahı", Color(0xFF1E222B), Color(0xFF12141A)),
    PhoneColor("col_cyan", "Sibernetik Cyan", Color(0xFF00F5D4), Color(0xFF00A896)),
    PhoneColor("col_blue", "Kobalt Elektrik Mavi", Color(0xFF3A86FF), Color(0xFF023E8A)),
    PhoneColor("col_emerald", "Zümrüt Aurora", Color(0xFF2D6A4F), Color(0xFF1B4332)),
    PhoneColor("col_gold", "Çöl Titanyumu Altın", Color(0xFFFFB703), Color(0xFFFB8500)),
    PhoneColor("col_white", "Buz Beyazı", Color(0xFFF8F9FA), Color(0xFFCED4DA)),
    PhoneColor("col_violet", "Neon Cyberpunk Mor", Color(0xFF8338EC), Color(0xFF5A189A))
  )

  val softwareFeatures = listOf(
    SoftwareOption(
      id = "sw_clean_os",
      name = "EkuOS Saf Arayüz",
      scoreBonus = 5,
      developmentCost = 8000,
      description = "Gereksiz uygulamalardan arındırılmış akıcı 120 FPS sistem."
    ),
    SoftwareOption(
      id = "sw_ai_camera",
      name = "AI Fotoğraf ve Portre Motoru",
      scoreBonus = 12,
      developmentCost = 24000,
      description = "Yapay zeka ile gece fotoğraflarını aydınlatma ve ten rengi doğrulama.",
      techRequired = "tech_ai_npu"
    ),
    SoftwareOption(
      id = "sw_game_turbo",
      name = "Oyun Hızlandırıcı Turbo Mod",
      scoreBonus = 8,
      developmentCost = 15000,
      description = "İşlemci saat hızlarını maksimuma kilitleyen soğutma algoritması."
    ),
    SoftwareOption(
      id = "sw_7yr_update",
      name = "7 Yıl Yazılım & Güvenlik Desteği",
      scoreBonus = 15,
      developmentCost = 45000,
      description = "Müşteri sadakatini ve tekrar satın alma oranını zirveye taşır.",
      techRequired = "tech_flagship_soc"
    )
  )
}
