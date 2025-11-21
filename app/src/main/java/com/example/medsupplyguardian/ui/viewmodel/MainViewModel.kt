package com.example.medsupplyguardian.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.medsupplyguardian.data.AppDatabase
import com.example.medsupplyguardian.data.Repository
import com.example.medsupplyguardian.data.SupplyItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val repo = Repository(db.supplyDao())

    val supplies = repo.getAll().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    // SharedPreferences simple implementation
    private val prefs: SharedPreferences = application.getSharedPreferences("medprefs", Context.MODE_PRIVATE)
    fun saveStaff(name: String, id: String) {
        prefs.edit().putString("staff_name", name).putString("staff_id", id).apply()
    }
    fun getStaffName() = prefs.getString("staff_name", "Technician")
    fun getStaffId() = prefs.getString("staff_id", "0000")

    // CRUD helpers
    fun updateQuantity(id: Int, newQ: Int) {
        viewModelScope.launch {
            repo.updateQuantity(id, newQ)
        }
    }
    fun insertSampleDataIfEmpty() {
        viewModelScope.launch {
            val current = supplies.value
            if (current.isEmpty()) {
                repo.insert(SupplyItem(name="Surgical Mask", category="PPE", minimumRequired=50, currentQuantity=30, expiryDate=null, location="Room A", riskLevel="Elevated"))
                repo.insert(SupplyItem(name="Saline 0.9%", category="Medication", minimumRequired=10, currentQuantity=12, expiryDate=null, location="Pharmacy", riskLevel="Normal"))
                repo.insert(SupplyItem(name="Insulin", category="Medication", minimumRequired=5, currentQuantity=2, expiryDate=null, location="Fridge 1", riskLevel="Critical"))
            }
        }
    }
}
