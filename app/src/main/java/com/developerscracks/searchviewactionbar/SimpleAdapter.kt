package com.developerscracks.searchviewactionbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter(private var itemList: MutableList<Item>, private val showMenuDelete: (Boolean) -> Unit) : RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder>() {

    private var isEnable = false
    private val itemSelectedList = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val item = itemList[position]
        holder.nameTextView.text = item.name
        holder.ivCheck.visibility = if (item.selected) View.VISIBLE else View.GONE

        holder.itemView.setOnLongClickListener {
            if (!item.selected) {
                selectItem(holder, item, position)
            }
            true
        }

        holder.itemView.setOnClickListener {
            if (item.selected) {
                unselectItem(holder, item, position)
            } else if (isEnable) {
                selectItem(holder, item, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    // Función para actualizar la lista completa de elementos
    fun updateItems(items: List<Item>) {
        itemList = items as MutableList<Item>
        notifyDataSetChanged()
    }
    private fun selectItem(holder: SimpleViewHolder, item: Item, position: Int) {
        isEnable = true
        itemSelectedList.add(position)
        item.selected = true
        holder.ivCheck.visibility = View.VISIBLE
        showMenuDelete(true)
    }

    private fun unselectItem(holder: SimpleViewHolder, item: Item, position: Int) {
        itemSelectedList.removeAll { it == position }
        item.selected = false
        holder.ivCheck.visibility = View.GONE

        if (itemSelectedList.isEmpty()) {
            showMenuDelete(false)
            isEnable = false
        }
    }

    fun getSelectedIndices():List<Int>{
        return itemSelectedList.toList()
    }

    inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val ivCheck: ImageView = itemView.findViewById(R.id.ivCheck)
    }
}