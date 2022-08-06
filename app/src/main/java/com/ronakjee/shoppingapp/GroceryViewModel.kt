package com.ronakjee.shoppingapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceryViewModel(private val repository: GroceryRepository): ViewModel(){

    @OptIn(DelicateCoroutinesApi::class)
    fun insert(items: GroceryItems) = GlobalScope.launch {
        repository.insert(items)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delete(items: GroceryItems) = GlobalScope.launch {
        repository.delete(items)
    }

    fun getAllGroceryItems() = repository.getAllItems()

}