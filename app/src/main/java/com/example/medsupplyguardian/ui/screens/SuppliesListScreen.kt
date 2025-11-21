package com.example.medsupplyguardian.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medsupplyguardian.data.SupplyItem
import com.example.medsupplyguardian.ui.viewmodel.MainViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuppliesListScreen(onItemClick: (Int)->Unit) {
    val vm: MainViewModel = viewModel()
    val supplies by vm.supplies.collectAsState()
    var query by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Supplies") }) }, floatingActionButton = {}) { padding ->
        Column(modifier = Modifier.padding(16.dp).padding(padding)) {
            OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Search items...") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            // Filter chips simplified
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text("Category") })
                AssistChip(onClick = {}, label = { Text("Location") })
                AssistChip(onClick = {}, label = { Text("Risk") })
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val list = if (query.isBlank()) supplies else supplies.filter { it.name.contains(query, ignoreCase = true) }
                items(list) { item ->
                    ElevatedCard(modifier = Modifier.fillMaxWidth().clickable { onItemClick(item.itemId) }) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(item.name, style = MaterialTheme.typography.titleMedium)
                            Text("${item.category} • ${item.location}", style = MaterialTheme.typography.bodyMedium)
                            Text("Qty: ${item.currentQuantity} / Min: ${item.minimumRequired}", style = MaterialTheme.typography.bodyLarge)
                            AssistChip(onClick = {}, label = { Text(item.riskLevel) })
                        }
                    }
                }
            }
        }
    }
}
