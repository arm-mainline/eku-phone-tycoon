package com.example.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.ui.graphics.Color
import com.example.model.*
import org.json.JSONArray
import org.json.JSONObject

class GameRepository(context: Context) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences("eku_tycoon_save_v1", Context.MODE_PRIVATE)

  data class GameSaveState(
    val company: Company,
    val activePhones: List<PhoneModel>,
    val archivedPhones: List<PhoneModel>,
    val unlockedTechIds: Set<String>,
    val competitors: List<Competitor>,
    val newsFeed: List<NewsItem>
  )

  fun hasSavedGame(): Boolean {
    return prefs.contains(KEY_COMPANY)
  }

  fun saveGame(
    company: Company,
    activePhones: List<PhoneModel>,
    archivedPhones: List<PhoneModel>,
    techTree: List<ResearchTech>,
    competitors: List<Competitor>,
    newsFeed: List<NewsItem>
  ) {
    try {
      val companyJson = JSONObject().apply {
        put("name", company.name)
        put("balance", company.balance)
        put("reputation", company.reputation)
        put("fans", company.fans)
        put("researchPoints", company.researchPoints)
        put("officeLevel", company.officeLevel)
        put("week", company.week)
        put("year", company.year)
        put("totalPhonesLaunched", company.totalPhonesLaunched)
        put("totalUnitsSold", company.totalUnitsSold)
        put("totalRevenue", company.totalRevenue)
      }

      val activeArray = JSONArray()
      activePhones.forEach { activeArray.put(serializePhone(it)) }

      val archivedArray = JSONArray()
      archivedPhones.forEach { archivedArray.put(serializePhone(it)) }

      val unlockedTechsArray = JSONArray()
      techTree.filter { it.isUnlocked }.forEach { unlockedTechsArray.put(it.id) }

      val newsArray = JSONArray()
      newsFeed.take(15).forEach { news ->
        newsArray.put(JSONObject().apply {
          put("week", news.week)
          put("year", news.year)
          put("title", news.title)
          put("body", news.body)
          put("isPositive", news.isPositive)
        })
      }

      prefs.edit()
        .putString(KEY_COMPANY, companyJson.toString())
        .putString(KEY_ACTIVE_PHONES, activeArray.toString())
        .putString(KEY_ARCHIVED_PHONES, archivedArray.toString())
        .putString(KEY_UNLOCKED_TECHS, unlockedTechsArray.toString())
        .putString(KEY_NEWS, newsArray.toString())
        .apply()
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  fun loadGame(): GameSaveState? {
    val companyStr = prefs.getString(KEY_COMPANY, null) ?: return null
    return try {
      val cObj = JSONObject(companyStr)
      val company = Company(
        name = cObj.optString("name", "Eku Mobile"),
        balance = cObj.optLong("balance", 350_000L),
        reputation = cObj.optInt("reputation", 100),
        fans = cObj.optLong("fans", 10_000L),
        researchPoints = cObj.optInt("researchPoints", 40),
        officeLevel = cObj.optInt("officeLevel", 1),
        week = cObj.optInt("week", 1),
        year = cObj.optInt("year", 2020),
        totalPhonesLaunched = cObj.optInt("totalPhonesLaunched", 0),
        totalUnitsSold = cObj.optLong("totalUnitsSold", 0L),
        totalRevenue = cObj.optLong("totalRevenue", 0L)
      )

      val activeList = mutableListOf<PhoneModel>()
      val activeStr = prefs.getString(KEY_ACTIVE_PHONES, null)
      if (activeStr != null) {
        val arr = JSONArray(activeStr)
        for (i in 0 until arr.length()) {
          deserializePhone(arr.getJSONObject(i))?.let { activeList.add(it) }
        }
      }

      val archivedList = mutableListOf<PhoneModel>()
      val archivedStr = prefs.getString(KEY_ARCHIVED_PHONES, null)
      if (archivedStr != null) {
        val arr = JSONArray(archivedStr)
        for (i in 0 until arr.length()) {
          deserializePhone(arr.getJSONObject(i))?.let { archivedList.add(it) }
        }
      }

      val unlockedSet = mutableSetOf<String>()
      val techsStr = prefs.getString(KEY_UNLOCKED_TECHS, null)
      if (techsStr != null) {
        val arr = JSONArray(techsStr)
        for (i in 0 until arr.length()) {
          unlockedSet.add(arr.getString(i))
        }
      }

      val newsList = mutableListOf<NewsItem>()
      val newsStr = prefs.getString(KEY_NEWS, null)
      if (newsStr != null) {
        val arr = JSONArray(newsStr)
        for (i in 0 until arr.length()) {
          val obj = arr.getJSONObject(i)
          newsList.add(
            NewsItem(
              week = obj.optInt("week", 1),
              year = obj.optInt("year", 2020),
              title = obj.optString("title", ""),
              body = obj.optString("body", ""),
              isPositive = obj.optBoolean("isPositive", true)
            )
          )
        }
      }

      GameSaveState(
        company = company,
        activePhones = activeList,
        archivedPhones = archivedList,
        unlockedTechIds = unlockedSet,
        competitors = com.example.engine.SimulationEngine.getInitialCompetitors(),
        newsFeed = if (newsList.isEmpty()) {
          listOf(
            NewsItem(
              week = 1,
              year = 2020,
              title = "Eku Mobile 2020 Yılında Faaliyete Başladı!",
              body = "Yeni akıllı telefon üreticisi Eku Mobile, sektörün ilk 5G ve 4G dönüşüm döneminde sektöre adım attı.",
              isPositive = true
            )
          )
        } else newsList
      )
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }
  }

  fun clearSave() {
    prefs.edit().clear().apply()
  }

  private fun serializePhone(phone: PhoneModel): JSONObject {
    return JSONObject().apply {
      put("id", phone.id)
      put("name", phone.name)
      put("releaseWeek", phone.releaseWeek)
      put("releaseYear", phone.releaseYear)
      put("processorId", phone.processor.id)
      put("ramId", phone.ram.id)
      put("storageId", phone.storage.id)
      put("displayPanelId", phone.displayPanel.id)
      put("displayResolutionId", phone.displayResolution.id)
      put("refreshRateId", phone.refreshRate.id)
      put("cameraId", phone.camera.id)
      put("batteryId", phone.battery.id)
      put("materialId", phone.material.id)
      put("colorId", phone.color.id)
      put("unitCost", phone.unitCost)
      put("retailPrice", phone.retailPrice)
      put("marketingTier", phone.marketingTier.name)
      put("performanceScore", phone.performanceScore)
      put("displayScore", phone.displayScore)
      put("cameraScore", phone.cameraScore)
      put("batteryScore", phone.batteryScore)
      put("buildScore", phone.buildScore)
      put("overallScore", phone.overallScore)
      put("criticScore", phone.criticScore.toDouble())
      put("weeksOnMarket", phone.weeksOnMarket)
      put("weeklySoldUnits", phone.weeklySoldUnits)
      put("totalSoldUnits", phone.totalSoldUnits)
      put("totalRevenue", phone.totalRevenue)
      put("totalProfit", phone.totalProfit)
      put("isDiscontinued", phone.isDiscontinued)

      // Reviews
      val revArr = JSONArray()
      phone.reviews.forEach { r ->
        revArr.put(JSONObject().apply {
          put("outlet", r.mediaOutlet)
          put("reviewer", r.reviewerName)
          put("score", r.score.toDouble())
          put("headline", r.headline)
          put("comment", r.comment)
          put("badge", r.badge)
        })
      }
      put("reviews", revArr)
    }
  }

  private fun deserializePhone(obj: JSONObject): PhoneModel? {
    return try {
      val proc = HardwareCatalog.processors.find { it.id == obj.optString("processorId") }
        ?: HardwareCatalog.processors.first()
      val ram = HardwareCatalog.ramOptions.find { it.id == obj.optString("ramId") }
        ?: HardwareCatalog.ramOptions.first()
      val rom = HardwareCatalog.storageOptions.find { it.id == obj.optString("storageId") }
        ?: HardwareCatalog.storageOptions.first()
      val panel = HardwareCatalog.displayPanels.find { it.id == obj.optString("displayPanelId") }
        ?: HardwareCatalog.displayPanels.first()
      val res = HardwareCatalog.displayResolutions.find { it.id == obj.optString("displayResolutionId") }
        ?: HardwareCatalog.displayResolutions.first()
      val hz = HardwareCatalog.refreshRates.find { it.id == obj.optString("refreshRateId") }
        ?: HardwareCatalog.refreshRates.first()
      val cam = HardwareCatalog.cameraSetups.find { it.id == obj.optString("cameraId") }
        ?: HardwareCatalog.cameraSetups.first()
      val bat = HardwareCatalog.batterySetups.find { it.id == obj.optString("batteryId") }
        ?: HardwareCatalog.batterySetups.first()
      val mat = HardwareCatalog.materialOptions.find { it.id == obj.optString("materialId") }
        ?: HardwareCatalog.materialOptions.first()
      val col = HardwareCatalog.colors.find { it.id == obj.optString("colorId") }
        ?: HardwareCatalog.colors.first()

      val mTierStr = obj.optString("marketingTier", MarketingTier.ORGANIC.name)
      val mTier = try { MarketingTier.valueOf(mTierStr) } catch (_: Exception) { MarketingTier.ORGANIC }

      val revList = mutableListOf<ReviewItem>()
      val revArr = obj.optJSONArray("reviews")
      if (revArr != null) {
        for (i in 0 until revArr.length()) {
          val rObj = revArr.getJSONObject(i)
          revList.add(
            ReviewItem(
              mediaOutlet = rObj.optString("outlet"),
              reviewerName = rObj.optString("reviewer"),
              score = rObj.optDouble("score", 7.0).toFloat(),
              headline = rObj.optString("headline"),
              comment = rObj.optString("comment"),
              badge = rObj.optString("badge")
            )
          )
        }
      }

      PhoneModel(
        id = obj.optString("id"),
        name = obj.optString("name"),
        releaseWeek = obj.optInt("releaseWeek", 1),
        releaseYear = obj.optInt("releaseYear", 2020),
        processor = proc,
        ram = ram,
        storage = rom,
        displayPanel = panel,
        displayResolution = res,
        refreshRate = hz,
        camera = cam,
        battery = bat,
        material = mat,
        color = col,
        softwareFeatures = emptyList(),
        unitCost = obj.optInt("unitCost"),
        retailPrice = obj.optInt("retailPrice"),
        marketingTier = mTier,
        performanceScore = obj.optInt("performanceScore"),
        displayScore = obj.optInt("displayScore"),
        cameraScore = obj.optInt("cameraScore"),
        batteryScore = obj.optInt("batteryScore"),
        buildScore = obj.optInt("buildScore"),
        overallScore = obj.optInt("overallScore"),
        criticScore = obj.optDouble("criticScore", 7.0).toFloat(),
        reviews = revList,
        weeksOnMarket = obj.optInt("weeksOnMarket"),
        weeklySoldUnits = obj.optLong("weeklySoldUnits"),
        totalSoldUnits = obj.optLong("totalSoldUnits"),
        totalRevenue = obj.optLong("totalRevenue"),
        totalProfit = obj.optLong("totalProfit"),
        isDiscontinued = obj.optBoolean("isDiscontinued", false)
      )
    } catch (e: Exception) {
      e.printStackTrace()
      null
    }
  }

  companion object {
    private const val KEY_COMPANY = "save_company"
    private const val KEY_ACTIVE_PHONES = "save_active_phones"
    private const val KEY_ARCHIVED_PHONES = "save_archived_phones"
    private const val KEY_UNLOCKED_TECHS = "save_unlocked_techs"
    private const val KEY_NEWS = "save_news"
  }
}
