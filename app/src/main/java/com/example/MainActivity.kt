package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.viewmodel.AppTab
import com.example.viewmodel.TycoonViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        TycoonApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TycoonApp(viewModel: TycoonViewModel = viewModel()) {
  val company by viewModel.company.collectAsState()
  val currentTab by viewModel.currentTab.collectAsState()
  val draft by viewModel.draft.collectAsState()
  val activePhones by viewModel.activePhones.collectAsState()
  val archivedPhones by viewModel.archivedPhones.collectAsState()
  val techTree by viewModel.techTree.collectAsState()
  val competitors by viewModel.competitors.collectAsState()
  val newsFeed by viewModel.newsFeed.collectAsState()
  val lastLaunchedPhone by viewModel.lastLaunchedPhone.collectAsState()
  val snackMessage by viewModel.snackMessage.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(snackMessage) {
    snackMessage?.let {
      snackbarHostState.showSnackbar(it)
      viewModel.clearSnack()
    }
  }

  BackHandler(enabled = currentTab != AppTab.DASHBOARD) {
    viewModel.setTab(AppTab.DASHBOARD)
  }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = TechDarkBg,
    snackbarHost = { SnackbarHost(snackbarHostState) },
    contentWindowInsets = WindowInsets.safeDrawing,
    bottomBar = {
      NavigationBar(
        containerColor = TechSurface,
        tonalElevation = 8.dp,
        windowInsets = WindowInsets.navigationBars
      ) {
        AppTab.values().forEach { tab ->
          val isSelected = currentTab == tab
          NavigationBarItem(
            selected = isSelected,
            onClick = { viewModel.setTab(tab) },
            icon = {
              Icon(
                imageVector = when (tab) {
                  AppTab.DASHBOARD -> Icons.Default.Dashboard
                  AppTab.STUDIO -> Icons.Default.Smartphone
                  AppTab.RESEARCH -> Icons.Default.Science
                  AppTab.MARKET -> Icons.Default.Leaderboard
                  AppTab.HISTORY -> Icons.Default.History
                },
                contentDescription = tab.title
              )
            },
            label = {
              Text(
                text = tab.title,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = CyanNeonDark,
              selectedTextColor = CyanNeon,
              indicatorColor = CyanNeon,
              unselectedIconColor = TextMuted,
              unselectedTextColor = TextMuted
            )
          )
        }
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      when (currentTab) {
        AppTab.DASHBOARD -> {
          DashboardScreen(
            company = company,
            activePhones = activePhones,
            newsFeed = newsFeed,
            onAdvanceWeeks = { viewModel.advanceWeeks(it) },
            onNavigateToStudio = { viewModel.setTab(AppTab.STUDIO) },
            onDiscontinuePhone = { viewModel.discontinuePhone(it) },
            onUpgradeOffice = { viewModel.upgradeOffice() },
            onSaveGame = { viewModel.saveGameExplicitly() },
            onResetGame = { viewModel.resetGame() }
          )
        }

        AppTab.STUDIO -> {
          StudioScreen(
            draft = draft,
            currentYear = company.year,
            isComponentAvailable = { year, tech -> viewModel.isComponentAvailable(year, tech) },
            onNameChange = { viewModel.updateDraftName(it) },
            onSelectProcessor = { viewModel.selectProcessor(it) },
            onSelectRam = { viewModel.selectRam(it) },
            onSelectStorage = { viewModel.selectStorage(it) },
            onSelectDisplayPanel = { viewModel.selectDisplayPanel(it) },
            onSelectDisplayResolution = { viewModel.selectDisplayResolution(it) },
            onSelectRefreshRate = { viewModel.selectRefreshRate(it) },
            onSelectCamera = { viewModel.selectCamera(it) },
            onSelectBattery = { viewModel.selectBattery(it) },
            onSelectMaterial = { viewModel.selectMaterial(it) },
            onSelectColor = { viewModel.selectColor(it) },
            onToggleSoftware = { viewModel.toggleSoftware(it) },
            onUpdatePrice = { viewModel.updateRetailPrice(it) },
            onSelectMarketing = { viewModel.selectMarketingTier(it) },
            onLaunchPhone = { viewModel.launchPhone() }
          )
        }

        AppTab.RESEARCH -> {
          ResearchScreen(
            company = company,
            techTree = techTree,
            onUnlockTech = { viewModel.unlockTech(it) }
          )
        }

        AppTab.MARKET -> {
          MarketScreen(
            company = company,
            competitors = competitors,
            activePhones = activePhones
          )
        }

        AppTab.HISTORY -> {
          HistoryScreen(
            company = company,
            activePhones = activePhones,
            archivedPhones = archivedPhones
          )
        }
      }

      lastLaunchedPhone?.let { launched ->
        LaunchReviewDialog(
          phone = launched,
          onDismiss = {
            viewModel.dismissLaunchDialog()
            viewModel.setTab(AppTab.DASHBOARD)
          }
        )
      }
    }
  }
}
