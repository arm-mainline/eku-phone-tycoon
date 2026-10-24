package com.example.engine

import androidx.compose.ui.graphics.Color
import com.example.model.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.random.Random

object SimulationEngine {

  fun getInitialTechTree(): List<ResearchTech> {
    return listOf(
      ResearchTech(
        id = "tech_oled_advanced",
        title = "Gelişmiş AMOLED & 1.5K Çözünürlük",
        category = "Ekran",
        requiredYear = 2021,
        description = "Göz yormayan PWM karartma ve 1.5K CrystalRes çözünürlük teknolojisi.",
        cashCost = 45_000L,
        rpCost = 35,
        isUnlocked = false,
        unlocksDescription = "Super AMOLED Plus ve 1.5K Çözünürlük"
      ),
      ResearchTech(
        id = "tech_5g_advanced",
        title = "Gelişmiş 5G ve 4nm/5nm Döküm",
        category = "İşlemci",
        requiredYear = 2022,
        description = "Dimensity 8100 ve Snapdragon 7+ Gen 3 gibi verimli çiplerin mimarisini açar.",
        cashCost = 65_000L,
        rpCost = 45,
        isUnlocked = false,
        unlocksDescription = "Dimensity 8100 ve Snapdragon 7+ Gen 3"
      ),
      ResearchTech(
        id = "tech_premium_build",
        title = "Gorilla Glass Victus & Vegan Deri",
        category = "Malzeme",
        requiredYear = 2022,
        description = "Lüks dokulu sırt panelleri ve dayanıklı premium cam işçiliği.",
        cashCost = 35_000L,
        rpCost = 30,
        isUnlocked = false,
        unlocksDescription = "Victus Cam ve Vegan Deri Gövde"
      ),
      ResearchTech(
        id = "tech_high_ram",
        title = "LPDDR5X Yüksek Hızlı Bellek Modülleri",
        category = "Donanım",
        requiredYear = 2023,
        description = "12GB, 16GB ve 24GB LPDDR5X ultra hızlı RAM seçeneklerini açar.",
        cashCost = 50_000L,
        rpCost = 40,
        isUnlocked = false,
        unlocksDescription = "12GB, 16GB ve 24GB RAM"
      ),
      ResearchTech(
        id = "tech_ltpo_screen",
        title = "LTPO Dinamik 1-120Hz ve 2K QHD+ Ekran",
        category = "Ekran",
        requiredYear = 2023,
        description = "Pili yormayan dinamik değişken yenileme hızı ve 2K QHD+ tepe çözünürlüğü.",
        cashCost = 85_000L,
        rpCost = 55,
        isUnlocked = false,
        unlocksDescription = "LTPO Dinamik AMOLED & 2K QHD+ Çözünürlük"
      ),
      ResearchTech(
        id = "tech_camera_pro",
        title = "Sony IMX Üçlü Kamera ve OIS",
        category = "Kamera",
        requiredYear = 2023,
        description = "Üçlü 50MP Sony sensörleri ve 3x optik telefoto mimarisini açar.",
        cashCost = 60_000L,
        rpCost = 45,
        isUnlocked = false,
        unlocksDescription = "Sony IMX Üçlü 50MP Kamera"
      ),
      ResearchTech(
        id = "tech_ufs4",
        title = "UFS 4.0 Çift Kanallı Depolama",
        category = "Donanım",
        requiredYear = 2024,
        description = "512GB ve 1TB kapasitelerinde 4200 MB/s okuma hızlarını açar.",
        cashCost = 55_000L,
        rpCost = 45,
        isUnlocked = false,
        unlocksDescription = "512GB ve 1TB UFS 4.0 Depolama"
      ),
      ResearchTech(
        id = "tech_flagship_soc",
        title = "4nm/3nm Amiral Gemisi Dökümü",
        category = "İşlemci",
        requiredYear = 2024,
        description = "Snapdragon 8 Gen 3, Dimensity 9300+ ve Exynos 2400 çiplerini kullanıma açar.",
        cashCost = 120_000L,
        rpCost = 70,
        isUnlocked = false,
        unlocksDescription = "Snapdragon 8 Gen 3, Dimensity 9300+, Exynos 2400"
      ),
      ResearchTech(
        id = "tech_periscope_zoom",
        title = "Prizmatik Periskop 5x-100x Zoom & 200MP",
        category = "Kamera",
        requiredYear = 2024,
        description = "200MP ana sensör ve prizmatik periskop optik zoom mekanizması.",
        cashCost = 110_000L,
        rpCost = 65,
        isUnlocked = false,
        unlocksDescription = "200MP + 50MP Periskop Zoom Kamera"
      ),
      ResearchTech(
        id = "tech_titanium",
        title = "Uzay Sınıfı Titanyum Çerçeve İşleme",
        category = "Malzeme",
        requiredYear = 2024,
        description = "Hafif, çizilmeye ve bükülmeye dirençli 5. seviye titanyum şasi.",
        cashCost = 90_000L,
        rpCost = 60,
        isUnlocked = false,
        unlocksDescription = "Havacılık Sınıfı Titanyum Kasa"
      ),
      ResearchTech(
        id = "tech_fast_charge",
        title = "120W GaN HyperCharge",
        category = "Donanım",
        requiredYear = 2024,
        description = "19 dakikada %100 dolum sağlayan çift hücreli şarj çipi.",
        cashCost = 55_000L,
        rpCost = 40,
        isUnlocked = false,
        unlocksDescription = "5500 mAh + 120W Şarj"
      ),
      ResearchTech(
        id = "tech_foldable_screen",
        title = "Katlanabilir Esnek UTG Ekran Mimarisi",
        category = "Ekran",
        requiredYear = 2024,
        description = "Kitap şeklinde katlanabilen Ultra Thin Glass esnek OLED panel.",
        cashCost = 150_000L,
        rpCost = 85,
        isUnlocked = false,
        unlocksDescription = "Katlanabilir Esnek OLED Ekran"
      ),
      ResearchTech(
        id = "tech_elite_arch",
        title = "Yeni Nesil Oryon & 3nm Çip Mimarisi",
        category = "İşlemci",
        requiredYear = 2026,
        description = "Snapdragon 8 Elite ve Dimensity 9400 rekor kırıcı çiplerinin lisansını sağlar.",
        cashCost = 220_000L,
        rpCost = 110,
        isUnlocked = false,
        unlocksDescription = "Snapdragon 8 Elite ve Dimensity 9400"
      ),
      ResearchTech(
        id = "tech_silicon_battery",
        title = "Yeni Nesil Silikon-Karbon Bataryalar",
        category = "Donanım",
        requiredYear = 2026,
        description = "İnce gövdede 6500 mAh devasa batarya ve 150W şarj.",
        cashCost = 140_000L,
        rpCost = 80,
        isUnlocked = false,
        unlocksDescription = "6500 mAh Si/C + 150W Şarj"
      ),
      ResearchTech(
        id = "tech_1inch_sensor",
        title = "1 İnç Devasa Sensör & Zeiss Optik",
        category = "Kamera",
        requiredYear = 2025,
        description = "Profesyonel DSLR fotoğraf makinesi seviyesinde ışık toplama.",
        cashCost = 180_000L,
        rpCost = 90,
        isUnlocked = false,
        unlocksDescription = "Zeiss Dörtlü 1\" Sensör Kamera"
      ),
      ResearchTech(
        id = "tech_ai_npu",
        title = "Yerleşik Yapay Zeka NPU ve Fotoğraf Motoru",
        category = "Yazılım",
        requiredYear = 2022,
        description = "Tensor çipleri ve kamera yapay zekasını sisteme entegre eder.",
        cashCost = 60_000L,
        rpCost = 45,
        isUnlocked = false,
        unlocksDescription = "Google Tensor & AI Kamera Motoru"
      )
    )
  }

  fun getInitialCompetitors(): List<Competitor> {
    return listOf(
      Competitor("Samstar", "Güney Kore", Color(0xFF1428A0), 28.5f, "Galaxy S20 / S24", 999, 88),
      Competitor("Pear", "ABD", Color(0xFF707070), 26.0f, "iFruit 12 / 16", 1099, 92),
      Competitor("MiGlobal", "Çin", Color(0xFFFF6900), 18.2f, "Mi 10 / Mi 14", 699, 82),
      Competitor("OneSpeed", "Çin", Color(0xFFEB0029), 9.5f, "Speed 8 / 12", 599, 79),
      Competitor("Diğer Üreticiler", "Global", Color(0xFF4A4E69), 17.8f, "Çeşitli Modeller", 350, 68)
    )
  }

  data class BenchmarkResult(
    val performance: Int,
    val display: Int,
    val camera: Int,
    val battery: Int,
    val build: Int,
    val overall: Int,
    val bottleneckWarning: String? = null
  )

  fun calculateBenchmark(
    processor: Processor,
    ram: RamOption,
    storage: StorageOption,
    displayPanel: DisplayPanel,
    displayResolution: DisplayResolution,
    refreshRate: RefreshRateOption,
    camera: CameraSetup,
    battery: BatterySetup,
    material: MaterialOption,
    software: List<SoftwareOption>
  ): BenchmarkResult {
    var rawPerf = (processor.cpuScore * 0.50f + processor.gpuScore * 0.35f + ram.speedScore * 0.15f).roundToInt()

    var bottleneck: String? = null
    // Check bottleneck: High end CPU with low RAM
    if (processor.cpuScore >= 75 && ram.sizeGb <= 4) {
      rawPerf = (rawPerf * 0.80f).roundToInt()
      bottleneck = "Darboğaz: Güçlü ${processor.name} için ${ram.typeName} yetersiz kalıyor!"
    } else if (processor.cpuScore <= 45 && ram.sizeGb >= 16) {
      bottleneck = "Dengesizlik: Giriş seviyesi işlemci için 16GB+ RAM gereksiz maliyet yaratıyor."
    }

    // Display calculation: Panel contrast + Resolution sharpness + Refresh rate smoothness
    val displayScore = ((displayPanel.contrastScore * 0.40f) + (displayResolution.sharpnessScore * 0.35f) + (refreshRate.smoothnessScore * 0.25f)).roundToInt()

    val cameraBonus = if (software.any { it.id == "sw_ai_camera" }) 6 else 0
    val cameraScore = min(100, camera.photoScore + cameraBonus)

    // Battery efficiency check based on Resolution & Refresh rate drain
    val resolutionDrain = displayResolution.powerConsumptionCost / 10.0f
    val refreshDrain = refreshRate.hz / 90.0f
    val drainFactor = max(0.85f, (resolutionDrain * 0.4f + refreshDrain * 0.6f))
    val batteryEfficiency = (battery.batteryScore * (processor.efficiency / 100.0f) / drainFactor).roundToInt()
    val finalBatteryScore = min(100, max(25, batteryEfficiency))

    val buildScore = (material.prestigeScore * 0.65f + material.durabilityScore * 0.35f).roundToInt()

    val swBonus = software.sumOf { it.scoreBonus }
    val weightedOverall = (rawPerf * 0.32f + displayScore * 0.22f + cameraScore * 0.20f + finalBatteryScore * 0.14f + buildScore * 0.12f).roundToInt()
    val finalOverall = min(100, weightedOverall + swBonus)

    return BenchmarkResult(
      performance = rawPerf,
      display = displayScore,
      camera = cameraScore,
      battery = finalBatteryScore,
      build = buildScore,
      overall = finalOverall,
      bottleneckWarning = bottleneck
    )
  }

  fun calculateUnitCost(
    processor: Processor,
    ram: RamOption,
    storage: StorageOption,
    displayPanel: DisplayPanel,
    displayResolution: DisplayResolution,
    refreshRate: RefreshRateOption,
    camera: CameraSetup,
    battery: BatterySetup,
    material: MaterialOption,
    software: List<SoftwareOption>
  ): Int {
    val componentsSum = processor.cost + ram.cost + storage.cost +
        displayPanel.cost + displayResolution.cost + refreshRate.cost +
        camera.cost + battery.cost + material.cost
    val assemblyLabor = 14 // $14 fabrika montaj ve test masrafı
    return componentsSum + assemblyLabor
  }

  fun generateReviews(
    phoneName: String,
    benchmark: BenchmarkResult,
    unitCost: Int,
    retailPrice: Int,
    processor: Processor,
    ram: RamOption,
    camera: CameraSetup,
    displayPanel: DisplayPanel,
    displayResolution: DisplayResolution,
    refreshRate: RefreshRateOption,
    battery: BatterySetup,
    marketing: MarketingTier
  ): Pair<Float, List<ReviewItem>> {
    val fairPrice = (unitCost * 1.35f).roundToInt()
    val priceRatio = retailPrice.toFloat() / max(1, fairPrice)

    // Score base calculation (out of 10)
    var baseScore = (benchmark.overall / 10.0f)

    if (priceRatio < 0.95f) {
      baseScore += 0.8f // Great value!
    } else if (priceRatio > 1.55f) {
      baseScore -= 1.3f // Overpriced
    } else if (priceRatio > 1.25f) {
      baseScore -= 0.5f // Pricey
    }

    if (benchmark.bottleneckWarning != null) {
      baseScore -= 0.5f
    }

    val finalRating = (min(10.0f, max(3.5f, baseScore)) * 10).roundToInt() / 10.0f

    val reviews = mutableListOf<ReviewItem>()

    // TechRadar Review
    val trHeadline: String
    val trComment: String
    val trBadge: String
    if (priceRatio <= 1.05f && benchmark.overall >= 75) {
      trHeadline = "Yılın Fiyat/Performans Canavarı!"
      trComment = "$phoneName, ${processor.name} gücü, ${displayPanel.name} ${displayResolution.name} ekranı ve $retailPrice $ fiyatıyla pazarda dengeleri değiştiriyor."
      trBadge = "F/P Kralı 👑"
    } else if (benchmark.overall >= 88) {
      trHeadline = "Üstün Amiral Gemisi Deneyimi"
      trComment = "${displayResolution.name} çözünürlükteki ${displayPanel.name} paneli ve ${camera.megapixels} kamerasıyla eksiksiz bir tepe model."
      trBadge = "Amiral Gemisi 🏆"
    } else if (priceRatio > 1.4f) {
      trHeadline = "Tasarım İyi Ama Fiyatı El Yakıyor"
      trComment = "Donanım başarılı fakat $retailPrice $ etiketine karşı rakipler daha güçlü alternatifler sunuyor."
      trBadge = "Pahalı Bulundu ⚠️"
    } else {
      trHeadline = "Dengeli ve Kararlı Günlük Telefon"
      trComment = "${processor.brand.displayName} çipi, ${refreshRate.hz}Hz akıcılığı ve ergonomik yapısıyla günlük işlerde çok rahat."
      trBadge = "Dengeli Seçim ⚖️"
    }
    reviews.add(
      ReviewItem(
        mediaOutlet = "TechRadar Global",
        reviewerName = "Marcus Bell",
        score = min(10.0f, max(4.0f, finalRating + (Random.nextFloat() * 0.4f - 0.2f))),
        headline = trHeadline,
        comment = trComment,
        badge = trBadge
      )
    )

    // GsmEku Review (Hardware Focus)
    val gsmComment = "${processor.name} işlemcisi ve ${ram.typeName} bellek mimarisiyle sentetik testlerde gayet akıcı. ${displayPanel.name} panel ${displayResolution.name} netlik sunuyor."
    reviews.add(
      ReviewItem(
        mediaOutlet = "GsmEku Mobile",
        reviewerName = "Eren Kaya",
        score = min(10.0f, max(4.0f, finalRating + (Random.nextFloat() * 0.4f - 0.2f))),
        headline = "${processor.name} & ${displayResolution.name} İncelemesi",
        comment = gsmComment,
        badge = "Donanım Testi ⚡"
      )
    )

    // TheVerge / DonanımDiyarı
    val outlet3 = if (Random.nextBoolean()) "DonanımDiyarı" else "TheVerge"
    val camVerdict = if (camera.hasPeriscope) {
      "Periskop zoom kamerası konserlerde ve uzak çekimlerde olağanüstü detay yakalıyor."
    } else if (camera.photoScore >= 70) {
      "Kamera renk dengesi ve gece modu sosyal medya üreticileri için çok başarılı."
    } else {
      "Kamera günlük anlık kareler için yeterli ancak düşük ışıkta gürültü üretiyor."
    }
    reviews.add(
      ReviewItem(
        mediaOutlet = outlet3,
        reviewerName = "Nilay Patel",
        score = min(10.0f, max(4.0f, finalRating + (Random.nextFloat() * 0.5f - 0.25f))),
        headline = "$phoneName Detaylı Kullanıcı Deneyimi",
        comment = "$camVerdict ${battery.capacityMah} mAh batarya günü rahat tamamlatıyor.",
        badge = "Kullanıcı Deneyimi 📱"
      )
    )

    return Pair(finalRating, reviews)
  }

  fun simulateWeeklySales(
    phone: PhoneModel,
    company: Company
  ): Long {
    if (phone.isDiscontinued) return 0L

    val weekAge = phone.weeksOnMarket
    // Lifespan is around 18 weeks
    if (weekAge > 18) {
      phone.isDiscontinued = true
      return 0L
    }

    val ageMultiplier = when (weekAge) {
      0 -> 1.4f // Pre-orders
      1 -> 1.7f // Launch week peak
      2 -> 1.5f
      3 -> 1.3f
      4 -> 1.1f
      in 5..8 -> 0.85f
      in 9..13 -> 0.60f
      else -> 0.30f
    }

    val scoreFactor = (phone.criticScore / 10.0f) * (phone.criticScore / 10.0f)
    val marketingBoost = phone.marketingTier.hypeMultiplier
    val reputationBoost = (company.reputation / 100.0f)

    // Slower, balanced market volume per price category:
    val priceSegmentVolume = when {
      phone.retailPrice <= 250 -> 6_500f
      phone.retailPrice <= 450 -> 4_800f
      phone.retailPrice <= 750 -> 3_200f
      phone.retailPrice <= 1050 -> 2_100f
      else -> 1_300f
    }

    val fairPrice = (phone.unitCost * 1.35f)
    val valueAppeal = max(0.45f, min(1.8f, (fairPrice / phone.retailPrice)))

    val units = (priceSegmentVolume * scoreFactor * marketingBoost * reputationBoost * ageMultiplier * valueAppeal).roundToInt()
    val variance = (Random.nextFloat() * 0.16f - 0.08f)
    val finalUnits = max(80L, (units * (1.0f + variance)).toLong())

    return finalUnits
  }
}
