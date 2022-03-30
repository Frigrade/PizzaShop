package com.pizzashop.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pizzashop.R
import com.pizzashop.domain.entity.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class PizzaAdapter : ListAdapter<Article, PizzaAdapter.ArticleViewHolder>(DiffUtilCallback()) {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = currentList[position]

        holder.itemView.apply {
            Glide.with(this)
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_no_image)
                .into(ivArticleImage)

            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

}

private class DiffUtilCallback: DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}
