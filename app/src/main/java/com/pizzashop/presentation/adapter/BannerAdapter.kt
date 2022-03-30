package com.pizzashop.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pizzashop.R
import kotlinx.android.synthetic.main.banner_item.view.*

class BannerAdapter(private val context: Context): ListAdapter<Int, BannerAdapter.BannerViewHolder>(
    DiffUtilCallback()
) {
    inner class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.banner_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val imageRes = currentList[position]
        val bImage = ResourcesCompat.getDrawable(context.resources, imageRes, context.theme)

        holder.itemView.cBanner.setImageDrawable(bImage)
    }

        class DiffUtilCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}
