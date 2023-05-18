package com.developerscracks.searchviewactionbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.developerscracks.searchviewactionbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SimpleAdapter

    val listaElementos = listOf(
        Item("Facebook"),
        Item("Instagram"),
        Item("WhatApp"),
        Item("Titok"),
        Item("Youtube"),
        Item("Twitter"),
        Item("Telegram")
    )

    private lateinit var arrayAdapter: ArrayAdapter<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SimpleAdapter(listaElementos)
        binding.rvElements.adapter = adapter


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_action_bar, menu)

        val buscar = menu?.findItem(R.id.buscador)
        val searchView = buscar?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filteredList = listaElementos.filter { item ->
                    item.name.contains(newText, ignoreCase = true)
                }

                adapter.updateItems(filteredList)

                return true
            }
        })
        return true
    }
}