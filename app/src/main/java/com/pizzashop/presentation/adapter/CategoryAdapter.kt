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
import kotlinx.android.synthetic.main.category_item.view.*

class CategoryAdapter(private val context: Context) :
    ListAdapter<CategoryItem, CategoryAdapter.CategoryViewHolder>(
        DiffUtilCallback()
    ) {
    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.category_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val item = currentList[position]
        val activeBackgroundColor = ResourcesCompat.getColor(
            context.resources,
            R.color.activeBackgroundColor,
            context.theme
        )
        val inactiveBackgroundColor = ResourcesCompat.getColor(
            context.resources,
            R.color.inactiveBackgroundColor,
            context.theme
        )
        val activeTextColor =
            ResourcesCompat.getColor(context.resources, R.color.activeTextColor, context.theme)
        val inactiveTextColor =
            ResourcesCompat.getColor(context.resources, R.color.inactiveTextColor, context.theme)

        holder.itemView.cCategory.text = item.category

        if (item.selected) {
            holder.itemView.cCategory.setTextColor(activeTextColor)
            holder.itemView.cCategory.setBackgroundColor(activeBackgroundColor)
        } else {
            holder.itemView.cCategory.setTextColor(inactiveTextColor)
            holder.itemView.cCategory.setBackgroundColor(inactiveBackgroundColor)
        }

        holder.itemView.setOnClickListener {
            val index = currentList.indexOf(item)
            currentList.map { it.copy(selected = false) }
                .toMutableList()
                .also {
                    it[index] = item.copy(selected = true)
                    submitList(it)
                }
            notifyDataSetChanged()
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return (oldItem.category == newItem.category) && (oldItem.selected == newItem.selected)
        }
    }
}