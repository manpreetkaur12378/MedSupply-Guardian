package com.example.medsupplyguardian.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplyDao {
    @Query("SELECT * FROM SupplyItem ORDER BY name")
    fun getAllItems(): Flow<List<SupplyItem>>

    @Query("SELECT * FROM SupplyItem WHERE name LIKE '%' || :keyword || '%'")
    fun searchItems(keyword: String): Flow<List<SupplyItem>>

    @Query("SELECT * FROM SupplyItem WHERE category = :category")
    fun filterByCategory(category: String): Flow<List<SupplyItem>>

    @Query("SELECT * FROM SupplyItem WHERE riskLevel = :riskLevel")
    fun filterByRisk(riskLevel: String): Flow<List<SupplyItem>>

    @Query("UPDATE SupplyItem SET currentQuantity = :newQuantity WHERE itemId = :id")
    suspend fun updateQuantity(id: Int, newQuantity: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: SupplyItem)

    @Delete
    suspend fun deleteItem(item: SupplyItem)
}
