package com.example.medsupplyguardian.data

import kotlinx.coroutines.flow.Flow

class Repository(private val dao: SupplyDao) {
    fun getAll() : Flow<List<SupplyItem>> = dao.getAllItems()
    fun search(q: String): Flow<List<SupplyItem>> = dao.searchItems(q)
    fun filterCategory(cat: String): Flow<List<SupplyItem>> = dao.filterByCategory(cat)
    fun filterRisk(risk: String): Flow<List<SupplyItem>> = dao.filterByRisk(risk)
    suspend fun updateQuantity(id: Int, newQ: Int) = dao.updateQuantity(id,newQ)
    suspend fun insert(item: SupplyItem) = dao.insertItem(item)
    suspend fun delete(item: SupplyItem) = dao.deleteItem(item)
}
