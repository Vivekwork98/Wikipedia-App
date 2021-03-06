package com.example.wikipedia

import com.google.gson.Gson
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select


class FavouritesRepository(val databaseHelper : ArticleDatabaseOpenHelper)
{
    private val TABLE_NAME : String = "Favourites"

    fun addFavourite(page:Wikipage)
    {
        databaseHelper.use {
            insert(TABLE_NAME,
                "id" to page.pageid,
                "title" to page.title,
                "url" to page.fullurl,
                "thumbnailJson" to Gson().toJson(page.thumbnail))
        }
    }

    fun removeFavouriteById(pageId : Int)
    {
        databaseHelper.use {
            delete(TABLE_NAME,"id = {pageId}","pageId" to pageId)
        }
    }

    fun isArticleFavourite(pageId: Int) : Boolean
    {
        var pages = getAllFavourites()
        return pages.any{page ->
            page.pageid == pageId
        }
    }

    fun getAllFavourites() : ArrayList<Wikipage>
    {
        var pages = ArrayList<Wikipage>()

        val articleRowParser = rowParser {id : Int , title : String , url : String ,thumbnailJson : String ->

            val page = Wikipage()
            page.title = title
            page.pageid = id
            page.fullurl = url
            page.thumbnail = Gson().fromJson(thumbnailJson, WikiThumbnail::class.java)

            pages.add(page)
        }

        databaseHelper.use { select(TABLE_NAME).parseList(articleRowParser) }
        return pages
    }
}