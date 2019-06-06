package com.example.wikipedia


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.EditText
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


class HistoryFragment : Fragment() {

    private var wikiManager : WikiManager? = null
    var historyRecycler: RecyclerView? = null
    private val adapter : ArticleListItemAdapter = ArticleListItemAdapter()
    private var currentPage : Wikipage? = null
    private var pagelist : ArrayList<String> = ArrayList()


    init {
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_history, container, false)
        historyRecycler = view.findViewById<RecyclerView>(R.id.history_article_recycler)
        historyRecycler!!.layoutManager = LinearLayoutManager(context)
        historyRecycler!!.adapter = adapter
        return view

    }

    override fun onResume() {
        super.onResume()

        doAsync {

            val history = wikiManager?.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(history as ArrayList<Wikipage>)
            activity!!.runOnUiThread { adapter.notifyDataSetChanged() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater!!.inflate(R.menu.history_menu, menu)

        val searchItem = menu!!.findItem(R.id.menu_search)
        if(searchItem != null) {
            val searchView = searchItem.actionView as SearchView
            val editext = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text)
            editext.hint = "Search here..."

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    pagelist.clear()
                    if (newText!!.isNotEmpty()) {

                        val search = newText.toLowerCase()
                        currentPage?.title?.forEach {
                            Log.d("dsfs","textiy")
                            if (currentPage!!.title!!.toLowerCase().contains(search)) {
                                pagelist.add(currentPage?.title.toString())
                            }
                        }
                    } else {
                        pagelist.addAll(listOf(currentPage.toString()))
                    }
                    historyRecycler!!.adapter!!.notifyDataSetChanged()
                    return true
                }

            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.action_clear_history) {
            activity!!.alert ("Are you sure you want to clear your history","Confirm")
            {
                yesButton {
                    adapter.currentResults.clear()
                    doAsync {
                        wikiManager!!.clearHistory()
                    }
                    activity!!.runOnUiThread { adapter.notifyDataSetChanged() }
                }
                noButton {

                }
            }.show()
        }
            return true
        }

}