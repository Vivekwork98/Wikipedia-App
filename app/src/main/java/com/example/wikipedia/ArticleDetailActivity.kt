package com.example.wikipedia

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.article_detail.*
import org.jetbrains.anko.toast



class ArticleDetailActivity : AppCompatActivity()
{
    private var wikiManager : WikiManager? = null
    private var currentPage : Wikipage? = null
    var webview : WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_detail)

        wikiManager = (applicationContext as WikiApplication).wikiManager

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val wikiPageJson = intent.getStringExtra("page")
        currentPage = Gson().fromJson<Wikipage>(wikiPageJson, Wikipage::class.java)

        supportActionBar?.title = currentPage?.title

        webview = findViewById(R.id.article_detail_webView)
        /*
        webview?.webViewClient = object : WebViewClient()
        {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }
        }*/

        webview!!.loadUrl(currentPage!!.fullurl)

        wikiManager?.addHistory(currentPage!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.article_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == android.R.id.home)
        {
            finish()
        }

        else if (item!!.itemId == R.id.action_favourite)
        {
            try
            {
                if (wikiManager!!.getIsFavourite(currentPage!!.pageid!!))
                {
                    wikiManager!!.removeFavourite(currentPage!!.pageid!!)
                    toast("Article removed from favourites")
                }
                else
                {
                    wikiManager!!.addFavourite(currentPage!!)
                    toast("Article added to favourites")
                }
            }
            catch (ex : Exception)
            {
                toast("Unable to update this article")
            }
        }
        return true
    }
}