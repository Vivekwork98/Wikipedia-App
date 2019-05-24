package com.example.wikipedia

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ArticleListItemAdapter()  : RecyclerView.Adapter<ListItemHolder>()
{

    val currentResults : ArrayList<Wikipage> = ArrayList<Wikipage>()


    override fun getItemCount(): Int {
        return  currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {

        var page = currentResults[position]
        holder?.updateWithPage(page)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_list_item,parent,false)
        return ListItemHolder(cardItem)
    }

}
