package com.example.medsupplyguardian.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import kotlinx.coroutines.delay
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuditStartScreen(onBegin: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Start Audit") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "This audit includes 5 steps: Verify Quantity, Check Expiry, Storage Conditions, " +
                        "Missing/Damaged, Summary."
            )
            Button(onClick = onBegin) {
                Text("Begin Audit")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuditStepScreen(
    stepNumber: Int,
    onNext: (Int) -> Unit,
    onSummary: () -> Unit
) {
    val total = 5

    Scaffold(
        topBar = { TopAppBar(title = { Text("Audit Step $stepNumber / $total") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LinearProgressIndicator(
                progress = stepNumber / total.toFloat(),
                modifier = Modifier.fillMaxWidth()
            )
            Text("Content for step $stepNumber")

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (stepNumber < total) {
                    Button(onClick = { onNext(stepNumber + 1) }) {
                        Text("Next")
                    }
                } else {
                    Button(onClick = onSummary) {
                        Text("Summary")
                    }
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuditSummaryScreen(onUploadComplete: () -> Unit) {
    var uploading by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Audit Summary") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Total Items Reviewed: 10")
                    Text("Items Failing Compliance: 2")
                    Text("Items Expiring Soon: 1")
                    Text("Technician: Example (ID: 0000)")
                }
            }

            Button(onClick = { uploading = true }) {
                Text("Upload Audit Results")
            }
        }

        // Uploading Overlay
        if (uploading) {
            LaunchedEffect(Unit) {
                delay(2000)
                uploading = false
                onUploadComplete()
            }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.6f),
                    color = MaterialTheme.colorScheme.surface
                ) {}

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Uploading audit report...")
                }
            }
        }
    }
}
