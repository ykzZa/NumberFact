package com.example.testnumbers.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testnumbers.data.search.Search
import com.example.testnumbers.databinding.ItemSearchBinding

class SearchAdapter(private val listener: OnItemClickListener): ListAdapter<Search, SearchAdapter.SearchViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SearchViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                relativeLayoutSearch.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val search = getItem(position)
                        listener.onItemClick(search)
                    }
                }
            }
        }

        fun bind(search: Search) {
            binding.apply {
                textViewSearch.text = search.text
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(song: Search)
    }

    class DiffCallback : DiffUtil.ItemCallback<Search>() {

        override fun areItemsTheSame(oldItem: Search, newItem: Search) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Search, newItem: Search) = oldItem == newItem
    }
}