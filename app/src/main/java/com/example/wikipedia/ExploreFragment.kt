package com.example.wikipedia


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class ExploreFragment : Fragment() {

    private var wikiManager : WikiManager? = null
    var searchCardView : CardView? = null
    var exploreRecycler : RecyclerView? = null
    var adapter : ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()
    var refresher : SwipeRefreshLayout? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater!!.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById<CardView>(R.id.search_card_view)
        exploreRecycler = view.findViewById<RecyclerView>(R.id.explore_article_recycler)
        refresher = view.findViewById<SwipeRefreshLayout>(R.id.refresher)

        searchCardView!!.setOnClickListener{
            val searchIntent = Intent(context,Search::class.java)
            context!!.startActivity(searchIntent)
        }

        exploreRecycler!!.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
            getRandomArticles()
        }

        getRandomArticles()

        return view
    }

    private fun getRandomArticles()
    {

        refresher?.isRefreshing = true

        //try
        //{
            wikiManager?.getRandom(15,{wikiResult ->
                adapter.currentResults.clear()
                adapter.currentResults.addAll(wikiResult.query!!.pages)
                activity!!.runOnUiThread {
                    adapter.notifyDataSetChanged()
                    Log.d("xdfxd","vivek")
                    refresher?.isRefreshing = false
                }
            })
        //}

        /*catch (ex : Exception)
        {
            val builder = activity?.let { AlertDialog.Builder(it) }
            builder?.setMessage(ex.message)?.setTitle("oops")
            val dialog = builder?.create()
            dialog?.show()
        }*/


    }

}
