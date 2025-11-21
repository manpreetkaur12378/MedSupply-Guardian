package com.example.medsupplyguardian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.medsupplyguardian.ui.AppNavHost
import com.example.medsupplyguardian.ui.theme.MedSupplyGuardianTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedSupplyGuardianTheme {
                AppNavHost()
            }
        }
    }
}
