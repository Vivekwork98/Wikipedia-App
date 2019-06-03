package com.example.wikipedia

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.article_detail.toolbar

class Search : AppCompatActivity() {

    private var wikiManager : WikiManager? = null
    private var adapter : ArticleListItemAdapter = ArticleListItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        search_results.layoutManager = LinearLayoutManager(this)
        search_results.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home)
        {
            finish()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_menu,menu)

        val searchItem = menu!!.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem!!.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.requestFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String): Boolean {

                wikiManager?.search(query,0,25) { wikiResult ->
                    adapter.currentResults.clear()
                    adapter.currentResults.addAll(wikiResult.query!!.pages)
                    runOnUiThread { adapter.notifyDataSetChanged() }
                }
                println("Updated search")
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}



