package com.developerscracks.searchviewactionbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.developerscracks.searchviewactionbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var mainMenu: Menu? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SimpleAdapter

    private lateinit var filteredList: MutableList<Item>
    private var newText: String = ""

    val listaElementos = mutableListOf(
        Item("Facebook"),
        Item("Instagram"),
        Item("WhatApp"),
        Item("Titok"),
        Item("Youtube"),
        Item("Twitter"),
        Item("Telegram")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filteredList = listaElementos

        adapter = SimpleAdapter(filteredList){ show->
            showDeleteMenu(show)
        }

        binding.rvElements.adapter = adapter


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        mainMenu = menu
        menuInflater.inflate(R.menu.menu_action_bar, mainMenu)
        showDeleteMenu(false)

        val buscar = menu?.findItem(R.id.buscador)
        val searchView = buscar?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                this@MainActivity.newText = newText
                filteredList = listaElementos.filter { item ->
                    item.name.contains(newText, ignoreCase = true)
                }.toMutableList()

                adapter.updateItems(filteredList)

                return true
            }
        })
        return true
    }

    fun showDeleteMenu(show:Boolean){
        mainMenu?.findItem(R.id.borrar)?.isVisible = show
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.borrar -> {
                delete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun delete() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Eliminar")
        alertDialog.setMessage("¿Quieres borrar los items?")
        alertDialog.setPositiveButton("Borrar") { _, _ ->
            val selectedIndices = adapter.getSelectedIndices()

            val tempList = listaElementos.toMutableList() // Copia de la lista original

            val newList = tempList.filterIndexed { index, _ ->
                index !in selectedIndices
            }

            filteredList = newList.filter { item ->
                item.name.contains(newText, ignoreCase = true)
            }.toMutableList()

            // Actualizar la lista original con la nueva lista
            listaElementos.clear()
            listaElementos.addAll(newList)

            adapter.updateItems(filteredList)
            adapter.notifyDataSetChanged()

            showDeleteMenu(false)
        }
        alertDialog.setNegativeButton("Cancel") { _, _ ->

        }
        alertDialog.show()
    }
}