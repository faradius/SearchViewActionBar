package com.developerscracks.searchviewactionbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter(private var itemList: List<Item>) : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

    // ViewHolder representa cada elemento en el RecyclerView
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Aquí puedes obtener referencias a las vistas dentro del elemento
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.name

        // Puedes realizar otras acciones según tus necesidades, como establecer listeners para eventos de clic, etc.
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // Función para actualizar la lista completa de elementos
    fun updateItems(items: List<Item>) {
        itemList = items
        notifyDataSetChanged()
    }
}