package com.example.wikipedia

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ArticleCardRecyclerAdapter() : RecyclerView.Adapter<CardViewHolder>()
{
    val currentResults : ArrayList<Wikipage> = ArrayList<Wikipage>()


    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        var page = currentResults[position]
        holder?.updateWithPage(page)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        var cardItem = LayoutInflater.from(parent?.context).inflate(R.layout.article_card_item,parent,false)
        return CardViewHolder(cardItem)
    }

}