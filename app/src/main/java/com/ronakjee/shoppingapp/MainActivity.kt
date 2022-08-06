package com.ronakjee.shoppingapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), GroceryRVAdapter.GroceryItemClickInterface {

    private lateinit var itemsRV: RecyclerView
    private lateinit var addFAB: FloatingActionButton
    private lateinit var list: List<GroceryItems>
    private lateinit var groceryRVAdapter: GroceryRVAdapter
    private lateinit var groceryViewModel: GroceryViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRV = findViewById(R.id.recyclerViewItems)
        addFAB = findViewById(R.id.addItemsForGrocery)
        list = ArrayList()

        groceryRVAdapter = GroceryRVAdapter(list,this)
        itemsRV.layoutManager = LinearLayoutManager(this@MainActivity)
        itemsRV.adapter = groceryRVAdapter
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this,factory)[GroceryViewModel::class.java]
        groceryViewModel.getAllGroceryItems().observe(this) {
            groceryRVAdapter.list = it
            groceryRVAdapter.notifyDataSetChanged()
        }
        addFAB.setOnClickListener{
            openDialog()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)

        val cancelBtn = dialog.findViewById<Button>(R.id.btnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.btnAdd)
        val itemEditName = dialog.findViewById<EditText>(R.id.editItemName)
        val itemEditQuantity = dialog.findViewById<EditText>(R.id.editItemQuantity)
        val itemEditPrice = dialog.findViewById<EditText>(R.id.editItemPrice)

        cancelBtn.setOnClickListener{
            dialog.dismiss()
        }

        addBtn.setOnClickListener{
            val itmName: String = itemEditName.text.toString()
            val itmQuantity: String = itemEditQuantity.text.toString()
            val itmPrice: String = itemEditPrice.text.toString()

            val qty: Int = itmQuantity.toInt()
            val pr: Int = itmPrice.toInt()

            if (itmName.isNotEmpty() && itmQuantity.isNotEmpty() && itmPrice.isNotEmpty()){

                val items = GroceryItems(itmName, qty, pr)
                groceryViewModel.insert(items)
                Toast.makeText(applicationContext,"Item Inserted..", Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {

                Toast.makeText(applicationContext,"Please Enter All the Data", Toast.LENGTH_LONG).show()
            }
        }
        dialog.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext,"Item Deleted..", Toast.LENGTH_SHORT).show()
    }
}