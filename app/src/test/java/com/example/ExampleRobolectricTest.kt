package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.HardwareCatalog
import com.example.repository.GameRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Eku Phone Tycoon", appName)
  }

  @Test
  fun `verify hardware catalog and simulation`() {
    val processor = HardwareCatalog.processors.first()
    val ram = HardwareCatalog.ramOptions.first()
    val storage = HardwareCatalog.storageOptions.first()
    val panel = HardwareCatalog.displayPanels.first()
    val res = HardwareCatalog.displayResolutions.first()
    val hz = HardwareCatalog.refreshRates.first()
    val camera = HardwareCatalog.cameraSetups.first()
    val battery = HardwareCatalog.batterySetups.first()
    val material = HardwareCatalog.materialOptions.first()

    val cost = com.example.engine.SimulationEngine.calculateUnitCost(
      processor, ram, storage, panel, res, hz, camera, battery, material, emptyList()
    )
    assertTrue(cost > 0)

    val benchmark = com.example.engine.SimulationEngine.calculateBenchmark(
      processor, ram, storage, panel, res, hz, camera, battery, material, emptyList()
    )
    assertTrue(benchmark.overall in 1..100)
  }

  @Test
  fun `verify game repository save and load`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val repo = GameRepository(context)

    val testCompany = com.example.model.Company(
      name = "Test Eku",
      balance = 450_000L,
      year = 2021,
      week = 15
    )

    repo.saveGame(
      company = testCompany,
      activePhones = emptyList(),
      archivedPhones = emptyList(),
      techTree = com.example.engine.SimulationEngine.getInitialTechTree(),
      competitors = com.example.engine.SimulationEngine.getInitialCompetitors(),
      newsFeed = emptyList()
    )

    val loaded = repo.loadGame()
    assertNotNull(loaded)
    assertEquals("Test Eku", loaded?.company?.name)
    assertEquals(2021, loaded?.company?.year)
    assertEquals(15, loaded?.company?.week)
  }
}
