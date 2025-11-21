package com.example.medsupplyguardian.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SupplyItem(
    @PrimaryKey(autoGenerate = true) val itemId: Int = 0,
    val name: String,
    val category: String,
    val minimumRequired: Int,
    val currentQuantity: Int,
    val expiryDate: Long?, // epoch millis
    val location: String,
    val riskLevel: String
)
