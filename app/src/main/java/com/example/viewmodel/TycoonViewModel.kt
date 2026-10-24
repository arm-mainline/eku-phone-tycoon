package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.engine.SimulationEngine
import com.example.model.*
import com.example.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

enum class AppTab(val title: String, val iconName: String) {
  DASHBOARD("Ofis", "Dashboard"),
  STUDIO("Tasarla", "Build"),
  RESEARCH("AR-GE", "Science"),
  MARKET("Pazar", "BarChart"),
  HISTORY("Geçmiş", "History")
}

data class StudioDraft(
  val name: String = "Eku Prime 1",
  val processor: Processor = HardwareCatalog.processors[0], // 2020: Helio G90T
  val ram: RamOption = HardwareCatalog.ramOptions[0],       // 4GB
  val storage: StorageOption = HardwareCatalog.storageOptions[0], // 64GB
  val displayPanel: DisplayPanel = HardwareCatalog.displayPanels[0], // IPS LCD
  val displayResolution: DisplayResolution = HardwareCatalog.displayResolutions[0], // HD+
  val refreshRate: RefreshRateOption = HardwareCatalog.refreshRates[0], // 60Hz
  val camera: CameraSetup = HardwareCatalog.cameraSetups[0],     // 13MP
  val battery: BatterySetup = HardwareCatalog.batterySetups[0],   // 4000mAh 18W
  val material: MaterialOption = HardwareCatalog.materialOptions[0], // Mat polikarbonat
  val color: PhoneColor = HardwareCatalog.colors[0],
  val selectedSoftwareIds: Set<String> = setOf("sw_clean_os"),
  val retailPrice: Int = 149,
  val marketingTier: MarketingTier = MarketingTier.ORGANIC
) {
  val unitCost: Int
    get() = SimulationEngine.calculateUnitCost(
      processor, ram, storage, displayPanel, displayResolution, refreshRate,
      camera, battery, material,
      HardwareCatalog.softwareFeatures.filter { selectedSoftwareIds.contains(it.id) }
    )

  val benchmark: SimulationEngine.BenchmarkResult
    get() = SimulationEngine.calculateBenchmark(
      processor, ram, storage, displayPanel, displayResolution, refreshRate,
      camera, battery, material,
      HardwareCatalog.softwareFeatures.filter { selectedSoftwareIds.contains(it.id) }
    )

  val recommendedPrice: Int
    get() = (unitCost * 1.35f).roundToInt()

  val profitPerUnit: Int
    get() = retailPrice - unitCost
}

class TycoonViewModel(application: Application) : AndroidViewModel(application) {

  private val repository = GameRepository(application)

  private val _company = MutableStateFlow(Company())
  val company: StateFlow<Company> = _company.asStateFlow()

  private val _currentTab = MutableStateFlow(AppTab.DASHBOARD)
  val currentTab: StateFlow<AppTab> = _currentTab.asStateFlow()

  private val _draft = MutableStateFlow(StudioDraft())
  val draft: StateFlow<StudioDraft> = _draft.asStateFlow()

  private val _activePhones = MutableStateFlow<List<PhoneModel>>(emptyList())
  val activePhones: StateFlow<List<PhoneModel>> = _activePhones.asStateFlow()

  private val _archivedPhones = MutableStateFlow<List<PhoneModel>>(emptyList())
  val archivedPhones: StateFlow<List<PhoneModel>> = _archivedPhones.asStateFlow()

  private val _techTree = MutableStateFlow(SimulationEngine.getInitialTechTree())
  val techTree: StateFlow<List<ResearchTech>> = _techTree.asStateFlow()

  private val _competitors = MutableStateFlow(SimulationEngine.getInitialCompetitors())
  val competitors: StateFlow<List<Competitor>> = _competitors.asStateFlow()

  private val _newsFeed = MutableStateFlow<List<NewsItem>>(
    listOf(
      NewsItem(
        week = 1,
        year = 2020,
        title = "Eku Mobile 2020 Yılında Faaliyete Başladı!",
        body = "Yeni teknoloji girişimi Eku Mobile, 2020 yılında ilk akıllı telefonunu tasarlamak için yola çıktı.",
        isPositive = true
      )
    )
  )
  val newsFeed: StateFlow<List<NewsItem>> = _newsFeed.asStateFlow()

  private val _lastLaunchedPhone = MutableStateFlow<PhoneModel?>(null)
  val lastLaunchedPhone: StateFlow<PhoneModel?> = _lastLaunchedPhone.asStateFlow()

  private val _snackMessage = MutableStateFlow<String?>(null)
  val snackMessage: StateFlow<String?> = _snackMessage.asStateFlow()

  init {
    loadSavedGame()
  }

  private fun loadSavedGame() {
    val saved = repository.loadGame()
    if (saved != null) {
      _company.value = saved.company
      _activePhones.value = saved.activePhones
      _archivedPhones.value = saved.archivedPhones
      _techTree.update { list ->
        list.map { tech ->
          if (saved.unlockedTechIds.contains(tech.id)) tech.copy(isUnlocked = true) else tech
        }
      }
      _competitors.value = saved.competitors
      _newsFeed.value = saved.newsFeed

      // Adjust draft name
      val nextNum = saved.company.totalPhonesLaunched + 1
      _draft.update { it.copy(name = "Eku Model $nextNum") }
    }
  }

  fun saveGameExplicitly() {
    saveCurrentState()
    _snackMessage.value = "Oyun başarıyla kaydedildi!"
  }

  private fun saveCurrentState() {
    repository.saveGame(
      company = _company.value,
      activePhones = _activePhones.value,
      archivedPhones = _archivedPhones.value,
      techTree = _techTree.value,
      competitors = _competitors.value,
      newsFeed = _newsFeed.value
    )
  }

  fun setTab(tab: AppTab) {
    _currentTab.value = tab
  }

  fun dismissLaunchDialog() {
    _lastLaunchedPhone.value = null
  }

  fun clearSnack() {
    _snackMessage.value = null
  }

  // Draft Mutations
  fun updateDraftName(newName: String) {
    _draft.update { it.copy(name = newName) }
  }

  fun selectProcessor(processor: Processor) {
    _draft.update {
      val newDraft = it.copy(processor = processor)
      newDraft.copy(retailPrice = newDraft.recommendedPrice)
    }
  }

  fun selectRam(ram: RamOption) {
    _draft.update {
      val newDraft = it.copy(ram = ram)
      newDraft.copy(retailPrice = newDraft.recommendedPrice)
    }
  }

  fun selectStorage(storage: StorageOption) {
    _draft.update {
      val newDraft = it.copy(storage = storage)
      newDraft.copy(retailPrice = newDraft.recommendedPrice)
    }
  }

  fun selectDisplayPanel(panel: DisplayPanel) {
    _draft.update {
      val newDraft = it.copy(displayPanel = panel)
      newDraft.copy(retailPrice = newDraft.recommendedPrice)
    }
  }

  fun selectDisplayResolution(res: DisplayResolution) {
    _draft.update {
      val newDraft = it.copy(displayResolution = res)
      newDraft.copy(retailPrice = newDraft.recommendedPrice)
    }
  }

  fun selectRefreshRate(hz: RefreshRateOption) {
    _draft.update {
      val newDraft = it.copy(refreshRate = hz)
      newDraft.copy(retailPrice = newDraft.recommendedPrice)
    }
  }

  fun selectCamera(camera: CameraSetup) {
    _draft.update {
      val newDraft = it.copy(camera = camera)
      newDraft.copy(retailPrice = newDraft.recommendedPrice)
    }
  }

  fun selectBattery(battery: BatterySetup) {
    _draft.update {
      val newDraft = it.copy(battery = battery)
      newDraft.copy(retailPrice = newDraft.recommendedPrice)
    }
  }

  fun selectMaterial(material: MaterialOption) {
    _draft.update {
      val newDraft = it.copy(material = material)
      newDraft.copy(retailPrice = newDraft.recommendedPrice)
    }
  }

  fun selectColor(color: PhoneColor) {
    _draft.update { it.copy(color = color) }
  }

  fun toggleSoftware(softwareId: String) {
    _draft.update {
      val current = it.selectedSoftwareIds.toMutableSet()
      if (current.contains(softwareId)) {
        current.remove(softwareId)
      } else {
        current.add(softwareId)
      }
      it.copy(selectedSoftwareIds = current)
    }
  }

  fun updateRetailPrice(newPrice: Int) {
    _draft.update { it.copy(retailPrice = max(20, newPrice)) }
  }

  fun selectMarketingTier(tier: MarketingTier) {
    _draft.update { it.copy(marketingTier = tier) }
  }

  // Launch Phone
  fun launchPhone(): Boolean {
    val currentCompany = _company.value
    val currentDraft = _draft.value

    if (_activePhones.value.size >= currentCompany.maxActivePhones) {
      _snackMessage.value = "Ofis kapasiteniz dolu! (Maksimum ${currentCompany.maxActivePhones} aktif model). Eski bir modeli durdurun veya ofisi büyütün."
      return false
    }

    val swCost = HardwareCatalog.softwareFeatures
      .filter { currentDraft.selectedSoftwareIds.contains(it.id) }
      .sumOf { it.developmentCost.toLong() }
    val launchExpenses = currentDraft.marketingTier.cost + swCost + 15_000L

    if (currentCompany.balance < launchExpenses) {
      _snackMessage.value = "Yetersiz bütçe! Lansman için en az $${launchExpenses} gerekli."
      return false
    }

    val benchmark = currentDraft.benchmark
    val (rating, reviews) = SimulationEngine.generateReviews(
      phoneName = currentDraft.name,
      benchmark = benchmark,
      unitCost = currentDraft.unitCost,
      retailPrice = currentDraft.retailPrice,
      processor = currentDraft.processor,
      ram = currentDraft.ram,
      camera = currentDraft.camera,
      displayPanel = currentDraft.displayPanel,
      displayResolution = currentDraft.displayResolution,
      refreshRate = currentDraft.refreshRate,
      battery = currentDraft.battery,
      marketing = currentDraft.marketingTier
    )

    val launchedPhone = PhoneModel(
      id = UUID.randomUUID().toString(),
      name = currentDraft.name,
      releaseWeek = currentCompany.week,
      releaseYear = currentCompany.year,
      processor = currentDraft.processor,
      ram = currentDraft.ram,
      storage = currentDraft.storage,
      displayPanel = currentDraft.displayPanel,
      displayResolution = currentDraft.displayResolution,
      refreshRate = currentDraft.refreshRate,
      camera = currentDraft.camera,
      battery = currentDraft.battery,
      material = currentDraft.material,
      color = currentDraft.color,
      softwareFeatures = HardwareCatalog.softwareFeatures.filter { currentDraft.selectedSoftwareIds.contains(it.id) },
      unitCost = currentDraft.unitCost,
      retailPrice = currentDraft.retailPrice,
      marketingTier = currentDraft.marketingTier,
      performanceScore = benchmark.performance,
      displayScore = benchmark.display,
      cameraScore = benchmark.camera,
      batteryScore = benchmark.battery,
      buildScore = benchmark.build,
      overallScore = benchmark.overall,
      criticScore = rating,
      reviews = reviews
    )

    val firstWeekUnits = SimulationEngine.simulateWeeklySales(launchedPhone, currentCompany)
    val firstWeekRevenue = firstWeekUnits * launchedPhone.retailPrice
    val firstWeekProfit = firstWeekUnits * launchedPhone.profitPerUnit

    launchedPhone.weeklySoldUnits = firstWeekUnits
    launchedPhone.totalSoldUnits = firstWeekUnits
    launchedPhone.totalRevenue = firstWeekRevenue
    launchedPhone.totalProfit = firstWeekProfit
    launchedPhone.weeksOnMarket = 1

    val newBalance = currentCompany.balance - launchExpenses + firstWeekProfit
    val repGain = ((rating - 5.0f) * 10).roundToInt()
    val newReputation = min(1000, max(20, currentCompany.reputation + repGain))
    val fanGain = (firstWeekUnits * 0.12f).toLong()

    _company.value = currentCompany.copy(
      balance = newBalance,
      reputation = newReputation,
      fans = currentCompany.fans + fanGain,
      totalPhonesLaunched = currentCompany.totalPhonesLaunched + 1,
      totalUnitsSold = currentCompany.totalUnitsSold + firstWeekUnits,
      totalRevenue = currentCompany.totalRevenue + firstWeekRevenue
    )

    _activePhones.update { listOf(launchedPhone) + it }
    _lastLaunchedPhone.value = launchedPhone

    val nextNumber = currentCompany.totalPhonesLaunched + 2
    _draft.value = currentDraft.copy(name = "Eku Prime $nextNumber")

    _newsFeed.update {
      listOf(
        NewsItem(
          week = currentCompany.week,
          year = currentCompany.year,
          title = "${launchedPhone.name} Satışa Çıktı!",
          body = "${launchedPhone.processor.name} ve ${launchedPhone.displayPanel.name} (${launchedPhone.displayResolution.name}) özellikli telefon piyasaya girdi. İlk hafta: ${firstWeekUnits} adet, Puan: $rating/10.",
          isPositive = rating >= 7.0f
        )
      ) + it
    }

    saveCurrentState()
    return true
  }

  // Advance weeks (1 week or 4 weeks / 1 month)
  fun advanceWeeks(weeksCount: Int = 1) {
    repeat(weeksCount) {
      stepSingleWeek()
    }
    saveCurrentState()
  }

  private fun stepSingleWeek() {
    val currentCompany = _company.value
    var weeklyTotalProfit = 0L
    var weeklyTotalRevenue = 0L
    var weeklyTotalUnits = 0L

    val updatedActive = mutableListOf<PhoneModel>()
    val newlyArchived = mutableListOf<PhoneModel>()

    _activePhones.value.forEach { phone ->
      if (!phone.isDiscontinued) {
        val soldUnits = SimulationEngine.simulateWeeklySales(phone, currentCompany)
        phone.weeksOnMarket += 1
        phone.weeklySoldUnits = soldUnits
        phone.totalSoldUnits += soldUnits

        val revenue = soldUnits * phone.retailPrice
        val profit = soldUnits * phone.profitPerUnit

        phone.totalRevenue += revenue
        phone.totalProfit += profit

        weeklyTotalProfit += profit
        weeklyTotalRevenue += revenue
        weeklyTotalUnits += soldUnits

        if (phone.isDiscontinued || phone.weeksOnMarket > 18) {
          phone.isDiscontinued = true
          newlyArchived.add(phone)
        } else {
          updatedActive.add(phone)
        }
      } else {
        newlyArchived.add(phone)
      }
    }

    _activePhones.value = updatedActive
    if (newlyArchived.isNotEmpty()) {
      _archivedPhones.update { newlyArchived + it }
    }

    var nextWeek = currentCompany.week + 1
    var nextYear = currentCompany.year
    var yearChanged = false

    if (nextWeek > 52) {
      nextWeek = 1
      nextYear += 1
      yearChanged = true
    }

    val rpGain = currentCompany.officeLevel * 5
    val officeMaintenance = currentCompany.officeLevel * 2500L
    val netWeeklyCash = weeklyTotalProfit - officeMaintenance

    _company.value = currentCompany.copy(
      week = nextWeek,
      year = nextYear,
      balance = max(0L, currentCompany.balance + netWeeklyCash),
      researchPoints = currentCompany.researchPoints + rpGain,
      totalUnitsSold = currentCompany.totalUnitsSold + weeklyTotalUnits,
      totalRevenue = currentCompany.totalRevenue + weeklyTotalRevenue
    )

    if (yearChanged) {
      _newsFeed.update {
        listOf(
          NewsItem(
            week = 1,
            year = nextYear,
            title = "Teknoloji Dünyası $nextYear Yılına Girdi!",
            body = "Yeni yıl ile birlikte mobil sektörde daha gelişmiş işlemciler, ekran panelleri ve kamera teknolojileri pazara sunuluyor.",
            isPositive = true
          )
        ) + it
      }
      _snackMessage.value = "Yeni bir yıla girildi: $nextYear! Yeni teknolojileri inceleyin."
    }

    updateMarketShares()
  }

  private fun updateMarketShares() {
    val totalCompanySales = _company.value.totalUnitsSold
    val ekuShare = min(40.0f, max(2.0f, (totalCompanySales / 18_000f)))

    _competitors.update { list ->
      val remaining = 100.0f - ekuShare
      list.map { comp ->
        val adjustedShare = (comp.marketSharePercent / 100.0f) * remaining
        comp.copy(marketSharePercent = (adjustedShare * 10).roundToInt() / 10.0f)
      }
    }
  }

  fun discontinuePhone(phoneId: String) {
    val phone = _activePhones.value.find { it.id == phoneId } ?: return
    phone.isDiscontinued = true
    _activePhones.update { list -> list.filter { it.id != phoneId } }
    _archivedPhones.update { list -> listOf(phone) + list }
    _snackMessage.value = "${phone.name} satıştan kaldırıldı ve arşive taşındı."
    saveCurrentState()
  }

  fun unlockTech(techId: String): Boolean {
    val tech = _techTree.value.find { it.id == techId } ?: return false
    val currentCompany = _company.value

    if (tech.isUnlocked) {
      _snackMessage.value = "Bu teknoloji zaten açık!"
      return false
    }

    if (currentCompany.year < tech.requiredYear) {
      _snackMessage.value = "Bu teknoloji dönemi henüz gelmedi! (Gereken Yıl: ${tech.requiredYear}, Mevcut Yıl: ${currentCompany.year})"
      return false
    }

    if (currentCompany.balance < tech.cashCost || currentCompany.researchPoints < tech.rpCost) {
      _snackMessage.value = "Yetersiz kaynak! $${tech.cashCost} nakit ve ${tech.rpCost} AR-GE puanı gerekiyor."
      return false
    }

    _company.value = currentCompany.copy(
      balance = currentCompany.balance - tech.cashCost,
      researchPoints = currentCompany.researchPoints - tech.rpCost
    )

    _techTree.update { list ->
      list.map { if (it.id == techId) it.copy(isUnlocked = true) else it }
    }

    _snackMessage.value = "'${tech.title}' teknolojisi başarıyla geliştirildi!"
    saveCurrentState()
    return true
  }

  fun upgradeOffice(): Boolean {
    val currentCompany = _company.value
    if (currentCompany.officeLevel >= 5) {
      _snackMessage.value = "Ofisiniz zaten en yüksek seviyede!"
      return false
    }

    val upgradeCost = when (currentCompany.officeLevel) {
      1 -> 120_000L
      2 -> 350_000L
      3 -> 850_000L
      4 -> 2_000_000L
      else -> 5_000_000L
    }

    if (currentCompany.balance < upgradeCost) {
      _snackMessage.value = "Yetersiz bütçe! Ofis yükseltmesi için $${upgradeCost} gerekli."
      return false
    }

    _company.value = currentCompany.copy(
      balance = currentCompany.balance - upgradeCost,
      officeLevel = currentCompany.officeLevel + 1,
      reputation = currentCompany.reputation + 70
    )

    _snackMessage.value = "Şirket seviyeniz yükseltildi: ${currentCompany.officeTitle}!"
    saveCurrentState()
    return true
  }

  fun isComponentAvailable(releaseYear: Int, techRequired: String?): Boolean {
    val currentYear = _company.value.year
    if (releaseYear > currentYear) return false
    if (techRequired != null) {
      return _techTree.value.find { it.id == techRequired }?.isUnlocked ?: false
    }
    return true
  }

  fun resetGame() {
    repository.clearSave()
    _company.value = Company()
    _activePhones.value = emptyList()
    _archivedPhones.value = emptyList()
    _techTree.value = SimulationEngine.getInitialTechTree()
    _competitors.value = SimulationEngine.getInitialCompetitors()
    _draft.value = StudioDraft()
    _snackMessage.value = "Oyun sıfırlandı, 2020 yılından yeniden başlandı!"
  }
}
