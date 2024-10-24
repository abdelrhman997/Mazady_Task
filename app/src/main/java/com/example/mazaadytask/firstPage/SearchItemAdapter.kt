package com.example.mazaadytask.firstPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.example.core.models.OptionsItem
import com.example.mazaadytask.R

class SearchItemAdapter(
    private var items: ArrayList<OptionsItem>,
    private val onItemClick: (OptionsItem) -> Unit
) : RecyclerView.Adapter<SearchItemAdapter.ViewHolder>(), Filterable {

    var filteredItems: ArrayList<OptionsItem> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_list_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredItems[position]
        holder.bind(item, onItemClick)
    }

    override fun getItemCount(): Int = filteredItems.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: MaterialTextView = itemView.findViewById(R.id.textView)

        fun bind(item: OptionsItem, onItemClick: (OptionsItem) -> Unit) {
            textView.text = item.name
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<OptionsItem>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(items)
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()
                    for (item in items) {
                        if (item.name?.lowercase()?.contains(filterPattern)==true) {
                            filteredList.add(item)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems = results?.values as? ArrayList<OptionsItem> ?: arrayListOf()
                notifyDataSetChanged()
            }
        }
    }
}
