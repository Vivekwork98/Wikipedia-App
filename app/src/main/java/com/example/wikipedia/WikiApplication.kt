package com.example.wikipedia

import android.app.Application

class WikiApplication : Application()
{
    private var dbHelper : ArticleDatabaseOpenHelper? = null
    private var favouritesRepository : FavouritesRepository? = null
    private var historyRepository : HistoryRepository? = null
    private var wikiProvider : ArticleDataProvider? = null
    var wikiManager : WikiManager? = null
        private set

    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favouritesRepository = FavouritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!! , favouritesRepository!! , historyRepository!!)

    }
}