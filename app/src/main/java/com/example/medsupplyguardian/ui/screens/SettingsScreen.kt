package com.example.medsupplyguardian.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medsupplyguardian.ui.viewmodel.MainViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val vm: MainViewModel = viewModel()
    var name by remember { mutableStateOf(vm.getStaffName() ?: "") }
    var id by remember { mutableStateOf(vm.getStaffId() ?: "") }
    var sorting by remember { mutableStateOf("Name") }
    var threshold by remember { mutableStateOf(50f) }
    var darkMode by remember { mutableStateOf(false) }
    var reminder by remember { mutableStateOf("24") }

    Scaffold(topBar = { TopAppBar(title = { Text("Settings") }) }) { padding ->
        Column(modifier = Modifier.padding(16.dp).padding(padding), verticalArrangement = Arrangement.spacedBy(24.dp)) {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Staff Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = id, onValueChange = { id = it }, label = { Text("Staff ID") }, modifier = Modifier.fillMaxWidth())
            ExposedDropdownMenuBox(expanded = false, onExpandedChange = {}) {
                Text("Sorting Mode: $sorting")
            }
            Column {
                Text("Alert Threshold: ${threshold.toInt()}")
                Slider(value = threshold, onValueChange = { threshold = it }, valueRange = 0f..100f)
            }
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Text("Theme Mode")
                Switch(checked = darkMode, onCheckedChange = { darkMode = it })
            }
            OutlinedTextField(value = reminder, onValueChange = { reminder = it }, label = { Text("Audit Reminder (hours)") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.weight(1f))
            FilledTonalButton(onClick = { vm.saveStaff(name, id) }, modifier = Modifier.fillMaxWidth()) { Text("Save Settings") }
        }
    }
}
