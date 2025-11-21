package com.example.medsupplyguardian.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay


@Composable
fun StartupScreen(onLoaded: () -> Unit) {
    // Simulate DB load
    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        delay(800) // simulate load
        loading = false
        onLoaded()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (loading) {
            CircularProgressIndicator()
        }
    }
}
