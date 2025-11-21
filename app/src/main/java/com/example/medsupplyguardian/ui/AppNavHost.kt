package com.example.medsupplyguardian.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.medsupplyguardian.ui.screens.*

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "startup") {
        composable("startup") { StartupScreen(onLoaded = { navController.navigate("home"){ popUpTo("startup") { inclusive = true } } }) }
        composable("home") { HomeScreen(onViewSupplies = { navController.navigate("supplies") }, onStartAudit = { navController.navigate("audit/start") }, onSettings = { navController.navigate("settings") }) }
        composable("supplies") { SuppliesListScreen(onItemClick = { id -> navController.navigate("supply/$id") }) }
        composable("supply/{id}", arguments = listOf(navArgument("id"){ type = NavType.IntType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            SupplyDetailScreen(itemId = id)
        }
        composable("audit/start") { AuditStartScreen(onBegin = { navController.navigate("audit/step/1") }) }
        composable("audit/step/{stepNumber}", arguments = listOf(navArgument("stepNumber"){ type = NavType.IntType })) { backStackEntry ->
            val step = backStackEntry.arguments?.getInt("stepNumber") ?: 1
            AuditStepScreen(stepNumber = step, onNext = { next -> navController.navigate("audit/step/$next") }, onSummary = { navController.navigate("audit/summary") })
        }
        composable("audit/summary") { AuditSummaryScreen(onUploadComplete = { navController.navigate("home") }) }
        composable("settings") { SettingsScreen() }
    }
}
