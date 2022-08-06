package com.ronakjee.shoppingapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GroceryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: GroceryItems)

    @Delete
    suspend fun delete(item: GroceryItems)

    @Query("SELECT * FROM grocery_items")
    fun getAllGroceryItems(): LiveData<List<GroceryItems>>

}