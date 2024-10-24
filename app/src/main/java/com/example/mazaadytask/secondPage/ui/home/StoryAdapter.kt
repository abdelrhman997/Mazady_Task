package com.example.mazaadytask.secondPage.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mazaadytask.R

class StoryAdapter(private val items: List<Int>) :
    RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        // Bind your slide content here
        // For example: holder.itemView.findViewById<ImageView>(R.id.imageView).setImageResource(items[position])
    }

    override fun getItemCount(): Int = items.size
}
