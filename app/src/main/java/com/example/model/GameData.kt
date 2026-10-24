package com.example.model

import androidx.compose.ui.graphics.Color

data class Company(
  val name: String = "Eku Mobile",
  val balance: Long = 350_000L,
  val reputation: Int = 100, // 0 - 1000
  val fans: Long = 10_000L,
  val researchPoints: Int = 40,
  val officeLevel: Int = 1,
  val week: Int = 1,
  val year: Int = 2020, // Oyuna 2020 yılında başlanır!
  val totalPhonesLaunched: Int = 0,
  val totalUnitsSold: Long = 0L,
  val totalRevenue: Long = 0L
) {
  val officeTitle: String
    get() = when (officeLevel) {
      1 -> "Garaj Ofisi"
      2 -> "Küçük Teknoloji Atölyesi"
      3 -> "Modern İnovasyon Ofisi"
      4 -> "Global AR-GE Merkezi"
      else -> "Silikon Vadisi Kampüsü"
    }

  val maxActivePhones: Int
    get() = officeLevel + 1
}

data class ReviewItem(
  val mediaOutlet: String,
  val reviewerName: String,
  val score: Float, // 1.0 - 10.0
  val headline: String,
  val comment: String,
  val badge: String
)

enum class MarketingTier(
  val displayName: String,
  val cost: Long,
  val hypeMultiplier: Float,
  val description: String
) {
  ORGANIC("Organik Ağızdan Ağıza", 0L, 1.0f, "Ek bütçe yok, tamamen topluluk ve kulaktan kulağa."),
  SOCIAL_MEDIA("Sosyal Medya & Tanıtım", 20_000L, 1.7f, "İnternet reklamları ve teknoloji forumları kampanyası."),
  TECH_INFLUENCERS("YouTuber & Basın İnceleme Sponsorluğu", 60_000L, 3.0f, "Büyük teknoloji kanallarına inceleme kutuları."),
  GLOBAL_KEYNOTE("Global Canlı Lansman Etkinliği", 160_000L, 5.0f, "Salonda binlerce izleyiciyle canlı yayın lansmanı.")
}

data class PhoneModel(
  val id: String,
  val name: String,
  val releaseWeek: Int,
  val releaseYear: Int,

  // Selected Hardware (Separated Panel & Resolution)
  val processor: Processor,
  val ram: RamOption,
  val storage: StorageOption,
  val displayPanel: DisplayPanel,
  val displayResolution: DisplayResolution,
  val refreshRate: RefreshRateOption,
  val camera: CameraSetup,
  val battery: BatterySetup,
  val material: MaterialOption,
  val color: PhoneColor,
  val softwareFeatures: List<SoftwareOption>,

  // Financials
  val unitCost: Int,
  val retailPrice: Int,
  val marketingTier: MarketingTier,

  // Performance Scores
  val performanceScore: Int,
  val displayScore: Int,
  val cameraScore: Int,
  val batteryScore: Int,
  val buildScore: Int,
  val overallScore: Int,

  // Review
  val criticScore: Float,
  val reviews: List<ReviewItem>,

  // Market status
  var weeksOnMarket: Int = 0,
  var weeklySoldUnits: Long = 0L,
  var totalSoldUnits: Long = 0L,
  var totalRevenue: Long = 0L,
  var totalProfit: Long = 0L,
  var isDiscontinued: Boolean = false
) {
  val profitPerUnit: Int get() = retailPrice - unitCost
}

data class ResearchTech(
  val id: String,
  val title: String,
  val category: String, // İşlemci, Ekran, Çözünürlük, Kamera, Donanım, Malzeme, Yazılım
  val requiredYear: Int, // Bu teknoloji en erken bu yılda araştırılabilir
  val description: String,
  val cashCost: Long,
  val rpCost: Int,
  val isUnlocked: Boolean = false,
  val unlocksDescription: String
)

data class Competitor(
  val name: String,
  val country: String,
  val brandColor: Color,
  val marketSharePercent: Float,
  val currentFlagship: String,
  val flagshipPrice: Int,
  val flagshipScore: Int
)

data class NewsItem(
  val week: Int,
  val year: Int,
  val title: String,
  val body: String,
  val isPositive: Boolean
)
