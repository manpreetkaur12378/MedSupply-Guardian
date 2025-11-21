package com.example.medsupplyguardian.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.medsupplyguardian.ui.viewmodel.MainViewModel
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplyDetailScreen(itemId: Int) {
    val vm: MainViewModel = viewModel()
    val supplies by vm.supplies.collectAsState()
    val item = supplies.find { it.itemId == itemId }
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var newQty by remember { mutableStateOf(item?.currentQuantity ?: 0) }
    Scaffold(topBar = { TopAppBar(title = { Text(item?.name ?: "Item") }) }, floatingActionButton = {
        ExtendedFloatingActionButton(text = { Text("Update Quantity") }, onClick = { showDialog = true }, icon = { Icon(Icons.Default.Edit,"edit") })
    }) { padding ->
        Column(modifier = Modifier.padding(16.dp).padding(padding).fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(item?.name ?: "", style = MaterialTheme.typography.titleLarge)
            Text("Category: ${item?.category ?: ""}", style = MaterialTheme.typography.bodyLarge)
            Text("Current: ${item?.currentQuantity ?: 0}", style = MaterialTheme.typography.titleLarge)
            Text("Minimum: ${item?.minimumRequired ?: 0}", style = MaterialTheme.typography.bodyMedium)
            Text("Expiry: ${item?.expiryDate ?: "N/A"}", style = MaterialTheme.typography.bodyMedium)
            Text("Location: ${item?.location ?: ""}", style = MaterialTheme.typography.bodyMedium)
            AssistChip(onClick = {}, label = { Text(item?.riskLevel ?: "") })
        }
    }
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false }, confirmButton = {
            TextButton(onClick = {
                val q = newQty
                if (q >= 0 && item != null) {
                    vm.updateQuantity(item.itemId, q)
                    showDialog = false
                }
            }) { Text("Confirm") }
        }, dismissButton = {
            TextButton(onClick = { showDialog = false }) { Text("Cancel") }
        }, title = { Text("Update Quantity") }, text = {
            Column {
                OutlinedTextField(value = newQty.toString(), onValueChange = { v -> newQty = v.toIntOrNull() ?: newQty }, label = { Text("Quantity") })
                Row {
                    IconButton(onClick = { newQty = (newQty - 1).coerceAtLeast(0) }) { Text("-") }
                    IconButton(onClick = { newQty = newQty + 1 }) { Text("+") }
                }
            }
        })
    }
}
