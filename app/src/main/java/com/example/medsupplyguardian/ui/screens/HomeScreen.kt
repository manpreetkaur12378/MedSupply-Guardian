package com.example.medsupplyguardian.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medsupplyguardian.ui.viewmodel.MainViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onViewSupplies: () -> Unit, onStartAudit: () -> Unit, onSettings: () -> Unit) {
    val vm: MainViewModel = viewModel()
    vm.insertSampleDataIfEmpty()
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text("MedSupply Guardian™") })
    }, content = { padding ->
        Column(modifier = Modifier.padding(16.dp).padding(padding).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(text = "Welcome, ${vm.getStaffName() ?: "Technician"}", style = MaterialTheme.typography.titleLarge)
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Critical Stock Alerts", style = MaterialTheme.typography.titleMedium)
                    Text("3 items require immediate attention")
                }
            }
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Expiring Soon", style = MaterialTheme.typography.titleMedium)
                    Text("1 item expiring within 30 days")
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = onViewSupplies, modifier = Modifier.fillMaxWidth()) { Text("View Supplies") }
                FilledTonalButton(onClick = onStartAudit, modifier = Modifier.fillMaxWidth()) { Text("Start Audit") }
                OutlinedButton(onClick = onSettings, modifier = Modifier.fillMaxWidth()) { Text("Settings") }
            }
        }
    })
}
