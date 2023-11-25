package com.example.nearbyapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nearbyapp.R
import com.example.nearbyapp.data.model.Venue


class CityAdapter() :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    var loadMoreData: LoadMoreData? = null

    private val data = mutableListOf<Venue>() // Replace with your data type
    private var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.city_item_layout, parent, false)
        return CityViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setListener(callback: LoadMoreData) {
        loadMoreData = callback
    }

    fun clearData() {
        data.clear()
    }

    fun addData(newData: List<Venue>, loading: Boolean = false, isPaginated: Boolean) {

        data.addAll(newData)
        isLoading = loading
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val currentItem = data[position]
        bind(holder, currentItem)
        // Load more items if the user is near the end of the list
        if (position == data.size - 1 && isLoading) {
            loadMoreData?.onLastItemReached()
        }
    }

    private fun bind(
        holder: CityViewHolder,
        currentItem: Venue
    ) {
        holder.titleTextView.text = currentItem.nameV2
        holder.stateTextView.text = currentItem.address.toString()
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val stateTextView: TextView = itemView.findViewById(R.id.stateTextView)
    }

    interface LoadMoreData {
        fun onLastItemReached()
    }

}